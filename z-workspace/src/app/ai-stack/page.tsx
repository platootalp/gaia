/** genAI_master_start */
'use client'

import { useState, memo, useEffect, useRef } from 'react'

interface TechItem {
  name: string
  description: string
  popularity: 'high' | 'medium' | 'rising'
}

interface TechCategory {
  id: string
  name: string
  icon: string
  color: string
  problem: string
  description: string
  mainstream: TechItem[]
}

const techCategories: TechCategory[] = [
  {
    id: 'llm',
    name: '大语言模型',
    icon: '🧠',
    color: '#A855F7',
    problem: 'AI应用核心',
    description: '闭源与开源模型并存,提供强大的自然语言理解和生成能力。',
    mainstream: [
      { name: 'GPT-4o', description: 'OpenAI最新多模态模型', popularity: 'high' },
      { name: 'Claude 3.5', description: 'Anthropic高性能模型', popularity: 'high' },
      { name: 'Gemini', description: 'Google多模态大模型', popularity: 'high' },
      { name: 'Llama 3', description: 'Meta开源模型', popularity: 'high' },
      { name: 'Qwen', description: '阿里通义千问', popularity: 'rising' },
      { name: 'DeepSeek', description: '高性价比开源模型', popularity: 'rising' },
    ]
  },
  {
    id: 'framework',
    name: 'AI框架',
    icon: '🔮',
    color: '#9333EA',
    problem: 'AI应用开发',
    description: '提供RAG、Agent等AI应用开发能力,简化LLM集成。',
    mainstream: [
      { name: 'LangChain', description: 'LLM应用开发框架', popularity: 'high' },
      { name: 'LlamaIndex', description: 'RAG框架标准', popularity: 'high' },
      { name: 'LangGraph', description: 'Agent工作流编排', popularity: 'rising' },
      { name: 'CrewAI', description: '多Agent协作', popularity: 'rising' },
      { name: 'Dify', description: 'LLM应用开发平台', popularity: 'rising' },
      { name: 'AutoGen', description: '微软Agent框架', popularity: 'medium' },
    ]
  },
  {
    id: 'vector',
    name: '向量数据库',
    icon: '📊',
    color: '#7C3AED',
    problem: '向量检索',
    description: '专为AI应用设计的向量存储和相似度检索数据库。',
    mainstream: [
      { name: 'Milvus', description: '开源向量数据库', popularity: 'high' },
      { name: 'Qdrant', description: 'Rust实现,高性能', popularity: 'high' },
      { name: 'Pinecone', description: '托管向量数据库', popularity: 'high' },
      { name: 'Weaviate', description: 'GraphQL查询', popularity: 'medium' },
      { name: 'Chroma', description: '嵌入式向量库', popularity: 'medium' },
      { name: 'pgvector', description: 'PostgreSQL扩展', popularity: 'rising' },
    ]
  },
  {
    id: 'deployment',
    name: '模型部署',
    icon: '🚀',
    color: '#6D28D9',
    problem: '高效推理',
    description: '提供模型推理加速和部署能力,优化推理性能。',
    mainstream: [
      { name: 'vLLM', description: 'PagedAttention加速', popularity: 'high' },
      { name: 'TensorRT-LLM', description: 'NVIDIA推理引擎', popularity: 'high' },
      { name: 'Ollama', description: '本地模型运行', popularity: 'high' },
      { name: 'llama.cpp', description: '量化推理', popularity: 'high' },
      { name: 'Triton', description: 'NVIDIA推理服务器', popularity: 'medium' },
    ]
  },
  {
    id: 'multimodal',
    name: '多模态模型',
    icon: '🎨',
    color: '#A855F7',
    problem: '图文理解',
    description: '支持图像、文本等多模态输入,提供跨模态理解能力。',
    mainstream: [
      { name: 'GPT-4V', description: 'OpenAI视觉模型', popularity: 'high' },
      { name: 'Gemini', description: 'Google原生多模态', popularity: 'high' },
      { name: 'Claude Vision', description: 'Anthropic视觉模型', popularity: 'high' },
      { name: 'LLaVA', description: '开源视觉语言模型', popularity: 'medium' },
      { name: 'Qwen-VL', description: '通义千问视觉版', popularity: 'rising' },
    ]
  },
  {
    id: 'finetune',
    name: '模型微调',
    icon: '⚙️',
    color: '#9333EA',
    problem: '模型定制',
    description: '提供参数高效微调方法,快速定制领域模型。',
    mainstream: [
      { name: 'LoRA', description: '低秩适应微调', popularity: 'high' },
      { name: 'QLoRA', description: '量化LoRA', popularity: 'high' },
      { name: 'PEFT', description: 'HF参数高效微调', popularity: 'high' },
      { name: 'DPO', description: '直接偏好优化', popularity: 'rising' },
      { name: 'PPO', description: '近端策略优化', popularity: 'medium' },
    ]
  },
  {
    id: 'tools',
    name: 'AI开发工具',
    icon: '🛠️',
    color: '#7C3AED',
    problem: 'AI辅助开发',
    description: 'AI驱动的代码生成和开发辅助工具。',
    mainstream: [
      { name: 'Cursor', description: 'AI原生IDE', popularity: 'high' },
      { name: 'GitHub Copilot', description: 'GitHub AI助手', popularity: 'high' },
      { name: 'Claude Code', description: 'Anthropic代码助手', popularity: 'high' },
      { name: 'Windsurf', description: 'AI编程助手', popularity: 'rising' },
      { name: 'Continue', description: '开源AI助手', popularity: 'medium' },
    ]
  },
  {
    id: 'ml',
    name: '深度学习框架',
    icon: '🔬',
    color: '#6D28D9',
    problem: '模型训练',
    description: '提供神经网络构建、训练和部署能力。',
    mainstream: [
      { name: 'PyTorch', description: '研究与应用首选', popularity: 'high' },
      { name: 'TensorFlow', description: 'Google出品', popularity: 'high' },
      { name: 'JAX', description: 'Google高性能框架', popularity: 'medium' },
      { name: 'Hugging Face', description: '模型hub', popularity: 'high' },
      { name: 'PaddlePaddle', description: '百度深度学习平台', popularity: 'medium' },
    ]
  },
  {
    id: 'image-gen',
    name: '图像生成',
    icon: '🎨',
    color: '#C084FC',
    problem: '文生图',
    description: '文本到图像生成模型。',
    mainstream: [
      { name: 'Stable Diffusion', description: '开源文生图', popularity: 'high' },
      { name: 'DALL-E 3', description: 'OpenAI图像生成', popularity: 'high' },
      { name: 'Midjourney', description: '艺术图像生成', popularity: 'high' },
      { name: 'FLUX', description: '高质量图像', popularity: 'rising' },
      { name: 'SDXL', description: 'SD升级版', popularity: 'high' },
    ]
  },
  {
    id: 'video-gen',
    name: '视频生成',
    icon: '🎬',
    color: '#A855F7',
    problem: '文生视频',
    description: '文本到视频生成的新趋势。',
    mainstream: [
      { name: 'Sora', description: 'OpenAI视频生成', popularity: 'rising' },
      { name: 'Runway Gen-3', description: '专业视频生成', popularity: 'high' },
      { name: 'Pika', description: '快速视频生成', popularity: 'rising' },
      { name: 'Kling', description: '快手视频生成', popularity: 'medium' },
    ]
  },
  {
    id: 'speech',
    name: '语音识别',
    icon: '🎤',
    color: '#9333EA',
    problem: 'ASR',
    description: '语音转文字识别。',
    mainstream: [
      { name: 'Whisper', description: 'OpenAI语音识别', popularity: 'high' },
      { name: 'Google Speech', description: 'Google语音API', popularity: 'high' },
      { name: 'Azure Speech', description: 'Microsoft语音', popularity: 'medium' },
      { name: '阿里语音', description: '阿里云语音', popularity: 'medium' },
    ]
  },
  {
    id: 'tts',
    name: '语音合成',
    icon: '🔊',
    color: '#7C3AED',
    problem: 'TTS',
    description: '文字转语音合成。',
    mainstream: [
      { name: 'ElevenLabs', description: '高质量TTS', popularity: 'high' },
      { name: 'OpenAI TTS', description: 'OpenAI语音', popularity: 'high' },
      { name: 'Azure TTS', description: 'Microsoft TTS', popularity: 'medium' },
      { name: 'Coqui TTS', description: '开源TTS', popularity: 'medium' },
    ]
  },
  {
    id: 'code-model',
    name: '代码模型',
    icon: '💻',
    color: '#6D28D9',
    problem: '代码生成',
    description: '专门的代码生成模型。',
    mainstream: [
      { name: 'GitHub Copilot', description: 'GitHub代码助手', popularity: 'high' },
      { name: 'CodeLlama', description: 'Meta代码模型', popularity: 'high' },
      { name: 'DeepSeek Coder', description: '高性能代码模型', popularity: 'rising' },
      { name: 'StarCoder', description: 'BigCode项目', popularity: 'medium' },
    ]
  },
  {
    id: 'sdk',
    name: 'LLM SDK',
    icon: '🔧',
    color: '#A855F7',
    problem: 'API调用',
    description: '官方SDK,直接调用更简洁。',
    mainstream: [
      { name: 'OpenAI SDK', description: 'OpenAI官方', popularity: 'high' },
      { name: 'Anthropic SDK', description: 'Claude官方', popularity: 'high' },
      { name: 'Google AI SDK', description: 'Gemini官方', popularity: 'high' },
      { name: 'LangChain', description: 'LLM应用框架', popularity: 'high' },
    ]
  },
  {
    id: 'rag',
    name: 'RAG框架',
    icon: '📚',
    color: '#9333EA',
    problem: '检索增强',
    description: '检索增强生成框架。',
    mainstream: [
      { name: 'LlamaIndex', description: 'RAG标准框架', popularity: 'high' },
      { name: 'LangChain', description: 'LLM应用链', popularity: 'high' },
      { name: 'Haystack', description: 'NLP pipeline', popularity: 'medium' },
      { name: 'DSPy', description: '编程式Prompt', popularity: 'rising' },
      { name: 'RAGFlow', description: '企业RAG平台', popularity: 'medium' },
    ]
  },
  {
    id: 'agent',
    name: 'Agent框架',
    icon: '🤖',
    color: '#7C3AED',
    problem: '智能体协作',
    description: '多智能体协作和工作流编排。',
    mainstream: [
      { name: 'LangGraph', description: 'Agent工作流', popularity: 'rising' },
      { name: 'CrewAI', description: '多Agent协作', popularity: 'rising' },
      { name: 'AutoGen', description: 'Microsoft Agent', popularity: 'high' },
      { name: 'Dify', description: 'LLM应用平台', popularity: 'rising' },
      { name: 'Semantic Kernel', description: 'Microsoft AI框架', popularity: 'medium' },
    ]
  },
  {
    id: 'prompt',
    name: 'Prompt管理',
    icon: '✍️',
    color: '#6D28D9',
    problem: 'Prompt版本',
    description: 'Prompt工程和版本管理。',
    mainstream: [
      { name: 'LangSmith', description: 'LangChain监控', popularity: 'high' },
      { name: 'Promptflow', description: 'Microsoft Prompt', popularity: 'medium' },
      { name: 'Weights & Biases', description: '实验追踪', popularity: 'high' },
      { name: 'Helicone', description: 'LLM可观测', popularity: 'medium' },
    ]
  },
  {
    id: 'structured',
    name: '结构化输出',
    icon: '📋',
    color: '#A855F7',
    problem: '结构化响应',
    description: 'LLM输出结构化数据。',
    mainstream: [
      { name: 'Instructor', description: 'Pydantic输出', popularity: 'high' },
      { name: 'Marvin', description: 'AI工程工具', popularity: 'medium' },
      { name: 'Pydantic AI', description: 'Pydantic官方', popularity: 'rising' },
      { name: 'Outlines', description: '结构化生成', popularity: 'medium' },
    ]
  },
  {
    id: 'function-calling',
    name: 'Function Calling',
    icon: '🔌',
    color: '#9333EA',
    problem: '工具调用',
    description: 'LLM调用外部工具能力。',
    mainstream: [
      { name: 'OpenAI Functions', description: 'OpenAI工具调用', popularity: 'high' },
      { name: 'Tool Use', description: 'Claude工具使用', popularity: 'high' },
      { name: 'MCP', description: 'Model Context Protocol', popularity: 'rising' },
    ]
  },
  {
    id: 'memory',
    name: '记忆管理',
    icon: '🧠',
    color: '#7C3AED',
    problem: '长期记忆',
    description: 'LLM长期记忆和上下文管理。',
    mainstream: [
      { name: 'Mem0', description: '记忆管理标准', popularity: 'rising' },
      { name: 'Letta', description: '长期记忆', popularity: 'medium' },
      { name: 'MemGPT', description: '虚拟上下文', popularity: 'medium' },
      { name: 'LangMem', description: 'LangChain记忆', popularity: 'medium' },
    ]
  },
  {
    id: 'router',
    name: '模型路由',
    icon: '🔀',
    color: '#6D28D9',
    problem: '统一调用',
    description: '统一多模型调用网关。',
    mainstream: [
      { name: 'LiteLLM', description: '统一模型接口', popularity: 'high' },
      { name: 'OpenRouter', description: '模型路由平台', popularity: 'rising' },
      { name: 'Together AI', description: '开源模型API', popularity: 'medium' },
      { name: 'OneAPI', description: 'OpenAI代理', popularity: 'medium' },
    ]
  },
  {
    id: 'optimization',
    name: '推理优化',
    icon: '⚡',
    color: '#A855F7',
    problem: '推理加速',
    description: '推理速度和效率优化技术。',
    mainstream: [
      { name: 'Flash Attention', description: '注意力加速', popularity: 'high' },
      { name: 'PagedAttention', description: 'vLLM核心技术', popularity: 'high' },
      { name: 'KV Cache优化', description: '缓存优化', popularity: 'high' },
      { name: 'Continuous Batching', description: '连续批处理', popularity: 'medium' },
    ]
  },
  {
    id: 'quantization',
    name: '模型量化',
    icon: '📉',
    color: '#9333EA',
    problem: '模型压缩',
    description: '模型压缩和加速技术。',
    mainstream: [
      { name: 'GPTQ', description: '后训练量化', popularity: 'high' },
      { name: 'AWQ', description: '激活感知量化', popularity: 'high' },
      { name: 'GGUF', description: 'llama.cpp格式', popularity: 'high' },
      { name: 'bitsandbytes', description: '8bit量化', popularity: 'medium' },
    ]
  },
  {
    id: 'serving',
    name: '模型服务',
    icon: '🌐',
    color: '#7C3AED',
    problem: '模型服务化',
    description: '模型服务化和API部署。',
    mainstream: [
      { name: 'Ray Serve', description: '分布式服务', popularity: 'high' },
      { name: 'BentoML', description: '模型服务框架', popularity: 'high' },
      { name: 'Modal', description: '云端部署', popularity: 'rising' },
      { name: 'RunPod', description: 'GPU云服务', popularity: 'medium' },
    ]
  },
  {
    id: 'embedding',
    name: 'Embedding模型',
    icon: '🔢',
    color: '#6D28D9',
    problem: '文本向量化',
    description: '文本向量化模型。',
    mainstream: [
      { name: 'text-embedding-3', description: 'OpenAI嵌入', popularity: 'high' },
      { name: 'BGE', description: '中文嵌入模型', popularity: 'high' },
      { name: 'Cohere', description: 'Cohere嵌入', popularity: 'medium' },
      { name: 'Voyage', description: 'Voyage AI', popularity: 'medium' },
    ]
  },
  {
    id: 'rerank',
    name: '重排序',
    icon: '🔄',
    color: '#A855F7',
    problem: '检索优化',
    description: '搜索结果重排序。',
    mainstream: [
      { name: 'Cohere Rerank', description: 'Cohere重排序', popularity: 'high' },
      { name: 'BGE Reranker', description: 'BGE重排序', popularity: 'high' },
      { name: 'Voyage Rerank', description: 'Voyage重排序', popularity: 'medium' },
      { name: 'ColBERT', description: '学术重排序', popularity: 'medium' },
    ]
  },
  {
    id: 'training',
    name: '训练框架',
    icon: '🏋️',
    color: '#9333EA',
    problem: '分布式训练',
    description: '大规模分布式训练框架。',
    mainstream: [
      { name: 'DeepSpeed', description: 'Microsoft训练框架', popularity: 'high' },
      { name: 'PyTorch FSDP', description: '全分片训练', popularity: 'high' },
      { name: 'Megatron-LM', description: 'NVIDIA训练', popularity: 'medium' },
      { name: 'Axolotl', description: '微调工具', popularity: 'rising' },
    ]
  },
  {
    id: 'data-prep',
    name: '数据处理',
    icon: '📊',
    color: '#7C3AED',
    problem: '训练数据',
    description: '训练数据准备和标注。',
    mainstream: [
      { name: 'HF Datasets', description: 'Hugging Face数据集', popularity: 'high' },
      { name: 'Label Studio', description: '数据标注', popularity: 'high' },
      { name: 'Doccano', description: '文本标注', popularity: 'medium' },
      { name: 'spaCy', description: 'NLP工具', popularity: 'high' },
    ]
  },
  {
    id: 'rlhf',
    name: '强化学习',
    icon: '🎯',
    color: '#6D28D9',
    problem: 'RLHF',
    description: '人类反馈强化学习。',
    mainstream: [
      { name: 'DPO', description: '直接偏好优化', popularity: 'rising' },
      { name: 'PPO', description: '近端策略优化', popularity: 'high' },
      { name: 'RLHF', description: '人类反馈', popularity: 'high' },
      { name: 'KTO', description: 'Kahneman-Tversky优化', popularity: 'medium' },
    ]
  },
  {
    id: 'eval',
    name: '模型评估',
    icon: '📊',
    color: '#A855F7',
    problem: '能力评测',
    description: '模型能力评测和基准测试。',
    mainstream: [
      { name: 'lm-eval-harness', description: 'EleutherAI评测', popularity: 'high' },
      { name: 'OpenAI Evals', description: 'OpenAI评估', popularity: 'high' },
      { name: 'Ragas', description: 'RAG评估', popularity: 'rising' },
      { name: 'DeepEval', description: 'LLM评估', popularity: 'medium' },
    ]
  },
  {
    id: 'benchmark',
    name: '基准测试',
    icon: '📈',
    color: '#9333EA',
    problem: '标准测试',
    description: '标准化能力测试基准。',
    mainstream: [
      { name: 'MMLU', description: '多任务理解', popularity: 'high' },
      { name: 'HumanEval', description: '代码评估', popularity: 'high' },
      { name: 'GSM8K', description: '数学推理', popularity: 'high' },
      { name: 'MT-Bench', description: '对话评估', popularity: 'high' },
    ]
  },
  {
    id: 'safety',
    name: '安全评估',
    icon: '🛡️',
    color: '#7C3AED',
    problem: '安全合规',
    description: '安全性和合规性评估。',
    mainstream: [
      { name: 'LLM Guard', description: '安全防护', popularity: 'high' },
      { name: 'Garak', description: '安全测试', popularity: 'medium' },
      { name: 'Red Team Testing', description: '红队测试', popularity: 'medium' },
      { name: 'Constitutional AI', description: 'Anthropic安全', popularity: 'medium' },
    ]
  },
  {
    id: 'monitoring',
    name: '质量监控',
    icon: '📡',
    color: '#6D28D9',
    problem: '应用监控',
    description: 'LLM应用质量监控。',
    mainstream: [
      { name: 'LangSmith', description: 'LangChain监控', popularity: 'high' },
      { name: 'Arize', description: 'AI可观测平台', popularity: 'high' },
      { name: 'Phoenix', description: 'Arize开源', popularity: 'medium' },
      { name: 'Helicone', description: 'LLM监控', popularity: 'medium' },
    ]
  },
  {
    id: 'platform',
    name: '开发平台',
    icon: '🏗️',
    color: '#A855F7',
    problem: '快速原型',
    description: '模型托管和快速原型开发。',
    mainstream: [
      { name: 'Hugging Face', description: '模型hub', popularity: 'high' },
      { name: 'Gradio', description: '快速UI', popularity: 'high' },
      { name: 'Streamlit', description: '数据应用', popularity: 'high' },
      { name: 'Chainlit', description: 'LLM UI', popularity: 'rising' },
    ]
  },
  {
    id: 'notebook',
    name: 'Notebook',
    icon: '📓',
    color: '#9333EA',
    problem: '交互开发',
    description: '交互式开发环境。',
    mainstream: [
      { name: 'Jupyter', description: '标准Notebook', popularity: 'high' },
      { name: 'Google Colab', description: '免费GPU', popularity: 'high' },
      { name: 'Kaggle', description: '数据竞赛平台', popularity: 'high' },
      { name: 'Marimo', description: '现代Notebook', popularity: 'rising' },
    ]
  },
  {
    id: 'experiment',
    name: '实验管理',
    icon: '🔬',
    color: '#7C3AED',
    problem: '实验追踪',
    description: '实验追踪和版本管理。',
    mainstream: [
      { name: 'Weights & Biases', description: '实验追踪', popularity: 'high' },
      { name: 'MLflow', description: 'ML生命周期', popularity: 'high' },
      { name: 'Neptune', description: '元数据存储', popularity: 'medium' },
      { name: 'ClearML', description: 'ML运维', popularity: 'medium' },
    ]
  },
  {
    id: 'automl',
    name: 'AutoML',
    icon: '🤖',
    color: '#6D28D9',
    problem: '自动机器学习',
    description: '自动化机器学习流程。',
    mainstream: [
      { name: 'Optuna', description: '超参数优化', popularity: 'high' },
      { name: 'Ray Tune', description: '分布式调优', popularity: 'high' },
      { name: 'AutoGluon', description: 'AWS AutoML', popularity: 'medium' },
      { name: 'H2O.ai', description: 'AutoML平台', popularity: 'medium' },
    ]
  },
  {
    id: 'labeling',
    name: '数据标注',
    icon: '🏷️',
    color: '#A855F7',
    problem: '数据标注',
    description: '数据标注平台和工具。',
    mainstream: [
      { name: 'Label Studio', description: '多模态标注', popularity: 'high' },
      { name: 'Doccano', description: '文本标注', popularity: 'medium' },
      { name: 'Prodigy', description: 'spaCy标注', popularity: 'medium' },
      { name: 'CVAT', description: '视觉标注', popularity: 'medium' },
    ]
  },
  {
    id: 'conversion',
    name: '模型转换',
    icon: '🔄',
    color: '#9333EA',
    problem: '格式转换',
    description: '模型格式转换工具。',
    mainstream: [
      { name: 'ONNX', description: '开放神经网络', popularity: 'high' },
      { name: 'TensorRT', description: 'NVIDIA推理', popularity: 'high' },
      { name: 'OpenVINO', description: 'Intel推理', popularity: 'medium' },
      { name: 'CoreML', description: 'Apple模型', popularity: 'medium' },
    ]
  },
  {
    id: 'edge',
    name: '边缘部署',
    icon: '📱',
    color: '#7C3AED',
    problem: '端侧推理',
    description: '端侧模型部署方案。',
    mainstream: [
      { name: 'llama.cpp', description: 'CPU推理', popularity: 'high' },
      { name: 'MLC LLM', description: '移动端LLM', popularity: 'rising' },
      { name: 'TensorFlow Lite', description: '移动端ML', popularity: 'high' },
      { name: 'Core ML', description: 'Apple ML', popularity: 'medium' },
    ]
  },
]

