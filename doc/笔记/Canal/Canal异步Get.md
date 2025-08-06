## Canal异步Get/Ack详解

### 基本概念

Canal的`get/ack/rollback`协议采用**异步流式处理模型**，允许客户端：

1. 连续调用`get`获取多批数据（无需等待前一批ack）
2. 后续按**严格顺序**异步提交`ack`或`rollback`
3. 通过**递增游标**保证数据顺序性

### 流式API工作原理

1. **异步处理机制**
   ```mermaid
   graph LR
   Client-->|get1| CanalServer
   Client-->|get2| CanalServer
   Client-->|异步ack1| CanalServer
   Client-->|ack2| CanalServer
   ```

2. **双游标系统**
    - `getCursor`：客户端最新拉取的binlog位置（可多线程推进）
    - `ackCursor`：服务端确认消费成功的位置（**必须严格连续**）

3. **数据消费流程**
   ```java
   // 伪代码示例
   Long batchId = null;
   try {
     for (int i=0; i<5; i++) {
       // 连续get不阻塞
       Message message = connector.getWithoutAck(100); 
       batchId = message.getId();
       process(message);
     }
     // 按顺序提交最后batchId
     connector.ack(batchId); 
   } catch (Exception e) {
     connector.rollback(batchId); // 回滚到未确认位置
   }
   ```

### 技术优势对比

| 模式        | 吞吐量   | 资源消耗  | 顺序保证  | 复杂度 |
|-----------|-------|-------|-------|-----|
| 同步ACK     | 低     | 高     | 强     | 低   |
| **异步ACK** | **高** | **低** | **强** | 中   |

### 关键协议组件

1. **Get**
    - 参数：`batchSize`（控制单次获取量）
    - 返回：`batchId`（唯一递增标识）

2. **Ack**
    - **必须提交当前连续处理的最大batchId**
    - 示例：已处理batch1~3，需ack(3)而非ack(2)

3. **Rollback**
    - 回滚到指定batchId，下次get从此位置重新拉取
    - **慎用**：可能导致重复消费

### 实现特点

**多游标并行模型**：

```plaintext
       getCursor=105 (线程1)
         │
         ▼
... [103][104][105][106]...  (内存队列)
         ▲
         │
      ackCursor=102 (已确认)
```

### 常见问题及解决方案

1. **乱序ACK导致数据阻塞**  
   *现象*：`ack(5)`后提交`ack(4)`，服务端拒绝  
   *方案*：客户端维护`ack`单调递增队列

2. **位点丢失风险**  
   *场景*：客户端重启后未持久化batchId  
   *方案*：将`batchId`与业务数据原子存储

3. **服务端超时回滚**  
   *机制*：Canal Server在`ackTimeout`（默认30s）后自动rollback  
   *对策*：增大超时时间或优化消费逻辑

4. **并行消费的坑**  
   *错误*：多线程同时get但交叉ack  
   *正确*：单线程get + 多线程处理 + 单线程按序ack

5. **网络中断恢复**  
   *策略*：记录最后成功ack的batchId，重启后从`get(batchId+1)`继续

### 性能调优建议

```yaml
# canal.properties配置
canal.instance.network.receiveBufferSize = 16384
canal.instance.network.sendBufferSize = 16384
canal.instance.get.timeout.millis = 1000 # 控制get阻塞时间
canal.instance.ack.timeout.millis = 60000 # 增大ACK超时
```

### 典型应用场景

1. **缓存双写**
   ```mermaid
   graph LR
   MySQL-->Canal-->Redis
   Canal-->Memcached
   ```
2. **ES索引同步**  
   `Canal -> Kafka -> Spark Streaming -> Elasticsearch`

---
