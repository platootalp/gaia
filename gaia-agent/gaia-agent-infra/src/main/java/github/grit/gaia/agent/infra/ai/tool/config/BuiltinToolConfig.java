package github.grit.gaia.agent.infra.ai.tool.config;

/** genAI_master_start */
import github.grit.gaia.agent.infra.ai.tool.ToolManager;
import github.grit.gaia.agent.infra.ai.tool.builtin.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * 内置工具配置类
 * 在应用启动时自动注册所有内置工具
 *
 * @author Cursor AI
 * @date 2026-01-21
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BuiltinToolConfig {

    private final ToolManager toolManager;
    private final CalculatorTool calculatorTool;
    private final DateTimeTool dateTimeTool;
    private final StringUtilTool stringUtilTool;
    private final RandomTool randomTool;

    /**
     * 应用启动完成后自动注册内置工具
     */
    @EventListener(ApplicationReadyEvent.class)
    public void registerBuiltinTools() {
        log.info("开始注册内置工具...");

        // 注册计算器工具
        ToolCallback calculatorCallback = FunctionToolCallback.builder(CalculatorTool.getToolName(), calculatorTool)
                .description(CalculatorTool.getToolDescription())
                .inputType(CalculatorTool.Request.class)
                .build();
        toolManager.registerTool(CalculatorTool.getToolName(), calculatorCallback);
        log.info("✅ 已注册工具: {}", CalculatorTool.getToolName());

        // 注册日期时间工具
        ToolCallback dateTimeCallback = FunctionToolCallback.builder(DateTimeTool.getToolName(), dateTimeTool)
                .description(DateTimeTool.getToolDescription())
                .inputType(DateTimeTool.Request.class)
                .build();
        toolManager.registerTool(DateTimeTool.getToolName(), dateTimeCallback);
        log.info("✅ 已注册工具: {}", DateTimeTool.getToolName());

        // 注册字符串工具
        ToolCallback stringUtilCallback = FunctionToolCallback.builder(StringUtilTool.getToolName(), stringUtilTool)
                .description(StringUtilTool.getToolDescription())
                .inputType(StringUtilTool.Request.class)
                .build();
        toolManager.registerTool(StringUtilTool.getToolName(), stringUtilCallback);
        log.info("✅ 已注册工具: {}", StringUtilTool.getToolName());

        // 注册随机数工具
        ToolCallback randomCallback = FunctionToolCallback.builder(RandomTool.getToolName(), randomTool)
                .description(RandomTool.getToolDescription())
                .inputType(RandomTool.Request.class)
                .build();
        toolManager.registerTool(RandomTool.getToolName(), randomCallback);
        log.info("✅ 已注册工具: {}", RandomTool.getToolName());

        log.info("内置工具注册完成，共注册 {} 个工具", toolManager.getToolCount());
    }
}
/** genAI_master_end */
