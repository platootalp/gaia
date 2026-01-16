package github.grit.gaia.infrastructure.ratelimiter.event;

import java.time.Instant;

import github.grit.gaia.infrastructure.ratelimiter.config.RateLimiterConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateLimiterConfigChangeEvent {
	private final String limiterKey;
	private final RateLimiterConfig config;
	private final Instant timestamp;
}

