package github.grit.gaia.agent.infra.ai.tool.builtin;

/** genAI_master_start */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * 计算器工具 - 执行基本的数学运算
 * 支持加、减、乘、除四则运算
 *
 * @author Cursor AI
 * @date 2026-01-21
 */
@Slf4j
@Component
public class CalculatorTool implements Function<CalculatorTool.Request, CalculatorTool.Response> {

    /**
     * 计算请求参数
     */
    public record Request(
            @JsonProperty(required = true, value = "expression")
            @JsonPropertyDescription("数学表达式，例如: '2 + 2', '10 * 5', '100 / 4'")
            String expression
    ) {
    }

    /**
     * 计算结果响应
     */
    public record Response(
            boolean success,
            String result,
            String error
    ) {
    }

    @Override
    public Response apply(Request request) {
        try {
            log.info("执行计算: {}", request.expression());

            // 简单的表达式解析（仅支持基本四则运算）
            String expression = request.expression().trim().replaceAll("\\s+", "");
            double result = evaluateExpression(expression);

            log.info("计算结果: {} = {}", request.expression(), result);
            return new Response(true, String.valueOf(result), null);

        } catch (Exception e) {
            log.error("计算失败: {}", request.expression(), e);
            return new Response(false, null, "计算失败: " + e.getMessage());
        }
    }

    /**
     * 简单的表达式计算（支持 +、-、*、/ 运算）
     */
    private double evaluateExpression(String expression) {
        // 优先处理乘除
        if (expression.contains("*") || expression.contains("/")) {
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (c == '*' || c == '/') {
                    // 提取左右操作数
                    String left = extractLeftOperand(expression, i);
                    String right = extractRightOperand(expression, i);

                    double leftVal = Double.parseDouble(left);
                    double rightVal = Double.parseDouble(right);
                    double result = (c == '*') ? leftVal * rightVal : leftVal / rightVal;

                    // 替换表达式
                    String newExpression = expression.substring(0, i - left.length())
                            + result
                            + expression.substring(i + 1 + right.length());
                    return evaluateExpression(newExpression);
                }
            }
        }

        // 处理加减
        for (int i = 1; i < expression.length(); i++) { // 从 1 开始以支持负数
            char c = expression.charAt(i);
            if (c == '+' || c == '-') {
                String left = expression.substring(0, i);
                String right = expression.substring(i + 1);
                double leftVal = evaluateExpression(left);
                double rightVal = evaluateExpression(right);
                return (c == '+') ? leftVal + rightVal : leftVal - rightVal;
            }
        }

        // 单个数字
        return Double.parseDouble(expression);
    }

    /**
     * 提取左操作数
     */
    private String extractLeftOperand(String expression, int operatorPos) {
        int start = operatorPos - 1;
        while (start > 0 && isPartOfNumber(expression.charAt(start - 1))) {
            start--;
        }
        return expression.substring(start, operatorPos);
    }

    /**
     * 提取右操作数
     */
    private String extractRightOperand(String expression, int operatorPos) {
        int end = operatorPos + 1;
        // 支持负号
        if (end < expression.length() && expression.charAt(end) == '-') {
            end++;
        }
        while (end < expression.length() && isPartOfNumber(expression.charAt(end))) {
            end++;
        }
        return expression.substring(operatorPos + 1, end);
    }

    /**
     * 判断字符是否是数字的一部分
     */
    private boolean isPartOfNumber(char c) {
        return Character.isDigit(c) || c == '.' || c == '-';
    }

    /**
     * 获取工具名称
     */
    public static String getToolName() {
        return "calculator";
    }

    /**
     * 获取工具描述
     */
    public static String getToolDescription() {
        return "执行基本的数学计算，支持加减乘除四则运算。例如: calculator('2+2'), calculator('10*5')";
    }
}
/** genAI_master_end */
