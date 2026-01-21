# 内置工具说明

本目录包含了 Agent 框架的内置工具集合，这些工具会在应用启动时自动注册到 `ToolManager` 中。

## 工具列表

### 1. CalculatorTool (计算器工具)

**工具名称**: `calculator`

**功能描述**: 执行基本的数学计算，支持加减乘除四则运算。

**输入参数**:
- `expression` (String, 必填): 数学表达式，例如 `"2 + 2"`, `"10 * 5"`, `"100 / 4"`

**输出结果**:
```json
{
  "success": true,
  "result": "30",
  "error": null
}
```

**使用示例**:
```java
CalculatorTool.Request request = new CalculatorTool.Request("2 + 2 * 5");
CalculatorTool.Response response = calculatorTool.apply(request);
// result: "12"
```

**注意事项**:
- 支持基本的四则运算：`+`, `-`, `*`, `/`
- 遵循运算优先级（先乘除后加减）
- 支持负数
- 表达式中的空格会被自动忽略

---

### 2. DateTimeTool (日期时间工具)

**工具名称**: `datetime`

**功能描述**: 获取当前日期和时间信息，支持指定时区和格式。

**输入参数**:
- `timezone` (String, 可选): 时区，例如 `"Asia/Shanghai"`, `"America/New_York"`。默认为系统时区
- `format` (String, 可选): 日期格式，例如 `"yyyy-MM-dd HH:mm:ss"`, `"yyyy/MM/dd"`。默认为 `"yyyy-MM-dd HH:mm:ss"`

**输出结果**:
```json
{
  "success": true,
  "currentDateTime": "2026-01-21 14:30:00",
  "timestamp": "1737445800000",
  "timezone": "Asia/Shanghai",
  "error": null
}
```

**使用示例**:
```java
DateTimeTool.Request request = new DateTimeTool.Request("Asia/Shanghai", "yyyy-MM-dd HH:mm:ss");
DateTimeTool.Response response = dateTimeTool.apply(request);
// currentDateTime: "2026-01-21 14:30:00"
```

---

### 3. StringUtilTool (字符串处理工具)

**工具名称**: `string_util`

**功能描述**: 提供常用的字符串操作功能。

**输入参数**:
- `text` (String, 必填): 要处理的文本内容
- `operation` (String, 必填): 操作类型
  - `"uppercase"`: 转换为大写
  - `"lowercase"`: 转换为小写
  - `"reverse"`: 反转字符串
  - `"length"`: 获取字符串长度
  - `"trim"`: 去除首尾空格

**输出结果**:
```json
{
  "success": true,
  "result": "HELLO WORLD",
  "error": null
}
```

**使用示例**:
```java
StringUtilTool.Request request = new StringUtilTool.Request("hello world", "uppercase");
StringUtilTool.Response response = stringUtilTool.apply(request);
// result: "HELLO WORLD"
```

---

### 4. RandomTool (随机数生成工具)

**工具名称**: `random`

**功能描述**: 生成随机数、UUID或布尔值。

**输入参数**:
- `type` (String, 可选): 生成类型，默认为 `"number"`
  - `"number"`: 生成随机整数
  - `"uuid"`: 生成 UUID
  - `"boolean"`: 生成随机布尔值
- `min` (Integer, 可选): 最小值（仅对 `number` 类型有效），默认为 0
- `max` (Integer, 可选): 最大值（仅对 `number` 类型有效），默认为 100

**输出结果**:
```json
{
  "success": true,
  "result": "42",
  "type": "number",
  "error": null
}
```

**使用示例**:
```java
// 生成随机整数
RandomTool.Request request1 = new RandomTool.Request("number", 1, 100);
RandomTool.Response response1 = randomTool.apply(request1);
// result: "42" (1-100之间的随机数)

// 生成UUID
RandomTool.Request request2 = new RandomTool.Request("uuid", null, null);
RandomTool.Response response2 = randomTool.apply(request2);
// result: "550e8400-e29b-41d4-a716-446655440000"

// 生成随机布尔值
RandomTool.Request request3 = new RandomTool.Request("boolean", null, null);
RandomTool.Response response3 = randomTool.apply(request3);
// result: "true" 或 "false"
```

