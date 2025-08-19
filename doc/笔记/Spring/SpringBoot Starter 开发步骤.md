### 一、理解 Spring Boot Starter 的结构

一个典型的 Spring Boot Starter 包含两个模块（推荐）：

1. **`xxx-spring-boot-starter`**：对外提供的 starter 依赖，通常为空，只引入自动配置模块。
2. **`xxx-spring-boot-autoconfigure`**：核心模块，包含自动配置类、条件装配、默认配置等。

> 示例：`myapp-spring-boot-starter` 和 `myapp-spring-boot-autoconfigure`

---

### 二、创建项目结构（Maven 示例）

```bash
myapp-spring-boot-starter/
├── myapp-spring-boot-starter/          # Starter 模块
│   └── pom.xml
└── myapp-spring-boot-autoconfigure/    # 自动配置模块
    └── pom.xml
```

---

### 三、步骤详解

#### 1. 创建 `autoconfigure` 模块

##### (1) 添加依赖（pom.xml）

```xml

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-autoconfigure</artifactId>
		<version>3.2.0</version> <!-- 与 Spring Boot 版本一致 -->
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-configuration-processor</artifactId>
		<optional>true</optional>
	</dependency>
	<!-- 你自己的功能依赖，比如某个 SDK -->
</dependencies>
```

> `spring-boot-configuration-processor` 用于生成 `spring-configuration-metadata.json`，支持 IDE 提示。

##### (2) 创建配置属性类（POJO）

```java

@ConfigurationProperties(prefix = "myapp.service")
public class MyAppProperties {
	private String url = "http://localhost:8080";
	private int timeout = 5000;

	// getter and setter
}
```

##### (3) 创建核心服务类

```java
public class MyAppService {
	private final String url;
	private final int timeout;

	public MyAppService(String url, int timeout) {
		this.url = url;
		this.timeout = timeout;
	}

	public void doSomething() {
		System.out.println("Calling service at " + url);
	}
}
```

##### (4) 创建自动配置类

```java

@Configuration
@EnableConfigurationProperties(MyAppProperties.class)
@ConditionalOnClass(MyAppService.class)
@ConditionalOnProperty(prefix = "myapp.service", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MyAppAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean // 避免重复创建
	public MyAppService myAppService(MyAppProperties properties) {
		return new MyAppService(properties.getUrl(), properties.getTimeout());
	}
}
```

##### (5) 注册自动配置类

在 `src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 文件中添加：

```txt
com.example.autoconfigure.MyAppAutoConfiguration
```

> 注意：Spring Boot 2.7+ 推荐使用 `AutoConfiguration.imports`，旧版本使用 `spring.factories`。

如果你使用 Spring Boot < 2.7，则使用 `META-INF/spring.factories`：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.autoconfigure.MyAppAutoConfiguration
```

---

#### 2. 创建 `starter` 模块

##### (1) pom.xml（只依赖 autoconfigure）

```xml

<dependencies>
	<dependency>
		<groupId>com.example</groupId>
		<artifactId>myapp-spring-boot-autoconfigure</artifactId>
		<version>1.0.0</version>
	</dependency>
</dependencies>
```

> Starter 模块只是一个“便捷依赖”，不包含实际代码。

---

#### 3. 构建并安装/发布

```bash
mvn clean install
```

或发布到私有仓库（如 Nexus、Maven Central）。

---

### 四、使用你开发的 Starter

在其他 Spring Boot 项目中添加依赖：

```xml

<dependency>
	<groupId>com.example</groupId>
	<artifactId>myapp-spring-boot-starter</artifactId>
	<version>1.0.0</version>
</dependency>
```

配置 `application.yml`：

```yaml
myapp:
  service:
    url: http://api.example.com
    timeout: 3000
    enabled: true
```

在 Service 中注入使用：

```java

@Service
public class BusinessService {
	private final MyAppService myAppService;

	public BusinessService(MyAppService myAppService) {
		this.myAppService = myAppService;
	}

	public void process() {
		myAppService.doSomething();
	}
}
```

---

### 五、最佳实践

1. **命名规范**：
    - 官方：`spring-boot-starter-xxx`
    - 第三方：`xxx-spring-boot-starter`

2. **避免循环依赖**：Starter 不应引入 `spring-boot-starter`。

3. **条件装配**：使用 `@ConditionalOnClass`、`@ConditionalOnMissingBean` 等确保安全。

4. **提供默认配置**：通过 `@ConfigurationProperties` 支持外部化配置。

5. **文档说明**：提供 README 说明如何使用。

---

### 六、常见问题

| 问题      | 解决方案                                                     |
|---------|----------------------------------------------------------|
| 自动配置不生效 | 检查 `AutoConfiguration.imports` 或 `spring.factories` 是否正确 |
| 配置属性无提示 | 确保添加 `spring-boot-configuration-processor`               |
| Bean 冲突 | 使用 `@ConditionalOnMissingBean`                           |

---

### 总结

开发 Spring Boot Starter 的核心步骤：

1. 创建 `autoconfigure` 模块，编写配置类、属性类、自动配置。
2. 使用 `@Conditional` 系列注解控制加载条件。
3. 注册自动配置（通过 `AutoConfiguration.imports` 或 `spring.factories`）。
4. 创建 `starter` 模块，依赖 `autoconfigure`。
5. 构建发布，供他人使用。

这样，你就可以像使用 `spring-boot-starter-web` 一样，轻松集成自己的功能了。