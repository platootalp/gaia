Spring Bean 的生命周期是指一个 Bean 从创建到销毁的整个过程。Spring 容器负责管理这个过程，开发者可以通过实现特定的接口或使用注解来介入
Bean 的初始化和销毁过程，从而实现自定义逻辑。

以下是 Spring Bean 生命周期的详细流程（以单例 Bean 为例，使用基于 XML 或注解配置的 IoC 容器）：

---

## 🌱 Spring Bean 生命周期的完整流程

### 1. 实例化（Instantiation）

Spring 容器根据 Bean 的定义（如 XML 配置、`@Component` 注解等）通过反射机制创建 Bean 实例。

- 调用构造函数（无参或有参）
- 此时尚未进行属性赋值和依赖注入

```java
public class MyBean {
	public MyBean() {
		System.out.println("1. 调用构造函数");
	}
}
```

---

### 2. 属性赋值（Populate Properties）

Spring 容器将配置中定义的属性值或依赖注入（如 `@Autowired`、`@Value`）注入到 Bean 实例中。

- 自动装配（byType、byName）
- 手动注入（XML 中的 `<property>` 或注解）

```java

@Autowired
private Dependency dependency;
```

---

### 3. BeanNameAware.setBeanName()

如果 Bean 实现了 `BeanNameAware` 接口，Spring 会调用其 `setBeanName()` 方法，传入该 Bean 在容器中的名称。

```java
public class MyBean implements BeanNameAware {
	@Override
	public void setBeanName(String name) {
		System.out.println("3. BeanNameAware: Bean 名称为 " + name);
	}
}
```

---

### 4. BeanFactoryAware.setBeanFactory()

如果实现了 `BeanFactoryAware` 接口，Spring 会调用 `setBeanFactory()`，传入当前的 `BeanFactory` 实例。

```java
public class MyBean implements BeanFactoryAware {
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("4. BeanFactoryAware: 获取 BeanFactory");
	}
}
```

---

### 5. ApplicationContextAware.setApplicationContext()

如果实现了 `ApplicationContextAware` 接口，Spring 会调用 `setApplicationContext()`，传入 `ApplicationContext` 实例。

```java
public class MyBean implements ApplicationContextAware {
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		System.out.println("5. ApplicationContextAware: 获取上下文");
	}
}
```

> 注意：Aware 接口一般不建议频繁使用，避免与 Spring 强耦合。

---

### 6. BeanPostProcessor.postProcessBeforeInitialization()

如果容器中注册了 `BeanPostProcessor`，其 `postProcessBeforeInitialization()` 方法会被调用。

- 在初始化前对 Bean 进行增强（如 AOP 代理）
- 可返回一个包装后的对象

```java

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		System.out.println("6. BeanPostProcessor - 初始化前");
		return bean;
	}
}
```

---

### 7. @PostConstruct 注解方法

如果 Bean 中有方法标注了 `@PostConstruct`，该方法会被调用。

- JSR-250 标准注解
- 通常用于资源初始化

```java

@PostConstruct
public void init() {
	System.out.println("7. @PostConstruct 方法执行");
}
```

---

### 8. InitializingBean.afterPropertiesSet()

如果 Bean 实现了 `InitializingBean` 接口，Spring 会调用其 `afterPropertiesSet()` 方法。

```java
public class MyBean implements InitializingBean {
	@Override
	public void afterPropertiesSet() {
		System.out.println("8. InitializingBean.afterPropertiesSet()");
	}
}
```

> ⚠️ 建议优先使用 `@PostConstruct`，避免与 Spring 耦合。

---

### 9. 自定义 init-method

在 XML 配置中指定 `init-method`，或使用 `@Bean(initMethod = "xxx")` 注解。

```java

@Bean(initMethod = "customInit")
public MyBean myBean() {
	return new MyBean();
}

public void customInit() {
	System.out.println("9. 自定义 init-method 执行");
}
```

> 执行顺序：`@PostConstruct` → `afterPropertiesSet()` → `init-method`

---

### 10. BeanPostProcessor.postProcessAfterInitialization()

`BeanPostProcessor` 的 `postProcessAfterInitialization()` 方法被调用。

- 用于初始化后的处理，如生成代理对象（AOP）
- Spring AOP 就是在这里织入切面的

