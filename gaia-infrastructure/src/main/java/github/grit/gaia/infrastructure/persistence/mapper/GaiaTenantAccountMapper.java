package github.grit.gaia.infrastructure.persistence.mapper;

import github.grit.gaia.infrastructure.persistence.entity.GaiaTenantAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 租户-账号关联表，多租户支持，包含角色和当前激活租户信息 Mapper 接口
 * </p>
 *
 * @author plato
 * @since 2025-07-06 15:31:18
 */
public interface GaiaTenantAccountMapper extends BaseMapper<GaiaTenantAccount> {

}
