package github.grit.gaia.infrastructure.ratelimiter.source;

public interface RateLimiterConfigSource {
	ConfigSourceTypeEnum getSourceType();

	String getSourceId();

	void addListener(String configKey, String limiterKey, RateLimiterConfigListener listener);

	void removeListener(String configKey, String limiterKey);

	void start();

	void stop();

	enum ConfigSourceTypeEnum {
		LOCAL,
		APOLLO,
		NACOS,
		REDIS,
		ZOOKEEPER
	}
}

