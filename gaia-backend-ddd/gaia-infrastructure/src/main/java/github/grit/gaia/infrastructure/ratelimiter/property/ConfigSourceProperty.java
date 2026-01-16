package github.grit.gaia.infrastructure.ratelimiter.property;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gaia.rate.limiter")
public class ConfigSourceProperty {
	private String type;
	private String id;

}
