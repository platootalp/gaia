package github.grit.gaia.agent.domain.trip;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 天气信息
 *
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfo {

    /**
     * 日期
     */
    @NotBlank(message = "日期不能为空")
    private String date;

    /**
     * 白天天气
     */
    @NotBlank(message = "白天天气不能为空")
    private String dayWeather;

    /**
     * 夜间天气
     */
    @NotBlank(message = "夜间天气不能为空")
    private String nightWeather;

    /**
     * 白天温度(摄氏度)
     */
    @NotNull(message = "白天温度不能为空")
    private Integer dayTemp;

    /**
     * 夜间温度(摄氏度)
     */
    @NotNull(message = "夜间温度不能为空")
    private Integer nightTemp;

    /**
     * 风向
     */
    @NotBlank(message = "风向不能为空")
    private String windDirection;

    /**
     * 风力
     */
    @NotBlank(message = "风力不能为空")
    private String windPower;

    /**
     * 解析温度字符串："16°C" -> 16
     * 用于处理高德地图API返回的温度格式
     */
    public static Integer parseTemperature(String tempStr) {
        if (tempStr == null || tempStr.isEmpty()) {
            return 0;
        }

        try {
            String cleaned = tempStr.replace("°C", "")
                    .replace("℃", "")
                    .replace("°", "")
                    .trim();
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            return 0; // 容错处理
        }
    }
}
