package github.grit.gaia.agent.controller.agent;

import github.grit.gaia.agent.common.response.Result;
import github.grit.gaia.agent.controller.dto.AgentExecuteRequest;
import github.grit.gaia.agent.controller.dto.AgentExecuteResponse;
import github.grit.gaia.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Agent 控制器
 *
 * @author Gaia Team
 */
@Slf4j
@RestController
@RequestMapping("/v1/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    /**
     * 执行 Agent
     */
    @PostMapping("/execute")
    public Result<AgentExecuteResponse> execute(@Validated @RequestBody AgentExecuteRequest request) {
        log.info("执行 Agent 请求: {}", request);
        AgentExecuteResponse response = agentService.execute(request);
        return Result.success(response);
    }

    /**
     * 获取 Agent 列表
     */
    @GetMapping("/list")
    public Result<?> listAgents() {
        // TODO: 实现获取 Agent 列表
        return Result.success();
    }

    /**
     * 获取 Agent 详情
     */
    @GetMapping("/{agentId}")
    public Result<?> getAgent(@PathVariable String agentId) {
        // TODO: 实现获取 Agent 详情
        return Result.success();
    }
}
