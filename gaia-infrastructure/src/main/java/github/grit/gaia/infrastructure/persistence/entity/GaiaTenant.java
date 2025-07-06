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
 * 租户表，记录租户基础信息
 * </p>
 *
 * @author plato
 * @since 2025-07-06 15:31:18
 */
@Getter
@Setter
@TableName("gaia_tenant")
public class GaiaTenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID，自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 租户加密用的公钥，用于数据加密/解密
     */
    private String encryptPublicKey;

    /**
     * 租户状态（enable=启用，disabled=禁用）
     */
    private String status;

    /**
     * 租户自定义配置，JSON结构，支持灵活扩展
     */
    private String customConfig;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
