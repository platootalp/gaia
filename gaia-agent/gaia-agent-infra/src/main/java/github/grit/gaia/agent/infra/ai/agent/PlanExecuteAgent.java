package github.grit.gaia.agent.infra.ai.agent;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

/**
 * Plan-Execute Agent 实现
 * 先规划再执行的模式
 * 
 * @author Gaia Team
 */
@Slf4j
public class PlanExecuteAgent extends Agent {
    
    @Override
    public String execute(AgentContext context) {
        log.info("Plan-Execute Agent 开始执行任务: {}", context.getUserInput());
        
        // 第一步：规划
        List<String> plan = plan(context.getUserInput());
        log.info("生成执行计划，共 {} 步", plan.size());
        
        // 第二步：逐步执行
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < plan.size(); i++) {
            String step = plan.get(i);
            log.info("执行步骤 {}/{}: {}", i + 1, plan.size(), step);
            
            String stepResult = executeStep(step, context);
            result.append(stepResult).append("\n");
            
            // 更新上下文
            context.getVariables().put("step_" + i + "_result", stepResult);
        }
        
        log.info("Plan-Execute Agent 完成任务");
        return result.toString();
    }
    
    @Override
    public List<String> plan(String task) {
        log.info("开始规划任务: {}", task);
        
        // TODO: 调用 LLM 生成执行计划
        List<String> steps = new ArrayList<>();
        steps.add("分析用户需求");
        steps.add("收集必要信息");
        steps.add("执行核心任务");
        steps.add("总结结果");
        
        return steps;
    }
    
    @Override
    protected String executeStep(String step, AgentContext context) {
        // TODO: 执行单个步骤
        // 可能需要调用工具、查询数据库等
        
        log.debug("执行步骤: {}", step);
        return "步骤执行完成: " + step;
    }
}
