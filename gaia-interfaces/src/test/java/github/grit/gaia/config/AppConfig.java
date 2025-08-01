package github.grit.gaia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
	public LifeCycleBean lifeCycleBean() {
		return new LifeCycleBean();
	}
}
