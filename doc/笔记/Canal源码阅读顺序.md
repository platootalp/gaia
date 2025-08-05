## 📚 一、前置知识准备

在阅读源码前，建议先掌握以下基础知识：

1. **MySQL binlog 原理**
    - 了解 `ROW`、`STATEMENT`、`MIXED` 三种格式
    - 理解 binlog event 类型（如 `WriteRowsEvent`、`UpdateRowsEvent` 等）

2. **Netty 基础**
    - Canal Server 使用 Netty 实现高性能网络通信

3. **ZooKeeper / Spring / Spring Boot**
    - Canal 使用 ZooKeeper 做集群协调
    - 项目基于 Spring 框架构建

4. **Java NIO、多线程、阻塞队列等基础**

---

## 🧭 二、源码阅读推荐顺序（由浅入深）

### ✅ 第一阶段：了解整体架构和启动流程

**目标**：掌握 Canal 的模块划分、核心组件和启动流程。

1. **入口类：`CanalLauncher`**
    - 位于 `canal.deployer` 模块
    - 是 Canal Server 的启动入口
    - 阅读 `main()` 方法，了解 Spring 容器如何加载配置

2. **配置文件解析：`canal.properties` 和 `instance.properties`**
    - 理解 Canal 的配置体系
    - 关注 `destination`、`dbAddress`、`username/password` 等关键配置

3. **核心组件：`CanalInstance`**
    - 每个数据同步任务对应一个 `CanalInstance`
    - 包含 `EventParser`、`EventSink`、`EventStore`、`MetaManager`、`AlarmHandler` 等

> 🔍 建议路径：`CanalLauncher → CanalController → CanalInstance`  
> 目标：画出组件关系图，理解“一个 instance 代表一个数据源同步任务”

---

### ✅ 第二阶段：理解核心处理流程（Pipeline）

Canal 的数据同步流程是一个典型的 **生产者 → 中转 → 消费者** 模型，称为 **Pipeline**。

#### 1. **EventParser（数据解析）**

- 负责连接 MySQL，拉取 binlog 并解析为 `Event`
- 核心类：`AbstractMysqlEventParser`、`ShowMode`、`LogEventConvert`
- 关键技术：使用 `mysql-binlog-connector-java` 库解析 binlog

> 📌 重点：`MysqlConnection` 如何 dump binlog？如何处理 GTID、位点（position）？

#### 2. **EventSink（数据中转）**

- 将解析后的 event 过滤、加工后传递给 `EventStore`
- 默认实现：`EntryEventSink`
- 支持 filter（如 table filter）

#### 3. **EventStore（数据存储）**

- 内存中的环形缓冲区（`RingBuffer`），基于 Disruptor 实现
- 核心类：`MemoryEventStoreWithBuffer`
- 存储结构：`Entry` → `Header + RowData`

> 📌 重点：RingBuffer 的 put/get 机制，如何避免内存溢出？

#### 4. **MetaManager（元数据管理）**

- 管理消费位点（cursor）、ack/rollback 状态
- 默认实现：`ZooKeeperMetaManager` 或 `LocalMetaManager`

---

### ✅ 第三阶段：客户端消费机制（Get/Ack/Rollback）

这是 Canal 的特色协议，理解它对掌握异步流式消费至关重要。

1. **Canal Server 端：`CanalServerWithEmbedded`**
    - 嵌入式 Server，提供 `get`、`ack`、`rollback` 接口
    - 位于 `canal.server` 模块

2. **客户端流程：`CanalConnector` 实现类（如 `SimpleCanalConnector`）**
    - `connector.connect()` → `connector.subscribe()` → `connector.getWithoutAck()`
    - 理解 `batchId` 的生成与 ack 机制

3. **异步流式 API 原理**
    - `get` 可以连续调用，返回一批 Entry
    - `ack(batchId)` 异步确认，Server 更新位点
    - `rollback()` 用于失败回滚，重置消费位点

> 📌 重点：MetaManager 如何记录未 ack 的 batch？如何防止位点错乱？

---

### ✅ 第四阶段：网络通信与高可用

1. **Netty 处理器：`CanalServerWithNetty`**
    - 基于 Netty 实现 TCP 协议通信
    - 处理 `ClientQuery`、`ClientAck`、`ClientSubscribe` 等指令

2. **集群模式：`CanalHAController`**
    - 基于 ZooKeeper 实现主备切换
    - `ZookeeperRunningMonitor` 监控 instance 是否存活

3. **ZooKeeper 路径结构**
    - `/otter/canal/destinations/{name}/cluster` → server 列表
    - `/otter/canal/destinations/{name}/running` → 当前运行节点

---

### ✅ 第五阶段：深入细节与扩展点

1. **Binlog 解析细节**
    - `LogEventConvert` 如何将 MySQL event 转为 `Entry`
    - `RowData` 结构：old / new value、SQL 类型映射

2. **Filter 机制**
    - 正则表达式过滤表：`canal.instance.filter.regex`
    - `TableMetaTSDB` 缓存表结构

3. **监控与报警**
    - `CanalAlarmHandler` 支持邮件、HTTP 回调等

4. **SPI 扩展机制**
    - 如自定义 `EventStore`、`MetaManager`、`AlarmHandler`

---

## 🗺️ 推荐阅读路径图

```
启动流程
   ↓
CanalLauncher → CanalController → CanalInstance
   ↓
Pipeline 组件
   ↓
EventParser → EventSink → EventStore
   ↓
客户端协议
   ↓
CanalServer / CanalConnector (get/ack/rollback)
   ↓
网络通信
   ↓
CanalServerWithNetty + Protocol
   ↓
高可用
   ↓
ZooKeeper + HA
   ↓
扩展机制与SPI
```

---

## 📂 模块结构（canal 项目）

| 模块               | 作用               |
|------------------|------------------|
| `canal-common`   | 公共类、模型定义         |
| `canal-parser`   | binlog 解析核心      |
| `canal-sink`     | 数据中转             |
| `canal-store`    | 数据存储（RingBuffer） |
| `canal-server`   | Server 接口与实现     |
| `canal-admin`    | 配置管理后台（新版本）      |
| `canal-deployer` | 启动脚本和配置          |
| `canal-client`   | 客户端 SDK          |

---

## ✅ 阅读建议

1. **先跑通 demo**：部署一个 Canal Server + Client，观察数据同步过程
2. **结合日志调试**：开启 DEBUG 日志，跟踪 `EventParser`、`EventStore` 等组件行为
3. **画图辅助**：绘制组件关系图、数据流图、状态机图
4. **关注注释和 Wiki**：Canal 官方 GitHub 有详细文档

---

## 🔗 参考资源

- GitHub 仓库：[https://github.com/alibaba/canal](https://github.com/alibaba/canal)
- 官方 Wiki：[https://github.com/alibaba/canal/wiki](https://github.com/alibaba/canal/wiki)
- 源码注释丰富，建议使用 IDE（IntelliJ IDEA）进行调试阅读

