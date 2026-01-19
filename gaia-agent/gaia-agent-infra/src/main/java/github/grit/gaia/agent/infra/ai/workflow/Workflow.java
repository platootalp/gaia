package github.grit.gaia.agent.infra.ai.workflow;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Workflow 工作流
 * 定义复杂的多步骤任务流程
 * 
 * @author Gaia Team
 */
@Data
public class Workflow {
    
    /** 工作流 ID */
    private String workflowId;
    
    /** 工作流名称 */
    private String name;
    
    /** 工作流描述 */
    private String description;
    
    /** 工作流节点列表 */
    private List<WorkflowNode> nodes;
    
    /** 工作流变量 */
    private Map<String, Object> variables;
    
    /** 是否启用 */
    private Boolean enabled;
    
    /**
     * 获取起始节点
     */
    public WorkflowNode getStartNode() {
        return nodes.stream()
                .filter(WorkflowNode::isStart)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * 根据 ID 获取节点
     */
    public WorkflowNode getNodeById(String nodeId) {
        return nodes.stream()
                .filter(node -> node.getNodeId().equals(nodeId))
                .findFirst()
                .orElse(null);
    }
}
