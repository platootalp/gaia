package github.grit.gaia.agent.infra.ai.agent;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * ReAct Agent实现
 * ReAct (Reasoning + Acting) 是一种结合推理和行动的Agent模式
 * <p>
 * 工作流程：
 * 1. Reasoning: Agent思考下一步要做什么
 * 2. Acting: Agent执行工具调用
 * 3. Observing: Agent观察工具执行结果
 * 4. 循环: 重复上述过程直到完成任务或达到最大迭代次数
 */
@Slf4j
public class ReActAgent extends Agent {

    /**
     * ReAct范式提示词常量
     * 包含ReAct模式的工作流程、格式要求等，这是Agent体系的核心部分
     */
    private static final String REACT_PARADIGM_PROMPT = """
            You are using the ReAct (Reasoning + Acting) approach to solve problems.

            Process:
            1. Think about what you need to do (Thought)
            2. Decide if you need to use a tool (Action)
            3. If yes, specify the tool and input (Action Input)
            4. Observe the tool result (Observation)
            5. Repeat until you have the final answer
            6. Provide the Final Answer

            Format your responses as:
                Thought: [your reasoning]
                Action: [tool name or None]
                Action Input: [input for the tool]
                Observation: [result from tool]
                Final Answer: [your final answer]
            """;

    /**
     * Protected构造函数
     */
    protected ReActAgent() {
        super();
    }

    /**
     * 同步调用Agent
     * 实现ReAct模式的核心循环逻辑
     *
     * @param prompt 提示词
     * @return ChatResponse
     */
    @Override
    public ChatResponse call(Prompt prompt) {
        Assert.notNull(chatModel, "ChatModel cannot be null");
        Assert.notNull(prompt, "Prompt cannot be null");

        log.debug("ReActAgent [{}] starting call, maxIterations: {}", name, maxIterations);

        // 创建ChatClient
        ChatClient chatClient = createChatClient();

        // 执行ReAct循环
        List<Message> conversationHistory = new ArrayList<>();

        // 添加系统提示词（合并用户提供的系统提示词和内置范式提示词）
        String finalSystemPrompt = buildSystemPrompt();
        if (finalSystemPrompt != null && !finalSystemPrompt.isEmpty()) {
            conversationHistory.add(new SystemMessage(finalSystemPrompt));
        }

        // 添加初始用户消息
        String userInput = extractUserInput(prompt);
        conversationHistory.add(new UserMessage(userInput));

        // ReAct循环：推理 -> 行动 -> 观察
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            log.debug("ReActAgent [{}] iteration {}/{}", name, iteration + 1, maxIterations);

            // 1. Reasoning: 让模型思考下一步
            ChatResponse response = chatClient.prompt()
                    .messages(conversationHistory)
                    .call()
                    .chatResponse();

            if (response == null || response.getResult() == null) {
                log.warn("ReActAgent [{}] received null response at iteration {}", name, iteration + 1);
                break;
            }

            String assistantResponse = extractContent(response);
            conversationHistory.add(new AssistantMessage(assistantResponse));

            log.debug("ReActAgent [{}] assistant response: {}", name, assistantResponse);

            // 2. 检查是否完成任务（模型返回最终答案）
            // Spring AI 的 ChatClient 会自动处理工具调用
            // 如果响应中包含 Final Answer 或没有 Action 关键字，认为任务完成
            if (isTaskComplete(assistantResponse)) {
                log.info("ReActAgent [{}] task completed at iteration {}", name, iteration + 1);
                return response;
            }

            // 3. 如果还没完成，继续下一轮迭代
            // Spring AI 会自动处理工具调用并将结果添加到对话历史中
            log.debug("ReActAgent [{}] continuing to next iteration", name);
            /** genAI_master_end */
        }

        // 达到最大迭代次数
        log.warn("ReActAgent [{}] reached max iterations ({})", name, maxIterations);

