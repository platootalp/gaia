package github.grit.gaia.infrastructure.ratelimiter.core;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import github.grit.gaia.infrastructure.ratelimiter.config.RateLimiterConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class TokenBucketBandwidthLimiter implements BandwidthLimiter {

	private final String id;
	private volatile RateLimiter guavaRateLimiter;
	private volatile RateLimiterConfig currentConfig;

	/**
	 * 静态工厂方法
	 *
	 * @param id     限流器ID
	 * @param config 限流配置
	 * @return 初始化的限流器实例
	 */
	public static TokenBucketBandwidthLimiter create(String id, RateLimiterConfig config) {
		Objects.requireNonNull(config, "Config must not be null");
		return new TokenBucketBandwidthLimiter(id, config);
	}

	public TokenBucketBandwidthLimiter(String id, RateLimiterConfig config) {
		this.id = Objects.requireNonNull(id, "Limiter ID must not be null");
		refresh(config); // 初始化时刷新配置
	}

	@Override
	public RateLimiterTypeEnum getType() {
		return RateLimiterTypeEnum.BANDWIDTH;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public RateLimiterConfig getConfig() {
		return this.currentConfig;
	}

	@Override
	public boolean tryAcquire(Integer permits) {
		validatePermits(permits);
		return guavaRateLimiter.tryAcquire(permits);
	}

	@Override
	public void acquire(Integer permits) throws InterruptedException {
		validatePermits(permits);
		try {
			guavaRateLimiter.acquire(permits);
		}
		catch (Exception e) {
			Thread.currentThread().interrupt(); // 保持线程中断标记
			log.error("Thread interrupted while acquiring permits for limiter [{}]", id, e);
			throw new InterruptedException("Thread interrupted while acquiring permits");
		}
	}

	@Override
	public void refresh(RateLimiterConfig config) {
		Objects.requireNonNull(config, "RateLimitConfig must not be null");

		synchronized (this) {
			if (config.equals(this.currentConfig)) {
				log.debug("Config for limiter [{}] is already up-to-date", id);
				return; // 如果配置相同，不做更新
			}

			this.currentConfig = config;
			if (guavaRateLimiter == null) {
				guavaRateLimiter = createRateLimiter(config);
			}
			else {
				guavaRateLimiter.setRate(config.getPermitsPerSecond()); // 更新速率
			}
			log.info("Limiter [{}] config updated: {}", id, config);
		}
	}

	private RateLimiter createRateLimiter(RateLimiterConfig config) {
		if (config.getWarmupPeriodMillis() > 0) {
			return RateLimiter.create(config.getPermitsPerSecond(),
					config.getWarmupPeriodMillis(),
					TimeUnit.MILLISECONDS);
		}
		return RateLimiter.create(config.getPermitsPerSecond());
	}

	private void validatePermits(Integer permits) {
		if (permits == null || permits <= 0) {
			throw new IllegalArgumentException("Permits must be positive");
		}
	}
}


