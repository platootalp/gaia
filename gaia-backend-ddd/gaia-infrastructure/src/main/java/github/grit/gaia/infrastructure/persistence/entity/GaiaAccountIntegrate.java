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
 * 账号与第三方平台绑定表，支持OAuth第三方登录和token管理
 * </p>
 *
 * @author plato
 * @since 2025-07-06 15:31:18
 */
@Getter
@Setter
@TableName("gaia_account_integrate")
public class GaiaAccountIntegrate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 本系统账号ID，关联gaia_account表
     */
    private Long accountId;

    /**
     * 第三方平台标识（如github、google、wechat、feishu等）
     */
    private String provider;

    /**
     * 第三方平台用户唯一ID（openid、unionid等）
     */
    private String openId;

    /**
     * 加密存储的第三方授权token
     */
    private String encryptedToken;

    /**
     * refresh_token，如果有
     */
    private String refreshToken;

    /**
     * access_token的过期时间
     */
    private LocalDateTime expiresAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
