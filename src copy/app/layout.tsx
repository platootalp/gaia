import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import { Suspense } from 'react'
import './globals.css'
import { Sidebar } from '@/components/sidebar'
import { ErrorBoundary } from '@/components/error-boundary'
import { PageSkeleton } from '@/components/page-skeleton'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: '技术栈全景图 | TechStack Panorama',
  description: '全栈技术栈可视化展示平台 - 涵盖前端、后端(Python/Go/Java/Rust/Node.js)、AI开发、基础设施等完整技术生态',
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="zh-CN">
      <body className={inter.className}>
        <Sidebar />
        <main className="ml-64">
          <ErrorBoundary>
            <Suspense fallback={<PageSkeleton />}>
              {children}
            </Suspense>
          </ErrorBoundary>
        </main>
      </body>
    </html>
  )
}
