package github.grit.gaia.agent.infra.ai.agent;

import lombok.Data;
import java.util.List;

/**
 * Agent 核心接口
 * 定义 AI Agent 的基本行为
 * 
 * @author Gaia Team
 */
@Data
public abstract class Agent {
    
    /** Agent ID */
    private String agentId;
    
    /** Agent 名称 */
    private String name;
    
    /** Agent 描述 */
    private String description;
    
    /** Agent 策略 */
    private AgentPolicy policy;
    
    /** 可用工具列表 */
    private List<String> availableTools;
    
    /**
     * 执行 Agent 任务
     * 
     * @param context Agent 执行上下文
     * @return 执行结果
     */
    public abstract String execute(AgentContext context);
    
    /**
     * 规划任务步骤
     * 
     * @param task 任务描述
     * @return 规划步骤
     */
    public abstract List<String> plan(String task);
    
    /**
     * 执行单个步骤
     * 
     * @param step 步骤描述
     * @param context 执行上下文
     * @return 步骤执行结果
     */
    protected abstract String executeStep(String step, AgentContext context);
}
