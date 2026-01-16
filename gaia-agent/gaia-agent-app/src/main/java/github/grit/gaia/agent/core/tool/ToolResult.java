package github.grit.gaia.agent.core.tool;

import lombok.Builder;
import lombok.Data;

/**
 * 工具执行结果
 * 
 * @author Gaia Team
 */
@Data
@Builder
public class ToolResult {
    
    /** 是否成功 */
    private Boolean success;
    
    /** 执行结果 */
    private String result;
    
    /** 错误信息 */
    private String error;
    
    /** 执行耗时（毫秒） */
    private Long duration;
}
