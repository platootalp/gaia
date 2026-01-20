package github.grit.gaia.agent.domain.trip;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 酒店信息
 *
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    /**
     * 酒店名称
     */
    @NotBlank(message = "酒店名称不能为空")
    private String name;

    /**
     * 酒店地址
     */
    @Builder.Default
    private String address = "";

    /**
     * 酒店位置
     */
    private Location location;

    /**
     * 价格范围
     */
    @Builder.Default
    private String priceRange = "";

    /**
     * 评分
     */
    @Builder.Default
    private String rating = "";

    /**
     * 距离景点距离
     */
    @Builder.Default
    private String distance = "";

    /**
     * 酒店类型
     */
    @Builder.Default
    private String type = "";

    /**
     * 预估费用(元/晚)
     */
    @Builder.Default
    private Integer estimatedCost = 0;
}
