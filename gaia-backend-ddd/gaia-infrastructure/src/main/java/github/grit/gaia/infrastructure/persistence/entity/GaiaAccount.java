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
 * 账号表，记录用户基础信息
 * </p>
 *
 * @author plato
 * @since 2025-07-06 15:31:18
 */
@Getter
@Setter
@TableName("gaia_account")
public class GaiaAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号ID，自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 密码（哈希存储）
     */
    private String password;

    /**
     * 密码盐
     */
    private String passwordSalt;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 界面语言（如zh-CN, en-US）
     */
    private String interfaceLanguage;

    /**
     * 界面主题（dark, light等）
     */
    private String interfaceTheme;

    /**
     * 时区（如Asia/Shanghai）
     */
    private String timezone;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginAt;

    /**
     * 上次登录IP
     */
    private String lastLoginIp;

    /**
     * 最近活跃时间
     */
    private LocalDateTime lastActiveAt;

    /**
     * 账号状态（enable=启用，disabled=禁用）
     */
    private String status;

    /**
     * 首次初始化时间
     */
    private LocalDateTime initializedAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
