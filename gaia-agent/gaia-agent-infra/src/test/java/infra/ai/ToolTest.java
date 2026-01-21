package infra.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import infra.TestConfiguration;
import infra.ToolUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Spring AI Tool测试类
 * 测试MethodToolCallback和ToolDefinition的基本功能
 */
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ToolTest {

    @Autowired
    private ToolUtil toolUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 解析工具调用返回的结果
     * 如果返回的是JSON字符串格式（带引号），则解析为实际值
     */
    private String parseToolResult(Object result) throws Exception {
        String resultStr = result.toString();
        // 如果返回的是JSON字符串格式（带引号），则解析它
        if (resultStr.startsWith("\"") && resultStr.endsWith("\"")) {
            return objectMapper.readValue(resultStr, String.class);
        }
        return resultStr;
    }

    /**
     * 工具方法类 - 用于测试的工具方法集合
     */
    public static class TestTools {

        /**
         * 计算两个数的和
         */
        public String add(int a, int b) {
            int result = a + b;
            return String.format("%d + %d = %d", a, b, result);
        }

        /**
         * 获取当前时间（模拟）
         */
        public String getCurrentTime() {
            return "当前时间: 2024-01-01 12:00:00";
        }

        /**
         * 格式化字符串
         */
        public String formatString(String template, String name) {
            return String.format(template, name);
        }

        /**
         * 计算两个数的乘积
         */
        public String multiply(int a, int b) {
            int result = a * b;
            return String.format("%d * %d = %d", a, b, result);
        }
    }

    /**
     * 测试基本工具方法调用 - 计算两个数的和
     */
    @Test
    public void testMethodTool() throws Exception {
        TestTools testTools = new TestTools();

        // 创建JSON Schema字符串
        String jsonSchema = """
                {
                    "type": "object",
                    "properties": {
                        "a": {
                            "type": "integer",
                            "description": "第一个数字"
                        },
                        "b": {
                            "type": "integer",
                            "description": "第二个数字"
                        }
                    },
                    "required": ["a", "b"]
                }
                """;

        // 创建工具定义
        ToolDefinition toolDefinition = ToolDefinition.builder()
                .name("add")
                .description("计算两个整数的和")
                .inputSchema(jsonSchema)
                .build();

        // 创建方法工具回调
        MethodToolCallback methodTool = MethodToolCallback.builder()
                .toolDefinition(toolDefinition)
                .toolMethod(testTools.getClass().getMethod("add", int.class, int.class))
                .toolObject(testTools)
                .build();

        // 准备调用参数
        String arguments = objectMapper.writeValueAsString(java.util.Map.of("a", 10, "b", 20));

        // 调用工具
        Object result = methodTool.call(arguments);

        // 验证结果
        Assertions.assertNotNull(result);
        Assertions.assertEquals("10 + 20 = 30", result.toString());
        System.out.println("工具调用结果: " + result);
    }

    /**
     * 测试无参数工具方法调用
     */
    @Test
    public void testMethodToolWithoutParameters() throws Exception {
        TestTools testTools = new TestTools();

        String jsonSchema = """
                {
                    "type": "object",
                    "properties": {}
                }
                """;

        ToolDefinition toolDefinition = ToolDefinition.builder()
                .name("getCurrentTime")
                .description("获取当前时间")
                .inputSchema(jsonSchema)
                .build();

        MethodToolCallback methodTool = MethodToolCallback.builder()
                .toolDefinition(toolDefinition)
                .toolMethod(testTools.getClass().getMethod("getCurrentTime"))
                .toolObject(testTools)
                .build();

        String arguments = objectMapper.writeValueAsString(java.util.Map.of());
        Object result = methodTool.call(arguments);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.toString().contains("当前时间"));
        System.out.println("工具调用结果: " + result);
    }

    /**
     * 测试字符串格式化工具方法
     */
    @Test
    public void testStringFormatTool() throws Exception {
        TestTools testTools = new TestTools();

        String jsonSchema = """
                {
                    "type": "object",
                    "properties": {
                        "template": {
                            "type": "string",
                            "description": "格式化模板，使用%s作为占位符"
                        },
                        "name": {
                            "type": "string",
                            "description": "要插入的名称"
                        }
                    },
                    "required": ["template", "name"]
                }
                """;

        ToolDefinition toolDefinition = ToolDefinition.builder()
                .name("formatString")
                .description("格式化字符串，使用模板和参数")
                .inputSchema(jsonSchema)
                .build();

        MethodToolCallback methodTool = MethodToolCallback.builder()
                .toolDefinition(toolDefinition)
                .toolMethod(testTools.getClass().getMethod("formatString", String.class, String.class))
                .toolObject(testTools)
                .build();

        String arguments = objectMapper.writeValueAsString(java.util.Map.of(
                "template", "你好，%s！",
                "name", "世界"));

        Object result = methodTool.call(arguments);

        Assertions.assertNotNull(result);
        String resultStr = parseToolResult(result);
        Assertions.assertEquals("你好，世界！", resultStr);
        System.out.println("工具调用结果: " + result);
    }

    /**
     * 测试工具定义的基本属性
     */
    @Test
    public void testToolDefinitionProperties() {
        String jsonSchema = """
                {
                    "type": "object",
                    "properties": {
                        "a": {"type": "integer"},
                        "b": {"type": "integer"}
                    },
                    "required": ["a", "b"]
                }
                """;

        ToolDefinition toolDefinition = ToolDefinition.builder()
                .name("testTool")
                .description("测试工具")
                .inputSchema(jsonSchema)
                .build();

        Assertions.assertEquals("testTool", toolDefinition.name());
        Assertions.assertEquals("测试工具", toolDefinition.description());
        Assertions.assertNotNull(toolDefinition.inputSchema());
        System.out.println("工具定义名称: " + toolDefinition.name());
        System.out.println("工具定义描述: " + toolDefinition.description());
    }

    /**
     * 测试多个工具方法的创建
     */
    @Test
    public void testMultipleTools() throws Exception {
        TestTools testTools = new TestTools();

        // 创建加法工具
        String addSchema = """
                {
                    "type": "object",
                    "properties": {
                        "a": {"type": "integer", "description": "第一个数字"},
                        "b": {"type": "integer", "description": "第二个数字"}
                    },
                    "required": ["a", "b"]
                }
                """;

        ToolDefinition addToolDefinition = ToolDefinition.builder()
                .name("add")
                .description("计算两个整数的和")
                .inputSchema(addSchema)
                .build();

        MethodToolCallback addTool = MethodToolCallback.builder()
                .toolDefinition(addToolDefinition)
                .toolMethod(testTools.getClass().getMethod("add", int.class, int.class))
                .toolObject(testTools)
                .build();

        // 创建乘法工具
        String multiplySchema = """
                {
                    "type": "object",
                    "properties": {
                        "a": {"type": "integer", "description": "第一个数字"},
                        "b": {"type": "integer", "description": "第二个数字"}
                    },
                    "required": ["a", "b"]
                }
                """;

        ToolDefinition multiplyToolDefinition = ToolDefinition.builder()
                .name("multiply")
                .description("计算两个整数的乘积")
                .inputSchema(multiplySchema)
                .build();

        MethodToolCallback multiplyTool = MethodToolCallback.builder()
                .toolDefinition(multiplyToolDefinition)
                .toolMethod(testTools.getClass().getMethod("multiply", int.class, int.class))
                .toolObject(testTools)
                .build();

        // 测试加法工具
        String addArgs = objectMapper.writeValueAsString(java.util.Map.of("a", 10, "b", 20));
        Object addResult = addTool.call(addArgs);
        Assertions.assertEquals("10 + 20 = 30", addResult.toString());

        // 测试乘法工具
        String multiplyArgs = objectMapper.writeValueAsString(java.util.Map.of("a", 5, "b", 6));
        Object multiplyResult = multiplyTool.call(multiplyArgs);
        Assertions.assertEquals("5 * 6 = 30", multiplyResult.toString());

        System.out.println("加法工具结果: " + addResult);
        System.out.println("乘法工具结果: " + multiplyResult);
    }
}
