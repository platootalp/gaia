package github.grit.gaia.application.service.impl;

import java.util.List;

import github.grit.gaia.application.service.IModelService;
import github.grit.gaia.domain.facade.request.ModelCreateRequest;
import github.grit.gaia.domain.facade.request.ModelUpdateRequest;
import github.grit.gaia.domain.facade.response.ModelProviderResponse;
import github.grit.gaia.domain.facade.response.ModelResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ModelService implements IModelService {

	@Override
	public List<ModelProviderResponse> getAllProviders() {
		return List.of();
	}

	@Override
	public List<ModelProviderResponse> getProvidersByModelType(String modelType) {
		return List.of();
	}

	@Override
	public Long createModel(ModelCreateRequest request) {
		return 0L;
	}

	@Override
	public Boolean updateModel(Long modelId, ModelUpdateRequest request) {
		return null;
	}

	@Override
	public void deleteModel(Long modelId) {

	}

	@Override
	public List<ModelResponse> listModels(String modelType) {
		return List.of();
	}

	@Override
	public void setDefaultModel(Long modelId) {

	}
}
