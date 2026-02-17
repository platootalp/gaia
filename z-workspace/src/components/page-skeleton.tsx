'use client'

export function PageSkeleton() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900 animate-pulse">
      <div className="container mx-auto px-4 py-16">
        {/* Header Skeleton */}
        <div className="text-center mb-16 pt-8">
          <div className="h-16 bg-gray-800/50 rounded-lg max-w-md mx-auto mb-6" />
          <div className="h-6 bg-gray-800/30 rounded max-w-xl mx-auto" />
        </div>

        {/* Cards Grid Skeleton */}
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl mx-auto">
          {[...Array(8)].map((_, i) => (
            <div key={i} className="bg-gray-800/30 border border-gray-700/30 rounded-2xl p-6 h-48">
              <div className="h-10 w-10 bg-gray-700/30 rounded-lg mb-4" />
              <div className="h-6 bg-gray-700/30 rounded mb-3 w-2/3" />
              <div className="h-4 bg-gray-700/20 rounded mb-2" />
              <div className="h-4 bg-gray-700/20 rounded w-4/5" />
            </div>
          ))}
        </div>

        {/* Features Skeleton */}
        <div className="mt-16 grid md:grid-cols-4 gap-6 max-w-6xl mx-auto">
          {[...Array(4)].map((_, i) => (
            <div key={i} className="text-center p-6 bg-gray-800/20 rounded-xl border border-gray-700/20">
              <div className="h-10 bg-gray-700/30 rounded-lg mb-2 mx-auto w-16" />
              <div className="h-4 bg-gray-700/20 rounded mx-auto w-24" />
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}

export function TableSkeleton() {
  return (
    <div className="animate-pulse">
      {/* Header */}
      <div className="h-8 bg-gray-700/30 rounded mb-4 w-48" />

      {/* Table */}
      <div className="overflow-x-auto rounded-xl border border-gray-700/30">
        <div className="space-y-2 p-4">
          {/* Table header */}
          <div className="flex gap-4 pb-4 border-b border-gray-700/30">
            <div className="h-6 bg-gray-700/30 rounded w-24" />
            <div className="h-6 bg-gray-700/30 rounded flex-1" />
            <div className="h-6 bg-gray-700/30 rounded w-32" />
            <div className="h-6 bg-gray-700/30 rounded w-40" />
          </div>

          {/* Table rows */}
          {[...Array(10)].map((_, i) => (
            <div key={i} className="flex gap-4 py-3">
              <div className="h-5 bg-gray-700/20 rounded w-24" />
              <div className="h-5 bg-gray-700/20 rounded flex-1" />
              <div className="h-5 bg-gray-700/20 rounded w-32" />
              <div className="h-5 bg-gray-700/20 rounded w-40" />
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
