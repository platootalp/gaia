package github.grit.gaia.agent.infra.ai.tool;

/**
 * genAI_master_start
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工具管理器
 * 负责工具的注册、管理和提供
 */
@Slf4j
@Component
public class ToolManager {

    /**
     * 工具注册表：工具名 -> 工具回调
     */
    private final Map<String, ToolCallback> toolRegistry = new ConcurrentHashMap<>();

    /**
     * 注册工具
     *
     * @param toolName     工具名称
     * @param toolCallback 工具回调
     */
    public void registerTool(String toolName, ToolCallback toolCallback) {
        if (toolCallback == null) {
            log.warn("ToolManager: 尝试注册空工具，已忽略");
            return;
        }

        if (toolName == null || toolName.trim().isEmpty()) {
            log.warn("ToolManager: 工具名称为空，已忽略");
            return;
        }

        if (toolRegistry.containsKey(toolName)) {
            log.warn("ToolManager: 工具 [{}] 已存在，将被覆盖", toolName);
        }

        toolRegistry.put(toolName, toolCallback);
        log.info("ToolManager: 成功注册工具 [{}]", toolName);
    }

    /**
     * 批量注册工具
     *
     * @param tools 工具映射表（工具名 -> 工具回调）
     */
    public void registerTools(Map<String, ToolCallback> tools) {
        if (tools == null || tools.isEmpty()) {
            log.warn("ToolManager: 尝试批量注册空工具列表，已忽略");
            return;
        }

        tools.forEach(this::registerTool);
    }

    /**
     * 获取指定名称的工具
     *
     * @param toolName 工具名称
     * @return 工具回调，不存在则返回null
     */
    public ToolCallback getTool(String toolName) {
        return toolRegistry.get(toolName);
    }

    /**
     * 获取所有工具列表
     * 供 ChatClient 使用
     *
     * @return 工具回调列表
     */
    public List<ToolCallback> getFunctions() {
        return new ArrayList<>(toolRegistry.values());
    }

    /**
     * 获取所有工具名称
     *
     * @return 工具名称列表
     */
    public List<String> getToolNames() {
        return new ArrayList<>(toolRegistry.keySet());
    }

    /**
     * 检查工具是否已注册
     *
     * @param toolName 工具名称
     * @return true表示已注册
     */
    public boolean hasTool(String toolName) {
        return toolRegistry.containsKey(toolName);
    }

    /**
     * 移除工具
     *
     * @param toolName 工具名称
     * @return 被移除的工具回调，不存在则返回null
     */
    public ToolCallback removeTool(String toolName) {
        ToolCallback removed = toolRegistry.remove(toolName);
        if (removed != null) {
            log.info("ToolManager: 成功移除工具 [{}]", toolName);
        }
        return removed;
    }

    /**
     * 清空所有工具
     */
    public void clear() {
        int count = toolRegistry.size();
        toolRegistry.clear();
        log.info("ToolManager: 已清空所有工具，共 {} 个", count);
    }

    /**
     * 获取工具数量
     *
     * @return 工具数量
     */
    public int getToolCount() {
        return toolRegistry.size();
    }
}
/** genAI_master_end */
