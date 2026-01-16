package github.grit.gaia.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import github.grit.gaia.infrastructure.persistence.entity.GaiaAccountIntegrate;

/**
* 账号与第三方平台绑定表，支持OAuth第三方登录和token管理 Repository 接口
*
* @author plato
* @since 2025-07-06 15:48:45
*/
public interface IGaiaAccountIntegrateRepository extends IService<GaiaAccountIntegrate> {

}
