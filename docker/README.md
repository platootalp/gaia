# Gaia Platform - 本地 & 生产环境部署指南

本项目基于 Docker Compose
构建支持本地开发与生产部署的多容器服务环境，服务包括：MySQL、Redis、Nacos、MinIO、Sentinel、Prometheus、Grafana、SkyWalking、Elasticsearch
等。

---

## 📁 项目结构说明

```

.
├── docker-compose.yml              # 通用服务定义（镜像、网络、基础结构）
├── docker-compose.override.yml     # 开发环境配置（挂载、本地端口）
├── docker-compose.prod.yml         # 生产环境配置（命名卷、健康检查）
├── .env                            # 可选环境变量（建议在实际部署中配置）
├── deploy.sh                       # 一键部署脚本（支持 dev / prod）
└── README.md                       # 当前文件

````

---

## 🚀 快速启动

### ✅ 开发环境

```bash
./deploy.sh dev
````

等价于：

```bash
docker-compose up -d
```

适用于本地调试，挂载数据目录，端口全部映射。

---

### 🏗️ 生产环境

```bash
./deploy.sh prod
```

等价于：

```bash
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

使用命名卷存储数据，配置重启策略、健康检查，更适合部署上线。

---

## 🧼 清理所有容器与数据（谨慎操作）

```bash
./deploy.sh clean
```

---

## 📝 其他说明

* 所有服务默认运行于 `gaia-net` 网络中
* 默认账号密码等请按需修改 `.env` 文件或 `deploy.sh` 中变量
* 生产部署建议搭配 Nginx / Traefik 做反向代理 + HTTPS

```