# Java 设计模式与架构模型速查手册（完整版）

> 本手册汇总了 Java 中常用的设计模式与架构模型，并逐一详细介绍每种模型的作用、结构、示例及典型应用场景，适用于构建企业级系统、平台
> SDK、微服务架构、插件化平台等复杂系统的工程师使用。

## 🧩 设计模式清单与应用概览

| 模型                          | 核心作用                  | 典型接口/类                               | 应用场景                                            |
|-----------------------------|-----------------------|--------------------------------------|-------------------------------------------------|
| **Singleton**               | 确保全局只有一个实例，并提供全局访问点   | 私有构造 + `getInstance()`               | 全局配置、线程池管理、JVM 级缓存                              |
| **Factory**                 | 封装对象创建逻辑，屏蔽具体实现       | `Factory Method` / `AbstractFactory` | 动态选择实现（上传器、序列化器、数据库驱动等）                         |
| **Builder**                 | 分步构建复杂对象，并支持链式调用      | `static builder()` + `build()`       | DTO/VO 构建、大量可选参数的请求对象                           |
| **Prototype**               | 克隆已有对象，避免重复初始化        | `clone()` 方法                         | 重量级对象复制、初始化开销大的对象复用                             |
| **Strategy**                | 将算法封装为可替换策略           | 策略接口 + 多个实现                          | 上传策略（流式/分片/断点续传）、支付渠道、鉴权方式                      |
| **Adapter**                 | 将一个接口转换为客户端所期望的另一接口   | 适配器类实现目标接口                           | 第三方 SDK 封装、老系统兼容                                |
| **Facade**                  | 为一组子系统提供统一简化接口        | `Facade` 类聚合多个子系统调用                  | 对外服务网关、模块聚合入口                                   |
| **Proxy**                   | 为目标对象提供代理，以在访问时添加控制逻辑 | 动态/静态代理实现                            | 权限校验、延迟加载、RPC 调用                                |
| **Decorator**               | 在不改变原对象的条件下，动态添加行为    | 抽象装饰器 + 具体装饰器                        | IO 流增强（Buffered → GZIP）、功能拓展                    |
| **Composite**               | 将对象组合成树形结构，统一对待叶子和容器  | 组件接口 + 容器 + 叶子                       | 菜单树、权限树、配置项层级                                   |
| **Template**                | 定义执行骨架，将变化步骤留给子类      | 抽象类 `templateMethod()`               | 通用流程控制（上传流程、任务调度、事务管理）                          |
| **Chain of Responsibility** | 将请求沿链传递，直到有处理者为止      | 责任接链接口 + 链节点                         | 拦截器链（Filter/Interceptor）、事件处理、命令路由              |
| **Command**                 | 将请求封装为命令对象            | 命令接口 + 具体命令 + 调用者                    | 操作回滚、任务队列、宏命令                                   |
| **Observer**                | 对象状态变化时，自动通知多个订阅对象    | `Observable` + `Observer`            | 事件发布/订阅、缓存失效通知                                  |
| **State**                   | 允许对象在内部状态改变时行为变化      | 状态接口 + 具体状态实现                        | 上传状态机（Pending → Uploading → Completed → Failed） |
| **Memento**                 | 在不破坏封装性的前提下保存/恢复对象状态  | 备忘录类 + 发起人 + 管理者                     | 撤销/重做、事务回滚                                      |
| **DAO / Repository**        | 抽象数据访问                | `DAO` 接口 + 实现类                       | JDBC / MyBatis / JPA 数据库交互                      |
| **Service Locator**         | 动态查找服务实现              | 注册中心 + 查找方法                          | SPI、插件式框架、微服务发现                                 |
| **Manager**                 | 管理资源或组件的生命周期与状态       | 管理器类（Map + 生命周期方法）                   | 连接池、线程池、会话、客户端实例管理                              |
| **Register**                | 注册并查找动态扩展点            | 注册中心（`Map<String, T>`）               | 策略注册、Handler、Processor、插件注册                     |
| **Plugin**                  | 支持热插拔扩展               | 插件接口 + 插件加载器                         | IDE 插件、平台插件（大模型检索/翻译插件等）                        |
| **Filter**                  | Servlet 层面请求/响应预处理    | 实现 `javax.servlet.Filter`            | 全局日志、跨域、压缩、安全校验                                 |
| **Interceptor**             | 框架层（MVC/ORM）前后处理      | `HandlerInterceptor` / JAX‑RS 拦截器    | Controller 前后流程、实体流审计、AOP 风格切面                  |
| **Listener**                | 监听容器/会话/请求生命周期事件      | `ServletContextListener` 等           | Spring 上下文初始化、Session 统计、资源清理                   |

