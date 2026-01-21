package github.grit.gaia.agent.infra.ai.tool.builtin;

/** genAI_master_start */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * 字符串处理工具 - 提供常用的字符串操作功能
 *
 * @author Cursor AI
 * @date 2026-01-21
 */
@Slf4j
@Component
public class StringUtilTool implements Function<StringUtilTool.Request, StringUtilTool.Response> {

    /**
     * 字符串处理请求参数
     */
    public record Request(
            @JsonProperty(required = true, value = "text")
            @JsonPropertyDescription("要处理的文本内容")
            String text,

            @JsonProperty(required = true, value = "operation")
            @JsonPropertyDescription("操作类型: 'uppercase'(转大写), 'lowercase'(转小写), 'reverse'(反转), 'length'(获取长度), 'trim'(去除首尾空格)")
            String operation
    ) {
    }

    /**
     * 字符串处理响应
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
            if (request.text() == null) {
                return new Response(false, null, "文本内容不能为空");
            }

            String operation = request.operation() != null ? request.operation().toLowerCase() : "";
            String result;

            switch (operation) {
                case "uppercase":
                    result = request.text().toUpperCase();
                    log.info("转换为大写: {} -> {}", request.text(), result);
                    break;

                case "lowercase":
                    result = request.text().toLowerCase();
                    log.info("转换为小写: {} -> {}", request.text(), result);
                    break;

                case "reverse":
                    result = new StringBuilder(request.text()).reverse().toString();
                    log.info("反转字符串: {} -> {}", request.text(), result);
                    break;

                case "length":
                    result = String.valueOf(request.text().length());
                    log.info("获取字符串长度: {} = {}", request.text(), result);
                    break;

                case "trim":
                    result = request.text().trim();
                    log.info("去除首尾空格: '{}' -> '{}'", request.text(), result);
                    break;

                default:
                    return new Response(false, null, "不支持的操作类型: " + operation);
            }

            return new Response(true, result, null);

        } catch (Exception e) {
            log.error("字符串处理失败", e);
            return new Response(false, null, "字符串处理失败: " + e.getMessage());
        }
    }

    /**
     * 获取工具名称
     */
    public static String getToolName() {
        return "string_util";
    }

    /**
     * 获取工具描述
     */
    public static String getToolDescription() {
        return "字符串处理工具，支持转大写、转小写、反转、获取长度、去除空格等操作。" +
                "例如: string_util({text: 'hello', operation: 'uppercase'})";
    }
}
/** genAI_master_end */
