# Gaia AI Agent Java - Java AI Agent 项目模板（多模块版）

基于 Spring Boot + spring-ai-alibaba 的 Java AI Agent 平台模板，采用 Maven 多模块架构。

## 特性

- **Maven 多模块架构**：清晰的模块划分，职责分明
- **多种 Agent 模式**：支持 ReAct、Plan-Execute 等多种 Agent 实现模式
- **工具系统**：完整的工具注册、调用和管理机制
- **记忆管理**：支持对话历史和上下文管理
- **工作流引擎**：支持复杂的多步骤任务编排
- **通义千问集成**：基于 spring-ai-alibaba 深度集成阿里云通义千问

## 技术栈

- **框架**：Spring Boot 3.2.12
- **AI 框架**：spring-ai-alibaba 1.0.0-M2
- **数据库**：MySQL 8.0 + MyBatis-Plus 3.5.5
- **缓存**：Redis
- **消息队列**：RocketMQ
- **工具库**：Lombok、Hutool、Fastjson2

## 模块结构

```
gaia-agent/                          # 父项目
├── gaia-agent-common/               # 通用组件模块
│   └── src/main/java/.../agent/common/
│       ├── exception/               # 异常处理
│       ├── util/                    # 工具类
│       ├── constant/                # 常量定义
│       └── response/                # 响应封装
│
├── gaia-agent-infra/                # 基础设施模块
│   └── src/main/java/.../agent/
│       ├── core/                    # 核心业务组件
│       │   ├── agent/               # Agent 核心（Agent、Executor、Policy等）
│       │   ├── tool/                # 工具系统（Tool、Registry等）
│       │   ├── memory/              # 记忆管理（Memory、Context等）
│       │   ├── workflow/            # 工作流（Workflow、Node等）
│       │   └── prompt/              # 提示词模板
│       ├── service/                 # 业务服务
│       │   ├── agent/               # Agent 服务
│       │   ├── workflow/            # 工作流服务
│       │   └── session/             # 会话服务
│       └── infra/                   # 基础设施
│           ├── ai/                  # AI 客户端（Qwen、Embedding等）
│           ├── persistence/         # 持久化（Entity、Mapper、Repository）
│           ├── cache/               # 缓存服务
│           ├── mq/                  # 消息队列
│           └── config/              # 配置类
│
└── gaia-agent-app/                  # 应用启动模块
    ├── src/main/java/.../agent/
    │   ├── AiAgentApplication.java  # 主应用类
    │   └── controller/              # REST API 控制器
    │       ├── agent/               # Agent 相关接口
    │       └── dto/                 # 数据传输对象
    └── src/main/resources/
        └── application.yml          # 应用配置
```

### 模块依赖关系

```
gaia-agent-app (应用层)
    └── depends on gaia-agent-infra (基础设施层)
            └── depends on gaia-agent-common (通用层)
```

## 模块说明

### 1. gaia-agent-common（通用组件模块）

**职责**：提供全局通用组件，被其他所有模块依赖。

**包含内容**：
- 异常处理：BusinessException、GlobalExceptionHandler
- 响应封装：Result 统一响应对象
- 工具类：JsonUtil 等
- 常量定义：Constants

**特点**：
- 无业务逻辑
- 不依赖其他内部模块
- 只依赖最基础的第三方库（Lombok、Fastjson2）

### 2. gaia-agent-infra（基础设施模块）

**职责**：提供所有业务逻辑和基础设施支持。

**包含内容**：

**core 包**（核心业务）：
- Agent 系统：Agent 抽象类、ReAct/Plan-Execute 实现、执行器、策略配置
- Tool 系统：工具接口、注册中心、调用封装
- Memory 系统：对话记忆、上下文窗口管理
- Workflow 系统：工作流定义、执行器、节点
- Prompt 系统：提示词模板

**service 包**（业务服务）：
- AgentService：Agent 执行服务
- WorkflowService：工作流服务
- SessionService：会话管理服务

**infra 包**（基础设施）：
- AI 客户端：QwenChatClient、QwenEmbeddingClient
- 持久化：Entity、Mapper、Repository
- 缓存：RedisService
- 消息队列：RocketMqProducer
- 配置：AiConfig、RedisConfig、SecurityConfig

**特点**：
- 包含所有核心业务逻辑
- 集成外部服务（AI、数据库、缓存等）
- 依赖 gaia-agent-common

### 3. gaia-agent-app（应用启动模块）

**职责**：提供应用入口和对外API接口。

**包含内容**：
- 主应用类：AiAgentApplication
- Controller：AgentController、ChatController、WorkflowController
- DTO：请求和响应对象
- 配置文件：application.yml

