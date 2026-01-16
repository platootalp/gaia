package github.grit.gaia.application.service.impl;

import cn.hutool.db.PageResult;
import github.grit.gaia.application.service.ITenantService;
import github.grit.gaia.domain.facade.request.TenantCreateRequest;
import github.grit.gaia.domain.facade.request.TenantUpdateRequest;
import github.grit.gaia.domain.facade.response.TenantResponse;

import github.grit.gaia.domain.repository.IGaiaTenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements ITenantService {

	@Autowired
	private IGaiaTenantRepository tenantRepository;

	@Override
	public TenantResponse createTenant(TenantCreateRequest request) {
		return null;
	}

	@Override
	public TenantResponse getTenantById(Long tenantId) {
		return null;
	}

	@Override
	public Boolean updateTenant(Long tenantId, TenantUpdateRequest request) {
		return null;
	}

	@Override
	public void deleteTenant(Long tenantId) {

	}

	@Override
	public PageResult<TenantResponse> listTenants(int page, int size) {
		return null;
	}
}
