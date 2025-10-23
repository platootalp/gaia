# 大模型应用开发学习路线（180天）

以下是一个为期 **180天（约6个月）的大模型应用开发学习路线**，聚焦于**工程落地、应用集成与系统优化**，适合具备 Java
开发背景、关注系统架构与高可用性的工程师（如你当前的技术栈和关注点）。路线兼顾理论深度与实践强度，强调*
*可部署、可观测、可扩展的大模型应用系统构建能力**。

---

### 🎯 学习目标

- 掌握大模型基础原理与主流架构（Transformer、LLM、Embedding 等）
- 熟练使用开源大模型（如 Llama 3、Qwen、ChatGLM）进行本地部署与推理
- 构建端到端的大模型应用（RAG、Agent、微调、API 封装）
- 设计高可用、可观测、可扩展的大模型服务架构（含异步任务、限流、熔断、多云容灾）
- 能独立完成从原型到生产级部署的全流程

---

## 📅 阶段划分（共6个月）

---

### **第1~2个月：基础夯实 + 环境搭建**

**目标**：理解大模型基本原理，掌握本地部署与推理能力

| 周 | 主题                 | 关键任务                                                                                                              |
|---|--------------------|-------------------------------------------------------------------------------------------------------------------|
| 1 | 大模型基础概念            | - 学习 Transformer 架构<br>- 理解 Tokenization、Attention、Positional Encoding<br>- 了解 LLM 训练流程（预训练、SFT、RLHF）             |
| 2 | 开源模型生态             | - 对比 Llama 3 / Qwen / ChatGLM / Mistral<br>- 了解 Hugging Face、ModelScope 生态<br>- 安装 CUDA / PyTorch / vLLM / Ollama |
| 3 | 本地推理实践             | - 使用 transformers 库加载模型<br>- 用 vLLM 或 llama.cpp 实现高效推理<br>- 测试不同量化方式（4-bit/8-bit）对性能影响                            |
| 4 | Prompt Engineering | - 学习 CoT、Few-shot、Role Prompting<br>- 构建结构化输出（JSON Schema）<br>- 评估 prompt 效果（人工+自动）                               |

> ✅ 产出：本地可运行的 LLM 推理服务（支持 REST/gRPC）

---

### **第3~4个月：应用开发核心能力**

**目标**：构建典型大模型应用，掌握 RAG、Agent、微调等关键技术

| 周 | 主题                       | 关键任务                                                                                                           |
|---|--------------------------|----------------------------------------------------------------------------------------------------------------|
| 5 | 向量数据库与 Embedding         | - 学习 text-embedding 模型（bge、text2vec）<br>- 部署 Milvus / Qdrant / PGVector<br>- 实现文档切片 + 向量化 + 检索                 |
| 6 | RAG 系统构建                 | - 构建完整 RAG pipeline（文档加载 → 切片 → 向量化 → 检索 → 生成）<br>- 评估召回率与生成质量<br>- 加入 rerank 模块（如 bge-reranker）               |
| 7 | Function Calling & Agent | - 实现工具调用（如天气、计算、数据库查询）<br>- 构建 ReAct / Plan-and-Execute Agent<br>- 使用 LangChain / LlamaIndex / Semantic Kernel |
| 8 | 微调入门（LoRA / QLoRA）       | - 使用 Unsloth / PEFT 微调小模型<br>- 构建指令微调数据集<br>- 评估微调前后效果差异                                                       |

> ✅ 产出：一个支持 RAG + 工具调用的对话系统（Web/API）

---

### **第5~6个月：工程化与生产部署**

**目标**：将大模型应用工程化，满足高并发、可观测、多云容灾等要求

| 周  | 主题           | 关键任务                                                                                                                   |
|----|--------------|------------------------------------------------------------------------------------------------------------------------|
| 9  | 服务封装与 API 设计 | - 用 FastAPI / Spring Boot 封装 LLM 服务<br>- 统一错误码、请求/响应结构<br>- 支持流式输出（SSE / WebSocket）                                    |
| 10 | 异步任务与队列      | - 引入 Celery / RabbitMQ / Kafka 处理长文本/批量任务<br>- 实现任务状态机（pending → running → success/fail）<br>- 支持状态查询与结果回调              |
| 11 | 性能优化与限流      | - 压测服务（Locust / JMeter）<br>- 实现令牌桶/漏桶限流<br>- 集成熔断（Sentinel / Resilience4j）<br>- 控制 GPU/CPU 资源使用                        |
| 12 | 多云部署与可观测性    | - 容器化（Docker + Kubernetes）<br>- 多云部署策略（华为云/腾讯云）<br>- 日志（ELK）、指标（Prometheus）、链路追踪（Jaeger）<br>- 实现配置中心动态生效（Nacos/Apollo） |

> ✅ 产出：可部署、可观测、支持高并发的大模型应用系统（含监控面板）

---

## 🛠️ 技术栈建议（贴合你的背景）

- **语言**：Python（主力）、Java（服务封装/异步任务）
- **模型**：Llama 3（7B/8B）、Qwen2、ChatGLM3（开源可商用）
- **推理引擎**：vLLM（高性能）、llama.cpp（CPU友好）
- **向量库**：Qdrant（轻量）、Milvus（企业级）
- **框架**：LangChain（快速原型）、自研（生产级）
- **部署**：Docker + Kubernetes + Helm
- **监控**：Prometheus + Grafana + Loki + Tempo

---

## 📌 关键原则（贴合你的工程关注点）

- **不盲目追求 SOTA 模型**，优先选择**可部署、可量化、可商用**的开源方案
- **异步任务模型**需具备：状态机、幂等性、可观测性、失败重试
- **接口设计**兼顾灵活性（支持多种输入）与统一性（标准错误码、结构）
- **多云链路**需验证：模型输出一致性、带宽控制、容灾切换逻辑
- **日志控制**：关闭冗余 SDK 日志（如华为云），统一日志格式

---

## 📚 推荐资源

- 书籍：《Hands-On Large Language Models》《AI Engineering》
- 课程：Hugging Face LLM Course、DeepLearning.AI LLM Specialization
- 项目：LangChain Docs、LlamaIndex Tutorials、OpenDevin（Agent 参考）
- 社区：Hugging Face、ModelScope、LMSYS、知乎/掘金工程实践文章

