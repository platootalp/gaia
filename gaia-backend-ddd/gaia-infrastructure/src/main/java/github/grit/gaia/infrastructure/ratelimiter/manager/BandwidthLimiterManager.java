package github.grit.gaia.infrastructure.ratelimiter.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import github.grit.gaia.infrastructure.ratelimiter.core.BandwidthLimiter;
import github.grit.gaia.infrastructure.ratelimiter.event.RateLimiterConfigChangeEvent;
import github.grit.gaia.infrastructure.ratelimiter.source.RateLimiterConfigSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BandwidthLimiterManager {

	private final Map<String, BandwidthLimiter> limiterMap = new ConcurrentHashMap<>();
	private final Map<String, RateLimiterConfigSource> dataSourceMap = new ConcurrentHashMap<>();

	public void registerLimiter(BandwidthLimiter limiter) {
		limiterMap.put(limiter.getId(), limiter);
	}

	public void registerDataSource(RateLimiterConfigSource ds) {
		dataSourceMap.put(ds.getSourceId(), ds);
		ds.start();
	}

	/**
	 * 绑定限流器和数据源
	 *
	 * @param limiterKey 限流器的标识
	 * @param sourceId   数据源的标识
	 * @param configKey  配置项的键
	 */
	public void bindLimiterToDataSource(String limiterKey, String sourceId, String configKey) {
		BandwidthLimiter limiter = limiterMap.get(limiterKey);
		RateLimiterConfigSource dataSource = dataSourceMap.get(sourceId);

		if (limiter == null) {
			log.error("BandwidthLimiter not found for key: {}", limiterKey);
			return;
		}

		if (dataSource == null) {
			log.error("RateLimiterConfigSource not found for sourceId: {}", sourceId);
			return;
		}

		// 为特定的配置源添加监听器，监听配置变化
		dataSource.addListener(configKey, limiterKey, event -> {
			// 当配置变化时更新限流器的配置
			updateLimiterConfiguration(limiter, event);
		});

		log.info("绑定成功：Limiter [{}] 与 DataSource [{}] 的配置键 [{}] 已绑定", limiterKey, sourceId, configKey);
	}

	/**
	 * 更新限流器的配置
	 *
	 * @param limiter 限流器实例
	 * @param event   新的限流器配置
	 */
	private void updateLimiterConfiguration(BandwidthLimiter limiter, RateLimiterConfigChangeEvent event) {
		// 这里更新限流器的配置信息，假设 BandwidthLimiter 有一个更新方法
		limiter.refresh(event.getConfig());
		log.info("更新限流器 [{}] 的配置为：{}", limiter.getId(), event);
	}
}


