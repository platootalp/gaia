package github.grit.gaia.agent.infra.ai.agent;

import github.grit.gaia.agent.infra.ai.memory.Memory;
import lombok.Builder;
import lombok.Data;
import java.util.Map;

/**
 * Agent 执行上下文
 * 包含 Agent 运行时所需的所有信息
 * 
 * @author Gaia Team
 */
@Data
@Builder
public class AgentContext {
    
    /** 会话 ID */
    private String sessionId;
    
    /** 用户输入 */
    private String userInput;
    
    /** 历史记忆 */
    private Memory memory;
    
    /** 上下文变量 */
    private Map<String, Object> variables;
    
    /** 最大迭代次数 */
    private Integer maxIterations;
    
    /** 当前迭代次数 */
    private Integer currentIteration;
    
    /** 超时时间（毫秒） */
    private Long timeout;
    
    /**
     * 增加迭代次数
     */
    public void incrementIteration() {
        if (currentIteration == null) {
            currentIteration = 0;
        }
        currentIteration++;
    }
    
    /**
     * 是否超过最大迭代次数
     */
    public boolean isMaxIterationsReached() {
        return maxIterations != null && currentIteration != null 
               && currentIteration >= maxIterations;
    }
}
