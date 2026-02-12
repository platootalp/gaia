/** genAI_master_start */
'use client'

import { useState } from 'react'

interface TechCategory {
  id: string
  name: string
  icon: string
  color: string
  problem: string
  description: string
  mainstream: {
    name: string
    description: string
    popularity: 'high' | 'medium' | 'rising'
  }[]
}

const techCategories: TechCategory[] = [
  {
    id: 'framework',
    name: '前端框架/库',
    icon: '⚛️',
    color: '#61DAFB',
    problem: '应用架构与开发效率',
    description: '解决组件化开发、DOM操作效率、应用状态管理、代码复用等核心问题,提供开发规范和最佳实践。',
    mainstream: [
      { name: 'React', description: 'Meta出品,组件化、虚拟DOM、生态丰富', popularity: 'high' },
      { name: 'Vue.js', description: '渐进式框架,易学易用,双向绑定', popularity: 'high' },
      { name: 'Next.js', description: 'React全栈框架,SSR/SSG支持', popularity: 'high' },
      { name: 'Nuxt.js', description: 'Vue全栈框架,服务端渲染', popularity: 'medium' },
      { name: 'Angular', description: 'Google出品,完整企业级方案', popularity: 'medium' },
      { name: 'Svelte', description: '编译时框架,无虚拟DOM', popularity: 'rising' },
    ]
  },
  {
    id: 'css',
    name: 'CSS框架/工具',
    icon: '🎨',
    color: '#38BDF8',
    problem: '样式管理与布局效率',
    description: '解决CSS模块化、响应式布局、主题切换、样式复用、开发效率等问题。',
    mainstream: [
      { name: 'Tailwind CSS', description: '原子化CSS,快速开发', popularity: 'high' },
      { name: 'SCSS/SASS', description: 'CSS预处理器,变量嵌套', popularity: 'high' },
      { name: 'CSS Modules', description: 'CSS模块化,作用域隔离', popularity: 'medium' },
      { name: 'styled-components', description: 'CSS-in-JS,组件级样式', popularity: 'medium' },
      { name: 'Bootstrap', description: '经典UI框架,快速原型', popularity: 'medium' },
      { name: 'UnoCSS', description: '即时原子化CSS引擎', popularity: 'rising' },
    ]
  },
  {
    id: 'build',
    name: '构建工具',
    icon: '📦',
    color: '#F7B93E',
    problem: '代码打包与性能优化',
    description: '解决模块打包、代码压缩、热更新、Tree-shaking、代码分割、开发体验等问题。',
    mainstream: [
      { name: 'Vite', description: '下一代前端构建工具,极速开发', popularity: 'high' },
      { name: 'Webpack', description: '功能强大的模块打包器', popularity: 'high' },
      { name: 'esbuild', description: '极速JavaScript打包器', popularity: 'high' },
      { name: 'Rollup', description: '库打包首选,Tree-shaking优秀', popularity: 'medium' },
      { name: 'Turbopack', description: 'Vercel出品,增量编译', popularity: 'rising' },
      { name: 'Rspack', description: 'Rust实现的高性能Webpack替代', popularity: 'rising' },
    ]
  },
  {
    id: 'state',
    name: '状态管理',
    icon: '🗄️',
    color: '#764ABC',
    problem: '应用状态共享与同步',
    description: '解决跨组件状态共享、异步状态管理、状态持久化、调试追踪等问题。',
    mainstream: [
      { name: 'Zustand', description: '轻量级,API简洁,无样板代码', popularity: 'high' },
      { name: 'Pinia', description: 'Vue官方推荐,TypeScript友好', popularity: 'high' },
      { name: 'Redux Toolkit', description: 'React生态标准,中间件丰富', popularity: 'high' },
      { name: 'Jotai', description: '原子化状态,细粒度更新', popularity: 'rising' },
      { name: 'MobX', description: '响应式状态管理,自动追踪', popularity: 'medium' },
      { name: 'TanStack Query', description: '服务端状态管理利器', popularity: 'high' },
    ]
  },
  {
    id: 'test',
    name: '测试工具',
    icon: '🧪',
    color: '#99425B',
    problem: '代码质量与可靠性',
    description: '解决单元测试、集成测试、端到端测试、测试覆盖率、自动化测试等问题。',
    mainstream: [
      { name: 'Vitest', description: 'Vite原生测试框架,极速运行', popularity: 'high' },
      { name: 'Jest', description: 'React生态主流测试框架', popularity: 'high' },
      { name: 'Playwright', description: '跨浏览器E2E测试,微软出品', popularity: 'high' },
      { name: 'Cypress', description: '开发体验优秀的E2E测试', popularity: 'medium' },
      { name: 'Testing Library', description: '以用户为中心的测试方法', popularity: 'high' },
      { name: 'Storybook', description: '组件开发与视觉测试', popularity: 'medium' },
    ]
  },
  {
    id: 'package',
    name: '包管理器',
    icon: '📥',
    color: '#CB3837',
    problem: '依赖管理与版本控制',
    description: '解决依赖安装、版本管理、依赖树优化、安全性检查、脚本执行等问题。',
    mainstream: [
      { name: 'pnpm', description: '磁盘高效,依赖严格,monorepo友好', popularity: 'high' },
      { name: 'bun', description: '全功能JS运行时,极速安装', popularity: 'rising' },
      { name: 'npm', description: 'Node.js默认包管理器', popularity: 'high' },
      { name: 'Yarn', description: '确定性安装,Plug\'n\'Play', popularity: 'medium' },
    ]
  },
  {
    id: 'typescript',
    name: 'TypeScript',
    icon: '🔷',
    color: '#3178C6',
    problem: '类型安全与开发体验',
    description: '解决JavaScript类型缺失、大型项目维护困难、IDE智能提示、代码重构安全等问题。',
    mainstream: [
      { name: 'TypeScript', description: 'JavaScript超集,静态类型检查', popularity: 'high' },
      { name: 'JSDoc', description: '通过注释添加类型提示', popularity: 'medium' },
      { name: 'Flow', description: 'Facebook的类型检查器', popularity: 'low' },
    ]
  },
  {
    id: 'http',
    name: 'HTTP客户端',
    icon: '🌐',
    color: '#5A29E4',
    problem: '网络请求与数据获取',
    description: '解决API调用、请求拦截、错误处理、缓存管理、取消请求、Mock数据等问题。',
    mainstream: [
      { name: 'Axios', description: '功能丰富,拦截器支持完善', popularity: 'high' },
      { name: 'Fetch API', description: '浏览器原生,现代标准', popularity: 'high' },
      { name: 'TanStack Query', description: '数据获取+缓存+同步', popularity: 'high' },
      { name: 'SWR', description: 'Vercel出品,数据请求Hooks', popularity: 'medium' },
      { name: 'Ky', description: '现代Fetch封装,体积小', popularity: 'rising' },
    ]
  },
  {
    id: 'ui',
    name: 'UI组件库',
    icon: '🧩',
    color: '#00D8FF',
    problem: '界面开发效率与一致性',
    description: '解决通用组件复用、设计规范落地、开发效率、可访问性(a11y)等问题。',
    mainstream: [
      { name: 'shadcn/ui', description: '可复制组件,Tailwind驱动', popularity: 'high' },
      { name: 'Ant Design', description: '企业级UI,组件丰富', popularity: 'high' },
      { name: 'Material-UI', description: 'Google Material Design实现', popularity: 'high' },
      { name: 'Chakra UI', description: '可访问性优先,主题强大', popularity: 'medium' },
      { name: 'Radix UI', description: '无样式原语,可访问性', popularity: 'rising' },
      { name: 'Headless UI', description: '无样式交互组件', popularity: 'medium' },
    ]
  },
  {
    id: 'meta',
    name: '元框架',
    icon: '🚀',
    color: '#FF6B35',
    problem: '全栈开发与部署',
    description: '解决服务端渲染(SSR)、静态生成(SSG)、路由管理、API路由、SEO优化等问题。',
    mainstream: [
      { name: 'Next.js', description: 'React全栈框架,Vercel出品', popularity: 'high' },
      { name: 'Nuxt.js', description: 'Vue全栈框架,SSR/SSG', popularity: 'high' },
      { name: 'Astro', description: '内容网站首选,零JS默认', popularity: 'rising' },
      { name: 'Remix', description: 'Web标准优先,渐进增强', popularity: 'medium' },
      { name: 'SvelteKit', description: 'Svelte官方全栈框架', popularity: 'rising' },
      { name: 'Fresh', description: 'Deno出品, islands架构', popularity: 'rising' },
    ]
  },
]

