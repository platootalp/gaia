package github.grit.gaia.agent.domain.trip;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 餐饮信息
 *
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    /**
     * 餐饮类型：breakfast/lunch/dinner/snack
     */
    @NotBlank(message = "餐饮类型不能为空")
    private String type;

    /**
     * 餐饮名称
     */
    @NotBlank(message = "餐饮名称不能为空")
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 经纬度坐标
     */
    private Location location;

    /**
     * 描述
     */
    private String description;

    /**
     * 预估费用(元)
     */
    @Builder.Default
    private Integer estimatedCost = 0;
}
