# 📚 [YourSDK] Java SDK 用户文档（Spring Boot 集成版）

> 适用于 Java 8+ 与 Spring Boot 2.5+

| 项目     | 内容                                                                                           |
|--------|----------------------------------------------------------------------------------------------|
| SDK 名称 | `your-sdk-client`                                                                            |
| 版本     | v1.0.0                                                                                       |
| 最后更新   | 2025年4月5日                                                                                    |
| 支持框架   | Spring Boot 2.5+, Java 8+                                                                    |
| 官网     | [https://yourcompany.com](https://yourcompany.com)                                           |
| 技术支持   | support@yourcompany.com                                                                      |
| GitHub | [https://github.com/yourcompany/your-sdk-java](https://github.com/yourcompany/your-sdk-java) |

---

## 1. 概述

### 1.1 简介

`your-sdk-client` 是一个用于与 **YourCompany API 服务** 进行交互的 Java 客户端 SDK，专为 Spring Boot
应用设计，支持自动装配、配置化管理、线程安全调用和异常统一处理。

主要功能包括：

- 身份认证（AppId + AppSecret）
- 接口调用封装（如：创建订单、查询数据）
- 自动重试与超时控制
- 日志输出与监控埋点支持

### 1.2 适用场景

- Spring Boot 微服务集成 YourCompany 服务
- 后台管理系统调用外部能力
- 分布式系统中作为客户端依赖

---

## 2. 快速开始

### 2.1 前提条件

- JDK 8 或以上
- Maven / Gradle 构建工具
- Spring Boot 2.5+
- 已注册 YourCompany 开发者账号，获取：
    - `appId`
    - `appSecret`

### 2.2 添加依赖（Maven）

```xml

<dependency>
	<groupId>com.yourcompany</groupId>
	<artifactId>your-sdk-client</artifactId>
	<version>1.0.0</version>
</dependency>
```

### 2.3 添加依赖（Gradle）

```groovy
implementation 'com.yourcompany:your-sdk-client:1.0.0'
```

> ✅ 提示：确保你的 `settings.xml` 或仓库配置已包含私有仓库地址（如 Nexus、Jfrog），或发布到 Maven Central / JitPack。

---

## 3. 配置 SDK（application.yml）

在 `application.yml` 中添加 SDK 配置：

```yaml
your-sdk:
  enabled: true
  app-id: your_app_id_here
  app-secret: your_app_secret_here
  api-base-url: https://api.yourcompany.com/v1
  connect-timeout: 5000     # 连接超时（毫秒）
  read-timeout: 10000       # 读取超时
  max-retries: 3            # 最大重试次数
  debug: true               # 是否开启调试日志
```

> 🔐 安全建议：生产环境中的 `app-secret` 建议通过环境变量注入：
>
> ```yaml
> app-secret: ${YOUR_SDK_APP_SECRET}
> ```

---

## 4. 启用 SDK 自动装配

### 4.1 添加注解到主类或配置类

```java

@SpringBootApplication
@EnableYourSdkClient  // 启用 SDK 自动配置
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

> 💡 `@EnableYourSdkClient` 会触发自动配置类 `YourSdkAutoConfiguration`，完成 Bean 注册。

---

## 5. 注入并使用 SDK 客户端

SDK 提供主客户端 `YourSdkClient`，封装所有 API 调用。

```java

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private YourSdkClient sdkClient;

	@PostMapping("/create")
	public ResponseEntity<String> createOrder() {
		try {
			// 构造请求参数
			CreateOrderRequest request = new CreateOrderRequest();
			request.setUserId("user_123");
			request.setAmount(99.9);
			request.setProduct("VIP会员");

			// 调用 SDK 方法
			CreateOrderResponse response = sdkClient.createOrder(request);

			return ResponseEntity.ok(response.getOrderId());
		}
		catch (YourSdkException e) {
			return ResponseEntity.badRequest().body("调用失败: " + e.getMessage());
		}
	}
}
```

---

## 6. 核心 API 参考

### 6.1 `YourSdkClient` 主要方法

| 方法                                    | 描述   | 参数                   | 返回值                   |
|---------------------------------------|------|----------------------|-----------------------|
| `createOrder(CreateOrderRequest req)` | 创建订单 | `CreateOrderRequest` | `CreateOrderResponse` |
| `queryOrder(String orderId)`          | 查询订单 | `orderId` (String)   | `QueryOrderResponse`  |
| `refundOrder(RefundRequest req)`      | 申请退款 | `RefundRequest`      | `RefundResponse`      |
| `healthCheck()`                       | 健康检查 | 无                    | `boolean`             |

---

### 6.2 请求/响应对象示例

#### CreateOrderRequest

```java
public class CreateOrderRequest {
	private String userId;
	private Double amount;
	private String product;
	// getter/setter 省略
}
```

#### CreateOrderResponse

```java
public class CreateOrderResponse {
	private String orderId;
	private String status; // PAID, PENDING
	private Long createdAt;
	// getter/setter
}
```

---

## 7. 异常处理

SDK 所有异常均继承自 `YourSdkException`，建议统一捕获：

```java
try{
		sdkClient.createOrder(request);
}catch(
InvalidParamException e){
		// 参数错误
		}catch(
AuthFailedException e){
		// 认证失败
		}catch(
ApiRateLimitException e){
		// 调用频率超限
		}catch(
YourSdkException e){
		// 其他 SDK 异常
		log.

error("SDK调用异常",e);
}
```

### 常见异常类型

| 异常类                      | 错误码 | 说明                |
|--------------------------|-----|-------------------|
| `InvalidParamException`  | 400 | 参数校验失败            |
| `AuthFailedException`    | 401 | AppId 或 Secret 错误 |
| `ApiRateLimitException`  | 429 | 调用频率超限            |
| `RemoteServiceException` | 500 | 服务端异常             |
| `NetworkException`       | -   | 网络连接失败            |

---

## 8. 高级配置（可选）

### 8.1 自定义 HttpClient

SDK 默认使用 `OkHttp`，可通过配置替换：

```java

@Bean
public YourSdkClient customSdkClient() {
	OkHttpClient customClient = new OkHttpClient.Builder()
			.connectTimeout(10, TimeUnit.SECONDS)
			.addInterceptor(new LoggingInterceptor())
			.build();

	return new YourSdkClient(config, customClient);
}
```

### 8.2 添加拦截器（如日志、埋点）

```java
sdkClient.addInterceptor(context ->{
		System.out.

println("API调用: "+context.getMethod() +" → "+context.

getUrl());
		});
```

---

## 9. 安全最佳实践

- ❌ 不要将 `app-secret` 硬编码在代码中
- ✅ 使用环境变量或配置中心（Nacos、Apollo）管理敏感信息
- ✅ 生产环境关闭 `debug` 模式
- ✅ 定期轮换 `app-secret`
- ✅ 使用 HTTPS 通信

---

## 10. 常见问题（FAQ）

**Q1：如何在非 Spring 项目中使用？**  
A：可以手动 new `YourSdkClient(config)`，但需自行管理生命周期。

**Q2：SDK 是否线程安全？**  
A：是的，`YourSdkClient` 是线程安全的，建议作为单例使用。

**Q3：如何查看 SDK 发出的请求日志？**  
A：开启 `debug: true` 并确保日志级别为 `DEBUG`，SDK 会输出请求 URL 和参数。

**Q4：能否支持 Spring Cloud OpenFeign？**  
A：当前不直接支持，但可通过封装 `YourSdkClient` 作为 Feign 调用的代理层。

---

## 11. 版本更新日志（Changelog）

### v1.0.0（2025-04-05）

- 初始版本发布
- 支持订单创建、查询、退款
- 集成 Spring Boot 自动装配
- 支持 OkHttp 自定义配置

---

## 12. 技术支持

- 邮箱：support@yourcompany.com
- 开发者平台：[https://console.yourcompany.com](https://console.yourcompany.com)
- 提交 Issue：[GitHub Issues](https://github.com/yourcompany/your-sdk-java/issues)
- 紧急问题：拨打技术支持热线 400-XXX-XXXX

---

## 附录

### A. 完整配置项清单

| 配置项                        | 默认值                            | 说明        |
|----------------------------|--------------------------------|-----------|
| `your-sdk.enabled`         | true                           | 是否启用 SDK  |
| `your-sdk.app-id`          | -                              | 应用 ID（必填） |
| `your-sdk.app-secret`      | -                              | 应用密钥（必填）  |
| `your-sdk.api-base-url`    | https://api.yourcompany.com/v1 | API 基础地址  |
| `your-sdk.connect-timeout` | 5000                           | 连接超时（ms）  |
| `your-sdk.read-timeout`    | 10000                          | 读取超时（ms）  |
| `your-sdk.max-retries`     | 3                              | 失败重试次数    |
| `your-sdk.debug`           | false                          | 是否输出调试日志  |

---

### B. 测试环境配置

```yaml
your-sdk:
  app-id: test_app_123
  app-secret: test_secret_xyz
  api-base-url: https://api-sandbox.yourcompany.com/v1
  debug: true
```

---

### C. 项目结构建议（SDK 内部）

```bash
your-sdk-client/
├── src/main/java
│   ├── client/YourSdkClient.java          # 主客户端
│   ├── config/YourSdkProperties.java      # 配置类
│   ├── config/YourSdkAutoConfiguration.java # 自动装配
│   ├── exception/YourSdkException.java     # 基础异常
│   ├── interceptor/RequestInterceptor.java # 拦截器接口
│   └── model/                              # 请求/响应模型
├── src/main/resources
│   └── META-INF/spring.factories           # Spring Boot 自动装配入口
```

`spring.factories` 内容：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.yourcompany.sdk.config.YourSdkAutoConfiguration
```

---

✅ **文档维护建议**：

- 使用 **Swagger / JavaDoc** 生成 API 文档
- 提供 **可运行的 demo 项目**（GitHub 示例）
- 每次发版同步更新文档版本号和 changelog
- 支持 PDF / Markdown / HTML 多格式导出

---

如果你提供的是开源 SDK，还可以增加：

- License 说明（如 Apache 2.0）
- 贡献指南（CONTRIBUTING.md）
- 代码示例仓库链接



