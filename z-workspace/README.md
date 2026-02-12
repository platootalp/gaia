# 🚀 技术栈全景图 - Z.ai Code Scaffold

一个集成了全栈技术栈和前端技术栈展示的现代化Web应用,采用侧边栏导航设计,方便快速切换不同视图。

## ✨ 项目特色

### 🎯 双视图展示
- **全栈技术栈全景图**: 涵盖前端、后端(Node.js/Python/Go/Java/Rust)、AI开发、基础设施等完整技术生态
- **前端技术栈全景图**: 深入前端领域,展示框架、构建工具、状态管理等前端核心技术

### 🎨 现代化设计
- 侧边栏导航,快速切换不同页面
- 响应式设计,适配各种设备
- 渐变色主题,视觉效果出众
- 流畅的交互动画

## 📁 项目结构

```
z-workspace/
├── src/
│   ├── app/
│   │   ├── layout.tsx              # 主布局(含侧边栏)
│   │   ├── page.tsx                # 首页
│   │   ├── tech-stack-all/        # 全栈技术栈页面
│   │   │   └── page.tsx
│   │   └── tech-stack-frontend/    # 前端技术栈页面
│   │       └── page.tsx
│   ├── components/
│   │   ├── ui/                     # shadcn/ui 组件库
│   │   └── sidebar.tsx             # 侧边栏组件
│   ├── hooks/                      # 自定义 React Hooks
│   └── lib/                        # 工具函数和配置
├── public/                         # 静态资源
└── prisma/                         # 数据库配置

```

## 🛠️ 技术栈

### 核心框架
- **⚡ Next.js 16** - React全栈框架
- **📘 TypeScript 5** - 类型安全
- **🎨 Tailwind CSS 4** - 原子化CSS

### UI组件
- **🧩 shadcn/ui** - 高质量组件库
- **🎯 Lucide React** - 图标库
- **🌈 Framer Motion** - 动画库

### 数据管理
- **🐻 Zustand** - 状态管理
- **🔄 TanStack Query** - 数据同步
- **🗄️ Prisma** - ORM

## 🚀 快速开始

### 安装依赖

```bash
bun install
```

### 启动开发服务器

```bash
bun run dev
```

访问 [http://localhost:3000](http://localhost:3000) 查看应用。

### 构建生产版本

```bash
bun run build
```

### 启动生产服务器

```bash
bun start
```

## 📄 页面说明

### 首页 (/)
- 展示两个主要模块的入口
- 卡片式导航设计
- 统计数据概览

### 全栈技术栈全景图 (/tech-stack-all)
- 20+ 前端技术领域
- 5 种后端语言生态(Node.js/Python/Go/Java/Rust)
- 40+ AI 技术领域
- 20+ 基础设施组件

### 前端技术栈全景图 (/tech-stack-frontend)
- 10 个主要前端技术分类
- 交互式卡片展示
- 可展开查看详细技术列表
- 主流/常用/新星标签分类

## 🎨 特色功能

1. **侧边栏导航**
   - 固定在左侧,方便切换
   - 高亮当前页面
   - 图标+文字双重提示

2. **响应式设计**
   - 移动端适配
   - 平板端优化
   - 桌面端完整展示

3. **渐变色主题**
   - 蓝紫粉渐变配色
   - 深色背景设计
   - 视觉层次分明

4. **交互体验**
   - Hover 效果
   - 平滑过渡动画
   - 卡片展开/收起

## 🔧 开发脚本

```bash
# 开发
bun run dev

# 构建
bun run build

# 启动
bun start

# Lint
bun run lint

# 数据库操作
bun run db:push      # 推送数据库结构
bun run db:generate  # 生成 Prisma Client
bun run db:migrate   # 运行迁移
bun run db:reset     # 重置数据库
```

## 🤖 AI 驱动开发

本项目使用 [Z.ai](https://chat.z.ai) 进行开发,享受AI辅助编程的强大能力:
- 💻 智能代码生成
- 🎨 UI快速开发
- 🔧 自动化错误修复
- 📝 文档自动生成

## 📝 许可证

MIT License

---

Built with ❤️ by Z.ai | © 2025
