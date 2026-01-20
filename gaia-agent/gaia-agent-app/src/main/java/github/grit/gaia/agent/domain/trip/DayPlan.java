package github.grit.gaia.agent.domain.trip;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 单日行程
 *
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayPlan {

    /**
     * 日期
     */
    @NotBlank(message = "日期不能为空")
    private String date;

    /**
     * 第几天(从0开始)
     */
    @NotNull(message = "天数索引不能为空")
    private Integer dayIndex;

    /**
     * 当日行程描述
     */
    @NotBlank(message = "行程描述不能为空")
    private String description;

    /**
     * 交通方式
     */
    @NotBlank(message = "交通方式不能为空")
    private String transportation;

    /**
     * 住宿安排
     */
    @NotBlank(message = "住宿安排不能为空")
    private String accommodation;

    /**
     * 酒店信息
     */
    private Hotel hotel;

    /**
     * 景点列表
     */
    @Builder.Default
    private List<Attraction> attractions = new ArrayList<>();

    /**
     * 餐饮安排
     */
    @Builder.Default
    private List<Meal> meals = new ArrayList<>();
}
