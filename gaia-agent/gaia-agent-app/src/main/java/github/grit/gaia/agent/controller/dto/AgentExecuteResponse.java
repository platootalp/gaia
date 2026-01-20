package github.grit.gaia.agent.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Agent 执行响应
 * 
 * @author Gaia Team
 */
@Data
@Builder
public class AgentExecuteResponse {
    
    /** 会话 ID */
    private String sessionId;
    
    /** 执行结果 */
    private String result;
    
    /** 迭代次数 */
    private Integer iterations;
    
    /** 执行耗时（毫秒） */
    private Long duration;
}