---

## 📘 模型详解

### 1. Singleton（单例模式）

#### 📌 动机

在全局范围内共享同一个实例，避免重复创建，节省资源，同时提供统一访问入口。例如：日志记录器、配置管理器、线程池、数据库连接池等。

#### 🔧 实现方式

* 饿汉式：类加载即创建实例，线程安全，推荐使用
* 懒汉式：延迟初始化，需加锁保证线程安全
* 枚举式：线程安全且防反序列化攻击

#### ✅ 示例

```java
public class ConfigManager {
	private static final ConfigManager INSTANCE = new ConfigManager();

	private ConfigManager() {
	}

	public static ConfigManager getInstance() {
		return INSTANCE;
	}
}
```

#### 🔄 与其他模型组合

* 可与 **Facade** 结合封装多个子系统实例
* 与 **Factory** 配合实现单例工厂类

#### ⚠️ 常见误用

* 懒汉式实现未加锁（线程不安全）
* 多线程中使用双重检查未加 volatile
* 滥用单例导致状态不可预测、难测试

---

### 2. Factory（工厂模式）

#### 📌 动机

屏蔽对象创建过程，使得客户端只依赖接口而非具体实现，提升系统的灵活性和可维护性。

#### 🔧 类型

* 简单工厂：根据类型参数返回具体类实例
* 工厂方法：每个子类负责创建一种产品
* 抽象工厂：创建多个系列产品族的工厂接口

#### ✅ 示例

```java
public interface FileUploader {
	void upload(File file);
}

public class OssUploader implements FileUploader { ...
}

public class S3Uploader implements FileUploader { ...
}

public class UploaderFactory {
	public static FileUploader getUploader(String type) {
		return switch (type) {
			case "oss" -> new OssUploader();
			case "s3" -> new S3Uploader();
			default -> throw new IllegalArgumentException("unknown type");
		};
	}
}
```

#### 🔄 与其他模型组合

* 与 **Strategy** 搭配：动态选择策略实现
* 与 **Singleton** 搭配：统一工厂为单例

#### ⚠️ 常见误用

* 工厂类过于庞大、违背单一职责
* 多个产品混在一起未封装成独立子工厂
* 使用反射创建实例缺乏约束与安全性

好的，接下来我继续为你详细补充 **Builder（构建器模式）**、**Prototype（原型模式）** 和 **Strategy（策略模式）**
的内容，包括动机、结构、示例、组合以及常见误用。

---

### 3. Builder（构建器模式）

#### 📌 动机

当一个对象包含多个可选参数或复杂的子对象时，构造函数会变得臃肿且难以维护。Builder 模式通过分步构建、链式调用，提供灵活且可读性强的对象创建方式。

#### 🔧 实现方式

* 内部静态 Builder 类，包含待构建对象的所有字段
* Builder 提供链式设置方法，最后调用 `build()` 生成目标对象
* 目标对象构造函数私有，防止外部直接实例化

#### ✅ 示例

```java
public class FileUploadRequest {
	private final String filename;
	private final long size;
	private final String contentType;
	private final boolean compress;

	private FileUploadRequest(Builder builder) {
		this.filename = builder.filename;
		this.size = builder.size;
		this.contentType = builder.contentType;
		this.compress = builder.compress;
	}

	public static class Builder {
		private String filename;
		private long size;
		private String contentType;
		private boolean compress;

		public Builder filename(String filename) {
			this.filename = filename;
			return this;
		}

		public Builder size(long size) {
			this.size = size;
			return this;
		}

		public Builder contentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder compress(boolean compress) {
			this.compress = compress;
			return this;
		}

		public FileUploadRequest build() {
			return new FileUploadRequest(this);
		}
	}
}
```

使用：

```java
FileUploadRequest request = new FileUploadRequest.Builder()
		.filename("test.txt")
		.size(1024)
		.contentType("text/plain")
		.compress(true)
		.build();
```

#### 🔄 与其他模型组合

