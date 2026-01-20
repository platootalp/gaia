package github.grit.gaia.agent.controller.agent;

import github.grit.gaia.agent.common.response.Result;
import github.grit.gaia.agent.controller.dto.TripPlanRequest;
import github.grit.gaia.agent.controller.dto.TripPlanResponse;
import github.grit.gaia.agent.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1/trip")
@RequiredArgsConstructor
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/plan")
    public Result<TripPlanResponse> createTripPlan(@RequestBody @Valid TripPlanRequest request) {
        return Result.success(tripService.create(request));
    }
}
