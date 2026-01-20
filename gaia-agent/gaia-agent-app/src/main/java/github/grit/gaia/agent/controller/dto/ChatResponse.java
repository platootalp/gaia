package github.grit.gaia.agent.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 聊天响应
 * 
 * @author Gaia Team
 */
@Data
@Builder
public class ChatResponse {
    
    /** 会话 ID */
    private String sessionId;
    
    /** 回复消息 */
    private String message;
    
    /** 时间戳 */
    private Long timestamp;
}
