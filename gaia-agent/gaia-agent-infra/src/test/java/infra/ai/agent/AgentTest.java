package infra.ai.agent;

/**
 * genAI_master_start
 */

import github.grit.gaia.agent.infra.ai.agent.ReActAgent;
import github.grit.gaia.agent.infra.ai.tool.ToolManager;
import github.grit.gaia.agent.infra.ai.tool.builtin.CalculatorTool;
import github.grit.gaia.agent.infra.ai.tool.builtin.DateTimeTool;
import github.grit.gaia.agent.infra.ai.tool.builtin.RandomTool;
import github.grit.gaia.agent.infra.ai.tool.builtin.StringUtilTool;
import infra.TestConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Agent 测试类
 * 测试各种 Agent 的基本功能和工具调用
 */
@Slf4j
@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AgentTest {

    @Autowired
    @Qualifier("dashScopeChatModel")
    private ChatModel chatModel;

    private ToolManager toolManager;

    @BeforeEach
    public void setUp() {
        toolManager = new ToolManager();
        registerBuiltinTools();
    }

    private void registerBuiltinTools() {
        CalculatorTool calculatorTool = new CalculatorTool();
        ToolCallback calculatorCallback = FunctionToolCallback.builder(
                        CalculatorTool.getToolName(), calculatorTool)
                .description(CalculatorTool.getToolDescription())
                .inputType(CalculatorTool.Request.class)
                .build();
        toolManager.registerTool(CalculatorTool.getToolName(), calculatorCallback);

        DateTimeTool dateTimeTool = new DateTimeTool();
        ToolCallback dateTimeCallback = FunctionToolCallback.builder(
                        DateTimeTool.getToolName(), dateTimeTool)
                .description(DateTimeTool.getToolDescription())
                .inputType(DateTimeTool.Request.class)
                .build();
        toolManager.registerTool(DateTimeTool.getToolName(), dateTimeCallback);

        StringUtilTool stringUtilTool = new StringUtilTool();
        ToolCallback stringUtilCallback = FunctionToolCallback.builder(
                        StringUtilTool.getToolName(), stringUtilTool)
                .description(StringUtilTool.getToolDescription())
                .inputType(StringUtilTool.Request.class)
                .build();
        toolManager.registerTool(StringUtilTool.getToolName(), stringUtilCallback);

        RandomTool randomTool = new RandomTool();
        ToolCallback randomCallback = FunctionToolCallback.builder(
                        RandomTool.getToolName(), randomTool)
                .description(RandomTool.getToolDescription())
                .inputType(RandomTool.Request.class)
                .build();
        toolManager.registerTool(RandomTool.getToolName(), randomCallback);

        log.info("已注册 {} 个工具", toolManager.getToolCount());
    }

    @Test
    public void testReActAgentWithCalculator() {
        log.info("=== 测试 ReActAgent 使用计算器工具 ===");

        ReActAgent agent = ReActAgent.builder()
                .chatModel(chatModel)
                .name("CalculatorAgent")
//                .systemPrompt("你是一个数学助手，擅长使用计算器工具解决数学问题。")
                .toolManager(toolManager)
                .maxIterations(5)
                .build();

        ChatResponse response = agent.call(new Prompt("请计算 15 + 27"));

        assertNotNull(response);
        log.info("计算结果: {}", response.getResult().getOutput().getText());
    }

    @Test
    public void testReActAgentWithStringTool() {
        log.info("=== 测试 ReActAgent 使用字符串工具 ===");

        ReActAgent agent = ReActAgent.builder()
                .chatModel(chatModel)
                .name("StringAgent")
                .systemPrompt("你是一个文本处理助手，擅长使用字符串工具。")
                .toolManager(toolManager)
                .maxIterations(5)
                .build();

        ChatResponse response = agent.call(new Prompt("请把 hello world 转换成大写"));

        assertNotNull(response);
        log.info("字符串处理结果: {}", response.getResult().getOutput().getText());
    }

    @Test
    public void testToolManager() {
        log.info("=== 测试工具管理器 ===");

        assertEquals(4, toolManager.getToolCount());
        assertTrue(toolManager.hasTool("calculator"));
        assertTrue(toolManager.hasTool("datetime"));
        assertTrue(toolManager.hasTool("string_util"));
        assertTrue(toolManager.hasTool("random"));

        log.info("工具列表: {}", toolManager.getToolNames());
    }
}
