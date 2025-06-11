package github.grit.gaia.interfaces.rest;


import java.util.List;

import github.grit.gaia.application.service.IModelService;
import github.grit.gaia.common.core.Result;
import github.grit.gaia.domain.facade.response.ModelProviderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "模型管理")
@RequestMapping("/api/v1/model")
@RestController
@RequiredArgsConstructor
public class ModelController {

	private final IModelService modelService;

	@Operation(summary = "获取所有模型供应商")
	@GetMapping("/providers")
	public Result<List<ModelProviderResponse>> listAllProviders() {
		return Result.success(modelService.getAllProviders());
	}

	@Operation(summary = "根据模型类型获取支持的供应商")
	@GetMapping("/providers/{type}")
	public List<ModelProviderResponse> listProvidersByType(@PathVariable("type") String modelType) {
		return Result.success(modelService.getProvidersByModelType(modelType));
	}

}

