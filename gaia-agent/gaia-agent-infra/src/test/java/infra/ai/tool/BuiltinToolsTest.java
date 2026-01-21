package infra.ai.tool;

/** genAI_master_start */
import github.grit.gaia.agent.infra.ai.tool.builtin.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 内置工具测试类
 * 测试所有内置工具的基本功能
 *
 * @author Cursor AI
 * @date 2026-01-21
 */
public class BuiltinToolsTest {

    /**
     * 测试计算器工具 - 加法
     */
    @Test
    public void testCalculatorAddition() {
        CalculatorTool tool = new CalculatorTool();
        CalculatorTool.Request request = new CalculatorTool.Request("2 + 3");
        CalculatorTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("5.0", response.result());
        assertNull(response.error());
    }

    /**
     * 测试计算器工具 - 乘法
     */
    @Test
    public void testCalculatorMultiplication() {
        CalculatorTool tool = new CalculatorTool();
        CalculatorTool.Request request = new CalculatorTool.Request("10 * 5");
        CalculatorTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("50.0", response.result());
        assertNull(response.error());
    }

    /**
     * 测试计算器工具 - 复杂表达式
     */
    @Test
    public void testCalculatorComplexExpression() {
        CalculatorTool tool = new CalculatorTool();
        CalculatorTool.Request request = new CalculatorTool.Request("2 + 3 * 4");
        CalculatorTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("14.0", response.result());
        assertNull(response.error());
    }

    /**
     * 测试日期时间工具 - 默认参数
     */
    @Test
    public void testDateTimeToolDefault() {
        DateTimeTool tool = new DateTimeTool();
        DateTimeTool.Request request = new DateTimeTool.Request(null, null);
        DateTimeTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertNotNull(response.currentDateTime());
        assertNotNull(response.timestamp());
        assertNotNull(response.timezone());
        assertNull(response.error());
    }

    /**
     * 测试日期时间工具 - 指定时区
     */
    @Test
    public void testDateTimeToolWithTimezone() {
        DateTimeTool tool = new DateTimeTool();
        DateTimeTool.Request request = new DateTimeTool.Request("Asia/Shanghai", "yyyy-MM-dd HH:mm:ss");
        DateTimeTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertNotNull(response.currentDateTime());
        assertTrue(response.currentDateTime().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
        assertEquals("Asia/Shanghai", response.timezone());
        assertNull(response.error());
    }

    /**
     * 测试字符串工具 - 转大写
     */
    @Test
    public void testStringUtilUppercase() {
        StringUtilTool tool = new StringUtilTool();
        StringUtilTool.Request request = new StringUtilTool.Request("hello world", "uppercase");
        StringUtilTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("HELLO WORLD", response.result());
        assertNull(response.error());
    }

    /**
     * 测试字符串工具 - 转小写
     */
    @Test
    public void testStringUtilLowercase() {
        StringUtilTool tool = new StringUtilTool();
        StringUtilTool.Request request = new StringUtilTool.Request("HELLO WORLD", "lowercase");
        StringUtilTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("hello world", response.result());
        assertNull(response.error());
    }

    /**
     * 测试字符串工具 - 反转
     */
    @Test
    public void testStringUtilReverse() {
        StringUtilTool tool = new StringUtilTool();
        StringUtilTool.Request request = new StringUtilTool.Request("hello", "reverse");
        StringUtilTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("olleh", response.result());
        assertNull(response.error());
    }

    /**
     * 测试字符串工具 - 获取长度
     */
    @Test
    public void testStringUtilLength() {
        StringUtilTool tool = new StringUtilTool();
        StringUtilTool.Request request = new StringUtilTool.Request("hello world", "length");
        StringUtilTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("11", response.result());
        assertNull(response.error());
    }

    /**
     * 测试字符串工具 - 去除空格
     */
    @Test
    public void testStringUtilTrim() {
        StringUtilTool tool = new StringUtilTool();
        StringUtilTool.Request request = new StringUtilTool.Request("  hello world  ", "trim");
        StringUtilTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertEquals("hello world", response.result());
        assertNull(response.error());
    }

    /**
     * 测试随机数工具 - 生成随机整数
     */
    @Test
    public void testRandomToolNumber() {
        RandomTool tool = new RandomTool();
        RandomTool.Request request = new RandomTool.Request("number", 1, 100);
        RandomTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertNotNull(response.result());
        int number = Integer.parseInt(response.result());
        assertTrue(number >= 1 && number <= 100);
        assertEquals("number", response.type());
        assertNull(response.error());
    }

    /**
     * 测试随机数工具 - 生成UUID
     */
    @Test
    public void testRandomToolUUID() {
        RandomTool tool = new RandomTool();
        RandomTool.Request request = new RandomTool.Request("uuid", null, null);
        RandomTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertNotNull(response.result());
        // UUID格式验证：8-4-4-4-12
        assertTrue(response.result().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));
        assertEquals("uuid", response.type());
        assertNull(response.error());
    }

    /**
     * 测试随机数工具 - 生成布尔值
     */
    @Test
    public void testRandomToolBoolean() {
        RandomTool tool = new RandomTool();
        RandomTool.Request request = new RandomTool.Request("boolean", null, null);
        RandomTool.Response response = tool.apply(request);

        assertTrue(response.success());
        assertNotNull(response.result());
        assertTrue(response.result().equals("true") || response.result().equals("false"));
        assertEquals("boolean", response.type());
        assertNull(response.error());
    }

    /**
     * 测试工具名称和描述
     */
    @Test
    public void testToolMetadata() {
        assertEquals("calculator", CalculatorTool.getToolName());
        assertNotNull(CalculatorTool.getToolDescription());

        assertEquals("datetime", DateTimeTool.getToolName());
        assertNotNull(DateTimeTool.getToolDescription());

        assertEquals("string_util", StringUtilTool.getToolName());
        assertNotNull(StringUtilTool.getToolDescription());

        assertEquals("random", RandomTool.getToolName());
        assertNotNull(RandomTool.getToolDescription());
    }
}
/** genAI_master_end */
