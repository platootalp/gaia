package github.grit.gaia.infrastructure.persistence.repository.impl;

import github.grit.gaia.infrastructure.persistence.entity.GaiaAccount;
import github.grit.gaia.infrastructure.persistence.mapper.GaiaAccountMapper;
import github.grit.gaia.infrastructure.persistence.repository.IGaiaAccountRepository;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.grit.gaia.infrastructure.persistence.entity.GaiaAccountIntegrate;


/**
* 账号表，记录用户基础信息 Repository 实现类
*
* @author plato
* @since 2025-07-06 15:48:45
*/
@Repository
public class GaiaAccountRepositoryImpl extends ServiceImpl<GaiaAccountMapper, GaiaAccount> implements IGaiaAccountRepository {

}
