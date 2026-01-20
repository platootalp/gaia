package github.grit.gaia.agent.domain.trip;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 景点信息
 *
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {

    /**
     * 景点名称
     */
    @NotBlank(message = "景点名称不能为空")
    private String name;

    /**
     * 地址
     */
    @NotBlank(message = "地址不能为空")
    private String address;

    /**
     * 经纬度坐标
     */
    @NotNull(message = "位置信息不能为空")
    private Location location;

    /**
     * 建议游览时间(分钟)
     */
    @NotNull(message = "游览时间不能为空")
    @Min(value = 1, message = "游览时间必须大于0")
    private Integer visitDuration;

    /**
     * 景点描述
     */
    @NotBlank(message = "景点描述不能为空")
    private String description;

    /**
     * 景点类别
     */
    @Builder.Default
    private String category = "景点";

    /**
     * 评分(0-5分)
     */
    @Min(value = 0, message = "评分不能小于0")
    @Max(value = 5, message = "评分不能大于5")
    private Double rating;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 门票价格(元)
     */
    @Builder.Default
    @Min(value = 0, message = "门票价格不能小于0")
    private Integer ticketPrice = 0;
}
