package github.grit.gaia.agent.core.prompt;

import lombok.Data;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prompt 模板
 * 用于生成和管理提示词
 * 
 * @author Gaia Team
 */
@Data
public class PromptTemplate {
    
    /** 模板名称 */
    private String name;
    
    /** 模板内容 */
    private String template;
    
    /** 变量占位符模式 */
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.*?)\\}\\}");
    
    public PromptTemplate(String name, String template) {
        this.name = name;
        this.template = template;
    }
    
    /**
     * 渲染模板
     * 
     * @param variables 变量值
     * @return 渲染后的文本
     */
    public String render(Map<String, Object> variables) {
        if (template == null || template.isEmpty()) {
            return "";
        }
        
        String result = template;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        
        while (matcher.find()) {
            String placeholder = matcher.group(0);
            String key = matcher.group(1).trim();
            
            Object value = variables.get(key);
            if (value != null) {
                result = result.replace(placeholder, value.toString());
            }
        }
        
        return result;
    }
    
    /**
     * 创建默认的 ReAct 提示词模板
     */
    public static PromptTemplate reactTemplate() {
        String template = """
                你是一个智能助手，可以使用以下工具来帮助用户：
                
                {{tools}}
                
                请按照以下格式思考和行动：
                
                Thought: 分析当前情况，思考下一步该做什么
                Action: 如果需要使用工具，说明要使用的工具和参数
                Observation: 观察工具执行结果
                ... (重复 Thought/Action/Observation 直到得出答案)
                Final Answer: 最终答案
                
                用户问题：{{question}}
                """;
        
        return new PromptTemplate("react", template);
    }
    
    /**
     * 创建默认的 Plan-Execute 提示词模板
     */
    public static PromptTemplate planExecuteTemplate() {
        String template = """
                请为以下任务制定详细的执行计划：
                
                任务：{{task}}
                
                请将任务分解为多个可执行的步骤，每个步骤应该：
                1. 清晰明确
                2. 可独立执行
                3. 有明确的输入和输出
                
                请按照以下格式输出：
                Step 1: [步骤描述]
                Step 2: [步骤描述]
                ...
                """;
        
        return new PromptTemplate("plan-execute", template);
    }
}
