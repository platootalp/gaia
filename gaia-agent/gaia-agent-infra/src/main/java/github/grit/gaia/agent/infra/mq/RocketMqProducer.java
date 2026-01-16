package github.grit.gaia.agent.infra.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 生产者
 * 
 * @author Gaia Team
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RocketMqProducer {
    
    /**
     * 发送消息
     */
    public void sendMessage(String topic, String message) {
        log.info("发送消息到 topic: {}", topic);
        // TODO: 实现消息发送
    }
    
    /**
     * 发送异步消息
     */
    public void sendAsyncMessage(String topic, String message) {
        log.info("发送异步消息到 topic: {}", topic);
        // TODO: 实现异步消息发送
    }
}
