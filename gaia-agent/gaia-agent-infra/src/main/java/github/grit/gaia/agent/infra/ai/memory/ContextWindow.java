package github.grit.gaia.agent.infra.ai.memory;

import lombok.Data;
import java.util.List;

/**
 * 上下文窗口
 * 管理 LLM 的上下文长度
 * 
 * @author Gaia Team
 */
@Data
public class ContextWindow {
    
    /** 最大 token 数 */
    private int maxTokens;
    
    /** 当前 token 数 */
    private int currentTokens;
    
    public ContextWindow(int maxTokens) {
        this.maxTokens = maxTokens;
        this.currentTokens = 0;
    }
    
    /**
     * 计算消息 token 数量
     * 简单估算：中文字符 = 1.5 token，英文单词 = 1 token
     */
    public int estimateTokens(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        
        int chineseCount = 0;
        int englishCount = 0;
        
        for (char c : text.toCharArray()) {
            if (c >= 0x4E00 && c <= 0x9FA5) {
                chineseCount++;
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                englishCount++;
            }
        }
        
        return (int) (chineseCount * 1.5 + englishCount / 4.0);
    }
    
    /**
     * 是否可以添加更多内容
     */
    public boolean canAdd(String text) {
        int tokens = estimateTokens(text);
        return currentTokens + tokens <= maxTokens;
    }
    
    /**
     * 添加文本并更新 token 计数
     */
    public void add(String text) {
        currentTokens += estimateTokens(text);
    }
    
    /**
     * 重置计数
     */
    public void reset() {
        currentTokens = 0;
    }
    
    /**
     * 获取剩余容量
     */
    public int getRemainingTokens() {
        return Math.max(0, maxTokens - currentTokens);
    }
}
