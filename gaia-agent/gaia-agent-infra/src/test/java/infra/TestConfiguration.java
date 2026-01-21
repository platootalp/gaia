package infra;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试配置类
 * 用于 gaia-agent-infra 模块的 Spring Boot 测试
 * <p>
 * 使用 @SpringBootConfiguration + @EnableAutoConfiguration 来启用 Spring Boot 自动配置
 * 包括 Spring AI 的自动配置，这样 ChatModel 才能被正确注入
 * <p>
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "github.grit.gaia.agent.infra")
public class TestConfiguration {
}
