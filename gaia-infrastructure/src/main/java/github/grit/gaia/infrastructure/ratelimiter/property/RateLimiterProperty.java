package github.grit.gaia.infrastructure.ratelimiter.property;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "gaia.rate.limiter")
public class RateLimiterProperty {
	private String key;
	private String type;
	private double permitsPerSecond;
	private int warmupPeriodMillis;
	private boolean enabled;
	private double rate;
}
