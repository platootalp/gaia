package github.grit.gaia.agent.core.memory;

import java.util.List;

/**
 * Memory 记忆接口
 * 管理对话历史和上下文
 * 
 * @author Gaia Team
 */
public interface Memory {
    
    /**
     * 添加消息
     * 
     * @param role 角色 (user/assistant/system)
     * @param content 消息内容
     */
    void addMessage(String role, String content);
    
    /**
     * 获取所有消息
     * 
     * @return 消息列表
     */
    List<Message> getMessages();
    
    /**
     * 获取最近 N 条消息
     * 
     * @param n 消息数量
     * @return 消息列表
     */
    List<Message> getRecentMessages(int n);
    
    /**
     * 清空记忆
     */
    void clear();
    
    /**
     * 获取记忆大小
     * 
     * @return 消息数量
     */
    int size();
    
    /**
     * 消息数据类
     */
    @lombok.Data
    @lombok.Builder
    class Message {
        private String role;
        private String content;
        private Long timestamp;
    }
}
