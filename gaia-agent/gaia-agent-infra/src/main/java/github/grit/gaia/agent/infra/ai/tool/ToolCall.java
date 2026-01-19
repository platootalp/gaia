package github.grit.gaia.agent.infra.ai.tool;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

/**
 * 工具调用信息
 * 
 * @author Gaia Team
 */
@Data
@Builder
public class ToolCall {
    
    /** 工具名称 */
    private String toolName;
    
    /** 工具参数 */
    private Map<String, Object> params;
    
    /** 调用原因/描述 */
    private String reason;
}
