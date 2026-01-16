package github.grit.gaia.agent.controller.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 工作流执行请求
 * 
 * @author Gaia Team
 */
@Data
public class WorkflowExecuteRequest {
    
    /** 工作流 ID */
    @NotBlank(message = "工作流 ID 不能为空")
    private String workflowId;
    
    /** 输入参数 */
    private Map<String, Object> input;
}
