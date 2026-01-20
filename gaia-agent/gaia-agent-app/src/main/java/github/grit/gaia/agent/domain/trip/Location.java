package github.grit.gaia.agent.domain.trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 位置信息(经纬度坐标)
 *
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    /**
     * 经度
     */
    @NotNull(message = "经度不能为空")
    @Min(value = -180, message = "经度必须大于等于-180")
    @Max(value = 180, message = "经度必须小于等于180")
    private Double longitude;

    /**
     * 纬度
     */
    @NotNull(message = "纬度不能为空")
    @Min(value = -90, message = "纬度必须大于等于-90")
    @Max(value = 90, message = "纬度必须小于等于90")
    private Double latitude;
}
