'use client'

import { useState, memo } from 'react'

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

// LLM算法 - 底层技术（模型、训练、优化）
const llmAlgorithmCategories: TechCategory[] = [
  {
    id: 'llm',
    name: '大语言模型',
    icon: '🧠',
    color: '#A855F7',
    problem: 'AI核心',
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
    id: 'multimodal',
    name: '多模态模型',
    icon: '🎨',
    color: '#C084FC',
    problem: '跨模态理解',
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
    id: 'embedding',
    name: 'Embedding模型',
    icon: '🔢',
    color: '#06B6D4',
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
    id: 'ml',
    name: '深度学习框架',
    icon: '🔬',
    color: '#14B8A6',
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
    id: 'finetune',
    name: '模型微调',
    icon: '⚙️',
    color: '#10B981',
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
    id: 'training',
    name: '训练框架',
    icon: '🏋️',
    color: '#84CC16',
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
    id: 'optimization',
    name: '推理优化',
    icon: '⚡',
    color: '#EAB308',
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
    color: '#F97316',
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
    id: 'rlhf',
    name: '强化学习',
    icon: '🎯',
    color: '#EC4899',
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
    color: '#8B5CF6',
    problem: '能力评测',
    description: '模型能力评测和基准测试。',
    mainstream: [
      { name: 'lm-eval-harness', description: 'EleutherAI评测', popularity: 'high' },
      { name: 'OpenAI Evals', description: 'OpenAI评估', popularity: 'high' },
      { name: 'Ragas', description: 'RAG评估', popularity: 'rising' },
      { name: 'DeepEval', description: 'LLM评估', popularity: 'medium' },
    ]
  },
]

// LLM应用 - 上层应用（框架、工具、平台）
const llmApplicationCategories: TechCategory[] = [
  {
    id: 'framework',
    name: 'AI应用框架',
    icon: '🔮',
    color: '#A855F7',
    problem: '应用开发',
    description: '提供RAG、Agent等AI应用开发能力,简化LLM集成。',
    mainstream: [
      { name: 'LangChain', description: 'LLM应用开发框架', popularity: 'high' },
      { name: 'LlamaIndex', description: 'RAG框架标准', popularity: 'high' },
      { name: 'LangGraph', description: 'Agent工作流编排', popularity: 'rising' },
      { name: 'CrewAI', description: '多Agent协作', popularity: 'rising' },
      { name: 'Dify', description: 'LLM应用开发平台', popularity: 'rising' },
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
    ]
  },
  {
    id: 'vector',
    name: '向量数据库',
    icon: '📊',
    color: '#6366F1',
    problem: '向量检索',
    description: '专为AI应用设计的向量存储和相似度检索数据库。',
    mainstream: [
      { name: 'Milvus', description: '开源向量数据库', popularity: 'high' },
      { name: 'Qdrant', description: 'Rust实现高性能', popularity: 'high' },
      { name: 'Pinecone', description: '托管向量数据库', popularity: 'high' },
      { name: 'Chroma', description: '嵌入式向量库', popularity: 'medium' },
      { name: 'pgvector', description: 'PostgreSQL扩展', popularity: 'rising' },
    ]
  },
  {
    id: 'sdk',
    name: 'LLM SDK',
    icon: '🔧',
    color: '#3B82F6',
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
    id: 'memory',
    name: '记忆管理',
    icon: '🧠',
    color: '#06B6D4',
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
    id: 'structured',
    name: '结构化输出',
    icon: '📋',
    color: '#14B8A6',
    problem: '结构化响应',
    description: 'LLM输出结构化数据。',
    mainstream: [
      { name: 'Instructor', description: 'Pydantic输出', popularity: 'high' },
      { name: 'Pydantic AI', description: 'Pydantic官方', popularity: 'rising' },
      { name: 'Outlines', description: '结构化生成', popularity: 'medium' },
    ]
  },
  {
    id: 'tools',
    name: 'AI开发工具',
    icon: '🛠️',
    color: '#10B981',
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
    id: 'platform',
    name: '开发平台',
    icon: '🏗️',
    color: '#84CC16',
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
    id: 'monitoring',
    name: '质量监控',
    icon: '📡',
    color: '#EAB308',
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
    id: 'router',
    name: '模型路由',
    icon: '🔀',
    color: '#F97316',
    problem: '统一调用',
    description: '统一多模型调用网关。',
    mainstream: [
      { name: 'LiteLLM', description: '统一模型接口', popularity: 'high' },
      { name: 'OpenRouter', description: '模型路由平台', popularity: 'rising' },
      { name: 'Together AI', description: '开源模型API', popularity: 'medium' },
      { name: 'OneAPI', description: 'OpenAI代理', popularity: 'medium' },
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
  const [activeTab, setActiveTab] = useState<'algorithm' | 'application'>('algorithm')
  const categories = activeTab === 'algorithm' ? llmAlgorithmCategories : llmApplicationCategories

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

      {/* Tab Switcher */}
      <div style={{
        maxWidth: '600px',
        margin: '0 auto 40px auto',
        display: 'flex',
        gap: '12px',
        padding: '6px',
        background: '#ffffff08',
        borderRadius: '16px',
        border: '1px solid #ffffff10',
      }}>
        <button
          onClick={() => setActiveTab('algorithm')}
          style={{
            flex: 1,
            padding: '14px 24px',
            borderRadius: '12px',
            border: 'none',
            cursor: 'pointer',
            fontSize: '15px',
            fontWeight: 600,
            transition: 'all 0.3s ease',
            background: activeTab === 'algorithm' 
              ? 'linear-gradient(135deg, #A855F7, #9333EA)' 
              : 'transparent',
            color: activeTab === 'algorithm' ? '#fff' : '#94a3b8',
          }}
        >
          <span style={{ fontSize: '18px', marginRight: '8px' }}>🔬</span>
          LLM算法
          <span style={{
            display: 'block',
            fontSize: '11px',
            fontWeight: 400,
            opacity: 0.8,
            marginTop: '2px',
          }}>
            模型、训练、优化
          </span>
        </button>
        <button
          onClick={() => setActiveTab('application')}
          style={{
            flex: 1,
            padding: '14px 24px',
            borderRadius: '12px',
            border: 'none',
            cursor: 'pointer',
            fontSize: '15px',
            fontWeight: 600,
            transition: 'all 0.3s ease',
            background: activeTab === 'application' 
              ? 'linear-gradient(135deg, #10B981, #059669)' 
              : 'transparent',
            color: activeTab === 'application' ? '#fff' : '#94a3b8',
          }}
        >
          <span style={{ fontSize: '18px', marginRight: '8px' }}>🚀</span>
          LLM应用
          <span style={{
            display: 'block',
            fontSize: '11px',
            fontWeight: 400,
            opacity: 0.8,
            marginTop: '2px',
          }}>
            框架、工具、平台
          </span>
        </button>
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
        {categories.map((category) => (
          <TechCard key={category.id} category={category} />
        ))}
      </div>

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
