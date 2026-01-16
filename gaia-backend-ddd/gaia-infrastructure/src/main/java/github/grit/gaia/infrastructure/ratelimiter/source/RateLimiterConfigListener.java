package github.grit.gaia.infrastructure.ratelimiter.source;

import github.grit.gaia.infrastructure.ratelimiter.event.RateLimiterConfigChangeEvent;

@FunctionalInterface
public interface RateLimiterConfigListener {
	void onConfigChanged(RateLimiterConfigChangeEvent event);
}
