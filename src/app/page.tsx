import Link from 'next/link'
import { Code2, Server, Cpu, Database, Bot, Network, ArrowRight } from 'lucide-react'

// 静态数据定义
const techModules = [
  {
    title: '前端技术栈',
    href: '/frontend',
    icon: Code2,
    description: '深入前端领域,涵盖框架、构建工具、状态管理、测试、UI组件库等前端技术',
    gradient: 'from-pink-500/10 to-purple-500/10',
    iconColor: 'text-pink-400',
    tags: ['React', 'Vue', '构建工具', '状态管理']
  },
  {
    title: 'Node.js / TypeScript',
    href: '/backend/nodejs',
    icon: Server,
    description: '企业级与轻量级并存,类型安全的后端开发生态',
    gradient: 'from-green-500/10 to-emerald-500/10',
    iconColor: 'text-green-400',
    tags: ['NestJS', 'Express', 'Prisma', 'Fastify']
  },
  {
    title: 'Python 技术栈',
    href: '/backend/python',
    icon: Cpu,
    description: '异步高性能API开发,数据处理与AI的首选语言',
    gradient: 'from-yellow-500/10 to-amber-500/10',
    iconColor: 'text-yellow-400',
    tags: ['FastAPI', 'Django', 'SQLAlchemy', 'Pandas']
  },
  {
    title: 'Go 技术栈',
    href: '/backend/go',
    icon: Database,
    description: '高性能微服务框架,原生并发支持',
    gradient: 'from-cyan-500/10 to-teal-500/10',
    iconColor: 'text-cyan-400',
    tags: ['Gin', 'GORM', 'gRPC', 'Goroutine']
  },
  {
    title: 'Java 技术栈',
    href: '/backend/java',
    icon: Server,
    description: '企业级应用首选,成熟稳定的后端生态',
    gradient: 'from-orange-500/10 to-red-500/10',
    iconColor: 'text-orange-400',
    tags: ['Spring Boot', 'MyBatis', 'Kafka', 'Redis']
  },
  {
    title: 'Rust 技术栈',
    href: '/backend/rust',
    icon: Cpu,
    description: '内存安全,零成本抽象,极致性能',
    gradient: 'from-red-500/10 to-rose-500/10',
    iconColor: 'text-red-400',
    tags: ['Axum', 'SeaORM', 'Tokio', 'Serde']
  },
  {
    title: 'AI 开发技术栈',
    href: '/ai-stack',
    icon: Bot,
    description: '大语言模型、多模态、RAG、Agent - AI开发全生态',
    gradient: 'from-purple-500/10 to-pink-500/10',
    iconColor: 'text-purple-400',
    tags: ['GPT-4', 'Claude', 'LangChain', 'vLLM']
  },
  {
    title: '基础设施',
    href: '/infrastructure',
    icon: Network,
    description: '云原生、DevOps、微服务 - 现代应用基础设施全景',
    gradient: 'from-blue-500/10 to-indigo-500/10',
    iconColor: 'text-blue-400',
    tags: ['Kubernetes', 'PostgreSQL', 'Redis', 'Kafka']
  },
]

// 服务端组件 - 无需 'use client'
export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900">
      <div className="container mx-auto px-4 py-16">
        {/* Header */}
        <div className="text-center mb-16 pt-8">
          <h1 className="text-5xl md:text-6xl font-bold mb-6">
            <span className="bg-gradient-to-r from-blue-400 via-purple-400 to-pink-400 bg-clip-text text-transparent">
              技术栈全景图
            </span>
          </h1>
          <p className="text-xl text-gray-300 max-w-2xl mx-auto">
            探索2025年主流技术栈,掌握前沿开发技术,助力职业发展
          </p>
        </div>

        {/* Cards Grid */}
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl mx-auto">
          {techModules.map((module) => (
            <Link key={module.href} href={module.href}>
              <div className="group relative bg-gray-800/50 backdrop-blur-sm border border-gray-700 rounded-2xl p-6 hover:border-purple-500 transition-all duration-300 cursor-pointer hover:shadow-2xl hover:shadow-purple-500/20 hover:-translate-y-2 h-full">
                <div className={`absolute inset-0 bg-gradient-to-br ${module.gradient} rounded-2xl opacity-0 group-hover:opacity-100 transition-opacity`} />

                <div className="relative">
                  <div className="flex items-center justify-between mb-4">
                    <module.icon className={`w-10 h-10 ${module.iconColor}`} />
                    <ArrowRight className="w-5 h-5 text-gray-400 group-hover:text-purple-400 transition-colors" />
                  </div>

                  <h2 className="text-xl font-bold text-white mb-3">
                    {module.title}
                  </h2>

                  <p className="text-gray-400 mb-4 text-sm leading-relaxed">
                    {module.description}
                  </p>

                  <div className="flex flex-wrap gap-2">
                    {module.tags.map((tag, i) => (
                      <span key={i} className="px-2.5 py-1 bg-gray-700/50 text-gray-300 rounded-full text-xs">
                        {tag}
                      </span>
                    ))}
                  </div>
                </div>
              </div>
            </Link>
          ))}
        </div>

        {/* Features */}
        <div className="mt-16 grid md:grid-cols-4 gap-6 max-w-6xl mx-auto">
          <div className="text-center p-6 bg-gray-800/30 rounded-xl border border-gray-700">
            <div className="text-3xl font-bold text-purple-400 mb-2">8</div>
            <div className="text-gray-400">技术领域</div>
          </div>
          <div className="text-center p-6 bg-gray-800/30 rounded-xl border border-gray-700">
            <div className="text-3xl font-bold text-blue-400 mb-2">100+</div>
            <div className="text-gray-400">技术分类</div>
          </div>
          <div className="text-center p-6 bg-gray-800/30 rounded-xl border border-gray-700">
            <div className="text-3xl font-bold text-pink-400 mb-2">500+</div>
            <div className="text-gray-400">主流工具</div>
          </div>
          <div className="text-center p-6 bg-gray-800/30 rounded-xl border border-gray-700">
            <div className="text-3xl font-bold text-green-400 mb-2">2025</div>
            <div className="text-gray-400">最新趋势</div>
          </div>
        </div>

        {/* Footer */}
        <div className="mt-16 text-center text-gray-500 text-sm">
          <p>💡 选择合适的技术栈，需综合考虑项目需求、团队经验和生态成熟度</p>
          <p className="mt-2">Built with ❤️ by TechStack Panorama | © 2025</p>
        </div>
      </div>
    </div>
  )
}
