package github.grit.gaia.agent.core.agent;

import github.grit.gaia.agent.core.tool.ToolRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Agent 执行器
 * 负责调度和执行 Agent
 * 
 * @author Gaia Team
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AgentExecutor {
    
    private final ToolRegistry toolRegistry;
    
    /**
     * 执行 Agent
     * 
     * @param agent Agent 实例
     * @param context 执行上下文
     * @return 执行结果
     */
    public String execute(Agent agent, AgentContext context) {
        log.info("开始执行 Agent: {}, 会话ID: {}", agent.getName(), context.getSessionId());
        
        try {
            // 执行前验证
            validateContext(context);
            
            // 执行 Agent
            String result = agent.execute(context);
            
            log.info("Agent 执行完成: {}", agent.getName());
            return result;
            
        } catch (Exception e) {
            log.error("Agent 执行失败: {}", agent.getName(), e);
            throw new RuntimeException("Agent 执行失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 验证执行上下文
     */
    private void validateContext(AgentContext context) {
        if (context.getSessionId() == null) {
            throw new IllegalArgumentException("会话ID不能为空");
        }
        if (context.getUserInput() == null || context.getUserInput().trim().isEmpty()) {
            throw new IllegalArgumentException("用户输入不能为空");
        }
    }
}
