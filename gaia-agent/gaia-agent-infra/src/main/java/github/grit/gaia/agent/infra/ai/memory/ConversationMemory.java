package github.grit.gaia.agent.infra.ai.memory;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 对话记忆实现
 * 简单的内存存储
 * 
 * @author Gaia Team
 */
@Slf4j
public class ConversationMemory implements Memory {
    
    private final List<Message> messages = new ArrayList<>();
    private final int maxSize;
    
    public ConversationMemory() {
        this(100); // 默认最多保存 100 条消息
    }
    
    public ConversationMemory(int maxSize) {
        this.maxSize = maxSize;
    }
    
    @Override
    public void addMessage(String role, String content) {
        Message message = Message.builder()
                .role(role)
                .content(content)
                .timestamp(System.currentTimeMillis())
                .build();
        
        messages.add(message);
        
        // 超过最大容量时，删除最旧的消息
        if (messages.size() > maxSize) {
            messages.remove(0);
            log.debug("记忆容量已满，删除最旧消息");
        }
    }
    
    @Override
    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }
    
    @Override
    public List<Message> getRecentMessages(int n) {
        int size = messages.size();
        if (n >= size) {
            return getMessages();
        }
        return Collections.unmodifiableList(messages.subList(size - n, size));
    }
    
    @Override
    public void clear() {
        messages.clear();
        log.info("记忆已清空");
    }
    
    @Override
    public int size() {
        return messages.size();
    }
}
