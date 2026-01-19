package github.grit.gaia.agent.infra.ai.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tool 注册中心
 * 管理所有可用的工具
 * 
 * @author Gaia Team
 */
@Slf4j
@Component
public class ToolRegistry {
    
    private final Map<String, Tool> tools = new ConcurrentHashMap<>();
    
    /**
     * 注册工具
     * 
     * @param tool 工具实例
     */
    public void register(Tool tool) {
        if (tool == null || tool.getName() == null) {
            throw new IllegalArgumentException("工具或工具名称不能为空");
        }
        
        tools.put(tool.getName(), tool);
        log.info("注册工具: {}", tool.getName());
    }
    
    /**
     * 获取工具
     * 
     * @param toolName 工具名称
     * @return 工具实例
     */
    public Tool getTool(String toolName) {
        Tool tool = tools.get(toolName);
        if (tool == null) {
            throw new IllegalArgumentException("工具不存在: " + toolName);
        }
        return tool;
    }
    
    /**
     * 执行工具
     * 
     * @param toolCall 工具调用信息
     * @return 执行结果
     */
    public ToolResult executeTool(ToolCall toolCall) {
        try {
            Tool tool = getTool(toolCall.getToolName());
            
            // 验证参数
            if (!tool.validateParams(toolCall.getParams())) {
                return ToolResult.builder()
                        .success(false)
                        .error("参数验证失败")
                        .build();
            }
            
            // 执行工具
            return tool.execute(toolCall.getParams());
            
        } catch (Exception e) {
            log.error("工具执行失败: {}", toolCall.getToolName(), e);
            return ToolResult.builder()
                    .success(false)
                    .error(e.getMessage())
                    .build();
        }
    }
    
    /**
     * 获取所有工具
     */
    public Map<String, Tool> getAllTools() {
        return new ConcurrentHashMap<>(tools);
    }
    
    /**
     * 注销工具
     */
    public void unregister(String toolName) {
        tools.remove(toolName);
        log.info("注销工具: {}", toolName);
    }
}
