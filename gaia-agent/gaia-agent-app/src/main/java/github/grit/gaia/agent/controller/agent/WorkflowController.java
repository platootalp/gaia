package github.grit.gaia.agent.controller.agent;

import github.grit.gaia.agent.common.response.Result;
import github.grit.gaia.agent.controller.dto.WorkflowExecuteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作流控制器
 *
 * @author Gaia Team
 */
@Slf4j
@RestController
@RequestMapping("/v1/workflow")
@RequiredArgsConstructor
public class WorkflowController {

    /**
     * 执行工作流
     */
    @PostMapping("/execute")
    public Result<?> execute(@Validated @RequestBody WorkflowExecuteRequest request) {
        log.info("执行工作流请求: {}", request);
        // TODO: 实现工作流执行
        return Result.success();
    }

    /**
     * 获取工作流列表
     */
    @GetMapping("/list")
    public Result<?> listWorkflows() {
        // TODO: 实现获取工作流列表
        return Result.success();
    }
}
