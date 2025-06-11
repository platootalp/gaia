package github.grit.gaia.application.service;

import cn.hutool.db.PageResult;
import github.grit.gaia.domain.facade.request.TenantCreateRequest;
import github.grit.gaia.domain.facade.request.TenantUpdateRequest;
import github.grit.gaia.domain.facade.response.TenantResponse;
import jakarta.validation.Valid;

public interface ITenantService {
	TenantResponse createTenant(TenantCreateRequest request);

	TenantResponse getTenantById(Long tenantId);

	Boolean updateTenant(Long tenantId, @Valid TenantUpdateRequest request);

	void deleteTenant(Long tenantId);

	PageResult<TenantResponse> listTenants(int page, int size);
}
