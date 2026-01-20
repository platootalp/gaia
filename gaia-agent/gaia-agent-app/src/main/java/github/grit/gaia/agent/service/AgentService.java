package github.grit.gaia.agent.service;

import github.grit.gaia.agent.controller.dto.AgentExecuteRequest;
import github.grit.gaia.agent.controller.dto.AgentExecuteResponse;

public interface AgentService {
    AgentExecuteResponse execute(AgentExecuteRequest request);
}
