package github.grit.gaia.interfaces.rest;

import cn.hutool.db.PageResult;
import github.grit.gaia.application.service.ITenantService;
import github.grit.gaia.common.core.Result;
import github.grit.gaia.domain.facade.request.TenantCreateRequest;
import github.grit.gaia.domain.facade.request.TenantUpdateRequest;
import github.grit.gaia.domain.facade.response.TenantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "租户管理")
@RequestMapping("/api/v1/tenant")
@RestController
@RequiredArgsConstructor
public class TenantController {

	private final ITenantService ITenantService;

	@Operation(
			summary = "创建租户",
			description = "创建一个新的租户并返回其主键 ID"
	)
	@PostMapping
	public Result<TenantResponse> createTenant(@RequestBody @Valid TenantCreateRequest request) {
		TenantResponse tenant = ITenantService.createTenant(request);
		return Result.success(tenant);
	}

	@GetMapping("/{tenantId}")
	public Result<TenantResponse> getTenant(@PathVariable Long tenantId) {
		return Result.success(ITenantService.getTenantById(tenantId));
	}

	@PutMapping("/{tenantId}")
	public Result<Boolean> updateTenant(@PathVariable Long tenantId,
										@RequestBody @Valid TenantUpdateRequest request) {
		return Result.success(ITenantService.updateTenant(tenantId, request));
	}

	@DeleteMapping("/{tenantId}")
	public Result<Void> deleteTenant(@PathVariable Long tenantId) {
		ITenantService.deleteTenant(tenantId);
		return Result.success();
	}

	@GetMapping("/s")
	public Result<PageResult<TenantResponse>> listTenants(@RequestParam(defaultValue = "1") int page,
														  @RequestParam(defaultValue = "10") int size) {
		return Result.success(ITenantService.listTenants(page, size));
	}
}

