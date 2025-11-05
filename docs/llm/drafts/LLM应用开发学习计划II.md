# 🧭 LLM 应用工程师学习路线图（180天）

> 🎯 目标：掌握从模型权重加载到应用集成的完整工程化路径，能独立构建和部署企业级 LLM 应用。

---

## 📆 阶段总览

| 阶段          | 时间        | 目标             | 核心技术                             |
|-------------|-----------|----------------|----------------------------------|
| ① 模型基础与推理原理 | 第1-30天    | 理解模型结构与推理机制    | Python, PyTorch, Transformers    |
| ② 推理服务部署    | 第31-60天   | 能加载并运行本地LLM模型  | vLLM, TGI, FastAPI               |
| ③ RAG知识增强   | 第61-90天   | 让模型具备企业知识与检索能力 | LangChain, Milvus, Embedding     |
| ④ 模型性能与成本优化 | 第91-120天  | 实现高效、低成本推理     | TensorRT, GPTQ, Batch/Cache优化    |
| ⑤ 智能体与工作流编排 | 第121-150天 | 构建多智能体与任务流     | LangGraph, Agent框架               |
| ⑥ 项目化与生产部署  | 第151-180天 | 构建完整可用系统并上线    | Docker, K8s, Prometheus, Grafana |

---

## 🧩 阶段一：模型基础与推理原理（Day 1-30）

### 🎯 目标

* 理解 Transformer 结构与大模型推理机制
* 能用 Python / PyTorch / Transformers 加载并生成文本

### 📚 学习内容

