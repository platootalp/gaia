package github.grit.gaia.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 租户订阅表，记录租户订阅信息
 * </p>
 *
 * @author plato
 * @since 2025-07-06 15:31:18
 */
@Getter
@Setter
@TableName("gaia_tenant_subscription")
public class GaiaTenantSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订阅ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 套餐（free、pro、enterprise）
     */
    private String plan;

    /**
     * 生效开始时间
     */
    private LocalDateTime startTime;

    /**
     * 生效结束时间
     */
    private LocalDateTime endTime;

    /**
     * 订阅状态（active=生效，expired=过期，cancelled=取消）
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
