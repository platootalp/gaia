package github.grit.gaia.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import github.grit.gaia.infrastructure.persistence.entity.GaiaTenantAccount;

/**
* 租户-账号关联表，多租户支持，包含角色和当前激活租户信息 Repository 接口
*
* @author plato
* @since 2025-07-06 15:48:45
*/
public interface IGaiaTenantAccountRepository extends IService<GaiaTenantAccount> {

}