**特点**：
- 应用启动和配置
- REST API 接口定义
- 依赖 gaia-agent-infra（间接依赖 common）
- 打包为可执行 JAR

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- RocketMQ 4.9+ (可选)

### 2. 构建项目

```bash
# 在项目根目录执行
mvn clean install
```

### 3. 配置应用

修改 `gaia-agent-app/src/main/resources/application.yml`：

```yaml
spring:
  ai:
    qwen:
      api-key: your-qwen-api-key  # 通义千问 API Key
  
  datasource:
    url: jdbc:mysql://localhost:3306/ai_agent
    username: root
    password: your-password
  
  redis:
    host: localhost
    port: 6379
```

### 4. 启动应用

```bash
# 方式1：Maven 运行
cd gaia-agent-app
mvn spring-boot:run

# 方式2：JAR 运行
java -jar gaia-agent-app/target/gaia-agent-app-1.0.0-SNAPSHOT.jar
```

### 5. 测试接口

```bash
# 执行 Agent
curl -X POST http://localhost:8080/api/v1/agent/execute \
  -H "Content-Type: application/json" \
  -d '{
    "agentId": "react-agent",
    "input": "今天北京天气怎么样？",
    "maxIterations": 10
  }'

# 发送聊天消息
curl -X POST http://localhost:8080/api/v1/chat/send \
  -H "Content-Type: application/json" \
  -d '{
    "message": "你好，请介绍一下自己",
    "sessionId": "test-session"
  }'
```

## 开发指南

### 添加新模块

如需添加新模块（如 gaia-agent-tool），按以下步骤：

1. 在父 pom.xml 中添加 module
2. 创建模块目录和 pom.xml
3. 在 dependencyManagement 中声明依赖
4. 在需要使用的模块中添加依赖

### 自定义 Agent

在 `gaia-agent-infra` 模块中扩展 Agent：

```java
package github.grit.gaia.agent.core.agent;

public class CustomAgent extends Agent {
    @Override
    public String execute(AgentContext context) {
        // 实现自定义执行逻辑
        return "执行结果";
    }
    
    // 实现其他抽象方法...
}
```

### 自定义 Tool

在 `gaia-agent-infra` 模块中实现 Tool：

```java
package github.grit.gaia.agent.core.tool;

@Component
public class CustomTool implements Tool {
    
    @Autowired
    private ToolRegistry toolRegistry;
    
    @PostConstruct
    public void init() {
        toolRegistry.register(this);
    }
    
    @Override
    public String getName() {
        return "custom_tool";
    }
    
    @Override
    public ToolResult execute(Map<String, Object> params) {
        // 实现工具逻辑
        return ToolResult.builder()
                .success(true)
                .result("执行成功")
                .build();
    }
    
    // 实现其他接口方法...
}
```

### 添加 REST API

在 `gaia-agent-app` 模块中添加 Controller：

```java
package github.grit.gaia.agent.controller;

@RestController
@RequestMapping("/api/v1/custom")
public class CustomController {
    
    @PostMapping("/action")
    public Result<?> customAction(@RequestBody CustomRequest request) {
        // 实现接口逻辑
        return Result.success();
    }
}
```

## 模块职责边界

### ✅ 正确的依赖方向

- app → infra → common ✓
- infra 可以访问所有 core、service、infra 的类 ✓
- common 不依赖任何内部模块 ✓

### ❌ 错误的依赖方向

- common → infra/app ✗
- infra → app ✗
- 跨模块循环依赖 ✗

## 最佳实践

1. **模块隔离**：保持模块间的清晰边界，避免循环依赖
2. **接口优先**：在 infra 中定义接口，在 app 中使用
3. **配置分离**：配置文件放在 app 模块，便于环境切换
4. **测试完整**：每个模块编写独立的单元测试
5. **文档更新**：及时更新模块说明和 API 文档

## 常见问题

### Q: 为什么要拆分模块？
A: 清晰的模块划分有助于：
- 职责分明，代码更易维护
- 复用性强，可独立打包使用
- 团队协作更高效
- 便于做微服务拆分

### Q: common 模块应该放什么？
A: 只放通用工具类、常量、异常等无业务逻辑的代码。

### Q: 为什么 core、service 都在 infra 模块？
A: 对于 AI Agent 项目，业务逻辑相对简单，合并到 infra 可以简化结构。如果业务复杂，可以进一步拆分。

### Q: 如何选择模块粒度？
A: 根据项目规模和团队大小调整。小项目可以更少模块，大项目可以更细粒度拆分。

## 贡献指南

欢迎提交 Issue 和 Pull Request！

## 许可证

MIT License