* 常与 **Factory** 结合，通过工厂返回 Builder 实例
* 与 **Singleton** 配合管理 Builder 实例缓存
* 在复杂对象创建后，可结合 **Prototype** 做对象复制

#### ⚠️ 常见误用

* 过度设计简单对象，导致代码冗余
* Builder 类过大，包含过多字段，违背单一职责
* 忽略参数校验导致构建非法对象

---

### 4. Prototype（原型模式）

#### 📌 动机

当新建对象开销较大，或需要大量相似对象时，使用克隆已有对象来提高性能。原型模式通过实现克隆接口，快速复制实例。

#### 🔧 实现方式

* 实现 `Cloneable` 接口，覆盖 `clone()` 方法
* 深拷贝或浅拷贝，视需求而定
* 可结合对象池模式缓存原型实例

#### ✅ 示例

```java
public class User implements Cloneable {
	private String name;
	private int age;
	private List<String> tags;

	public User(String name, int age, List<String> tags) {
		this.name = name;
		this.age = age;
		this.tags = tags;
	}

	@Override
	protected User clone() {
		try {
			User cloned = (User) super.clone();
			// 深拷贝集合
			cloned.tags = new ArrayList<>(this.tags);
			return cloned;
		}
		catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
```

使用：

```java
User user1 = new User("Alice", 30, Arrays.asList("admin", "user"));
User user2 = user1.clone();
user2.

setName("Bob");

```

#### 🔄 与其他模型组合

* 可结合 **Builder** 模式提供克隆后的二次构建能力
* 与 **Manager** 模式结合管理原型缓存实例
* 结合 **Factory** 实现动态克隆对象创建

#### ⚠️ 常见误用

* 使用浅拷贝导致对象共享可变字段，引发线程安全和状态污染
* 忽略 `CloneNotSupportedException` 导致运行时异常
* 滥用克隆而非设计清晰对象创建流程，代码难以维护

---

### 5. Strategy（策略模式）

#### 📌 动机

系统中存在多种可替换算法或行为，策略模式将算法封装成独立的策略类，客户端通过上下文选择具体策略实现，符合开闭原则。

#### 🔧 实现方式

* 定义策略接口，声明算法方法
* 多个策略类实现接口，封装具体算法
* 上下文持有策略引用，通过接口调用策略方法

#### ✅ 示例

```java
public interface UploadStrategy {
	void upload(File file);
}

public class StreamUploadStrategy implements UploadStrategy {
	public void upload(File file) {
		System.out.println("Stream uploading " + file.getName());
	}
}

public class ChunkUploadStrategy implements UploadStrategy {
	public void upload(File file) {
		System.out.println("Chunk uploading " + file.getName());
	}
}

public class UploadContext {
	private UploadStrategy strategy;

	public UploadContext(UploadStrategy strategy) {
		this.strategy = strategy;
	}

	public void setStrategy(UploadStrategy strategy) {
		this.strategy = strategy;
	}

	public void executeUpload(File file) {
		strategy.upload(file);
	}
}
```

使用：

```java
UploadContext context = new UploadContext(new StreamUploadStrategy());
context.

executeUpload(file);
context.

setStrategy(new ChunkUploadStrategy());
		context.

executeUpload(file);

```

#### 🔄 与其他模型组合

* 可由 **Factory** 创建具体策略实例
* 配合 **Register** 实现策略动态注册和查找
* 结合 **Decorator** 动态增强策略行为

#### ⚠️ 常见误用

* 策略数量过多，未做良好管理，导致维护困难
* 策略之间依赖强耦合，违反单一职责原则
* 将策略逻辑写死在上下文，失去灵活性

好的，下面详细补充 **Adapter（适配器模式）**、**Facade（外观模式）**、**Proxy（代理模式）** 这三个结构型设计模式的内容。

---

### 6. Adapter（适配器模式）

#### 📌 动机

系统中存在两个接口不兼容，但需要协同工作。Adapter 模式通过引入适配器类，将一个接口转换成客户端期望的接口，实现兼容性。

#### 🔧 实现方式

* 适配器类实现目标接口，内部持有被适配者实例
* 将目标接口的方法调用转发给被适配者的对应方法

#### ✅ 示例

假设已有第三方 SDK `OssClient` 接口如下：

```java
public class OssClient {
	public void putObject(File file) {
		System.out.println("Uploading file to OSS: " + file.getName());
	}
}
```

