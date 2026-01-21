package github.grit.gaia.agent.infra.ai.tool.builtin;

/** genAI_master_start */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

/**
 * 随机数生成工具 - 生成随机数、UUID等
 *
 * @author Cursor AI
 * @date 2026-01-21
 */
@Slf4j
@Component
public class RandomTool implements Function<RandomTool.Request, RandomTool.Response> {

    private final Random random = new SecureRandom();

    /**
     * 随机数生成请求参数
     */
    public record Request(
            @JsonProperty(value = "type")
            @JsonPropertyDescription("生成类型: 'number'(随机整数), 'uuid'(UUID), 'boolean'(布尔值)。默认为 'number'")
            String type,

            @JsonProperty(value = "min")
            @JsonPropertyDescription("最小值（仅对 number 类型有效）")
            Integer min,

            @JsonProperty(value = "max")
            @JsonPropertyDescription("最大值（仅对 number 类型有效）")
            Integer max
    ) {
    }

    /**
     * 随机数生成响应
     */
    public record Response(
            boolean success,
            String result,
            String type,
            String error
    ) {
    }

    @Override
    public Response apply(Request request) {
        try {
            String type = (request.type() != null && !request.type().isEmpty())
                    ? request.type().toLowerCase()
                    : "number";

            String result;

            switch (type) {
                case "number":
                    int min = (request.min() != null) ? request.min() : 0;
                    int max = (request.max() != null) ? request.max() : 100;

                    if (min > max) {
                        return new Response(false, null, type, "最小值不能大于最大值");
                    }

                    int randomNumber = random.nextInt(max - min + 1) + min;
                    result = String.valueOf(randomNumber);
                    log.info("生成随机整数: {} (范围: {} - {})", result, min, max);
                    break;

                case "uuid":
                    result = UUID.randomUUID().toString();
                    log.info("生成UUID: {}", result);
                    break;

                case "boolean":
                    result = String.valueOf(random.nextBoolean());
                    log.info("生成随机布尔值: {}", result);
                    break;

                default:
                    return new Response(false, null, type, "不支持的类型: " + type);
            }

            return new Response(true, result, type, null);

        } catch (Exception e) {
            log.error("生成随机数失败", e);
            return new Response(false, null, null, "生成随机数失败: " + e.getMessage());
        }
    }

    /**
     * 获取工具名称
     */
    public static String getToolName() {
        return "random";
    }

    /**
     * 获取工具描述
     */
    public static String getToolDescription() {
        return "生成随机数、UUID或布尔值。" +
                "例如: random({type: 'number', min: 1, max: 100}), random({type: 'uuid'})";
    }
}
/** genAI_master_end */
