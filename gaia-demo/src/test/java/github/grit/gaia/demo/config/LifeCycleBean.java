package github.grit.gaia.demo.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//@Component
public class LifeCycleBean implements BeanNameAware, BeanFactoryAware,
		ApplicationContextAware, InitializingBean, DisposableBean {

	//	@Autowired
	//	private Dependency dependency;

	public LifeCycleBean() {
		System.out.println("1. 构造函数");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("2. BeanNameAware: " + name);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		System.out.println("3. BeanFactoryAware");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		System.out.println("4. ApplicationContextAware");
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("5. @PostConstruct");
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("6. afterPropertiesSet");
	}

	public void initMethod() {
		System.out.println("7. 自定义 init-method");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("8. @PreDestroy");
	}

	@Override
	public void destroy() {
		System.out.println("9. DisposableBean.destroy");
	}

	public void destroyMethod() {
		System.out.println("10. 自定义 destroy-method");
	}
}
