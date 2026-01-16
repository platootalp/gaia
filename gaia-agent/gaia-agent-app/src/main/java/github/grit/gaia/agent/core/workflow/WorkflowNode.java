package github.grit.gaia.agent.core.workflow;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

/**
 * Workflow 节点
 * 工作流中的单个执行单元
 * 
 * @author Gaia Team
 */
@Data
@Builder
public class WorkflowNode {
    
    /** 节点 ID */
    private String nodeId;
    
    /** 节点名称 */
    private String name;
    
    /** 节点类型 (agent/tool/condition/parallel) */
    private String type;
    
    /** 节点配置 */
    private Map<String, Object> config;
    
    /** 是否为起始节点 */
    private boolean isStart;
    
    /** 是否为结束节点 */
    private boolean isEnd;
    
    /** 下一个节点 ID */
    private String nextNodeId;
    
    /** 条件分支 (用于条件节点) */
    private Map<String, String> branches;
}