```java

@Override
public Object postProcessAfterInitialization(Object bean, String beanName) {
	System.out.println("10. BeanPostProcessor - 初始化后");
	return bean;
}
```

---

### ✅ 此时 Bean 已准备就绪，可以被应用程序使用！

---

### 11. 使用阶段

Bean 被注入到其他组件中，正常提供服务。

---

### 12. @PreDestroy 注解方法

当容器关闭时，标注 `@PreDestroy` 的方法会被调用。

```java

@PreDestroy
public void destroy() {
	System.out.println("12. @PreDestroy 方法执行");
}
```

---

### 13. DisposableBean.destroy()

如果 Bean 实现了 `DisposableBean` 接口，`destroy()` 方法会被调用。

```java
public class MyBean implements DisposableBean {
	@Override
	public void destroy() {
		System.out.println("13. DisposableBean.destroy()");
	}
}
```

---

### 14. 自定义 destroy-method

在 XML 中配置 `destroy-method`，或使用 `@Bean(destroyMethod = "xxx")`。

```java

@Bean(destroyMethod = "customDestroy")
public MyBean myBean() {
	return new MyBean();
}

public void customDestroy() {
	System.out.println("14. 自定义 destroy-method 执行");
}
```

> 执行顺序：`@PreDestroy` → `destroy()` → `destroy-method`

---

## 🔄 生命周期执行顺序总结（关键步骤）

| 步骤  | 方法/接口                                                 | 说明             |
|-----|-------------------------------------------------------|----------------|
| 1   | 构造函数                                                  | 实例化            |
| 2   | 属性注入                                                  | 依赖注入           |
| 3   | `BeanNameAware.setBeanName()`                         | 设置 Bean 名称     |
| 4   | `BeanFactoryAware.setBeanFactory()`                   | 获取 BeanFactory |
| 5   | `ApplicationContextAware.setApplicationContext()`     | 获取上下文          |
| 6   | `BeanPostProcessor.postProcessBeforeInitialization()` | 初始化前处理         |
| 7   | `@PostConstruct`                                      | 初始化方法（推荐）      |
| 8   | `InitializingBean.afterPropertiesSet()`               | 初始化接口          |
| 9   | `init-method`                                         | 自定义初始化方法       |
| 10  | `BeanPostProcessor.postProcessAfterInitialization()`  | 初始化后处理（AOP）    |
| ... | 使用 Bean                                               | 正常调用           |
| 11  | `@PreDestroy`                                         | 销毁前方法（推荐）      |
| 12  | `DisposableBean.destroy()`                            | 销毁接口           |
| 13  | `destroy-method`                                      | 自定义销毁方法        |

---

## 📌 注意事项

1. **Aware 接口**：尽量避免直接实现，可通过其他方式获取上下文。
2. **BeanPostProcessor**：是 Spring AOP、自动代理等机制的基础，非常强大。
3. **@PostConstruct 和 @PreDestroy**：需要 `jakarta.annotation` 依赖（Java EE），在 Spring Boot 中通常已包含。
4. **单例 vs 原型（Prototype）**：
    - 单例 Bean：容器管理整个生命周期。
    - 原型 Bean：容器只负责创建和初始化，**不负责销毁**，`@PreDestroy` 等不会被调用。
5. **容器关闭**：必须显式调用 `context.close()` 或 `registerShutdownHook()` 才能触发销毁方法。

```java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
context.

registerShutdownHook(); // 注册 JVM 关闭钩子
```

---

## ✅ 示例代码整合

```java

@Component
public class LifeCycleBean implements BeanNameAware, BeanFactoryAware,
		ApplicationContextAware, InitializingBean, DisposableBean {

	@Autowired
	private Dependency dependency;

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
```

配置类：

```java

@Configuration
public class AppConfig {
	@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
	public LifeCycleBean lifeCycleBean() {
		return new LifeCycleBean();
	}
}
```

---

## 思维导图

