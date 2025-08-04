# ✅ **SOP A：Alpine 容器 SSH 免密登录 + 远程端口转发 —— 一体化部署脚本**

> **适用场景**：已有运行中的 Alpine 容器，手动配置 SSH 密钥登录与远程端口转发（无 systemd）

---

## 📦 文件一：`setup-sshd.sh`（在容器内执行）

```bash
#!/bin/sh
set -e

echo "🚀 开始配置 Alpine SSH 服务..."

# 1. 安装 OpenSSH（如未安装）
apk update && apk add --no-cache openssh openssh-client

# 2. 生成主机密钥
echo "🔑 生成 SSH 主机密钥..."
ssh-keygen -A

# 3. 备份并配置 sshd_config
echo "⚙️ 配置 /etc/ssh/sshd_config..."
cat > /etc/ssh/sshd_config << 'EOF'
Port 22
ListenAddress 0.0.0.0

PasswordAuthentication no
PermitEmptyPasswords no
PubkeyAuthentication yes

PermitRootLogin prohibit-password

AllowTcpForwarding yes
GatewayPorts yes
ClientAliveInterval 60
ClientAliveCountMax 3

# 可选：调试时启用
# LogLevel DEBUG3
EOF

# 4. 创建 .ssh 目录（用于后续注入公钥）
mkdir -p /root/.ssh
chmod 700 /root/.ssh

# 5. 提示用户注入公钥
echo "
✅ 基础配置完成！请执行以下任一操作注入公钥：

🔧 方法一（推荐）：从宿主机使用 ssh-copy-id
    ssh-copy-id -i ~/.ssh/id_ed25519.pub -p 22 root@<容器宿主IP>

🔧 方法二：手动复制公钥后运行以下命令（替换 YOUR_PUBKEY）：
    echo 'ssh-ed25519 AAAAC3NzaC...' >> /root/.ssh/authorized_keys
    chmod 600 /root/.ssh/authorized_keys
    chown -R root:root /root/.ssh

✅ 完成后，启动 SSH 服务：
    /usr/sbin/sshd -D -e -f /etc/ssh/sshd_config
"

# 可选：临时开启密码登录（仅首次配置，使用后务必关闭！）
ENABLE_PASSWORD_LOGIN=false
if [ "$ENABLE_PASSWORD_LOGIN" = "true" ]; then
    echo "⚠️ 临时启用密码登录，请尽快设置密码..."
    passwd root
    sed -i 's/#\?PasswordAuthentication.*/PasswordAuthentication yes/' /etc/ssh/sshd_config
    sed -i 's/#\?PermitRootLogin.*/PermitRootLogin yes/' /etc/ssh/sshd_config
    echo "💡 密码设置完成，稍后请关闭 PasswordAuthentication"
fi
```

## 🖥️ 文件二：宿主机操作 —— 注入公钥（任选其一）

### ✅ 推荐方式：使用 `ssh-copy-id`

```bash
# 替换 IP 为容器所在主机的 IP（如虚拟机、宿主机）
ssh-copy-id -i /Users/lijunyi/.ssh/linux/id_ed25519.pub -p 22 root@remote_ip
```

> ✅ 成功后会提示 `Number of key(s) added: 1`

---

### ✅ 备选方式：手动注入（适用于无法使用 ssh-copy-id）

```bash
# 1. 查看公钥内容并复制
cat /Users/lijunyi/.ssh/linux/id_ed25519.pub
```

进入容器后执行：

```bash
# 在容器内执行（替换 YOUR_PUBKEY 为实际内容）
echo "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIGf..." >> /root/.ssh/authorized_keys

# 设置权限
chmod 700 /root/.ssh
chmod 600 /root/.ssh/authorized_keys
chown -R root:root /root/.ssh
```

---

## ▶️ 文件三：启动 SSH 服务（在容器内执行）

```bash
# 杀掉旧进程（如有）
killall sshd || true

# 启动 SSH（前台运行，适合容器主进程）
/usr/sbin/sshd -D -e -f /etc/ssh/sshd_config
```