---

## 自动注册机制

所有内置工具会在应用启动时通过 `BuiltinToolConfig` 自动注册到 `ToolManager` 中。

**注册流程**:
1. Spring Boot 应用启动完成后触发 `ApplicationReadyEvent` 事件
2. `BuiltinToolConfig.registerBuiltinTools()` 方法被调用
3. 每个工具被包装为 `FunctionToolCallback` 并注册到 `ToolManager`
4. Agent 可以通过 `ToolManager` 获取并使用这些工具

**日志输出示例**:
```
2026-01-21 14:30:00 INFO  BuiltinToolConfig - 开始注册内置工具...
2026-01-21 14:30:00 INFO  BuiltinToolConfig - ✅ 已注册工具: calculator
2026-01-21 14:30:00 INFO  BuiltinToolConfig - ✅ 已注册工具: datetime
2026-01-21 14:30:00 INFO  BuiltinToolConfig - ✅ 已注册工具: string_util
2026-01-21 14:30:00 INFO  BuiltinToolConfig - ✅ 已注册工具: random
2026-01-21 14:30:00 INFO  BuiltinToolConfig - 内置工具注册完成，共注册 4 个工具
```

---

## 如何添加新的内置工具

1. **创建工具类**，实现 `Function<Request, Response>` 接口：
```java
@Slf4j
@Component
public class MyTool implements Function<MyTool.Request, MyTool.Response> {
    
    public record Request(
        @JsonProperty(required = true, value = "param")
        @JsonPropertyDescription("参数描述")
        String param
    ) {}
    
    public record Response(
        boolean success,
        String result,
        String error
    ) {}
    
    @Override
    public Response apply(Request request) {
        // 实现工具逻辑
        return new Response(true, "result", null);
    }
    
    public static String getToolName() {
        return "my_tool";
    }
    
    public static String getToolDescription() {
        return "工具描述";
    }
}
```

2. **在 `BuiltinToolConfig` 中注册**：
```java
@RequiredArgsConstructor
public class BuiltinToolConfig {
    private final MyTool myTool;
    
    @EventListener(ApplicationReadyEvent.class)
    public void registerBuiltinTools() {
        // ... 其他工具注册
        
        ToolCallback myToolCallback = FunctionToolCallback.builder(MyTool.getToolName(), myTool)
                .description(MyTool.getToolDescription())
                .build();
        toolManager.registerTool(MyTool.getToolName(), myToolCallback);
        log.info("✅ 已注册工具: {}", MyTool.getToolName());
    }
}
```

---

## Agent 中使用工具

工具会自动通过 `ToolManager` 提供给 Agent 使用：

```java
Agent agent = Agent.builder()
        .name("MyAgent")
        .chatModel(chatModel)
        .toolManager(toolManager)  // 注入 ToolManager
        .build();

// Agent 会自动获取所有注册的工具并在需要时调用
ChatResponse response = agent.call(new Prompt("计算 2 + 2"));
```

在 ReActAgent 中，工具调用是自动的：
1. Agent 分析用户输入，决定是否需要调用工具
2. 如果需要，Agent 会自动选择合适的工具并构造参数
3. Spring AI 的 `ChatClient` 自动执行工具调用
4. 工具执行结果会自动添加到对话历史中
5. Agent 基于工具结果继续推理，直到得出最终答案

---

## 技术细节

- **线程安全**: 所有工具类都是无状态的，可以安全地在多线程环境中使用
- **错误处理**: 每个工具都有完善的异常处理，返回统一的错误格式
- **日志记录**: 使用 `@Slf4j` 注解，所有工具操作都会记录日志
- **Spring 集成**: 使用 `@Component` 注解，由 Spring 容器管理生命周期
- **类型安全**: 使用 Java Record 定义请求和响应类型，提供编译时类型检查
