# Gaia 项目模板集合

本仓库包含多个企业级项目模板，旨在为不同类型的应用开发提供标准化的项目结构和最佳实践。

## 项目模板列表

### 1. gaia-backend-ddd - 后端DDD企业级项目模板

基于领域驱动设计(DDD)的Java后端项目模板，采用Maven多模块架构。

**技术栈：**

- Java
- Spring Boot
- MyBatis-Plus
- Maven多模块

**模块结构：**

- `gaia-interfaces` - 接口层（API控制器）
- `gaia-application` - 应用服务层
- `gaia-domain` - 领域层（核心业务逻辑）
- `gaia-infrastructure` - 基础设施层（数据持久化、外部服务）
- `gaia-gateway` - 网关服务
- `gaia-common` - 公共组件
- `gaia-demo` - 示例代码

**详细文档：** 请查看 [gaia-backend-ddd/README.md](./gaia-backend-ddd/README.md)

**部署配置：** 请查看 [gaia-backend-ddd/docker/](./gaia-backend-ddd/docker/)

---

### 2. gaia-agent - Java AI Agent 项目模板

基于 Spring Boot + spring-ai-alibaba 的 Java AI Agent 平台模板，专为 AI Agent 应用开发设计。

**技术栈：**

- Spring Boot 3.2.0
- spring-ai-alibaba 1.0.0-M2（通义千问）
- MyBatis-Plus 3.5.5
- Redis + RocketMQ

**核心特性：**

- 支持 ReAct、Plan-Execute 等多种 Agent 模式
- 完整的工具系统（Tool Registry & Execution）
- 对话记忆管理（Conversation Memory）
- 工作流编排引擎（Workflow Engine）
- 提示词模板系统（Prompt Template）

**项目结构：**

- `controller` - REST API 控制器
- `service` - 业务服务层
- `core` - Agent 核心组件（Agent、Tool、Memory、Workflow）
- `infra` - 基础设施（AI客户端、持久化、缓存、消息队列）
- `common` - 通用组件

**详细文档：** 请查看 [gaia-ai-agent-java/README.md](gaia-agent/README.md)

---

## 使用说明

1. 选择适合你需求的项目模板
2. 复制对应的模板目录到你的工作空间
3. 根据项目模板中的 README.md 进行配置和开发
4. 根据实际业务需求调整模块结构

## 贡献指南

欢迎提交新的项目模板或改进现有模板。请确保：

- 遵循相应的架构模式和最佳实践
- 提供完整的文档说明
- 包含必要的配置示例

## 许可证

请查看 [LICENSE](./LICENSE) 文件了解详情。