const PopularityBadge = memo(({ popularity }: { popularity: 'high' | 'medium' | 'rising' }) => {
  const styles = {
    high: { bg: '#10B98115', text: '#10B981', label: '主流' },
    medium: { bg: '#F59E0B15', text: '#F59E0B', label: '常用' },
    rising: { bg: '#8B5CF615', text: '#8B5CF6', label: '新星' },
  }
  const style = styles[popularity]

  return (
    <span style={{
      padding: '2px 8px',
      borderRadius: '12px',
      fontSize: '11px',
      fontWeight: 500,
      backgroundColor: style.bg,
      color: style.text,
    }}>
      {style.label}
    </span>
  )
})

PopularityBadge.displayName = 'PopularityBadge'

const TechCard = memo(({ category }: { category: TechCategory }) => {
  const [isExpanded, setIsExpanded] = useState(false)

  return (
    <div
      onClick={() => setIsExpanded(!isExpanded)}
      style={{
        background: 'linear-gradient(135deg, #1a1a2e 0%, #16213e 100%)',
        borderRadius: '16px',
        padding: '24px',
        border: `1px solid ${category.color}30`,
        cursor: 'pointer',
        transition: 'all 0.3s ease',
        position: 'relative',
        overflow: 'hidden',
      }}
    >
      <div style={{
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        height: '3px',
        background: `linear-gradient(90deg, ${category.color}, ${category.color}80)`,
      }} />

      <div style={{
        display: 'flex',
        alignItems: 'center',
        gap: '12px',
        marginBottom: '16px',
      }}>
        <span style={{ fontSize: '32px' }}>{category.icon}</span>
        <div>
          <h3 style={{
            margin: 0,
            fontSize: '20px',
            fontWeight: 600,
            color: '#fff',
          }}>{category.name}</h3>
          <span style={{
            fontSize: '13px',
            color: category.color,
            fontWeight: 500,
          }}>{category.problem}</span>
        </div>
      </div>

      <p style={{
        margin: '0 0 16px 0',
        fontSize: '14px',
        color: '#94a3b8',
        lineHeight: 1.6,
      }}>{category.description}</p>

      <div style={{
        display: 'flex',
        flexWrap: 'wrap',
        gap: '8px',
      }}>
        {category.mainstream.slice(0, isExpanded ? undefined : 3).map((tech) => (
          <div
            key={tech.name}
            style={{
              display: 'flex',
              alignItems: 'center',
              gap: '6px',
              padding: '6px 12px',
              background: '#ffffff08',
              borderRadius: '8px',
              border: '1px solid #ffffff10',
            }}
            onClick={(e) => e.stopPropagation()}
          >
            <span style={{
              fontSize: '13px',
              fontWeight: 500,
              color: '#fff',
            }}>{tech.name}</span>
            <PopularityBadge popularity={tech.popularity} />
          </div>
        ))}
      </div>

      {category.mainstream.length > 3 && (
        <div style={{
          marginTop: '12px',
          fontSize: '12px',
          color: '#64748b',
          display: 'flex',
          alignItems: 'center',
          gap: '4px',
        }}>
          <span>{isExpanded ? '收起' : `展开更多 (${category.mainstream.length - 3}项)`}</span>
          <span style={{
            transform: isExpanded ? 'rotate(180deg)' : 'rotate(0)',
            transition: 'transform 0.2s ease',
          }}>▼</span>
        </div>
      )}
    </div>
  )
})

