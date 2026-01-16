package github.grit.gaia.infrastructure.persistence.repository.impl;

import github.grit.gaia.infrastructure.persistence.entity.GaiaTenantSubscription;
import github.grit.gaia.infrastructure.persistence.mapper.GaiaTenantSubscriptionMapper;
import github.grit.gaia.infrastructure.persistence.repository.IGaiaTenantSubscriptionRepository;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.grit.gaia.infrastructure.persistence.entity.GaiaAccountIntegrate;


/**
* 租户订阅表，记录租户订阅信息 Repository 实现类
*
* @author plato
* @since 2025-07-06 15:48:45
*/
@Repository
public class GaiaTenantSubscriptionRepositoryImpl extends ServiceImpl<GaiaTenantSubscriptionMapper, GaiaTenantSubscription> implements IGaiaTenantSubscriptionRepository {

}
