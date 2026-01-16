package github.grit.gaia.infrastructure.persistence.repository.impl;

import github.grit.gaia.infrastructure.persistence.entity.GaiaTenantAccount;
import github.grit.gaia.infrastructure.persistence.mapper.GaiaTenantAccountMapper;
import github.grit.gaia.infrastructure.persistence.repository.IGaiaTenantAccountRepository;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.grit.gaia.infrastructure.persistence.entity.GaiaAccountIntegrate;


/**
* 租户-账号关联表，多租户支持，包含角色和当前激活租户信息 Repository 实现类
*
* @author plato
* @since 2025-07-06 15:48:45
*/
@Repository
public class GaiaTenantAccountRepositoryImpl extends ServiceImpl<GaiaTenantAccountMapper, GaiaTenantAccount> implements IGaiaTenantAccountRepository {

}