TechCard.displayName = 'TechCard'

export default function AIStack() {
  const [displayCount, setDisplayCount] = useState(12)
  const loadMoreRef = useRef<HTMLDivElement>(null)
  const displayCategories = techCategories.slice(0, displayCount)

  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        const first = entries[0]
        if (first.isIntersecting && displayCount < techCategories.length) {
          setDisplayCount(prev => Math.min(prev + 8, techCategories.length))
        }
      },
      { threshold: 0.1 }
    )

    const currentRef = loadMoreRef.current
    if (currentRef) {
      observer.observe(currentRef)
    }

    return () => {
      if (currentRef) {
        observer.unobserve(currentRef)
      }
    }
  }, [displayCount])

  return (
    <div style={{
      minHeight: '100vh',
      background: 'linear-gradient(180deg, #0f0f1a 0%, #1a0f2e 50%, #0f0f1a 100%)',
      padding: '40px 20px',
    }}>
      <div style={{
        maxWidth: '1200px',
        margin: '0 auto 48px auto',
        textAlign: 'center',
      }}>
        <h1 style={{
          fontSize: '42px',
          fontWeight: 700,
          background: 'linear-gradient(135deg, #C084FC, #A855F7, #9333EA)',
          WebkitBackgroundClip: 'text',
          WebkitTextFillColor: 'transparent',
          backgroundClip: 'text',
          margin: '0 0 16px 0',
          letterSpacing: '-0.02em',
        }}>
          AI 开发技术栈
        </h1>
        <p style={{
          fontSize: '18px',
          color: '#94a3b8',
          margin: 0,
          maxWidth: '600px',
          marginLeft: 'auto',
          marginRight: 'auto',
          lineHeight: 1.6,
        }}>
          大语言模型、多模态、RAG、Agent - AI开发全生态
        </p>
      </div>

      <div style={{
        maxWidth: '1200px',
        margin: '0 auto 32px auto',
        display: 'flex',
        justifyContent: 'center',
        gap: '24px',
        flexWrap: 'wrap',
      }}>
        {[
          { color: '#10B981', label: '主流 - 广泛采用' },
          { color: '#F59E0B', label: '常用 - 稳定使用' },
          { color: '#8B5CF6', label: '新星 - 快速崛起' },
        ].map((item, i) => (
          <div key={i} style={{
            display: 'flex',
            alignItems: 'center',
            gap: '8px',
            padding: '8px 16px',
            background: '#ffffff08',
            borderRadius: '24px',
            border: '1px solid #ffffff10',
          }}>
            <span style={{
              width: '8px',
              height: '8px',
              borderRadius: '50%',
              background: item.color,
            }} />
            <span style={{ fontSize: '13px', color: '#94a3b8' }}>{item.label}</span>
          </div>
        ))}
      </div>

      <div style={{
        maxWidth: '1200px',
        margin: '0 auto',
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(360px, 1fr))',
        gap: '24px',
      }}>
        {displayCategories.map((category) => (
          <TechCard key={category.id} category={category} />
        ))}
      </div>

      {displayCount < techCategories.length && (
        <div
          ref={loadMoreRef}
          style={{
            maxWidth: '1200px',
            margin: '32px auto',
            textAlign: 'center',
            padding: '24px',
          }}
        >
          <div style={{
            display: 'inline-flex',
            alignItems: 'center',
            gap: '12px',
            padding: '12px 24px',
            background: 'linear-gradient(135deg, #A855F715, #9333EA15)',
            border: '1px solid #A855F750',
            borderRadius: '12px',
          }}>
            <div style={{
              width: '20px',
              height: '20px',
              border: '3px solid #A855F730',
              borderTop: '3px solid #A855F7',
              borderRadius: '50%',
              animation: 'spin 1s linear infinite',
            }} />
            <span style={{
              fontSize: '14px',
              color: '#A855F7',
              fontWeight: 500,
            }}>
              加载更多分类... ({displayCount}/{techCategories.length})
            </span>
          </div>
        </div>
      )}

      <style jsx>{`
        @keyframes spin {
          0% { transform: rotate(0deg); }
          100% { transform: rotate(360deg); }
        }
      `}</style>

      <div style={{
        maxWidth: '1200px',
        margin: '48px auto 0 auto',
        textAlign: 'center',
        padding: '24px',
        background: '#ffffff05',
        borderRadius: '16px',
        border: '1px solid #ffffff10',
      }}>
        <p style={{
          margin: 0,
          fontSize: '14px',
          color: '#64748b',
        }}>
          💡 点击卡片可展开查看更多技术 | AI技术发展迅速,建议关注最新开源模型和工具
        </p>
      </div>
    </div>
  )
}
/** genAI_master_end */
