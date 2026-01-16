package github.grit.gaia.agent.controller.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Agent 执行请求
 * 
 * @author Gaia Team
 */
@Data
public class AgentExecuteRequest {
    
    /** Agent ID */
    @NotBlank(message = "Agent ID 不能为空")
    private String agentId;
    
    /** 用户输入 */
    @NotBlank(message = "用户输入不能为空")
    private String input;
    
    /** 会话 ID */
    private String sessionId;
    
    /** 最大迭代次数 */
    private Integer maxIterations;
    
    /** 额外参数 */
    private Map<String, Object> params;
}
