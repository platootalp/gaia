package github.grit.gaia.agent.infra.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 通义千问聊天客户端
 * 基于 spring-ai-alibaba
 * 
 * @author Gaia Team
 */
@Slf4j
@Component
public class QwenChatClient {
    
    // TODO: 注入 spring-ai-alibaba 的 ChatClient
    
    /**
     * 发送聊天消息
     * 
     * @param prompt 提示词
     * @return 响应内容
     */
    public String chat(String prompt) {
        log.info("发送聊天请求");
        // TODO: 实现聊天逻辑
        return "这是一个示例响应";
    }
    
    /**
     * 流式聊天
     * 
     * @param prompt 提示词
     * @return 流式响应
     */
    public void streamChat(String prompt) {
        // TODO: 实现流式聊天
    }
}
