package github.grit.gaia.agent.core.tool;

import java.util.Map;

/**
 * Tool 工具接口
 * 定义 Agent 可调用的工具
 * 
 * @author Gaia Team
 */
public interface Tool {
    
    /**
     * 获取工具名称
     */
    String getName();
    
    /**
     * 获取工具描述
     */
    String getDescription();
    
    /**
     * 获取工具参数说明
     */
    Map<String, String> getParameters();
    
    /**
     * 执行工具
     * 
     * @param params 工具参数
     * @return 执行结果
     */
    ToolResult execute(Map<String, Object> params);
    
    /**
     * 验证参数
     * 
     * @param params 工具参数
     * @return 是否有效
     */
    default boolean validateParams(Map<String, Object> params) {
        return true;
    }
}
