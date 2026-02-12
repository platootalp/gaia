/** genAI_master_start */
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import { Sidebar } from '@/components/sidebar'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: '技术栈全景图 - Z.ai',
  description: '前端与全栈技术栈全景图展示平台',
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
          {children}
        </main>
      </body>
    </html>
  )
}
/** genAI_master_end */
