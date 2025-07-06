package github.grit.gaia.infrastructure.persistence.repository.impl;

import github.grit.gaia.infrastructure.persistence.entity.GaiaTenant;
import github.grit.gaia.infrastructure.persistence.mapper.GaiaTenantMapper;
import github.grit.gaia.infrastructure.persistence.repository.IGaiaTenantRepository;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.grit.gaia.infrastructure.persistence.entity.GaiaAccountIntegrate;


/**
* 租户表，记录租户基础信息 Repository 实现类
*
* @author plato
* @since 2025-07-06 15:48:45
*/
@Repository
public class GaiaTenantRepositoryImpl extends ServiceImpl<GaiaTenantMapper, GaiaTenant> implements IGaiaTenantRepository {

}
