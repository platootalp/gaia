package github.grit.gaia.agent.controller.dto;

import github.grit.gaia.agent.domain.trip.Budget;
import github.grit.gaia.agent.domain.trip.DayPlan;
import github.grit.gaia.agent.domain.trip.WeatherInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅行计划
 * 
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripPlanResponse {
    
    /**
     * 目的地城市
     */
    @NotBlank(message = "目的地城市不能为空")
    private String city;
    
    /**
     * 开始日期
     */
    @NotBlank(message = "开始日期不能为空")
    private String startDate;
    
    /**
     * 结束日期
     */
    @NotBlank(message = "结束日期不能为空")
    private String endDate;
    
    /**
     * 每日行程
     */
    @Builder.Default
    private List<DayPlan> days = new ArrayList<>();
    
    /**
     * 天气信息
     */
    @Builder.Default
    private List<WeatherInfo> weatherInfo = new ArrayList<>();
    
    /**
     * 总体建议
     */
    @NotBlank(message = "总体建议不能为空")
    private String overallSuggestions;
    
    /**
     * 预算信息
     */
    private Budget budget;
}
