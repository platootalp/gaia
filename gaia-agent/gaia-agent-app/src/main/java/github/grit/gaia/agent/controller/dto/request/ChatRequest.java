package github.grit.gaia.agent.controller.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 聊天请求
 * 
 * @author Gaia Team
 */
@Data
public class ChatRequest {
    
    /** 会话 ID */
    private String sessionId;
    
    /** 消息内容 */
    @NotBlank(message = "消息内容不能为空")
    private String message;
    
    /** 是否流式输出 */
    private Boolean stream;
}
