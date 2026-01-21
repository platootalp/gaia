package github.grit.gaia.agent.infra.ai.tool.builtin;

/** genAI_master_start */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * 日期时间工具 - 获取当前日期时间信息
 *
 * @author Cursor AI
 * @date 2026-01-21
 */
@Slf4j
@Component
public class DateTimeTool implements Function<DateTimeTool.Request, DateTimeTool.Response> {

    /**
     * 日期时间请求参数
     */
    public record Request(
            @JsonProperty(value = "timezone")
            @JsonPropertyDescription("时区，例如: 'Asia/Shanghai', 'America/New_York'。默认为系统时区")
            String timezone,

            @JsonProperty(value = "format")
            @JsonPropertyDescription("日期格式，例如: 'yyyy-MM-dd HH:mm:ss', 'yyyy/MM/dd'。默认为 'yyyy-MM-dd HH:mm:ss'")
            String format
    ) {
    }

    /**
     * 日期时间响应
     */
    public record Response(
            boolean success,
            String currentDateTime,
            String timestamp,
            String timezone,
            String error
    ) {
    }

    @Override
    public Response apply(Request request) {
        try {
            // 确定时区
            ZoneId zoneId = (request.timezone() != null && !request.timezone().isEmpty())
                    ? ZoneId.of(request.timezone())
                    : ZoneId.systemDefault();

            // 获取当前时间
            LocalDateTime now = LocalDateTime.now(zoneId);

            // 确定格式
            String formatPattern = (request.format() != null && !request.format().isEmpty())
                    ? request.format()
                    : "yyyy-MM-dd HH:mm:ss";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
            String formattedDateTime = now.format(formatter);

            // 获取时间戳
            long timestamp = now.atZone(zoneId).toInstant().toEpochMilli();

            log.info("获取日期时间成功: {} (时区: {})", formattedDateTime, zoneId);

            return new Response(
                    true,
                    formattedDateTime,
                    String.valueOf(timestamp),
                    zoneId.toString(),
                    null
            );

        } catch (Exception e) {
            log.error("获取日期时间失败", e);
            return new Response(false, null, null, null, "获取日期时间失败: " + e.getMessage());
        }
    }

    /**
     * 获取工具名称
     */
    public static String getToolName() {
        return "datetime";
    }

    /**
     * 获取工具描述
     */
    public static String getToolDescription() {
        return "获取当前日期和时间信息，可以指定时区和格式。例如: datetime({timezone: 'Asia/Shanghai'})";
    }
}
/** genAI_master_end */
