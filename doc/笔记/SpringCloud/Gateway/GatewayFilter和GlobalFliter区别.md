在 Spring Cloud Gateway 中，`GatewayFilter` 和 `GlobalFilter` 都是用于处理 HTTP 请求和响应的过滤器，但它们的作用范围和使用场景有所不同。

### 1. **`GatewayFilter`**

#### 定义：

`GatewayFilter` 是一种局部过滤器，通常用于特定的路由上。它在处理某个具体请求时，根据路由规则进行过滤，通常与 `Route`（路由）相关联。

#### 作用范围：

* 只作用于某个特定的 **路由**，即只有匹配到该路由的请求才会被该过滤器处理。
* 适用于处理单个请求的定制需求，比如认证、日志、限流等，针对某个路由的过滤逻辑进行处理。

#### 使用场景：

* 需要在某个特定的路由上应用过滤逻辑时使用。比如，你只希望在某个特定的 API 上应用身份认证、日志记录等操作。

#### 示例：

```java

@Bean
public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	return builder.routes()
			.route(r -> r.path("/api/secure/**")
					.filters(f -> f.addRequestHeader("X-Secure-Header", "true"))  // 添加请求头
					.uri("http://example.com"))
			.build();
}
```

在这个例子中，`GatewayFilter` 是通过 `filters()` 来应用的，仅在 `/api/secure/**` 路由的请求上生效。

#### 配置方式：

* **过滤器链：** `GatewayFilter` 是通过 `RouteLocator` 配置的，每个路由可以有一个或多个过滤器。
* **局部过滤器：** 只对特定的请求进行处理。

### 2. **`GlobalFilter`**

#### 定义：

`GlobalFilter` 是一种全局过滤器，它作用于所有的请求，通常用于需要在所有请求中进行处理的场景，如请求日志、全局认证、全局限流等。

#### 作用范围：

* 作用于 **所有路由**，即无论请求匹配哪个路由，都会经过 `GlobalFilter`。
* 适用于一些全局的、对所有请求都有效的逻辑，例如请求的日志记录、全局认证、跨域处理等。

#### 使用场景：

* 需要对所有请求进行统一处理的场景。比如全局的身份认证、请求限流、日志记录等。

#### 示例：

```java

@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 全局请求日志记录
		System.out.println("Request path: " + exchange.getRequest().getPath());
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		// 设置过滤器执行顺序
		return -1;  // 执行顺序，负数越小越优先执行
	}
}
```

在这个例子中，`CustomGlobalFilter` 会作用于所有请求，在请求路径输出日志。

#### 配置方式：

* **全局过滤器：** `GlobalFilter` 是通过 `@Component` 或配置类注册的，自动作用于所有路由。
* **全局过滤器链：** 这些过滤器不会受具体路由的限制，会在所有请求中起作用。

### 区别总结

| **特性**   | **`GatewayFilter`**          | **`GlobalFilter`**             |
|----------|------------------------------|--------------------------------|
| **作用范围** | 只作用于特定的路由，局部过滤器              | 作用于所有的路由，全局过滤器                 |
| **使用场景** | 路由级别的过滤，例如：限流、认证、路由特定逻辑      | 全局请求处理，如日志记录、全局认证等             |
| **配置方式** | 在路由配置时使用，如 `RouteLocator` 配置 | 使用 `@Component` 注解注册，自动应用到所有路由 |
| **执行顺序** | 在匹配的路由请求处理时执行                | 在所有路由请求处理时执行                   |
| **应用粒度** | 精确到路由                        | 对所有路由生效                        |

### 扩展：

* `GatewayFilter` 可以与 `GlobalFilter` 结合使用。`GlobalFilter` 会在请求进入路由之前执行，而 `GatewayFilter`
  则在路由匹配之后执行，因此它们可以组合使用，实现更灵活的过滤逻辑。

例如，`GlobalFilter` 可以用来做日志记录和全局认证，`GatewayFilter` 用来处理某个特定路由的认证和限流策略。
