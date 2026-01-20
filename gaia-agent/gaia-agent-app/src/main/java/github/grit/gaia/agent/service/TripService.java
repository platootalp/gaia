package github.grit.gaia.agent.service;

import github.grit.gaia.agent.controller.dto.TripPlanRequest;
import github.grit.gaia.agent.controller.dto.TripPlanResponse;

public interface TripService {
    TripPlanResponse create(TripPlanRequest request);
}
