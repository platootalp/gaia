/** genAI_master_start */
'use client'

import { useState } from 'react'

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
    id: 'database',
    name: '数据库',
    icon: '🗄️',
    color: '#3B82F6',
    problem: '数据存储',
    description: '关系型和NoSQL数据库,提供高可用、高性能的数据存储方案。',
    mainstream: [
      { name: 'PostgreSQL', description: '功能强大的关系型数据库', popularity: 'high' },
      { name: 'MySQL', description: '最流行的开源数据库', popularity: 'high' },
      { name: 'MongoDB', description: '文档型NoSQL数据库', popularity: 'high' },
      { name: 'Redis', description: '高性能内存数据库', popularity: 'high' },
      { name: 'CockroachDB', description: '分布式SQL数据库', popularity: 'rising' },
      { name: 'PlanetScale', description: 'MySQL托管服务', popularity: 'rising' },
    ]
  },
  {
    id: 'queue',
    name: '消息队列',
    icon: '📨',
    color: '#2563EB',
    problem: '异步通信',
    description: '提供可靠的消息传递和事件驱动架构支持。',
    mainstream: [
      { name: 'Kafka', description: '分布式流处理平台', popularity: 'high' },
      { name: 'RabbitMQ', description: '功能丰富的消息代理', popularity: 'high' },
      { name: 'NATS', description: '云原生消息系统', popularity: 'rising' },
      { name: 'Redis Streams', description: 'Redis消息流', popularity: 'medium' },
      { name: 'Pulsar', description: '云原生消息队列', popularity: 'medium' },
    ]
  },
  {
    id: 'search',
    name: '搜索引擎',
    icon: '🔍',
    color: '#1D4ED8',
    problem: '全文检索',
    description: '提供强大的全文搜索和数据分析能力。',
    mainstream: [
      { name: 'Elasticsearch', description: '分布式搜索引擎', popularity: 'high' },
      { name: 'Meilisearch', description: '开箱即用的搜索引擎', popularity: 'rising' },
      { name: 'Typesense', description: '快速即时搜索', popularity: 'rising' },
      { name: 'Algolia', description: '托管搜索服务', popularity: 'medium' },
      { name: 'OpenSearch', description: 'ES开源分支', popularity: 'medium' },
    ]
  },
  {
    id: 'container',
    name: '容器编排',
    icon: '🐳',
    color: '#1E40AF',
    problem: '云原生部署',
    description: '容器化应用的编排、调度和管理平台。',
    mainstream: [
      { name: 'Kubernetes', description: '容器编排标准', popularity: 'high' },
      { name: 'Docker', description: '容器引擎', popularity: 'high' },
      { name: 'Docker Compose', description: '多容器编排', popularity: 'high' },
      { name: 'Podman', description: '无守护进程容器', popularity: 'medium' },
      { name: 'Nomad', description: 'HashiCorp编排工具', popularity: 'medium' },
    ]
  },
  {
    id: 'serverless',
    name: 'Serverless',
    icon: '⚡',
    color: '#3B82F6',
    problem: '边缘计算',
    description: '无服务器架构,按需计费,快速部署。',
    mainstream: [
      { name: 'Vercel', description: '前端部署平台', popularity: 'high' },
      { name: 'Cloudflare Workers', description: '边缘计算平台', popularity: 'high' },
      { name: 'AWS Lambda', description: 'AWS函数计算', popularity: 'high' },
      { name: 'Fly.io', description: '全球应用部署', popularity: 'rising' },
      { name: 'Deno Deploy', description: 'Deno边缘计算', popularity: 'medium' },
    ]
  },
  {
    id: 'monitoring',
    name: '可观测性',
    icon: '📊',
    color: '#2563EB',
    problem: '监控告警',
    description: '提供指标监控、日志收集和分布式追踪能力。',
    mainstream: [
      { name: 'Prometheus', description: '时序数据库和监控', popularity: 'high' },
      { name: 'Grafana', description: '可视化平台', popularity: 'high' },
      { name: 'Jaeger', description: '分布式追踪', popularity: 'medium' },
      { name: 'OpenTelemetry', description: '可观测性标准', popularity: 'rising' },
      { name: 'Datadog', description: '全栈监控平台', popularity: 'high' },
    ]
  },
  {
    id: 'cicd',
    name: 'CI/CD',
    icon: '🔄',
    color: '#1D4ED8',
    problem: '自动化部署',
    description: '持续集成和持续部署,自动化软件交付流程。',
    mainstream: [
      { name: 'GitHub Actions', description: 'GitHub原生CI/CD', popularity: 'high' },
      { name: 'GitLab CI', description: 'GitLab集成CI/CD', popularity: 'high' },
      { name: 'Jenkins', description: '经典CI/CD工具', popularity: 'high' },
      { name: 'ArgoCD', description: 'GitOps持续部署', popularity: 'rising' },
      { name: 'CircleCI', description: '云原生CI/CD', popularity: 'medium' },
    ]
  },
  {
    id: 'iac',
    name: '基础设施即代码',
    icon: '⚙️',
    color: '#1E40AF',
    problem: 'IaC自动化',
    description: '通过代码管理和配置基础设施资源。',
    mainstream: [
      { name: 'Terraform', description: 'HashiCorp IaC工具', popularity: 'high' },
      { name: 'Pulumi', description: '现代IaC平台', popularity: 'rising' },
      { name: 'AWS CDK', description: 'AWS云开发工具包', popularity: 'medium' },
      { name: 'Ansible', description: '自动化配置管理', popularity: 'high' },
      { name: 'Crossplane', description: 'K8s原生IaC', popularity: 'rising' },
    ]
  },
  {
    id: 'cdn',
    name: 'CDN & 边缘',
    icon: '🌐',
    color: '#3B82F6',
    problem: '内容分发',
    description: '全球内容分发网络,加速静态资源访问。',
    mainstream: [
      { name: 'Cloudflare', description: '全球CDN领导者', popularity: 'high' },
      { name: 'AWS CloudFront', description: 'AWS CDN服务', popularity: 'high' },
      { name: 'Vercel Edge', description: 'Vercel边缘网络', popularity: 'rising' },
      { name: 'Fastly', description: '实时CDN平台', popularity: 'medium' },
      { name: 'BunnyCDN', description: '高性价比CDN', popularity: 'medium' },
    ]
  },
  {
    id: 'nosql',
    name: 'NoSQL数据库',
    icon: '📄',
    color: '#1D4ED8',
    problem: '文档存储',
    description: '提供灵活的文档型和列式数据库。',
    mainstream: [
      { name: 'MongoDB', description: '文档型数据库', popularity: 'high' },
      { name: 'DynamoDB', description: 'AWS NoSQL', popularity: 'high' },
      { name: 'Cassandra', description: '分布式列式', popularity: 'medium' },
      { name: 'ScyllaDB', description: 'Cassandra兼容', popularity: 'medium' },
    ]
  },
  {
    id: 'timeseries',
    name: '时序数据库',
    icon: '📈',
    color: '#2563EB',
    problem: '监控指标',
    description: '专为时序数据优化的数据库。',
    mainstream: [
      { name: 'InfluxDB', description: '时序数据库', popularity: 'high' },
      { name: 'TimescaleDB', description: 'PostgreSQL扩展', popularity: 'high' },
      { name: 'Prometheus', description: '监控时序库', popularity: 'high' },
      { name: 'QuestDB', description: '高性能时序', popularity: 'rising' },
    ]
  },
  {
    id: 'storage',
    name: '对象存储',
    icon: '💾',
    color: '#1E40AF',
    problem: '文件存储',
    description: '提供海量文件对象存储能力。',
    mainstream: [
      { name: 'AWS S3', description: '对象存储标准', popularity: 'high' },
      { name: 'MinIO', description: '开源S3兼容', popularity: 'high' },
      { name: 'Cloudflare R2', description: '零出口费用', popularity: 'rising' },
      { name: 'Google Cloud Storage', description: 'GCP对象存储', popularity: 'medium' },
    ]
  },
  {
    id: 'gateway',
    name: 'API网关',
    icon: '🚪',
    color: '#3B82F6',
    problem: 'API管理',
    description: '提供API路由、认证和限流能力。',
    mainstream: [
      { name: 'Kong', description: '微服务网关', popularity: 'high' },
      { name: 'Traefik', description: '云原生网关', popularity: 'high' },
      { name: 'Nginx', description: '反向代理', popularity: 'high' },
      { name: 'Envoy', description: 'Service Mesh代理', popularity: 'medium' },
      { name: 'APISIX', description: 'Apache网关', popularity: 'medium' },
    ]
  },
  {
    id: 'loadbalancer',
    name: '负载均衡',
    icon: '⚖️',
    color: '#2563EB',
    problem: '流量分发',
    description: '提供流量分发和负载均衡能力。',
    mainstream: [
      { name: 'Nginx', description: '高性能负载均衡', popularity: 'high' },
      { name: 'HAProxy', description: 'TCP/HTTP负载均衡', popularity: 'high' },
      { name: 'Traefik', description: '云原生负载均衡', popularity: 'medium' },
      { name: 'Envoy', description: 'L7代理', popularity: 'medium' },
    ]
  },
  {
    id: 'logging',
    name: '日志管理',
    icon: '📋',
    color: '#1D4ED8',
    problem: '日志收集',
    description: '提供日志收集、存储和分析能力。',
    mainstream: [
      { name: 'ELK Stack', description: 'Elasticsearch+Logstash+Kibana', popularity: 'high' },
      { name: 'Loki', description: 'Grafana日志', popularity: 'rising' },
      { name: 'Datadog Logs', description: '托管日志服务', popularity: 'medium' },
      { name: 'Fluentd', description: '日志收集器', popularity: 'medium' },
    ]
  },
  {
    id: 'api',
    name: 'API协议',
    icon: '🔌',
    color: '#1E40AF',
    problem: 'API设计',
    description: '不同的API通信协议和风格。',
    mainstream: [
      { name: 'REST', description: 'RESTful API', popularity: 'high' },
      { name: 'GraphQL', description: '查询语言', popularity: 'high' },
      { name: 'tRPC', description: '端到端类型安全', popularity: 'rising' },
      { name: 'gRPC', description: 'RPC框架', popularity: 'high' },
      { name: 'WebSocket', description: '双向通信', popularity: 'high' },
    ]
  },
  {
    id: 'mesh',
    name: '服务网格',
    icon: '🕸️',
    color: '#3B82F6',
    problem: '微服务治理',
    description: '提供服务间通信、可观测性和安全。',
    mainstream: [
      { name: 'Istio', description: '功能完整的网格', popularity: 'high' },
      { name: 'Linkerd', description: '轻量级网格', popularity: 'medium' },
      { name: 'Consul Connect', description: 'HashiCorp网格', popularity: 'medium' },
      { name: 'Kuma', description: 'Kong网格', popularity: 'medium' },
    ]
  },
  {
    id: 'config',
    name: '配置中心',
    icon: '⚙️',
    color: '#2563EB',
    problem: '配置管理',
    description: '提供分布式配置管理能力。',
    mainstream: [
      { name: 'Consul', description: 'HashiCorp配置', popularity: 'high' },
      { name: 'Etcd', description: '分布式配置', popularity: 'high' },
      { name: 'Nacos', description: '阿里配置中心', popularity: 'medium' },
      { name: 'Apollo', description: '携程配置中心', popularity: 'medium' },
    ]
  },
  {
    id: 'secrets',
    name: '密钥管理',
    icon: '🔑',
    color: '#1D4ED8',
    problem: '密钥安全',
    description: '提供密钥和证书管理能力。',
    mainstream: [
      { name: 'HashiCorp Vault', description: '密钥管理标准', popularity: 'high' },
      { name: 'AWS Secrets Manager', description: 'AWS密钥管理', popularity: 'high' },
      { name: 'Azure Key Vault', description: 'Azure密钥', popularity: 'medium' },
      { name: 'Infisical', description: '开源密钥管理', popularity: 'rising' },
    ]
  },
]

const PopularityBadge = ({ popularity }: { popularity: 'high' | 'medium' | 'rising' }) => {
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
}

const TechCard = ({ category }: { category: TechCategory }) => {
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
}

export default function InfrastructureStack() {
  return (
    <div style={{
      minHeight: '100vh',
      background: 'linear-gradient(180deg, #0f0f1a 0%, #0f1a2e 50%, #0f0f1a 100%)',
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
          background: 'linear-gradient(135deg, #60A5FA, #3B82F6, #2563EB)',
          WebkitBackgroundClip: 'text',
          WebkitTextFillColor: 'transparent',
          backgroundClip: 'text',
          margin: '0 0 16px 0',
          letterSpacing: '-0.02em',
        }}>
          基础设施技术栈
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
          云原生、DevOps、微服务 - 现代应用基础设施全景
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
        {techCategories.map((category) => (
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
          💡 点击卡片可展开查看更多技术 | 基础设施选型直接影响系统的可靠性、可扩展性和运维成本
        </p>
      </div>
    </div>
  )
}
/** genAI_master_end */
