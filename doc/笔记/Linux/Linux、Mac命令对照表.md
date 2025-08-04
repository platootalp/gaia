以下是 **Linux 与 macOS（Mac）常用命令对照表**，以表格形式呈现，涵盖文件操作、系统管理、网络诊断等常用场景。虽然两者都基于 Unix，但部分命令在默认行为、参数支持或工具名称上存在差异。

---

### 🖥️ Linux 与 Mac 常用命令对照表

| 功能 | Linux 命令 | macOS (Mac) 命令 | 备注 |
|------|------------|------------------|------|
| **列出文件** | `ls -l` | `ls -l` | 基本相同，但 GNU `ls`（Linux）功能更丰富，macOS 使用 BSD `ls` |
| **分页查看文件** | `less file.txt` 或 `more file.txt` | `less file.txt` 或 `more file.txt` | 两者都支持 |
| **显示文件内容** | `cat file.txt` | `cat file.txt` | 完全相同 |
| **复制文件** | `cp file.txt /path/` | `cp file.txt /path/` | 行为一致 |
| **移动/重命名** | `mv old.txt new.txt` | `mv old.txt new.txt` | 相同 |
| **删除文件** | `rm file.txt` | `rm file.txt` | 相同 |
| **递归删除目录** | `rm -rf dir/` | `rm -rf dir/` | 用法一致 |
| **创建目录** | `mkdir dir` | `mkdir dir` | 相同 |
| **查看当前路径** | `pwd` | `pwd` | 相同 |
| **切换目录** | `cd /path` | `cd /path` | 相同 |
| **查找文件** | `find /path -name "file.txt"` | `find /path -name "file.txt"` | 基本相同，但语法细节略有差异（macOS 为 BSD find） |
| **按名查找（快速）** | `locate file.txt` | `mdfind "file.txt"` | Linux 用 `locate`，macOS 用 Spotlight 的 `mdfind` |
| **搜索文本内容** | `grep "text" file.txt` | `grep "text" file.txt` | 相同，但 macOS 的 grep 是 BSD 版本 |
| **全局正则搜索** | `grep -r "text" /dir` | `grep -r "text" /dir` | 支持，但 macOS 某些旧版本需 `--include` 等参数不同 |
| **打开文件（默认程序）** | `xdg-open file.pdf` | `open file.pdf` | Linux 用 `xdg-open`，macOS 用 `open` ✅ |
| **打开目录** | `xdg-open .` | `open .` | 打开当前目录的图形界面 |
| **查看进程** | `ps aux` 或 `ps -ef` | `ps aux` 或 `ps -ef` | 基本相同 |
| **实时监控进程** | `top` 或 `htop` | `top` | macOS 自带 `top`，`htop` 需手动安装 |
| **终止进程** | `kill PID` 或 `kill -9 PID` | `kill PID` 或 `kill -9 PID` | 相同 |
| **查看网络连接** | `netstat -tuln` | `netstat -an` | macOS 的 `netstat` 选项较少，建议用 `lsof` 替代 |
| | `ss -tuln` | 不支持 | `ss` 是 Linux 特有（替代 netstat） |
| **查看监听端口** | `lsof -i :8080` | `lsof -i :8080` | 两者都支持 `lsof` |
| **测试网络连通性** | `ping google.com` | `ping google.com` | 相同，但 macOS 默认持续 ping，Linux 可用 `-c 4` 控制次数 |
| **查看 IP 地址** | `ip addr` 或 `ifconfig` | `ifconfig` | Linux 推荐 `ip`，macOS 仍用 `ifconfig` ✅ |
| **查看 MAC 地址映射** | `arp -a` | `arp -a` | 输出格式略有不同，但功能相同 |
| **清屏** | `clear` 或 `Ctrl+L` | `clear` 或 `Ctrl+L` | 相同 |
| **历史命令** | `history` | `history` | 相同 |
| **编辑文本** | `vim file.txt` 或 `nano file.txt` | `vim file.txt` 或 `nano file.txt` | 两者都支持，macOS 自带 vim |
| **压缩文件（tar.gz）** | `tar -czf archive.tar.gz dir/` | `tar -czf archive.tar.gz dir/` | 相同 |
| **解压文件** | `tar -xzf archive.tar.gz` | `tar -xzf archive.tar.gz` | 相同 |
| **查看磁盘使用** | `df -h` | `df -h` | 相同 |
| **查看目录大小** | `du -sh dir/` | `du -sh dir/` | 相同，但 macOS 的 `du` 对符号链接处理略有不同 |

---

### 🔧 差异与建议

| 场景 | 说明 |
|------|------|
| **安装增强工具（macOS）** | 推荐安装 [Homebrew](https://brew.sh/)，然后使用 `brew install coreutils` 获取 GNU 版本命令（如 `gls`, `gfind`, `gcp` 等），更接近 Linux 行为 |
| **ls 颜色支持** | Linux 默认有颜色，macOS 需手动启用：在 `.zshrc` 中添加 `export CLICOLOR=1` |
| **find 命令差异** | macOS 的 `find` 是 BSD 实现，不支持某些 GNU 选项（如 `-printf`），可用 `brew install findutils` 安装 `gfind` |
| **默认 Shell** | macOS Catalina 及以后使用 `zsh`，Linux 多数仍用 `bash`，脚本兼容性需注意 |

---

### ✅ 总结

- ✅ **绝大多数基础命令在 Linux 和 macOS 上是相同的**。
- ⚠️ **差异主要体现在命令的扩展参数、默认版本（GNU vs BSD）和系统工具**。
- 💡 **建议 macOS 用户使用 Homebrew 安装 GNU 核心工具**，以获得与 Linux 更一致的体验。

> 📌 提示：可通过 `man command` 查看具体命令的手册，确认参数支持情况。

如需导出为 CSV 或 Markdown 表格用于文档，也可提供。