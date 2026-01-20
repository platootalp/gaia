package github.grit.gaia.agent.domain.trip;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预算信息
 *
 * @author Gaia Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    /**
     * 景点门票总费用
     */
    @Builder.Default
    private Integer totalAttractions = 0;

    /**
     * 酒店总费用
     */
    @Builder.Default
    private Integer totalHotels = 0;

    /**
     * 餐饮总费用
     */
    @Builder.Default
    private Integer totalMeals = 0;

    /**
     * 交通总费用
     */
    @Builder.Default
    private Integer totalTransportation = 0;

    /**
     * 总费用
     */
    @Builder.Default
    private Integer total = 0;
}