1. **Transformer 架构原理**

    * Self-Attention / Multi-Head / Feed Forward
    * Position Encoding / LayerNorm / Residual
    * 📘 推荐阅读：[The Illustrated Transformer](https://jalammar.github.io/illustrated-transformer/)
2. **Hugging Face Transformers 实战**

    * 模型加载与推理（`AutoModelForCausalLM`）
    * Tokenizer 原理与文本生成
    * 📗 教程：[Transformers 官方入门](https://huggingface.co/docs/transformers/index)
3. **PyTorch 实践**

    * Tensor 基础、自动求导、显存管理
    * 小模型（GPT2）推理实验

### 🧠 实践任务

✅ 使用 Transformers 加载 `gpt2`，输入一句话并生成续写结果。
✅ 尝试保存与加载模型权重。

---

## ⚙️ 阶段二：推理服务部署（Day 31-60）

### 🎯 目标

* 能将模型封装为 API
* 熟悉高性能推理引擎与部署架构

### 📚 学习内容

1. **推理框架**

    * vLLM / Text Generation Inference (TGI)
    * LMDeploy / FastChat / Ollama
    * 📘 官方资源：

        * [vLLM 文档](https://docs.vllm.ai/)
        * [TGI GitHub](https://github.com/huggingface/text-generation-inference)
2. **API 封装与服务化**

    * 使用 FastAPI 封装模型接口
    * OpenAI-Compatible API 格式标准
3. **部署与容器化**

    * Dockerfile 构建
    * GPU 调度与资源配置（CUDA / nvidia-smi）

### 🧠 实践任务

✅ 使用 vLLM 部署一个本地模型（如 Mistral-7B）。
✅ 封装成 FastAPI 服务并调用。

---

## 🔍 阶段三：RAG 知识增强（Day 61-90）

### 🎯 目标

* 让模型能使用企业内部知识
* 掌握检索增强生成（RAG）流程

### 📚 学习内容

1. **文本向量化与检索**

    * Embedding 模型（`text-embedding-3-large`, `bge-base`）
    * 向量数据库：Milvus / FAISS / Chroma
    * 📘 [Milvus 官方教程](https://milvus.io/docs)
2. **LangChain 入门**

    * DocumentLoader / VectorStore / Retriever / QAChain
    * PromptTemplate / Memory / Tools
3. **LangGraph 基础**

    * 状态化工作流
    * 多步任务编排

### 🧠 实践任务

✅ 构建一个企业文档问答系统（RAG Pipeline）。
✅ 使用 Milvus + LangChain 进行知识检索增强。

---

## ⚡ 阶段四：模型性能与成本优化（Day 91-120）

### 🎯 目标

* 提升模型推理速度与并发性能
* 优化 GPU 利用率与推理成本

### 📚 学习内容

1. **推理性能分析**

    * Batch、KV Cache、Prefill/Decode 区分
    * Streaming Token 输出
2. **模型压缩与量化**

    * LoRA、QLoRA、GPTQ、AWQ
    * TensorRT / vLLM KV Cache 重用
    * 📘 [LLM 推理优化指南](https://huggingface.co/docs/transformers/perf_infer)
3. **并发与负载均衡**

    * 多实例部署与路由
    * Redis 缓存、任务队列

### 🧠 实践任务

✅ 对比模型在 FP16 / INT8 模式下的延迟与显存占用。
✅ 优化模型吞吐率，达到 QPS > 10。

---

## 🤖 阶段五：智能体与工作流编排（Day 121-150）

### 🎯 目标

* 让模型具备“思考 + 行动”能力
* 能调用外部工具并进行多步推理

### 📚 学习内容

1. **LangGraph 与 Agent 架构**

    * State / Node / Edge / Memory 模型
    * 多 Agent 协作（Planner、Executor）
    * 📗 [LangGraph 官方文档](https://langchain-ai.github.io/langgraph/)
2. **工具调用与插件系统**

    * Function Calling
    * 外部 API 工具集成（搜索、数据库、HTTP）
3. **任务流设计**

    * 工作流控制：条件、循环、分支
    * 任务状态可视化与监控

### 🧠 实践任务

✅ 构建一个“文件分析智能体”：自动读取文档 → 总结 → 生成报告。
✅ 构建一个多 Agent 工作流，实现任务分工与协作。

---

## 🚀 阶段六：项目化与生产部署（Day 151-180）

### 🎯 目标

* 将 LLM 应用产品化、上线、可监控
* 实现全链路可观测与成本控制

### 📚 学习内容

1. **部署与运维**

    * Docker Compose / K8s / Helm
    * 模型滚动更新与灰度发布
2. **监控与可观测性**

    * Prometheus + Grafana 指标监控
    * OpenTelemetry 追踪
3. **日志与安全**

    * 接口限流、鉴权、审计日志
    * 敏感词检测与安全防护
4. **API 商业化封装**

    * OpenAI 兼容接口层
    * Token计费与调用分析

### 🧠 实践任务

✅ 将你的智能体应用部署到服务器上。
✅ 接入监控系统，统计响应延迟与调用量。

---

## 🧠 推荐资源总表

| 分类   | 资源                        | 链接                                                                                          |
|------|---------------------------|---------------------------------------------------------------------------------------------|
| 官方课程 | Hugging Face Transformers | [https://huggingface.co/course](https://huggingface.co/course)                              |
| 框架   | LangChain Docs            | [https://python.langchain.com](https://python.langchain.com)                                |
| 编排   | LangGraph Docs            | [https://langchain-ai.github.io/langgraph/](https://langchain-ai.github.io/langgraph/)      |
| 向量库  | Milvus Docs               | [https://milvus.io/docs](https://milvus.io/docs)                                            |
| 推理引擎 | vLLM Docs                 | [https://docs.vllm.ai](https://docs.vllm.ai)                                                |
| 部署   | Docker Docs               | [https://docs.docker.com](https://docs.docker.com)                                          |
| 可观测性 | Prometheus + Grafana      | [https://prometheus.io](https://prometheus.io) / [https://grafana.com](https://grafana.com) |

---

## 🧩 最终成果

在 180 天结束时，你将能够：
✅ 独立部署一个大模型推理服务；
✅ 构建基于 RAG 的知识问答系统；
✅ 设计多智能体工作流应用；
✅ 完成生产级部署与监控；
✅ 具备企业级 LLM 应用开发的全栈能力。


