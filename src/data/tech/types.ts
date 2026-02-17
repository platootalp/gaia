export interface TechDetail {
  id: string
  name: string
  category: 'frontend' | 'backend' | 'ai' | 'infrastructure'
  subcategory: string
  description: string
  tagline: string
  version: string
  pros: string[]
  cons: string[]
  bestFor: string[]
  notFor: string[]
  learningCurve: 'beginner' | 'intermediate' | 'advanced'
  ecosystemScore: number
  popularity: {
    githubStars: number
    npmDownloads?: number
  }
  companyUsers: string[]
  createdYear: number
  maintainedBy: string
  officialUrl: string
  githubUrl: string
  documentationUrl: string
  alternatives: string[]
}

export interface TechCollection {
  [key: string]: TechDetail
}
