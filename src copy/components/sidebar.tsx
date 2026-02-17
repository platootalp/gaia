'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { cn } from '@/lib/utils'
import { Home, Code2, Server, Bot, Network } from 'lucide-react'

const routes = [
  {
    label: '首页',
    icon: Home,
    href: '/',
    color: 'text-sky-500',
  },
  {
    label: '前端技术栈',
    icon: Code2,
    href: '/frontend',
    color: 'text-pink-500',
  },
  {
    label: '后端技术栈',
    icon: Server,
    href: '/backend',
    color: 'text-green-500',
  },
  {
    label: 'AI开发',
    icon: Bot,
    href: '/ai-stack',
    color: 'text-purple-500',
  },
  {
    label: '基础设施',
    icon: Network,
    href: '/infrastructure',
    color: 'text-blue-500',
  },
]

export function Sidebar() {
  const pathname = usePathname()

  return (
    <div className="space-y-4 py-4 flex flex-col h-screen bg-gray-900 text-white w-64 fixed left-0 top-0">
      <div className="px-3 py-2 flex-1 overflow-y-auto">
        <Link href="/" className="flex items-center pl-3 mb-14">
          <h1 className="text-2xl font-bold bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 bg-clip-text text-transparent">
            技术栈全景图
          </h1>
        </Link>
        <div className="space-y-1">
          {routes.map((route) => (
            <Link
              key={route.href}
              href={route.href}
              className={cn(
                'text-sm group flex p-3 w-full justify-start font-medium cursor-pointer hover:text-white hover:bg-white/10 rounded-lg transition',
                pathname === route.href ? 'text-white bg-white/10' : 'text-zinc-400',
              )}
            >
              <div className="flex items-center flex-1">
                <route.icon className={cn('h-5 w-5 mr-3', route.color)} />
                {route.label}
              </div>
            </Link>
          ))}
        </div>
      </div>
      <div className="px-3 py-2 flex-shrink-0">
        <div className="px-3 text-xs text-zinc-500">
          <p>© 2025 TechStack Panorama</p>
          <p className="mt-1">Powered by Next.js</p>
        </div>
      </div>
    </div>
  )
}
