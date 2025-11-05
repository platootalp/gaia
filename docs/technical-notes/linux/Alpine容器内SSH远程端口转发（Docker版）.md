# ✅ **SOP B：Docker 自动化部署版（Dockerfile + 挂载公钥）**

> **适用场景**：通过 `Dockerfile` 构建镜像或 `docker run` 快速部署支持 SSH 和远程转发的 Alpine 容器。

---

## **1. Dockerfile（推荐方式）**

```dockerfile
FROM alpine:latest

# 安装 OpenSSH 并生成主机密钥
RUN apk add --no-cache openssh openssh-client && \
    ssh-keygen -A

# 创建 .ssh 目录
RUN mkdir -p /root/.ssh && chmod 700 /root/.ssh

# 复制配置文件（可选）
COPY sshd_config /etc/ssh/sshd_config

# 启动脚本
COPY start.sh /start.sh
RUN chmod +x /start.sh

CMD ["/start.sh"]
```

---

## **2. `start.sh` 启动脚本**

```bash
#!/bin/sh
# 修复权限（挂载时可能丢失）
chmod 700 /root/.ssh 2>/dev/null || true
chmod 600 /root/.ssh/authorized_keys 2>/dev/null || true
chown -R root:root /root/.ssh 2>/dev/null || true

# 启动 SSH 服务（前台 + 日志输出）
exec /usr/sbin/sshd -D -e -f /etc/ssh/sshd_config
```

> ✅ 脚本确保挂载的公钥权限正确。

---

## **3. `sshd_config` 示例**

```ini
Port 22
ListenAddress 0.0.0.0

PasswordAuthentication no
PubkeyAuthentication yes
PermitRootLogin prohibit-password

AllowTcpForwarding yes
GatewayPorts yes
ClientAliveInterval 60
ClientAliveCountMax 3
```

---

## **4. 构建镜像**

```bash
docker build -t alpine-ssh-tunnel .
```

---

## **5. 运行容器（推荐：挂载公钥）**

```bash
docker run -d \
  --name ssh-tunnel \
  -p 2222:22 \
  -p 9001:9001 \
  -v ~/.ssh/id_ed25519.pub:/root/.ssh/authorized_keys:ro \
  alpine-ssh-tunnel
```

> ✅ 公钥动态注入，无需重建镜像  
> ✅ `:ro` 只读挂载更安全

---

## **6. 一键运行（无镜像构建）**

```bash
docker run -d \
  --name alpine-ssh \
  -p 2222:22 \
  -p 9001:9001 \
  -v ~/.ssh/id_ed25519.pub:/root/.ssh/authorized_keys:ro \
  alpine \
  sh -c "
    apk add --no-cache openssh openssh-client && \
    ssh-keygen -A && \
    chmod 600 /root/.ssh/authorized_keys && \
    /usr/sbin/sshd -D -e -f /etc/ssh/sshd_config
  "
```

> ✅ 快速测试，无需 Dockerfile

---

## **7. 建立远程端口转发（本地执行）**

```bash
ssh -i ~/.ssh/id_ed25519 \
    -R 0.0.0.0:9001:localhost:9000 \
    -N -f \
    -p 2222 \
    root@<宿主机IP>
```

---

## **8. 验证**

```bash
# 查看容器日志
docker logs ssh-tunnel

# 进入容器检查监听
docker exec ssh-tunnel netstat -tulnp | grep 9001
```

---

## **9. 健康检查（可选）**

在 `docker-compose.yml` 中添加：

```yaml
healthcheck:
  test: [ "CMD-SHELL", "ss -ltnp | grep 9001 || exit 1" ]
  interval: 30s
  timeout: 10s
  retries: 3
```

---

## **10. 生产建议**

| 项目             | 建议                         |
|----------------|----------------------------|
| 🔐 公钥注入        | 使用 `-v` 挂载，避免写入镜像          |
| 🧱 安全加固        | 禁用密码、最小权限、只读挂载             |
| 🔄 自动重启        | `--restart unless-stopped` |
| 📊 日志          | 使用 `-e`，结合 `docker logs`   |
| 🚫 不要暴露 22 到公网 | 通过跳板机或内网访问                 |

---

## ✅ **总结：两个 SOP 的分工**

| SOP              | 用途                   | 优点         | 适用场景              |
|------------------|----------------------|------------|-------------------|
| **A：手动部署**       | 在已有 Alpine 环境中配置 SSH | 灵活、无需构建    | 临时调试、已有容器、物理机     |
| **B：Docker 自动化** | 构建标准化镜像或一键运行         | 可复用、易维护、安全 | CI/CD、内网穿透服务、生产部署 |

---

✅ **两个 SOP 均支持：**

- 密钥登录（禁用密码）
- 远程端口转发 `-R`
- `GatewayPorts yes`
- 容器持久化运行
- 故障排查指南

可直接用于：

- 内网穿透
- CI/CD 回连构建机
- 本地服务暴露到测试环境
- 安全隧道代理