const PopularityBadge = ({ popularity }: { popularity: string }) => {
  const styles: Record<string, { bg: string; text: string; label: string }> = {
    high: { bg: '#10B98120', text: '#10B981', label: '主流' },
    medium: { bg: '#F59E0B20', text: '#F59E0B', label: '常用' },
    rising: { bg: '#8B5CF620', text: '#8B5CF6', label: '新星' },
  }
  const style = styles[popularity] || styles.medium

  return (
    <span style={{
      display: 'inline-flex',
      alignItems: 'center',
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
      onMouseEnter={(e) => {
        e.currentTarget.style.transform = 'translateY(-4px)'
        e.currentTarget.style.boxShadow = `0 20px 40px ${category.color}20`
        e.currentTarget.style.borderColor = `${category.color}60`
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.transform = 'translateY(0)'
        e.currentTarget.style.boxShadow = 'none'
        e.currentTarget.style.borderColor = `${category.color}30`
      }}
    >
      {/* Top accent bar */}
      <div style={{
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        height: '3px',
        background: `linear-gradient(90deg, ${category.color}, ${category.color}80)`,
      }} />

      {/* Header */}
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

      {/* Description */}
      <p style={{
        margin: '0 0 16px 0',
        fontSize: '14px',
        color: '#94a3b8',
        lineHeight: 1.6,
      }}>{category.description}</p>

      {/* Tech list */}
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
              transition: 'all 0.2s ease',
            }}
            onMouseEnter={(e) => {
              e.stopPropagation()
              e.currentTarget.style.background = `${category.color}15`
              e.currentTarget.style.borderColor = `${category.color}40`
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.background = '#ffffff08'
              e.currentTarget.style.borderColor = '#ffffff10'
            }}
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

      {/* Expand indicator */}
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

export default function TechStackFrontend() {
  return (
    <div style={{
      minHeight: '100vh',
      background: 'linear-gradient(180deg, #0f0f1a 0%, #1a1a2e 50%, #0f0f1a 100%)',
      padding: '40px 20px',
    }}>
      {/* Header */}
      <div style={{
        maxWidth: '1200px',
        margin: '0 auto 48px auto',
        textAlign: 'center',
      }}>
        <h1 style={{
          fontSize: '42px',
          fontWeight: 700,
          background: 'linear-gradient(135deg, #60a5fa, #a78bfa, #f472b6)',
          WebkitBackgroundClip: 'text',
          WebkitTextFillColor: 'transparent',
          backgroundClip: 'text',
          margin: '0 0 16px 0',
          letterSpacing: '-0.02em',
        }}>
          前端技术栈全景图
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
          了解前端各项技术的核心价值,掌握主流技术选型
        </p>
      </div>

      {/* Legend */}
      <div style={{
        maxWidth: '1200px',
        margin: '0 auto 32px auto',
        display: 'flex',
        justifyContent: 'center',
        gap: '24px',
        flexWrap: 'wrap',
      }}>
        <div style={{
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
            background: '#10B981',
          }} />
          <span style={{ fontSize: '13px', color: '#94a3b8' }}>主流 - 广泛采用</span>
        </div>
        <div style={{
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
            background: '#F59E0B',
          }} />
          <span style={{ fontSize: '13px', color: '#94a3b8' }}>常用 - 稳定使用</span>
        </div>
        <div style={{
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
            background: '#8B5CF6',
          }} />
          <span style={{ fontSize: '13px', color: '#94a3b8' }}>新星 - 快速崛起</span>
        </div>
      </div>

      {/* Tech Grid */}
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

      {/* Footer */}
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
          💡 点击卡片可展开查看更多技术 | 技术选型应结合项目需求、团队熟悉度、生态成熟度综合考量
        </p>
      </div>
    </div>
  )
}
/** genAI_master_end */
