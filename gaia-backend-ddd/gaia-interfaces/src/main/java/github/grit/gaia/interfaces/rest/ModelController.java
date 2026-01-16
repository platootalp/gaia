package github.grit.gaia.interfaces.rest;


import java.util.List;

import github.grit.gaia.application.service.IModelService;
import github.grit.gaia.common.core.Result;
import github.grit.gaia.domain.facade.request.ModelCreateRequest;
import github.grit.gaia.domain.facade.request.ModelUpdateRequest;
import github.grit.gaia.domain.facade.response.ModelProviderResponse;
import github.grit.gaia.domain.facade.response.ModelResponse;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "模型管理")
@RequestMapping("/api/v1/model")
@RestController
@RequiredArgsConstructor
public class ModelController {

	private final IModelService modelService;

	// ---------- 模型提供商 ----------

	@Operation(summary = "获取所有模型提供商")
	@GetMapping("/providers")
	public Result<List<ModelProviderResponse>> listAllProviders() {
		return Result.success(modelService.getAllProviders());
	}

	@Operation(summary = "根据模型类型获取支持的提供商")
	@GetMapping("/providers/{modelType}")
	public Result<List<ModelProviderResponse>> listProvidersByType(@PathVariable String modelType) {
		return Result.success(modelService.getProvidersByModelType(modelType));
	}

	//	@Operation(summary = "获取某个提供商的配置字段模板")
	//	@GetMapping("/providers/config-template")
	//	public Result<ProviderConfigTemplateVO> getProviderConfigTemplate(@RequestParam String providerCode) {
	//		return Result.success(modelService.getProviderConfigTemplate(providerCode));
	//	}

	// ---------- 模型实例管理 ----------

	@Operation(summary = "创建模型实例")
	@PostMapping
	public Result<Long> createModel(@RequestBody @Valid ModelCreateRequest request) {
		return Result.success(modelService.createModel(request));
	}

	@Operation(summary = "更新模型实例配置")
	@PutMapping("/{modelId}")
	public Result<Boolean> updateModel(@PathVariable Long modelId,
									   @RequestBody @Valid ModelUpdateRequest request) {
		return Result.success(modelService.updateModel(modelId, request));
	}

	@Operation(summary = "删除模型实例")
	@DeleteMapping("/{modelId}")
	public Result<Void> deleteModel(@PathVariable Long modelId) {
		modelService.deleteModel(modelId);
		return Result.success();
	}

	@Operation(summary = "列出当前租户下所有模型")
	@GetMapping("/")
	public Result<List<ModelResponse>> listAllModels(@PathVariable String modelType) {
		return Result.success(modelService.listModels(modelType));
	}

	@Operation(summary = "设置默认模型")
	@PostMapping("/{modelId}/default")
	public Result<Void> setDefaultModel(@PathVariable Long modelId) {
		modelService.setDefaultModel(modelId);
		return Result.success();
	}
}

