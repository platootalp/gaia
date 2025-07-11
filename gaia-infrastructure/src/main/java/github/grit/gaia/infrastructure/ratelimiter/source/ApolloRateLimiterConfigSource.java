package github.grit.gaia.infrastructure.ratelimiter.source;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson2.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import github.grit.gaia.infrastructure.ratelimiter.config.RateLimiterConfig;
import github.grit.gaia.infrastructure.ratelimiter.event.RateLimiterConfigChangeEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ApolloRateLimiterConfigSource implements RateLimiterConfigSource {
	// 配置源唯一标识
	private final String sourceId;
	// 需要监听的配置键列表
	private final List<String> configKeys;
	// Apollo配置中心客户端
	private final Config apolloConfig;

	/**
	 * 监听器映射表
	 * 第一层key: configKey 配置键
	 * 第二层key: limiterKey 限流器键
	 * value: 对应的监听器实例
	 */
	private final Map<String, Map<String, RateLimiterConfigListener>> listeners = new ConcurrentHashMap<>();

	// 服务运行状态标志
	private volatile boolean running;

	@Override
	public ConfigSourceTypeEnum getSourceType() {
		return ConfigSourceTypeEnum.APOLLO;
	}

	@Override
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * 添加配置变更监听器
	 *
	 * @param configKey  配置键
	 * @param limiterKey 限流器键
	 * @param listener   监听器实例
	 */
	@Override
	public void addListener(String configKey, String limiterKey, RateLimiterConfigListener listener) {
		listeners
				.computeIfAbsent(configKey, k -> {
					// 首次添加该configKey的监听器时，注册到Apollo
					registerChangeListenerForConfigKey(k);
					return new ConcurrentHashMap<>();
				})
				.put(limiterKey, listener);
	}

	/**
	 * 移除配置变更监听器
	 *
	 * @param configKey  配置键
	 * @param limiterKey 限流器键
	 */
	@Override
	public void removeListener(String configKey, String limiterKey) {
		Map<String, RateLimiterConfigListener> map = listeners.get(configKey);
		if (map != null) {
			map.remove(limiterKey);
		}
	}

	/**
	 * 启动配置监听服务
	 */
	@Override
	public void start() {
		if (running)
			return;

		// 为所有配置键注册变更监听
		for (String configKey : configKeys) {
			registerChangeListenerForConfigKey(configKey);
		}

		running = true;
	}

	/**
	 * 停止配置监听服务
	 */
	@Override
	public void stop() {
		running = false;
	}

	/**
	 * 为指定配置键注册Apollo变更监听器
	 *
	 * @param configKey 配置键
	 */
	private void registerChangeListenerForConfigKey(String configKey) {
		apolloConfig.addChangeListener(changeEvent -> {
			// 服务未运行或配置未变更则忽略
			if (!changeEvent.isChanged(configKey))
				return;

			var change = changeEvent.getChange(configKey);
			// 配置被删除时通知监听器 改为默认限流
			if (change == null || change.getChangeType() == PropertyChangeType.DELETED) {
				notifyListeners(configKey, RateLimiterConfig.defaultConfig());
				return;
			}

			// 解析新配置并通知监听器
			String newValue = change.getNewValue();
			try {
				RateLimiterConfig config = parseConfig(newValue);
				notifyListeners(configKey, config);
			}
			catch (Exception e) {
				log.error("ApolloRateLimiterConfigSource - [{}] 配置解析失败：{}，跳过", configKey, newValue, e);
			}
		});
	}

	/**
	 * 通知所有监听器配置变更
	 *
	 * @param configKey 配置键
	 * @param config    新配置
	 */
	private void notifyListeners(String configKey, RateLimiterConfig config) {
		Map<String, RateLimiterConfigListener> limiterListeners = listeners.get(configKey);
		if (limiterListeners == null || limiterListeners.isEmpty())
			return;

		Instant now = Instant.now();
		for (Map.Entry<String, RateLimiterConfigListener> entry : limiterListeners.entrySet()) {
			String limiterKey = entry.getKey();
			RateLimiterConfigChangeEvent event = new RateLimiterConfigChangeEvent(limiterKey, config, now);
			try {
				entry.getValue().onConfigChanged(event);
			}
			catch (Exception e) {
				log.error("ApolloRateLimiterConfigSource - 通知监听器 [{}] 异常：{}", limiterKey, e.getMessage(), e);
			}
		}
	}

	/**
	 * 解析配置值
	 *
	 * @param value 配置值字符串
	 * @return 解析后的配置对象
	 * @throws IOException 解析异常
	 */
	private RateLimiterConfig parseConfig(String value) throws IOException {
		// 简单数值格式: 1024
		if (value.matches("\\d+(\\.\\d+)?")) {
			return new RateLimiterConfig(Double.parseDouble(value), true);
		}
		// JSON格式: { "rate" : 1024, "enable" : true}
		return JSONObject.parseObject(value, RateLimiterConfig.class);
	}
}