```plantext
1. 创建阶段
   ├── 1.1 实例化 (Instantiation)
   │ └── 反射调用构造函数
   │ └── 此时未注入依赖
   │
   ├── 1.2 属性赋值 (Populate Properties)
   │ └── 依赖注入 (@Autowired/@Value)
   │ └── XML配置属性填充
   │
   ├── 1.3 Aware接口回调
   │ ├── BeanNameAware.setBeanName()
   │ ├── BeanFactoryAware.setBeanFactory()
   │ └── ApplicationContextAware.setApplicationContext()
   │

2. 初始化阶段
   ├── 2.1 BeanPostProcessor前置处理
   │ └── postProcessBeforeInitialization()
   │ └── (AOP代理在此准备)
   │
   ├── 2.2 初始化方法 (按顺序执行)
   │ ├── @PostConstruct 注解方法
   │ ├── InitializingBean.afterPropertiesSet()
   │ └── 自定义 init-method
   │
   └── 2.3 BeanPostProcessor后置处理
   └── postProcessAfterInitialization()
   └── (AOP代理实际生成点)

3. 运行阶段
   └── Bean 准备就绪
   └── 被其他组件调用

4. 销毁阶段 (容器关闭时触发)
   ├── 4.1 @PreDestroy 注解方法
   ├── 4.2 DisposableBean.destroy()
   └── 4.3 自定义 destroy-method
```

---

## 流程图

                              ┌──────────────────┐
                              │  1. 实例化        │
                              │  Instantiation   │
                              │ (调用构造函数)     │
                              └────────┬─────────┘
                                       ↓
                              ┌──────────────────┐
                              │  2. 属性赋值       │
                              │  Populate        │
                              │  Properties       │
                              │ (依赖注入 @Autowired) │
                              └────────┬─────────┘
                                       ↓
                    ┌────────────────────────────────────────────┐
                    │  3. Aware 接口回调 (顺序可变，通常在此阶段) │
                    ├────────────────────────────────────────────┤
                    │ • BeanNameAware.setBeanName()              │
                    │ • BeanFactoryAware.setBeanFactory()        │
                    │ • ApplicationContextAware.setApplicationContext() │
                    └────────────────────┬─────────────────────────┘
                                         ↓
                    ┌────────────────────────────────────────────┐
                    │  4. BeanPostProcessor 前置处理             │
                    │  postProcessBeforeInitialization()         │
                    └────────────────────┬─────────────────────────┘
                                         ↓
                    ┌────────────────────────────────────────────┐
                    │  5. 初始化方法调用 (顺序如下)               │
                    ├────────────────────────────────────────────┤
                    │ • @PostConstruct 注解方法                  │
                    │ • InitializingBean.afterPropertiesSet()    │
                    │ • 自定义 init-method (XML 或 @Bean)        │
                    └────────────────────┬─────────────────────────┘
                                         ↓
                    ┌────────────────────────────────────────────┐
                    │  6. BeanPostProcessor 后置处理             │
                    │  postProcessAfterInitialization()          │
                    │  (AOP 代理通常在此生成)                     │
                    └────────────────────┬─────────────────────────┘
                                         ↓
                              ┌──────────────────┐
                              │ ✅ Bean 准备就绪  │
                              │  可被应用程序使用  │
                              └──────────────────┘
                                         ↓
                              ┌──────────────────┐
                              │     使用阶段      │
                              │  (正常调用方法)   │
                              └────────┬─────────┘
                                       ↓
                    ┌────────────────────────────────────────────┐
                    │  7. 销毁阶段 (容器关闭时触发)               │
                    ├────────────────────────────────────────────┤
                    │ • @PreDestroy 注解方法                     │
                    │ • DisposableBean.destroy()                 │
                    │ • 自定义 destroy-method                    │
                    └────────────────────────────────────────────┘
                                       ↓
                              ┌──────────────────┐
                              │  8. Bean 销毁    │
                              │  (内存回收)       │
                              └──────────────────┘

---

## 📚 总结

Spring Bean 生命周期是一个高度可扩展的机制，通过多个扩展点（Aware、BeanPostProcessor、InitializingBean、@PostConstruct
等），开发者可以灵活地控制 Bean 的行为。理解生命周期对于掌握 Spring 框架、实现自定义逻辑、排查问题至关重要。

> 推荐使用：`@PostConstruct` + `@PreDestroy` + `@Bean(initMethod/destroyMethod)`，避免直接实现 Spring 接口，降低耦合。
