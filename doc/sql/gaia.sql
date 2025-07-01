CREATE TABLE gaia_tenant (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '租户ID，自增主键',
    name VARCHAR(255) NOT NULL COMMENT '租户名称',
    encrypt_public_key TEXT COMMENT '租户加密用的公钥，用于数据加密/解密',
    plan VARCHAR(255) NOT NULL DEFAULT 'basic' COMMENT '租户套餐类型（basic、pro、enterprise等）',
    status VARCHAR(255) NOT NULL DEFAULT 'normal' COMMENT '租户状态（normal=正常，disabled=禁用，expired=过期）',
    custom_config JSON COMMENT '租户自定义配置，JSON结构，支持灵活扩展',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='租户表，记录租户基础信息';

CREATE TABLE gaia_account (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '账号ID，自增主键',

    name VARCHAR(255) NOT NULL COMMENT '用户名',
    email VARCHAR(255) NOT NULL COMMENT '邮箱地址',

    password VARCHAR(255) DEFAULT NULL COMMENT '密码（哈希存储）',
    password_salt VARCHAR(255) DEFAULT NULL COMMENT '密码盐',

    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    interface_language VARCHAR(255) DEFAULT NULL COMMENT '界面语言（如zh-CN, en-US）',
    interface_theme VARCHAR(255) DEFAULT NULL COMMENT '界面主题（dark, light等）',
    timezone VARCHAR(255) DEFAULT NULL COMMENT '时区（如Asia/Shanghai）',

    last_login_at DATETIME DEFAULT NULL COMMENT '上次登录时间',
    last_login_ip VARCHAR(255) DEFAULT NULL COMMENT '上次登录IP',

    last_active_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近活跃时间',
    status VARCHAR(16) NOT NULL DEFAULT 'active' COMMENT '账号状态（active=正常，disabled=禁用）',

    initialized_at DATETIME DEFAULT NULL COMMENT '首次初始化时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    -- 索引设计
    UNIQUE KEY account_email_unique (email),                          -- 邮箱唯一
    KEY idx_account_status (status)                                  -- 状态查询
) COMMENT='账号表，记录用户基础信息';

CREATE TABLE gaia_tenant_account (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '账号ID，自增主键',
    tenant_id BIGINT UNSIGNED NOT NULL COMMENT '租户ID',
    account_id BIGINT UNSIGNED NOT NULL COMMENT '账号ID',
    current BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否为当前激活租户',
    role VARCHAR(16) NOT NULL DEFAULT 'normal' COMMENT '账号在租户下的角色（owner, admin, editor, normal, dataset_operator）',
    invited_by  VARCHAR(255) DEFAULT NULL COMMENT '邀请者邮箱',

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    -- 联合唯一约束
    UNIQUE KEY uk_tenant_account (tenant_id, account_id)
) COMMENT='租户-账号关联表，多租户支持，包含角色和当前激活租户信息';

CREATE TABLE gaia_account_integrate (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID，自增',
    account_id BIGINT UNSIGNED NOT NULL COMMENT '本系统账号ID，关联gaia_account表',
    provider VARCHAR(32) NOT NULL COMMENT '第三方平台标识（如github、google、wechat、feishu等）',
    open_id VARCHAR(255) NOT NULL COMMENT '第三方平台用户唯一ID（openid、unionid等）',
    encrypted_token VARCHAR(1024) NOT NULL COMMENT '加密存储的第三方授权token',
    refresh_token VARCHAR(1024) DEFAULT NULL COMMENT 'refresh_token，如果有',
    expires_at DATETIME DEFAULT NULL COMMENT 'access_token的过期时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    -- 唯一约束
    UNIQUE KEY uk_account_provider (account_id, provider) COMMENT '同一账号在一个平台只能绑定一个账户',
    UNIQUE KEY uk_provider_open_id (provider, open_id) COMMENT '同一平台的open_id在系统中唯一',

    -- 查询优化索引
    KEY idx_account_id (account_id) COMMENT '根据账号ID查询绑定信息'
) COMMENT='账号与第三方平台绑定表，支持OAuth第三方登录和token管理';



