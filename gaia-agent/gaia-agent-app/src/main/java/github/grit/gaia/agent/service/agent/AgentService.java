package github.grit.gaia.agent.service.agent;

import github.grit.gaia.agent.controller.dto.request.AgentExecuteRequest;
import github.grit.gaia.agent.controller.dto.response.AgentExecuteResponse;

/**
 * Agent 服务接口
 * 
 * @author Gaia Team
 */
public interface AgentService {
    
    /**
     * 执行 Agent
     * 
     * @param request 执行请求
     * @return 执行响应
     */
    AgentExecuteResponse execute(AgentExecuteRequest request);
}
