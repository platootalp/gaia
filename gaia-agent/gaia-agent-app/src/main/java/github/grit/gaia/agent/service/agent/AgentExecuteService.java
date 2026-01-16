package github.grit.gaia.agent.service.agent;

import github.grit.gaia.agent.controller.dto.request.AgentExecuteRequest;
import github.grit.gaia.agent.controller.dto.response.AgentExecuteResponse;
import github.grit.gaia.agent.core.agent.Agent;
import github.grit.gaia.agent.core.agent.AgentContext;
import github.grit.gaia.agent.core.agent.AgentExecutor;
import github.grit.gaia.agent.core.agent.ReActAgent;
import github.grit.gaia.agent.core.memory.ConversationMemory;
import github.grit.gaia.agent.core.tool.ToolRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.UUID;

/**
 * Agent 执行服务实现
 * 
 * @author Gaia Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgentExecuteService implements AgentService {
    
    private final AgentExecutor agentExecutor;
    private final ToolRegistry toolRegistry;
    
    @Override
    public AgentExecuteResponse execute(AgentExecuteRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            // 创建会话 ID
            String sessionId = request.getSessionId() != null 
                    ? request.getSessionId() 
                    : UUID.randomUUID().toString();
            
            // 构建 Agent 上下文
            AgentContext context = AgentContext.builder()
                    .sessionId(sessionId)
                    .userInput(request.getInput())
                    .memory(new ConversationMemory())
                    .variables(request.getParams() != null ? request.getParams() : new HashMap<>())
                    .maxIterations(request.getMaxIterations() != null ? request.getMaxIterations() : 10)
                    .currentIteration(0)
                    .build();
            
            // 创建 Agent 实例 (这里使用 ReAct Agent 作为示例)
            Agent agent = createAgent(request.getAgentId());
            
            // 执行 Agent
            String result = agentExecutor.execute(agent, context);
            
            long duration = System.currentTimeMillis() - startTime;
            
            return AgentExecuteResponse.builder()
                    .sessionId(sessionId)
                    .result(result)
                    .iterations(context.getCurrentIteration())
                    .duration(duration)
                    .build();
                    
        } catch (Exception e) {
            log.error("Agent 执行失败", e);
            throw new RuntimeException("Agent 执行失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 创建 Agent 实例
     */
    private Agent createAgent(String agentId) {
        // TODO: 根据 agentId 从数据库加载 Agent 配置
        // 这里简单返回一个 ReAct Agent
        ReActAgent agent = new ReActAgent(toolRegistry);
        agent.setAgentId(agentId);
        agent.setName("示例 Agent");
        return agent;
    }
}
