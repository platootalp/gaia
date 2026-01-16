package github.grit.gaia.agent.core.agent;

import lombok.Builder;
import lombok.Data;

/**
 * Agent 策略配置
 * 定义 Agent 的行为策略
 * 
 * @author Gaia Team
 */
@Data
@Builder
public class AgentPolicy {
    
    /** 模型名称 */
    private String modelName;
    
    /** 温度参数 */
    private Double temperature;
    
    /** 最大 token 数 */
    private Integer maxTokens;
    
    /** Top P 参数 */
    private Double topP;
    
    /** 系统提示词 */
    private String systemPrompt;
    
    /** 是否启用流式输出 */
    private Boolean streamEnabled;
    
    /** 最大重试次数 */
    private Integer maxRetries;
    
    /** 超时时间（秒） */
    private Integer timeoutSeconds;
    
    /**
     * 创建默认策略
     */
    public static AgentPolicy defaultPolicy() {
        return AgentPolicy.builder()
                .modelName("qwen-plus")
                .temperature(0.7)
                .maxTokens(2000)
                .topP(0.9)
                .streamEnabled(false)
                .maxRetries(3)
                .timeoutSeconds(60)
                .build();
    }
}
