# 分布式ID

## 一、简介

### 1. 什么是分布式ID？

分布式ID（Distributed ID）指的是在分布式系统中生成的全局唯一的ID。它能够在多个节点、多个数据中心中并发生成且不重复，广泛应用于数据库主键、日志跟踪、消息序列号、业务订单号等场景。

### 2. 分布式ID核心要求

* **全局唯一**：避免ID冲突。
* **高性能**：分布式 ID 的生成速度要快，对本地资源消耗要小。
* **高可用**：生成分布式 ID 的服务要保证可用性无限接近于 100%。
* **有序性（部分算法）**：满足数据库索引优化、日志排序等需求。
* **去中心化**：分布式部署下仍能独立生成ID。

### 3. 常见使用场景

* 数据库主键（MySQL、PostgreSQL、MongoDB）
* 消息队列（RocketMQ、Kafka）消息ID
* 分布式文件、对象存储ID
* 用户ID、订单ID、交易流水号
* API请求跟踪ID（TraceID）

---

## 二、分布式ID常见解决方案

| 类别            | 算法/方式           | 特点                        |
| ------------- | --------------- | ------------------------- |
| **UUID**      | UUIDv1/v4/v7/v8 | 全局唯一，无序，不可读，长度较长（128位）    |
| **雪花算法**      | Snowflake       | 有序，64位，时间+机器+序列，Twitter提出 |
| **数据库法**      | 单表自增ID，全局ID表    | 简单，可靠，性能瓶颈，单点问题           |
| **Redis法**    | Redis原子自增INCR   | 高并发，依赖Redis，受Redis可用性影响   |
| **Zookeeper** | 顺序节点            | 强一致，低性能，不推荐高并发使用          |
| **Segment**   | 美团Leaf（号段模式）    | 缓存预分配号段，性能极高，缺点是预分配       |
| **TinyID**    | 携程开源TinyID      | 分布式号段ID服务，支持数据库持久化        |
| **Base62短ID** | Hash/编码压缩       | 可读性强，适用于URL短链、邀请码         |

---

### 雪花算法（Snowflake）详细说明

| 位数 | 含义     | 位长度    | 最大值            |
| -- | ------ | ------ | -------------- |
| 1  | 符号位    | 1 bit  | 永远为0           |
| 2  | 时间戳    | 41 bit | 69年            |
| 3  | 数据中心ID | 5 bit  | 最大31个数据中心      |
| 4  | 机器ID   | 5 bit  | 最大32台机器        |
| 5  | 序列号    | 12 bit | 每毫秒支持4096个ID生成 |
| 合计 |        | 64 bit |                |

**优点：** 高并发、趋势递增、低延迟、无中心。

**缺点：** 时钟回拨问题，机器ID分配困难。

---

### UUID简述

* `UUIDv1`: 基于时间戳 + MAC地址
* `UUIDv4`: 随机数
* `UUIDv7`: 新一代，基于时间戳 + 随机，支持顺序性（推荐）

**优点：** 无中心依赖。

**缺点：** 长度大（128位），无序，对数据库索引不友好。

---

### 号段模式（Leaf/TinyID）

* 数据库维护号段：

```sql
table: id_alloc
+--------+-----------+--------+
| biz_tag| max_id    | step   |
+--------+-----------+--------+
```

* 客户端拉取一段ID到本地，内存自增。
* 性能极高，QPS百万级。

---

## 三、使用实例

### ✅ 雪花算法Java实现示例

```java
public class SnowflakeIdGenerator {
    private final long workerId;
    private final long datacenterId;
    private final long sequenceBits = 12L;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & ((1 << sequenceBits) - 1);
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - 1609459200000L) << 22)
                | (datacenterId << 17)
                | (workerId << 12)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
```

### ✅ Redis自增ID示例

```bash
INCR id:order
# 返回 100001
```

Java示例：

```java
Long id = redisTemplate.opsForValue().increment("id:order");
```

### ✅ UUID示例

```java
UUID uuid = UUID.randomUUID();
System.out.println(uuid.toString());
```

### ✅ 美团Leaf号段模式

* 启动Leaf服务
* Java客户端请求ID

```java
long id = leafSegmentService.getId("order_id").getId();
```

### ✅ 短ID（Base62编码）

* 示例ID：`aZ4c2d`
* 用于邀请码、短链URL、可读ID。

---

## 四、总结与选择建议

| 需求/场景      | 推荐方案              |
| ---------- | ----------------- |
| 高并发、低延迟    | 雪花算法、号段模式         |
| 多租户ID、唯一标识 | UUIDv7 或 雪花       |
| 可读性ID      | Base62短ID         |
| 简单、无需部署    | Redis自增           |
| 高可用强一致     | TinyID、Leaf（号段模式） |

---

## 五、企业级建议

* ✔ 核心业务推荐自建Leaf或TinyID服务。
* ✔ API TraceID推荐UUIDv7。
* ✔ 公开展示（工单、租户编码）推荐Snowflake或Base62。
* ✔ 数据库主键避免UUIDv4，建议Snowflake或UUIDv7。


