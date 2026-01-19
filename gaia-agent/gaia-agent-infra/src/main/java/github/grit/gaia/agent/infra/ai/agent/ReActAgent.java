package github.grit.gaia.agent.infra.ai.agent;

import github.grit.gaia.agent.infra.ai.tool.ToolCall;
import github.grit.gaia.agent.infra.ai.tool.ToolRegistry;
import github.grit.gaia.agent.infra.ai.tool.ToolResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * ReAct Agent 实现
 * 基于 Reasoning + Acting 模式
 *
 * @author Gaia Team
 */
@Slf4j
public class ReActAgent extends Agent {

    private final ToolRegistry toolRegistry;

    public ReActAgent(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    @Override
    public String execute(AgentContext context) {
        log.info("ReAct Agent 开始执行任务: {}", context.getUserInput());

        StringBuilder reasoning = new StringBuilder();
        context.setCurrentIteration(0);

        while (!context.isMaxIterationsReached()) {
            context.incrementIteration();

            // Reasoning: 思考下一步行动
            String thought = reason(context, reasoning.toString());
            reasoning.append("Thought: ").append(thought).append("\n");

            // Acting: 决定是否需要调用工具
            if (shouldCallTool(thought)) {
                ToolCall toolCall = parseToolCall(thought);
                ToolResult result = executeTool(toolCall);
                reasoning.append("Action: ").append(toolCall.getToolName()).append("\n");
                reasoning.append("Observation: ").append(result.getResult()).append("\n");
            } else {
                // 得出最终答案
                log.info("ReAct Agent 完成任务");
                return extractFinalAnswer(thought);
            }
        }

        log.warn("ReAct Agent 达到最大迭代次数");
        return "抱歉，任务处理超时，请稍后重试。";
    }

    @Override
    public List<String> plan(String task) {
        // ReAct 模式不需要预先规划
        return new ArrayList<>();
    }

    @Override
    protected String executeStep(String step, AgentContext context) {
        // ReAct 模式的步骤执行在 execute 方法中完成
        return "";
    }

    /**
     * 推理下一步行动
     */
    private String reason(AgentContext context, String history) {
        // TODO: 调用 LLM 进行推理
        return "需要查询天气信息";
    }

    /**
     * 判断是否需要调用工具
     */
    private boolean shouldCallTool(String thought) {
        // TODO: 解析思考结果，判断是否需要工具
        return thought.contains("需要") || thought.contains("查询");
    }

    /**
     * 解析工具调用
     */
    private ToolCall parseToolCall(String thought) {
        // TODO: 从思考结果中解析工具调用
        return ToolCall.builder()
                .toolName("weather_query")
                .build();
    }

    /**
     * 执行工具
     */
    private ToolResult executeTool(ToolCall toolCall) {
        // TODO: 通过 ToolRegistry 执行工具
        return ToolResult.builder()
                .success(true)
                .result("北京今天晴天，温度25°C")
                .build();
    }

    /**
     * 提取最终答案
     */
    private String extractFinalAnswer(String thought) {
        // TODO: 从思考结果中提取最终答案
        return thought;
    }
}
