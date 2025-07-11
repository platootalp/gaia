package github.grit.gaia.infrastructure.ratelimiter.core;


import github.grit.gaia.infrastructure.ratelimiter.config.RateLimiterConfig;

/**
 * 流量控制顶级接口
 * 职责：控制资源访问速率，支持动态配置
 *
 * @param <T> 许可类型（Long-带宽字节数，Integer-请求数等）
 */
public interface RateLimiter<T extends Number> {

	/**
	 * 获取限流器类型标识（如："BANDWIDTH"/"REQUEST"）
	 */
	RateLimiterTypeEnum getType();

	/**
	 * 获取限流器实例ID
	 */
	String getId();

	/**
	 * 非阻塞式获取许可
	 *
	 * @param permits 请求的许可量（>0）
	 * @return true-获取成功，false-被限流
	 * @throws IllegalArgumentException 参数不合法时抛出
	 */
	boolean tryAcquire(T permits);

	/**
	 * 阻塞式获取许可（直到成功）
	 *
	 * @param permits 请求的许可量（>0）
	 * @throws IllegalArgumentException 参数不合法时抛出
	 * @throws InterruptedException     线程中断时抛出
	 */
	void acquire(T permits) throws InterruptedException;

	/**
	 * 动态更新限流规则
	 *
	 * @param config 限流配置（包含速率值等）
	 * @throws IllegalArgumentException 配置不合法时抛出
	 */
	void refresh(RateLimiterConfig config);

	/**
	 * 获取当前限流配置
	 */
	RateLimiterConfig getConfig();

	enum RateLimiterTypeEnum {
		BANDWIDTH,
		REQUEST
	}
}


