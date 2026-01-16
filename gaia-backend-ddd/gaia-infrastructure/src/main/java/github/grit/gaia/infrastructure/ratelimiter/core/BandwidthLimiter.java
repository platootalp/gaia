package github.grit.gaia.infrastructure.ratelimiter.core;

/**
 * 表示基于带宽（如字节速率）控制的限流器
 * 可用于文件上传、网络传输等场景
 */
public interface BandwidthLimiter extends RateLimiter<Integer> {

}
