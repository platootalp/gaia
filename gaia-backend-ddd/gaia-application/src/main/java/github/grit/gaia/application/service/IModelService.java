package github.grit.gaia.application.service;

import java.util.List;

import github.grit.gaia.domain.facade.request.ModelCreateRequest;
import github.grit.gaia.domain.facade.request.ModelUpdateRequest;
import github.grit.gaia.domain.facade.response.ModelProviderResponse;
import github.grit.gaia.domain.facade.response.ModelResponse;
import jakarta.validation.Valid;

public interface IModelService {

	List<ModelProviderResponse> getAllProviders();

	List<ModelProviderResponse> getProvidersByModelType(String modelType);

	Long createModel(@Valid ModelCreateRequest request);

	Boolean updateModel(Long modelId, @Valid ModelUpdateRequest request);

	void deleteModel(Long modelId);

	List<ModelResponse> listModels(String modelType);

	void setDefaultModel(Long modelId);
}


