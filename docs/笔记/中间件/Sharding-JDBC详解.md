Sharding-JDBC 是 Apache ShardingSphere 项目中的一个重要组成部分，是一个轻量级的 Java 框架，定位为 **客户端数据库中间件**，用于在应用层实现数据库的分片（Sharding）、读写分离、数据加密等功能，而无需依赖独立的中间件服务。它通过 JDBC 的方式集成到 Java 应用中，兼容大部分 JDBC 和主流 ORM 框架（如 MyBatis、JPA 等）。

---

## 一、Sharding-JDBC 简介

### 1.1 什么是 Sharding-JDBC？

- **定位**：Java 客户端分库分表中间件。
- **核心功能**：
    - 数据分片（分库分表）
    - 读写分离
    - 数据脱敏（加密）
    - 分布式主键生成
    - 柔性事务（仅支持最大努力送达型）
- **特点**：
    - 无中心化部署，直接嵌入应用。
    - 对业务代码透明，只需配置即可使用。
    - 支持标准 SQL，兼容大多数 SQL 语法。
    - 支持多种数据源（MySQL、PostgreSQL、Oracle、SQLServer 等）。

> ⚠️ 注意：从 ShardingSphere 5.0 开始，**Sharding-JDBC 已更名为 `ShardingSphere-JDBC`**，属于 ShardingSphere 的三个产品之一（另两个是 Proxy 和 Sidecar）。

---

## 二、核心概念

### 2.1 逻辑表（Logic Table）

指一组具有相同结构的水平拆分后的表的逻辑集合。

例如：订单表按用户 ID 拆分为 `t_order_0`, `t_order_1`, ...，它们的逻辑表名为 `t_order`。

### 2.2 真实表（Actual Table）

数据库中真实存在的物理表。

如：`ds_0.t_order_0`, `ds_1.t_order_1`

### 2.3 数据节点（Data Node）

由数据源名称 + 真实表名组成，表示具体的数据存储位置。

格式：`data_source_name.table_name`

示例：`ds_0.t_order_0`

### 2.4 分片键（Sharding Key）

用于决定数据路由到哪个分片的字段，如 `user_id`、`order_id`。

### 2.5 分片策略（Sharding Strategy）

定义如何根据分片键进行数据分片，包括：

- **分库策略**（Database Sharding Strategy）
- **分表策略**（Table Sharding Strategy）

支持的策略类型：

| 类型 | 说明 |
|------|------|
| 标准分片（Standard） | 支持单分片键，提供 `PreciseShardingAlgorithm` 和 `RangeShardingAlgorithm` |
| 复合分片（Complex） | 支持多分片键 |
| Hint 分片 | 强制指定分片值，绕过 SQL 解析 |

### 2.6 分布式主键

ShardingSphere 提供内置的分布式主键生成器：

- UUID
- SNOWFLAKE（推荐，基于时间戳+机器ID）
- 自定义主键生成器

---

## 三、核心功能详解

### 3.1 分库分表（Sharding）

#### 场景：
当单表数据量过大（如超过千万行），查询性能下降，需水平拆分。

#### 配置示例（YAML）：

```yaml
dataSources:
  ds_0:
    url: jdbc:mysql://localhost:3306/demo_ds_0
    username: root
    password: root
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
  ds_1:
    url: jdbc:mysql://localhost:3306/demo_ds_1
    username: root
    password: root
    # ... 同上

rules:
- !SHARDING
  tables:
    t_order:
      actualDataNodes: ds_${0..1}.t_order_${0..1}
      tableStrategy:
        standard:
          shardingColumn: order_id
          shardingAlgorithmName: t_order_inline
      keyGenerateStrategy:
        column: order_id
        keyGeneratorName: snowflake
  defaultDatabaseStrategy:
    standard:
      shardingColumn: user_id
      shardingAlgorithmName: database_inline
  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: ds_${user_id % 2}
    t_order_inline:
      type: INLINE
      props:
        algorithm-expression: t_order_${order_id % 2}
  keyGenerators:
    snowflake:
      type: SNOWFLAKE
```

#### 解释：

- `actualDataNodes: ds_${0..1}.t_order_${0..1}`  
  表示有 2 个库（ds_0, ds_1），每个库有 2 张表（t_order_0, t_order_1），共 4 个数据节点。

- `defaultDatabaseStrategy`：默认按 `user_id % 2` 路由到 ds_0 或 ds_1。

- `t_order` 表按 `order_id % 2` 决定存入 t_order_0 或 t_order_1。

---

### 3.2 读写分离（Readwrite-splitting）

适用于主从架构，写操作走主库，读操作可走从库。

#### 配置示例：

```yaml
dataSources:
  write_ds:
    url: jdbc:mysql://localhost:3306/write_db
    username: root
    password: root
  read_ds_0:
    url: jdbc:mysql://localhost:3307/read_db_0
    username: root
    password: root
  read_ds_1:
    url: jdbc:mysql://localhost:3308/read_db_1
    username: root
    password: root

rules:
- !READWRITE_SPLITTING
  dataSources:
    rw_ds:
      writeDataSourceName: write_ds
      readDataSourceNames:
        - read_ds_0
        - read_ds_1
      loadBalancerName: round_robin
  loadBalancers:
    round_robin:
      type: ROUND_ROBIN
```

> 支持负载均衡策略：ROUND_ROBIN、RANDOM、WEIGHT（权重）

---

### 3.3 数据加密（Data Masking）

对敏感字段（如身份证、手机号）进行透明加解密。

#### 示例配置：

