package github.grit.gaia.infrastructure.ratelimiter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimiterConfig {
	private double rate;
	private boolean enable;
	private final double permitsPerSecond = 0.0;
	private final long warmupPeriodMillis = 0;

	private static final double DEFAULT_RATE = 1024 * 1024;
	private static final boolean DEFAULT_ENABLE = true;

	public static RateLimiterConfig defaultConfig() {
		return new RateLimiterConfig(DEFAULT_RATE, DEFAULT_ENABLE);
	}

	public static RateLimiterConfig disabled() {
		return new RateLimiterConfig(0.0, false);
	}
}
