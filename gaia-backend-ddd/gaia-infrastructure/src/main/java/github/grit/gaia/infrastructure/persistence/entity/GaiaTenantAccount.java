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
 * 租户-账号关联表，多租户支持，包含角色和当前激活租户信息
 * </p>
 *
 * @author plato
 * @since 2025-07-06 15:31:18
 */
@Getter
@Setter
@TableName("gaia_tenant_account")
public class GaiaTenantAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号ID，自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 账号ID
     */
    private Long accountId;

    /**
     * 是否为当前激活租户
     */
    private Boolean current;

    /**
     * 账号在租户下的角色（owner, admin, editor, normal, dataset_operator）
     */
    private String role;

    /**
     * 邀请者邮箱
     */
    private String invitedBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