```yaml
rules:
- !ENCRYPT
  encryptors:
    aes_encryptor:
      type: AES
      props:
        aes-key-value: 123456abc
    md5_encryptor:
      type: MD5
  tables:
    t_user:
      columns:
        phone:
          plainColumn: phone_plain
          cipherColumn: phone_cipher
          encryptorName: aes_encryptor
        id_card:
          cipherColumn: id_card_cipher
          encryptorName: md5_encryptor
```

- `plainColumn`：明文列（可选）
- `cipherColumn`：密文列
- 插入时自动加密，查询时自动解密（若查 cipher 列则不解密）

---

### 3.4 分布式主键生成

#### 使用 Snowflake 示例：

```yaml
keyGenerators:
  snowflake:
    type: SNOWFLAKE
    props:
      worker-id: 123
```

在建表时指定主键生成策略：

```java
@KeySequence(value = "snowflake", generatedKeysColumn = "order_id")
```

或通过配置绑定到某列。

---

## 四、工作原理

Sharding-JDBC 的核心流程如下：

1. **SQL 解析**：使用自研 SQL 解析器（或基于 JSqlParser）解析 SQL，提取分片条件。
2. **路由**：根据分片策略计算出目标数据节点（库/表）。
3. **改写 SQL**：将逻辑 SQL 改写为真实数据库能执行的物理 SQL。
4. **执行**：通过底层数据源执行 SQL。
5. **归并结果**：将多个结果集合并为一个逻辑结果集返回。

> 整个过程对应用透明，开发者仍使用原生 JDBC 接口。

---

## 五、支持的 SQL 类型

| 类型 | 是否支持 |
|------|----------|
| SELECT（含 JOIN、子查询） | ✅（有限支持） |
| INSERT | ✅ |
| UPDATE | ✅（单表） |
| DELETE | ✅（单表） |
| DDL（CREATE TABLE 等） | ✅（广播至所有节点） |
| 分页（LIMIT/OFFSET） | ✅（优化，避免内存溢出） |
| GROUP BY / ORDER BY | ✅（内存归并） |
| HAVING | ✅ |
| DISTINCT | ✅（内存去重） |

> ❗注意：复杂 JOIN、子查询跨库时不支持或性能较差，建议通过应用层关联。

---

## 六、集成方式

### 6.1 Spring Boot 集成（Maven 依赖）

```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
    <version>5.3.2</version>
</dependency>
```

### 6.2 配置文件（application.yml）

参考上面的 YAML 示例，放入 `application.yml`。

### 6.3 Java API 方式（编程式配置）

```java
Map<String, DataSource> dataSourceMap = new HashMap<>();
dataSourceMap.put("ds_0", createDataSource("demo_ds_0"));
dataSourceMap.put("ds_1", createDataSource("demo_ds_1"));

// 配置 t_order 表规则
ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("t_order", "ds_${0..1}.t_order_${0..1}");
orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("order_id", "t_order_inline"));
orderTableRuleConfig.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("order_id", "snowflake"));

// 构建分片规则
ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
shardingRuleConfig.getTables().add(orderTableRuleConfig);

// 设置默认数据库分片策略
shardingRuleConfig.setDefaultDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "database_inline"));

// 配置算法
Properties dbProps = new Properties();
dbProps.setProperty("algorithm-expression", "ds_${user_id % 2}");
shardingRuleConfig.getShardingAlgorithms().put("database_inline", new ShardingSphereAlgorithmConfiguration("INLINE", dbProps));

// 构建数据源
DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), new Properties());
```

---

## 七、优点与局限

### ✅ 优点

- 轻量级，无额外部署成本。
- 对业务透明，兼容 JDBC 和主流 ORM。
- 支持灵活的分片策略和扩展 SPI。
- 社区活跃，文档完善。

### ❌ 局限

- 复杂 SQL 支持有限（如跨库 JOIN、事务一致性）。
- 不支持跨数据库实例的强一致性事务（XA/TCC 需额外方案）。
- 运维能力弱于 ShardingSphere-Proxy（无统一管理界面）。
- 扩容再平衡需手动处理（无自动 re-sharding）。

---

## 八、最佳实践建议

1. **合理设计分片键**：选择分布均匀、查询频繁的字段（如 user_id）。
2. **避免跨库 JOIN**：通过冗余字段或应用层 join 解决。
3. **分页优化**：使用“标签记录法”或“二次查询”减少内存占用。
4. **监控与压测**：使用 Prometheus + Grafana 监控 SQL 路由情况。
5. **结合 ShardingSphere-Proxy**：复杂场景可用 Proxy 作为网关。

---

## 九、常见问题

### Q1：Sharding-JDBC 和 MyBatis 如何整合？
A：完全兼容，只需将 `SqlSessionFactory` 的 `DataSource` 替换为 ShardingSphere 生成的数据源即可。

### Q2：支持事务吗？
A：支持本地事务（同一连接内）。分布式事务需配合 Seata 或使用 ShardingSphere-Transaction 模块（最大努力送达型）。

### Q3：如何实现扩容？
A：目前需手动迁移数据并调整分片算法（如从 `%2` 改为 `%4`），未来版本可能支持在线扩缩容。

---

## 十、总结

Sharding-JDBC（现 ShardingSphere-JDBC）是 Java 生态中非常成熟的数据库分片解决方案，适合中小型企业应对大数据量场景。其优势在于轻量、易集成、功能丰富，但对复杂分布式事务和高运维需求场景，建议结合 ShardingSphere-Proxy 使用。

> 📚 官方文档：[https://shardingsphere.apache.org](https://shardingsphere.apache.org)