        // 返回最后一次响应
        return chatClient.prompt()
                .messages(conversationHistory)
                .call()
                .chatResponse();
    }

    /**
     * 流式调用Agent
     *
     * @param prompt 提示词
     * @return Flux<ChatResponse>
     */
    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        Assert.notNull(chatModel, "ChatModel cannot be null");
        Assert.notNull(prompt, "Prompt cannot be null");

        log.debug("ReActAgent [{}] starting stream, maxIterations: {}", name, maxIterations);

        // 创建ChatClient
        ChatClient chatClient = createChatClient();

        // 对于流式调用，简化实现：直接流式返回响应
        // 完整的ReAct循环在流式模式下比较复杂，这里先实现基本功能
        List<Message> messages = new ArrayList<>();

        // 添加系统提示词（合并用户提供的系统提示词和内置范式提示词）
        String finalSystemPrompt = buildSystemPrompt();
        if (finalSystemPrompt != null && !finalSystemPrompt.isEmpty()) {
            messages.add(new SystemMessage(finalSystemPrompt));
        }

        String userInput = extractUserInput(prompt);
        messages.add(new UserMessage(userInput));

        return chatClient.prompt()
                .messages(messages)
                .stream()
                .chatResponse();
    }

    /** genAI_master_start */
    /**
     * 创建ChatClient
     *
     * @return ChatClient实例
     */
    private ChatClient createChatClient() {
        ChatClient.Builder builder = ChatClient.builder(chatModel);

        // 如果有工具管理器，注册工具
        // 注意：ToolManager需要提供获取工具列表的方法
        if (toolManager != null) {
            builder.defaultToolCallbacks(toolManager.getFunctions());
        }

        return builder.build();
    }

    /** genAI_master_end */

    /**
     * 从Prompt中提取用户输入
     *
     * @param prompt Prompt对象
     * @return 用户输入文本
     */
    private String extractUserInput(Prompt prompt) {
        if (prompt == null || prompt.getInstructions() == null) {
            return "";
        }

        List<Message> messages = prompt.getInstructions();
        for (Message message : messages) {
            if (message instanceof UserMessage) {
                return message.getText();
            }
        }

        return "";
    }

    /** genAI_master_start */
    /**
     * 检查任务是否完成
     * 通过检查响应中是否包含"Final Answer"来判断
     *
     * @param response 助手响应
     * @return true表示任务完成
     */
    private boolean isTaskComplete(String response) {
        if (response == null || response.isEmpty()) {
            return false;
        }

        String lowerResponse = response.toLowerCase();
        return lowerResponse.contains("final answer") ||
                lowerResponse.contains("answer:") ||
                (!lowerResponse.contains("action:") && !lowerResponse.contains("thought:"));
    }

    /** genAI_master_end */

    /**
     * ReActAgent Builder
     */
    public static class ReActAgentBuilder extends AgentBuilder<ReActAgent, ReActAgentBuilder> {

        @Override
        public ReActAgent build() {
            // 创建ReActAgent实例
            ReActAgent agent = new ReActAgent();

            // 设置字段
            setFields(agent);

            // 设置Agent类型
            agent.agentType = AgentType.ReAct;

            // 如果没有设置名称，使用默认名称
            if (agent.name == null || agent.name.isEmpty()) {
                agent.name = "ReActAgent";
            }

            // 如果没有设置描述，使用默认描述
            if (StrUtil.isBlank(agent.description)) {
                agent.description = "ReAct Agent that combines reasoning and acting";
            }

            // 验证配置
            agent.validateConfig();

            log.debug("ReActAgent built: name={}, type={}, maxIterations={}",
                    agent.name, agent.agentType, agent.maxIterations);

            return agent;
        }
    }

    /**
     * 获取ReAct范式提示词常量
     *
     * @return ReAct范式提示词
     */
    @Override
    protected String getParadigmPrompt() {
        return REACT_PARADIGM_PROMPT;
    }

    /**
     * 创建Builder实例
     *
     * @return ReActAgentBuilder
     */
    public static ReActAgentBuilder builder() {
        return new ReActAgentBuilder();
    }
}
