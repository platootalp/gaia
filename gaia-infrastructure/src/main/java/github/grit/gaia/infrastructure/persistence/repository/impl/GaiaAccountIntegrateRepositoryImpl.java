package github.grit.gaia.infrastructure.persistence.repository.impl;

import github.grit.gaia.infrastructure.persistence.entity.GaiaAccountIntegrate;
import github.grit.gaia.infrastructure.persistence.mapper.GaiaAccountIntegrateMapper;
import github.grit.gaia.infrastructure.persistence.repository.IGaiaAccountIntegrateRepository;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.grit.gaia.infrastructure.persistence.entity.GaiaAccountIntegrate;


/**
* 账号与第三方平台绑定表，支持OAuth第三方登录和token管理 Repository 实现类
*
* @author plato
* @since 2025-07-06 15:48:45
*/
@Repository
public class GaiaAccountIntegrateRepositoryImpl extends ServiceImpl<GaiaAccountIntegrateMapper, GaiaAccountIntegrate> implements IGaiaAccountIntegrateRepository {

}
