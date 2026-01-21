package github.grit.gaia.agent.infra.ai.agent;

import github.grit.gaia.agent.infra.ai.memory.MemoryManager;
import github.grit.gaia.agent.infra.ai.tool.ToolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Agent抽象基类
 * 提供Agent的基本功能和通用方法
 */
@Slf4j
public abstract class Agent {

    /**
     * 聊天模型
     */
    protected ChatModel chatModel;

    /**
     * Agent类型
     */
    protected AgentType agentType;

    /**
     * 内存管理器
     */
    protected MemoryManager memoryManager;

    /**
     * 工具管理器
     */
    protected ToolManager toolManager;

    /**
     * Agent名称
     */
    protected String name;

    /**
     * Agent描述
     */
    protected String description;

    /**
     * 系统提示词
     */
    protected String systemPrompt;

    /**
     * 最大迭代次数
     */
    protected Integer maxIterations;

    /**
     * 温度参数
     */
    protected Double temperature;

    /**
     * 是否启用调试模式
     */
    protected Boolean debug;

    /**
     * Protected构造函数
     * 设置默认值，子类可以通过super()调用
     * 注意：使用Builder模式创建Agent实例，而不是直接调用构造函数
     */
    protected Agent() {
        // 设置默认值
        this.maxIterations = 10;
        this.temperature = 0.7;
        this.debug = false;
    }

    /**
     * 同步调用Agent
     *
     * @param prompt 提示词
     * @return ChatResponse
     */
    public abstract ChatResponse call(Prompt prompt);

    /**
     * 流式调用Agent
     *
     * @param prompt 提示词
     * @return Flux<ChatResponse>
     */
    public abstract Flux<ChatResponse> stream(Prompt prompt);

    /**
     * 执行Agent（便捷方法）
     *
     * @param userInput 用户输入
     * @return 执行结果
     */
    public String execute(String userInput) {
        log.info("Agent [{}] started execution, user input: {}", name, userInput);

        Prompt prompt = createPrompt(userInput);
        ChatResponse response = call(prompt);

        String result = extractContent(response);
        log.info("Agent [{}] execution completed, result: {}", name, result);

        return result;
    }

    /**
     * 流式执行Agent（便捷方法）
     *
     * @param userInput 用户输入
     * @return Flux<String>
     */
    public Flux<String> executeStream(String userInput) {
        log.info("Agent [{}] started streaming execution, user input: {}", name, userInput);

        Prompt prompt = createPrompt(userInput);
        return stream(prompt)
                .map(this::extractContent)
                .doOnComplete(() -> log.info("Agent [{}] streaming execution completed", name));
    }

    /**
     * 创建Prompt
     *
     * @param userInput 用户输入
     * @return Prompt
     */
    protected Prompt createPrompt(String userInput) {
        List<Message> messages = new ArrayList<>();

        // 添加用户消息
        messages.add(new UserMessage(userInput));

        return new Prompt(messages);
    }

    /**
     * 从ChatResponse中提取内容
     *
     * @param response ChatResponse
     * @return 提取的内容
     */
    protected String extractContent(ChatResponse response) {
        if (response == null || response.getResult() == null) {
            return "";
        }
        if (response.getResult().getOutput() == null) {
            return "";
        }
        return response.getResult().getOutput().getText();
    }

    /**
     * 获取内置范式提示词（由Agent类型决定）
     * 子类需要实现此方法，返回该Agent类型的范式提示词常量
     *
     * @return 内置范式提示词
     */
    protected abstract String getParadigmPrompt();

    /**
     * 构建完整的系统提示词
     * 合并用户提供的系统提示词和内置范式提示词
     *
     * @return 合并后的系统提示词
     */
    protected String buildSystemPrompt() {
        StringBuilder sb = new StringBuilder();

        // 先添加用户提供的系统提示词（如果有）
        if (systemPrompt != null && !systemPrompt.trim().isEmpty()) {
            sb.append(systemPrompt.trim());
            sb.append("\n\n");
        }

        // 再添加内置范式提示词（直接使用常量）
        String paradigmPromptValue = getParadigmPrompt();
        if (paradigmPromptValue != null && !paradigmPromptValue.trim().isEmpty()) {
            sb.append(paradigmPromptValue.trim());
        }

        String result = sb.toString().trim();
        return result.isEmpty() ? null : result;
    }


    /**
     * 验证Agent配置
     *
     * @throws IllegalStateException 如果配置无效
     */
    protected void validateConfig() {
        Assert.notNull(chatModel, "ChatModel cannot be null");
        Assert.notNull(agentType, "AgentType cannot be null");
        Assert.notNull(maxIterations, "maxIterations cannot be null");
        Assert.isTrue(maxIterations > 0, "maxIterations must be greater than 0");
        Assert.notNull(temperature, "temperature cannot be null");
        Assert.isTrue(temperature >= 0 && temperature <= 2, "temperature must be between 0 and 2");
    }

    /**
     * Agent Builder基类
     * 子类可以继承此Builder实现自己的构建器
     * <p>
     * 注意：agentType 是内置的特殊值，由子类在 build() 方法中自动设置，不对外暴露
     */
    protected static abstract class AgentBuilder<T extends Agent, B extends AgentBuilder<T, B>> {
        protected ChatModel chatModel;
        protected MemoryManager memoryManager;
        protected ToolManager toolManager;
        protected String name;
        protected String description;
        protected String systemPrompt;
        protected Integer maxIterations = 10;
        protected Double temperature = 0.7;
        protected Boolean debug = false;

        @SuppressWarnings("unchecked")
        protected B self() {
            return (B) this;
        }

        public B chatModel(ChatModel chatModel) {
            this.chatModel = chatModel;
            return self();
        }

        public B memoryManager(MemoryManager memoryManager) {
            this.memoryManager = memoryManager;
            return self();
        }

        public B toolManager(ToolManager toolManager) {
            this.toolManager = toolManager;
            return self();
        }

        public B name(String name) {
            this.name = name;
            return self();
        }

        public B description(String description) {
            this.description = description;
            return self();
        }

        public B systemPrompt(String systemPrompt) {
            this.systemPrompt = systemPrompt;
            return self();
        }

        public B maxIterations(Integer maxIterations) {
            this.maxIterations = maxIterations;
            return self();
        }

        public B temperature(Double temperature) {
            this.temperature = temperature;
            return self();
        }

        public B debug(Boolean debug) {
            this.debug = debug;
            return self();
        }

        /**
         * 构建Agent实例
         * 子类实现此方法时应该：
         * 1. 创建Agent实例
         * 2. 设置所有字段
         * 3. 调用validateConfig()进行验证
         *
         * @return Agent实例
         */
        public abstract T build();

        /**
         * 设置Builder中的字段到Agent实例
         * 子类可以在build()方法中调用此方法
         * <p>
         * 注意：agentType 不在此方法中设置，应由子类在 build() 方法中根据具体类型自动设置
         *
         * @param agent Agent实例
         */
        protected void setFields(Agent agent) {
            agent.chatModel = this.chatModel;
            agent.memoryManager = this.memoryManager;
            agent.toolManager = this.toolManager;
            agent.name = this.name;
            agent.description = this.description;
            agent.systemPrompt = this.systemPrompt;
            agent.maxIterations = this.maxIterations;
            agent.temperature = this.temperature;
            agent.debug = this.debug;
        }
    }
}
