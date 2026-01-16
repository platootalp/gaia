package github.grit.gaia.infrastructure.persistence.repository.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import github.grit.gaia.domain.repository.IGaiaTenantRepository;
import github.grit.gaia.infrastructure.persistence.entity.GaiaTenant;
import github.grit.gaia.infrastructure.persistence.mapper.GaiaTenantMapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
* 租户表，记录租户基础信息 Repository 实现类
*
* @author plato
* @since 2025-07-06 15:48:45
*/
@Repository
public class GaiaTenantRepositoryImpl extends ServiceImpl<GaiaTenantMapper, GaiaTenant> implements IGaiaTenantRepository {

}