> ✅ 若使用 `docker run`，建议将此命令作为 `CMD`

---

## 🌐 文件四：本地发起远程端口转发

```bash
# 在本地终端执行
ssh -i /Users/lijunyi/.ssh/linux/id_ed25519 \
    -R 0.0.0.0:9001:localhost:9000 \
    -N -f \
    -p 22 \
    root@10.71.40.249
```

- `9001`：容器监听端口
- `9000`：本地服务端口（如 Web 服务）
- `-N -f`：后台静默运行，仅转发

> ✅ 效果：外部访问 `http://10.71.40.249:9001` → 转发到你本地 `localhost:9000`

---

## 🔍 文件五：验证端口转发是否生效

### 在容器内检查监听：

```bash
netstat -tulnp | grep 9001
# 或
ss -ltnp | grep 9001
```

预期输出：

```
tcp 0 0 0.0.0.0:9001 0.0.0.0:* LISTEN 12345/sshd
```

### 从外部测试访问：

```bash
curl http://10.71.40.249:9001
```

---

## 🛠️ 故障排查速查表（容器内或本地）

| 问题                              | 命令/解决方案                                                        |
|---------------------------------|----------------------------------------------------------------|
| `Permission denied (publickey)` | 检查 `/root/.ssh/authorized_keys` 是否存在、权限 `600`                  |
| `bad ownership or modes`        | `chmod 700 /root/.ssh && chmod 600 /root/.ssh/authorized_keys` |
| `no hostkeys available`         | 补执行 `ssh-keygen -A`                                            |
| `Address already in use`        | `killall sshd` 后重启                                             |
| 容器启动后退出                         | 确保 `sshd` 使用 `-D` 前台运行                                         |
| 外部无法访问 `9001`                   | 确认 `GatewayPorts yes` 且 `docker -p 9001:9001`                  |

---

## ✅ 最终核心命令汇总（一键复制）

### 1. 容器内：初始化 SSH

```bash
apk update && apk add --no-cache openssh openssh-client && ssh-keygen -A
```

### 2. 容器内：配置 sshd_config

```bash
cat > /etc/ssh/sshd_config << 'EOF'
Port 22
ListenAddress 0.0.0.0
PasswordAuthentication no
PubkeyAuthentication yes
PermitRootLogin prohibit-password
AllowTcpForwarding yes
GatewayPorts yes
ClientAliveInterval 60
ClientAliveCountMax 3
EOF
```

### 3. 容器内：准备 .ssh 目录

```bash
mkdir -p /root/.ssh && chmod 700 /root/.ssh
```

### 4. 宿主机：注入公钥

```bash
ssh-copy-id -i /Users/lijunyi/.ssh/linux/id_ed25519.pub -p 22 root@remote_ip
```

### 5. 容器内：启动 SSH 服务

```bash
killall sshd || true
/usr/sbin/sshd -D -e -f /etc/ssh/sshd_config
```

### 6. 本地：建立远程转发

```bash
ssh -i /Users/lijunyi/.ssh/linux/id_ed25519 -R 0.0.0.0:9001:localhost:9000 -N -f -p 22 root@remote_ip
```

### 7. 验证

```bash
# 容器内
netstat -tulnp | grep 9001

# 本地或外部
curl http://remote_ip:port
```

---

## 📌 核心要点总结

| 项目                    | 要求                                   |
|-----------------------|--------------------------------------|
| 🔐 公钥注入               | 推荐 `ssh-copy-id`，手动需注意权限             |
| 📁 权限设置               | `.ssh` `700`，`authorized_keys` `600` |
| 🧱 SSH 前台运行           | 必须使用 `-D` 防止容器退出                     |
| 🔄 `GatewayPorts yes` | 否则 `-R 0.0.0.0:port` 无效              |
| 🛠️ 调试日志              | 加 `-e` 输出日志，便于 `docker logs` 查看      |