我们的系统定义了统一的上传接口：

```java
public interface FileUploader {
	void upload(File file);
}
```

适配器实现：

```java
public class OssUploaderAdapter implements FileUploader {
	private final OssClient ossClient;

	public OssUploaderAdapter(OssClient ossClient) {
		this.ossClient = ossClient;
	}

	@Override
	public void upload(File file) {
		ossClient.putObject(file);
	}
}
```

使用：

```java
OssClient ossClient = new OssClient();
FileUploader uploader = new OssUploaderAdapter(ossClient);
uploader.

upload(new File("test.txt"));
```

#### 🔄 与其他模型组合

* 结合 **Facade** 提供统一外观封装多个适配器
* 与 **Factory** 结合动态生成不同适配器实例
* 结合 **Decorator** 动态增强适配器功能

#### ⚠️ 常见误用

* 适配器类职责过重，处理过多转换逻辑
* 滥用适配器替代重构接口，增加复杂度
* 适配器链过长，造成性能和维护负担

---

### 7. Facade（外观模式）

#### 📌 动机

系统内部子系统复杂，客户端调用繁琐，Facade 模式为多个子系统提供一个统一且简化的接口，降低调用复杂度，隐藏内部实现细节。

#### 🔧 实现方式

* 定义一个门面类，聚合多个子系统接口
* 门面类对外暴露简化的方法，调用各子系统完成复杂业务

#### ✅ 示例

假设有如下子系统：

```java
public class AuthService {
	public void authenticate() {
		System.out.println("Authentication succeeded");
	}
}

public class StorageService {
	public void store(File file) {
		System.out.println("File stored: " + file.getName());
	}
}

public class LoggingService {
	public void log(String message) {
		System.out.println("Log: " + message);
	}
}
```

Facade 类：

```java
public class UploadFacade {
	private final AuthService authService = new AuthService();
	private final StorageService storageService = new StorageService();
	private final LoggingService loggingService = new LoggingService();

	public void uploadFile(File file) {
		authService.authenticate();
		storageService.store(file);
		loggingService.log("Uploaded file: " + file.getName());
	}
}
```

使用：

```java
UploadFacade facade = new UploadFacade();
facade.

uploadFile(new File("example.txt"));
```

#### 🔄 与其他模型组合

* 与 **Singleton** 结合，保证全局唯一门面实例
* 结合 **Factory** 生成子系统或门面实例
* 结合 **Proxy** 为门面增加访问控制或缓存

#### ⚠️ 常见误用

* 门面类功能膨胀，承担过多职责
* 客户端绕过门面，直接调用子系统，导致耦合
* 门面封装过浅，未真正降低复杂度

---

### 8. Proxy（代理模式）

#### 📌 动机

代理模式为目标对象提供一个代理对象，通过代理对象控制对目标对象的访问，常用于延迟加载、权限控制、日志记录等场景。

#### 🔧 实现方式

* 代理类实现与目标对象相同的接口
* 代理内部持有目标对象实例
* 代理方法对目标对象的方法调用进行增强或控制

#### ✅ 示例

定义接口：

```java
public interface FileUploader {
	void upload(File file);
}
```

目标类：

```java
public class RealUploader implements FileUploader {
	@Override
	public void upload(File file) {
		System.out.println("Uploading file: " + file.getName());
	}
}
```

代理类：

```java
public class LoggingUploaderProxy implements FileUploader {
	private final FileUploader realUploader;

	public LoggingUploaderProxy(FileUploader realUploader) {
		this.realUploader = realUploader;
	}

	@Override
	public void upload(File file) {
		System.out.println("Start upload: " + file.getName());
		realUploader.upload(file);
		System.out.println("End upload: " + file.getName());
	}
}
```

使用：

```java
FileUploader uploader = new LoggingUploaderProxy(new RealUploader());
uploader.

upload(new File("data.txt"));
```

#### 🔄 与其他模型组合

* 与 **Decorator** 结合，动态叠加多个代理
* 结合 **Factory** 动态创建代理实例
* 配合 **Singleton** 管理代理单例

#### ⚠️ 常见误用

* 代理链过长，导致调用栈复杂，性能下降
* 代理实现与目标对象强耦合，导致难以替换
* 将复杂业务逻辑放入代理，违背单一职责





