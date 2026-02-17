
export default function TechStackAll() {
  // ==================== 前端技术栈 ====================
  const frontendData = [
    { category: '框架', tech: 'React / Vue 3 / Angular / Svelte / Solid.js', popular: 'React 18 / Vue 3', description: '构建用户界面的核心框架' },
    { category: '元框架', tech: 'Next.js / Nuxt.js / SvelteKit / Remix / Astro / Fresh', popular: 'Next.js 15', description: '提供SSR/SSG/ISR等全栈能力' },
    { category: '语言', tech: 'TypeScript / JavaScript / ReScript / PureScript', popular: 'TypeScript 5.x', description: '类型安全的开发体验' },
    { category: '样式方案', tech: 'Tailwind CSS / CSS-in-JS (Emotion/styled) / SCSS / CSS Modules / UnoCSS', popular: 'Tailwind CSS', description: '原子化CSS成为主流趋势' },
    { category: '状态管理', tech: 'Zustand / Pinia / Jotai / Recoil / Redux Toolkit / MobX / XState', popular: 'Zustand / Pinia', description: '轻量化与原子化状态管理' },
    { category: '构建工具', tech: 'Vite / Turbopack / Webpack / Rollup / esbuild / Rspack', popular: 'Vite / Turbopack', description: '极速开发与构建体验' },
    { category: 'UI组件库', tech: 'shadcn/ui / Radix UI / Ant Design / MUI / Chakra UI / Arco Design', popular: 'shadcn/ui', description: '无样式/可定制组件受青睐' },
    { category: '测试工具', tech: 'Vitest / Jest / Playwright / Cypress / Testing Library / Storybook', popular: 'Vitest + Playwright', description: '单元测试+E2E测试组合' },
    { category: '代码格式化', tech: 'Prettier / Biome / dprint', popular: 'Prettier / Biome', description: '统一代码风格，Biome更快' },
    { category: '代码检查', tech: 'ESLint / Biome / Oxlint / Stylelint / Markdownlint', popular: 'ESLint + Biome', description: '代码质量与规范检查' },
    { category: 'Git工具', tech: 'Husky / lint-staged / Commitlint / Git Hooks / Semantic Release', popular: 'Husky + lint-staged', description: 'Git工作流自动化' },
    { category: '包管理器', tech: 'pnpm / Bun / yarn / npm', popular: 'pnpm / Bun', description: '高效依赖管理' },
    { category: '性能优化', tech: 'Lighthouse / Web Vitals / Bundle Analyzer / React Profiler / Lazy Loading', popular: 'Web Vitals', description: '性能监控与优化指标' },
    { category: '可视化', tech: 'ECharts / D3.js / Three.js / Recharts / Vis.js / Chart.js', popular: 'ECharts / D3.js', description: '数据可视化图表库' },
    { category: '表单处理', tech: 'React Hook Form / Formik / Zod / Yup / VeeValidate', popular: 'React Hook Form + Zod', description: '表单验证与状态管理' },
    { category: '动画库', tech: 'Framer Motion / GSAP / Lottie / React Spring / Motion One', popular: 'Framer Motion', description: '流畅的UI动画效果' },
    { category: '数据请求', tech: 'TanStack Query / SWR / Axios / ky / tRPC / React Router', popular: 'TanStack Query', description: '服务端状态管理' },
    { category: '桌面/移动端', tech: 'Electron / Tauri / React Native / Flutter / Capacitor', popular: 'Tauri / React Native', description: '跨平台应用开发' },
    { category: '国际化', tech: 'next-intl / i18next / FormatJS / vue-i18n', popular: 'next-intl', description: '多语言支持方案' },
    { category: '无障碍', tech: 'Radix UI / Reach UI / ARIA / axe-core', popular: 'Radix UI + axe', description: '符合WCAG标准的组件' },
  ]

  // ==================== 后端技术栈（按语言分类） ====================
  // Node.js / TypeScript 生态
  const nodejsData = [
    { category: 'Web框架', tech: 'NestJS / Express / Fastify / Hono / Koa / Elysia / Nest / Strapi', popular: 'NestJS / Hono', description: '企业级与轻量级并存' },
    { category: 'ORM/数据库', tech: 'Prisma / Drizzle ORM / TypeORM / MikroORM / Mongoose / Sequelize / Kysely', popular: 'Prisma / Drizzle', description: '类型安全的数据库操作' },
    { category: '验证', tech: 'Zod / class-validator / Joi / Yup / TypeBox / Valibot / Ajv', popular: 'Zod', description: '运行时类型验证' },
    { category: '认证授权', tech: 'NextAuth.js / Passport.js / Jose / Lucia / Clerk / Auth.js / Auth0 / SuperTokens', popular: 'NextAuth / Clerk', description: '身份认证解决方案' },
    { category: '实时通信', tech: 'Socket.io / ws / uWebSockets.js / Pusher / Ably / PartyKit', popular: 'Socket.io / PartyKit', description: 'WebSocket实时通信' },
    { category: '任务队列', tech: 'BullMQ / Agenda / Bee-Queue / Graphile Worker / Temporal', popular: 'BullMQ / Temporal', description: '后台任务与工作流' },
    { category: '缓存', tech: 'Redis / Keyv / cache-manager / node-cache', popular: 'Redis', description: '数据缓存方案' },
    { category: '文件存储', tech: 'AWS S3 SDK / MinIO / Uploadthing / Uppy / formidable / Multer', popular: 'AWS S3 / Uploadthing', description: '文件上传与存储' },
    { category: '邮件服务', tech: 'Nodemailer / Resend / SendGrid / Postmark / React Email', popular: 'Resend / Nodemailer', description: '邮件发送服务' },
    { category: '日志', tech: 'Pino / Winston / Bunyan / log4js / Datadog Logs', popular: 'Pino', description: '高性能日志记录' },
    { category: '测试', tech: 'Jest / Vitest / Supertest / Node-tap / TestContainers', popular: 'Vitest', description: '单元与集成测试' },
    { category: '文档生成', tech: 'Swagger / OpenAPI / TypeDoc / Tsoa / NestJS Swagger', popular: 'Swagger / OpenAPI', description: 'API文档自动生成' },
    { category: '安全', tech: 'Helmet / cors / bcrypt / argon2 / jsonwebtoken / rate-limiter-flexible', popular: 'Helmet + bcrypt', description: 'Web安全防护' },
    { category: 'CLI工具', tech: 'Commander / Yargs / Inquirer / Oclif / Citty', popular: 'Commander / Inquirer', description: '命令行工具开发' },
    { category: '进程管理', tech: 'PM2 / Docker / Systemd / Nginx / Caddy', popular: 'PM2 / Docker', description: '进程守护与部署' },
  ]

  // Python 生态
  const pythonData = [
    { category: 'Web框架', tech: 'FastAPI / Django / Flask / Starlette / Sanic / Quart / Tornado / Aiohttp', popular: 'FastAPI', description: '异步高性能API开发' },
    { category: 'ORM', tech: 'SQLAlchemy / Django ORM / Tortoise ORM / Prisma Python / Peewee / Pony ORM', popular: 'SQLAlchemy', description: '强大的ORM框架' },
    { category: '验证', tech: 'Pydantic / Marshmallow / Cerberus / attrs / Dataclasses', popular: 'Pydantic', description: '数据验证与序列化' },
    { category: '认证授权', tech: 'FastAPI Users / Django Auth / Authlib / PyJWT / Passlib / python-oauth2', popular: 'FastAPI Users / Authlib', description: '身份认证方案' },
    { category: '异步', tech: 'asyncio / uvicorn / gunicorn / hypercorn / uvloop', popular: 'uvicorn + uvloop', description: '异步运行时服务器' },
    { category: '任务队列', tech: 'Celery / Dramatiq / RQ (Redis Queue) / Huey / Taskiq', popular: 'Celery', description: '分布式任务队列' },
    { category: '爬虫', tech: 'Scrapy / httpx / BeautifulSoup / Playwright Python / Selenium / Parsel', popular: 'Scrapy / httpx', description: '数据采集与爬虫' },
    { category: '数据处理', tech: 'Pandas / NumPy / Polars / Vaex / DuckDB', popular: 'Pandas / Polars', description: '数据分析与处理' },
    { category: '缓存', tech: 'Redis-py / aiocache / cachetools / Memcached', popular: 'Redis-py / aiocache', description: '缓存客户端' },
    { category: '邮件服务', tech: 'FastAPI Mail / Django Mail / SendGrid Python / yagmail', popular: 'FastAPI Mail', description: '邮件发送' },
    { category: '日志', tech: 'structlog / Loguru / logging / ELK Stack', popular: 'structlog / Loguru', description: '结构化日志' },
    { category: '测试', tech: 'pytest / unittest / hypothesis / locust / factory_boy / faker', popular: 'pytest', description: 'Python测试生态' },
    { category: '文档生成', tech: 'FastAPI Swagger / MkDocs / Sphinx / Redoc', popular: 'FastAPI Swagger', description: '自动API文档' },
    { category: 'CLI工具', tech: 'Click / Typer / Argparse / Rich / Fire', popular: 'Typer + Rich', description: '命令行工具开发' },
    { category: '部署运行', tech: 'Gunicorn + Uvicorn / Docker / Systemd / Supervisor', popular: 'Gunicorn + Uvicorn', description: '生产环境部署' },
  ]

  // Go 生态
  const goData = [
    { category: 'Web框架', tech: 'Gin / Echo / Fiber / Chi / Go-Zero / Kratos / Hertz / Encore / Buffalo', popular: 'Gin / Go-Zero', description: '高性能微服务框架' },
    { category: 'ORM', tech: 'GORM / Ent / sqlx / Bun / sqlc / goquery', popular: 'GORM / Ent', description: 'Go数据库操作' },
    { category: '验证', tech: 'validator / go-playground/validator / ozzo-validation', popular: 'go-playground/validator', description: '结构体验证' },
    { category: '认证授权', tech: 'golang-jwt / go-oauth2 / casbin / goth / authboss', popular: 'golang-jwt + casbin', description: 'JWT与权限控制' },
    { category: '微服务', tech: 'gRPC / Go-Micro / Kit / Goa / Kratos / Dubbo-go', popular: 'gRPC / Go-Zero', description: '微服务通信协议' },
    { category: '并发', tech: 'Goroutine / Channel / errgroup / sync包 / ants(协程池)', popular: 'Goroutine + Channel', description: '原生并发支持' },
    { category: '配置', tech: 'Viper / envconfig / koanf / cleanenv', popular: 'Viper', description: '配置管理' },
    { category: '缓存', tech: 'go-redis / ristretto / bigcache / go-cache / groupcache', popular: 'go-redis + ristretto', description: 'Go缓存方案' },
    { category: '日志', tech: 'zap / logrus / zerolog / slog(Go 1.21+) / lumberjack', popular: 'zap / slog', description: '高性能日志' },
    { category: '测试', tech: 'go test / Testify / Ginkgo / mockery / go-sqlmock / testcontainers-go', popular: 'go test + Testify', description: 'Go测试生态' },
    { category: '文档生成', tech: 'Swag / go-swagger / OpenAPI / Hertz Swagger', popular: 'Swag / go-swagger', description: 'API文档生成' },
    { category: 'CLI工具', tech: 'Cobra / urfave/cli / Kong / mow.cli', popular: 'Cobra', description: '命令行工具开发' },
    { category: '服务发现', tech: 'Consul / Etcd / Nacos / Zookeeper', popular: 'Consul / Etcd', description: '服务注册与发现' },
    { category: '消息队列', tech: 'sarama(Kafka) / RocketMQ Client / NATS.go / RabbitMQ Go', popular: 'sarama / NATS', description: '消息队列客户端' },
    { category: '部署运行', tech: 'Docker / Kubernetes / Systemd / Supervisor', popular: 'Docker + K8s', description: '生产环境部署' },
  ]

  // Java 生态
  const javaData = [
    { category: 'Web框架', tech: 'Spring Boot / Quarkus / Micronaut / Vert.x / Ktor / Play Framework', popular: 'Spring Boot 3', description: '企业级应用首选' },
    { category: 'ORM', tech: 'Spring Data JPA / MyBatis / MyBatis-Plus / JOOQ / Hibernate / QueryDSL', popular: 'MyBatis-Plus / JPA', description: '数据库持久层' },
    { category: '验证', tech: 'Hibernate Validator / Spring Validation / Jakarta Validation', popular: 'Spring Validation', description: '数据验证框架' },
    { category: '认证授权', tech: 'Spring Security / Apache Shiro / JWT / OAuth2 / Keycloak', popular: 'Spring Security', description: '企业级安全框架' },
    { category: '微服务', tech: 'Spring Cloud / Dubbo / gRPC-Java / Service Mesh (Istio)', popular: 'Spring Cloud', description: '微服务架构支持' },
    { category: '构建工具', tech: 'Maven / Gradle / Bazel', popular: 'Gradle', description: '项目构建与依赖管理' },
    { category: '缓存', tech: 'Spring Cache + Redis / Caffeine / Ehcache / Hazelcast', popular: 'Redis + Caffeine', description: '多级缓存方案' },
    { category: '日志', tech: 'SLF4J + Logback / Log4j2 / Java Util Logging', popular: 'SLF4J + Logback', description: '日志框架门面' },
    { category: '测试', tech: 'JUnit 5 / Mockito / TestContainers / WireMock / AssertJ / RestAssured', popular: 'JUnit 5 + Mockito', description: 'Java测试生态' },
    { category: '文档生成', tech: 'SpringDoc / Swagger / OpenAPI / Knife4j', popular: 'SpringDoc', description: 'API文档生成' },
    { category: '任务调度', tech: 'Spring Scheduler / Quartz / XXL-Job / Elastic-Job', popular: 'XXL-Job', description: '分布式任务调度' },
    { category: '消息队列', tech: 'Spring Kafka / RocketMQ Spring / RabbitMQ / ActiveMQ', popular: 'Spring Kafka', description: '消息中间件' },
    { category: '搜索', tech: 'Spring Data Elasticsearch / Hibernate Search / Solr', popular: 'Elasticsearch', description: '全文搜索引擎' },
    { category: 'CLI工具', tech: 'Picocli / JCommander / Apache Commons CLI', popular: 'Picocli', description: '命令行工具开发' },
    { category: '部署运行', tech: 'Docker / Kubernetes / JAR / WAR / GraalVM Native', popular: 'Docker + K8s', description: '生产环境部署' },
  ]

  // Rust 生态
  const rustData = [
    { category: 'Web框架', tech: 'Axum / Actix-web / Rocket / Warp / Poem / Salvo / Tide', popular: 'Axum', description: '高性能异步Web框架' },
    { category: 'ORM', tech: 'Diesel / SeaORM / SQLx / Rustorm / Corm', popular: 'SeaORM / SQLx', description: '类型安全的数据库操作' },
    { category: '验证', tech: 'validator / garde / schemars', popular: 'validator / garde', description: '数据验证库' },
    { category: '认证授权', tech: 'jsonwebtoken / oxide-auth / casbin-rs / oauth2', popular: 'jsonwebtoken', description: 'JWT与OAuth实现' },
    { category: '异步运行时', tech: 'Tokio / async-std / smol', popular: 'Tokio', description: 'Rust异步运行时' },
    { category: '序列化', tech: 'Serde / serde_json / bincode / rkyv', popular: 'Serde', description: '数据序列化框架' },
    { category: 'HTTP客户端', tech: 'reqwest / hyper / surf / ureq', popular: 'reqwest / hyper', description: 'HTTP请求客户端' },
    { category: '缓存', tech: 'moka / cached / redis-rs', popular: 'redis-rs + moka', description: '缓存库' },
    { category: '日志', tech: 'tracing / log / slog / env_logger', popular: 'tracing', description: '结构化日志追踪' },
    { category: '测试', tech: '内置test / mockall / proptest / quickcheck / tokio-test', popular: '内置 + mockall', description: 'Rust测试生态' },
    { category: 'CLI工具', tech: 'clap / structopt / argh / pico-args', popular: 'clap', description: '命令行参数解析' },
    { category: '错误处理', tech: 'thiserror / anyhow / eyre / color-eyre', popular: 'thiserror + anyhow', description: '错误处理库' },
    { category: '并发', tech: 'rayon / crossbeam / parking_lot / dashmap', popular: 'rayon + crossbeam', description: '并发原语与数据结构' },
    { category: '消息队列', tech: 'lapin (RabbitMQ) / rdkafka / async-nats', popular: 'rdkafka', description: '消息队列客户端' },
    { category: '部署运行', tech: 'Docker / Systemd / Native Binary', popular: 'Native Binary', description: '单文件部署' },
  ]

  // 基础设施与通用组件
  const infrastructureData = [
    { category: '关系型数据库', tech: 'PostgreSQL / MySQL / MariaDB / SQLite / PlanetScale / CockroachDB / TiDB', popular: 'PostgreSQL', description: '关系型数据库选择' },
    { category: 'NoSQL数据库', tech: 'MongoDB / DynamoDB / Cassandra / ScyllaDB / Couchbase', popular: 'MongoDB / DynamoDB', description: '文档/列式数据库' },
    { category: '缓存', tech: 'Redis / Memcached / Dragonfly / KeyDB / Valkey', popular: 'Redis / Dragonfly', description: '高性能缓存方案' },
    { category: '时序数据库', tech: 'InfluxDB / TimescaleDB / Prometheus / QuestDB', popular: 'InfluxDB / TimescaleDB', description: '监控指标存储' },
    { category: '消息队列', tech: 'Kafka / RabbitMQ / Redis Streams / NATS / Pulsar / RocketMQ / Amazon SQS', popular: 'Kafka / NATS', description: '异步消息处理' },
    { category: '搜索引擎', tech: 'Elasticsearch / Meilisearch / Typesense / Algolia / Quickwit / OpenSearch', popular: 'Elasticsearch / Meilisearch', description: '全文搜索服务' },
    { category: '对象存储', tech: 'AWS S3 / MinIO / Cloudflare R2 / Google Cloud Storage / Azure Blob', popular: 'AWS S3 / MinIO', description: '文件对象存储' },
    { category: '容器编排', tech: 'Docker / Kubernetes / Docker Compose / Podman / Nomad', popular: 'Docker + K8s', description: '云原生部署标配' },
    { category: 'Serverless', tech: 'Vercel / AWS Lambda / Cloudflare Workers / Fly.io / Azure Functions / GCP Cloud Run', popular: 'Vercel / CF Workers', description: '边缘计算新趋势' },
    { category: 'API网关', tech: 'Kong / Traefik / Nginx / Envoy / APISIX / KrakenD / AWS API Gateway', popular: 'Kong / Traefik', description: 'API管理与路由' },
    { category: '负载均衡', tech: 'Nginx / HAProxy / Traefik / Envoy / AWS ALB / Cloudflare Load Balancing', popular: 'Nginx / Traefik', description: '流量分发与负载均衡' },
    { category: '可观测性', tech: 'Prometheus / Grafana / Jaeger / OpenTelemetry / Datadog / New Relic / Sentry', popular: 'Prometheus + Grafana', description: '监控与链路追踪' },
    { category: '日志管理', tech: 'ELK Stack / Loki / Datadog Logs / Fluentd / Fluent Bit', popular: 'ELK / Loki', description: '日志收集与分析' },
    { category: 'API协议', tech: 'REST / GraphQL / tRPC / gRPC / WebSocket / SSE / Server-Sent Events', popular: 'REST + GraphQL', description: 'API设计风格选择' },
    { category: 'CI/CD', tech: 'GitHub Actions / GitLab CI / Jenkins / CircleCI / ArgoCD / Tekton / Woodpecker', popular: 'GitHub Actions', description: '持续集成与部署' },
    { category: 'IaC', tech: 'Terraform / Pulumi / AWS CDK / Ansible / Crossplane', popular: 'Terraform / Pulumi', description: '基础设施即代码' },
    { category: '服务网格', tech: 'Istio / Linkerd / Consul Connect / Kuma', popular: 'Istio / Linkerd', description: '微服务治理' },
    { category: '配置中心', tech: 'Consul / Etcd / Nacos / Apollo / Spring Cloud Config', popular: 'Consul / Nacos', description: '分布式配置管理' },
    { category: '密钥管理', tech: 'HashiCorp Vault / AWS Secrets Manager / Azure Key Vault / Infisical', popular: 'Vault', description: '密钥与证书管理' },
    { category: 'CDN', tech: 'Cloudflare / AWS CloudFront / Fastly / Vercel Edge / BunnyCDN', popular: 'Cloudflare', description: '内容分发网络' },
  ]

  // ==================== AI开发技术栈 ====================
  const aiCoreData = [
    { category: '深度学习框架', tech: 'PyTorch / TensorFlow / JAX / PaddlePaddle / MindSpore / MXNet', popular: 'PyTorch 2.x', description: '研究与应用的主流选择' },
    { category: '大语言模型', tech: 'GPT-4o / Claude 3.5 / Gemini / Llama 3 / Qwen / DeepSeek / Mistral / Yi', popular: 'GPT-4o / Claude 3.5', description: '闭源与开源模型并存' },
    { category: '多模态模型', tech: 'GPT-4V / Gemini / Claude Vision / LLaVA / Qwen-VL / CogVLM / InternVL', popular: 'GPT-4V / Gemini', description: '图像+文本理解' },
    { category: '图像生成', tech: 'Stable Diffusion / DALL-E 3 / Midjourney / FLUX / SDXL / Playground', popular: 'SDXL / FLUX', description: '文生图模型' },
    { category: '视频生成', tech: 'Sora / Runway Gen-3 / Pika / Stable Video Diffusion / Kling / Haiper', popular: 'Sora / Runway', description: '文生视频新趋势' },
    { category: '语音识别', tech: 'Whisper / Google Speech / Azure Speech / 阿里语音 / 讯飞', popular: 'Whisper', description: 'ASR语音转文字' },
    { category: '语音合成', tech: 'ElevenLabs / Azure TTS / VITS / Coqui TTS / Bark / OpenAI TTS', popular: 'ElevenLabs / OpenAI TTS', description: 'TTS文字转语音' },
    { category: '代码模型', tech: 'GitHub Copilot / CodeLlama / StarCoder / DeepSeek Coder / Qwen-Coder', popular: 'Copilot / DeepSeek Coder', description: '代码生成模型' },
  ]

  const aiLLMData = [
    { category: 'LLM SDK', tech: 'OpenAI SDK / Anthropic SDK / Google AI SDK / LangChain / LlamaIndex / Haystack', popular: '官方SDK', description: '直接调用更简洁高效' },
    { category: 'RAG框架', tech: 'LlamaIndex / LangChain / Haystack / DSPy / RAGFlow / LangChain4j', popular: 'LlamaIndex / DSPy', description: '检索增强生成' },
    { category: 'Agent框架', tech: 'LangGraph / CrewAI / AutoGen / Semantic Kernel / Dify / AutoGPT / MemGPT', popular: 'LangGraph / Dify', description: '多智能体协作开发' },
    { category: 'Prompt管理', tech: 'Promptflow / LangSmith / Weights & Biases / Helicone / Literal AI', popular: 'LangSmith', description: 'Prompt版本管理' },
    { category: '结构化输出', tech: 'Instructor / Marvin / Pydantic AI / Outlines', popular: 'Instructor', description: 'LLM结构化响应' },
    { category: 'Function Calling', tech: 'OpenAI Functions / Tool Use / MCP (Model Context Protocol)', popular: 'OpenAI Functions / MCP', description: '工具调用能力' },
    { category: '记忆管理', tech: 'Mem0 / Letta / LangMem / MemGPT', popular: 'Mem0', description: 'LLM长期记忆' },
    { category: '模型路由', tech: 'LiteLLM / OpenRouter / Together AI / OneAPI', popular: 'LiteLLM', description: '统一模型调用网关' },
  ]

  const aiInfraData = [
    { category: '向量数据库', tech: 'Pinecone / Milvus / Weaviate / Qdrant / Chroma / pgvector / Elasticsearch', popular: 'Milvus / Qdrant', description: '向量存储与检索' },
    { category: '模型部署', tech: 'vLLM / TensorRT-LLM / TGI / Ollama / llama.cpp / Triton / Ray Serve', popular: 'vLLM / Ollama', description: '高效推理服务' },
    { category: '推理优化', tech: 'Flash Attention / PagedAttention / KV Cache优化 / Continuous Batching', popular: 'Flash Attention', description: '推理速度优化' },
    { category: '模型量化', tech: 'GPTQ / AWQ / GGUF / bitsandbytes / AutoGPTQ / TensorRT', popular: 'AWQ / GGUF', description: '模型压缩与加速' },
    { category: '模型服务', tech: 'Ray Serve / BentoML / Modal / RunPod / Replicate / Anyscale', popular: 'Ray Serve / Modal', description: '模型服务化平台' },
    { category: 'Embedding模型', tech: 'OpenAI Embedding / BGE / Cohere / Voyage / Jina / Mistral Embed', popular: 'text-embedding-3 / BGE', description: '文本向量化模型' },
    { category: '重排序', tech: 'Cohere Rerank / BGE Reranker / Voyage Rerank / ColBERT', popular: 'Cohere / BGE Reranker', description: '搜索结果重排序' },
    { category: 'GPU云服务', tech: 'AWS / GCP / Azure / Lambda Labs / RunPod / Modal / Together AI', popular: 'AWS / Modal', description: 'GPU计算资源' },
  ]

  const aiTrainData = [
    { category: '模型微调', tech: 'LoRA / QLoRA / PEFT / Full Fine-tuning / DPO / PPO / SFT', popular: 'LoRA / QLoRA', description: '参数高效微调' },
    { category: '训练框架', tech: 'PyTorch FSDP / DeepSpeed / Megatron-LM / Axolotl / Unsloth / LitParrot', popular: 'DeepSpeed / FSDP', description: '分布式训练框架' },
    { category: '数据处理', tech: 'Hugging Face Datasets / Pandas / Polars / spaCy / Label Studio / Doccano', popular: 'HF Datasets', description: '训练数据准备与标注' },
    { category: '分布式训练', tech: 'CUDA / NCCL / torchrun / FSDP / DeepSpeed ZeRO / Megatron', popular: 'FSDP + DeepSpeed', description: '多GPU/多节点训练' },
    { category: '合成数据', tech: 'Synthetic Data / Self-Instruct / Evol-Instruct / Magpie', popular: 'Self-Instruct', description: '训练数据合成' },
    { category: '强化学习', tech: 'RLHF / PPO / DPO / KTO / ORPO / RL4LMs', popular: 'DPO / PPO', description: '人类反馈强化学习' },
    { category: '数据增强', tech: 'Back Translation / Paraphrasing / Mixup / SpecAugment', popular: '数据增强', description: '训练数据增强' },
    { category: '模型合并', tech: 'Mergekit / TIES / DARE / Task Arithmetic / SLERP', popular: 'Mergekit', description: '多模型合并技术' },
  ]

  const aiEvalData = [
    { category: '模型评估', tech: 'lm-eval-harness / OpenAI Evals / TruLens / Ragas / DeepEval / Promptfoo', popular: 'lm-eval + Ragas', description: '模型能力评测' },
    { category: '基准测试', tech: 'MMLU / HumanEval / GSM8K / MT-Bench / C-Eval / GPQA / SWEBench', popular: 'MMLU / MT-Bench', description: '标准化能力测试' },
    { category: '安全评估', tech: 'LLM Guard / Garak / Red Team Testing / Constitutional AI / HarmBench', popular: 'LLM Guard / Garak', description: '安全性与合规评估' },
    { category: '质量监控', tech: 'LangSmith / Arize / Phoenix / Weights & Biases / Helicone / HoneyHive', popular: 'LangSmith / Arize', description: 'LLM应用监控' },
    { category: '幻觉检测', tech: 'HaluEval / Faithfulness / FactScore / SelfCheckGPT', popular: 'FactScore', description: '模型幻觉评估' },
    { category: '性能测试', tech: 'TTFT / Throughput / Latency / Token/s / Memory Usage', popular: '全指标监控', description: '推理性能指标' },
  ]

  const aiToolsData = [
    { category: '开发平台', tech: 'Hugging Face / Gradio / Streamlit / Chainlit / Open WebUI / Dify', popular: 'Hugging Face', description: '模型托管与快速原型' },
    { category: 'AI编程工具', tech: 'Cursor / GitHub Copilot / Claude Code / Windsurf / Continue / Zed', popular: 'Cursor + Claude', description: 'AI辅助开发新范式' },
    { category: 'Notebook', tech: 'Jupyter / Google Colab / Kaggle / Marimo / Deepnote', popular: 'Jupyter / Colab', description: '交互式开发环境' },
    { category: '实验管理', tech: 'MLflow / Weights & Biases / Neptune / ClearML / DVC', popular: 'W&B / MLflow', description: '实验追踪与版本管理' },
    { category: 'AutoML', tech: 'AutoGluon / H2O.ai / FLAML / Optuna / Ray Tune', popular: 'Optuna / Ray Tune', description: '自动化机器学习' },
    { category: '数据标注', tech: 'Label Studio / Doccano / Prodigy / CVAT / Roboflow', popular: 'Label Studio', description: '数据标注平台' },
    { category: '模型转换', tech: 'ONNX / TensorRT / OpenVINO / CoreML / TF Lite', popular: 'ONNX / TensorRT', description: '模型格式转换' },
    { category: '边缘部署', tech: 'llama.cpp / MLC LLM / TensorFlow Lite / Core ML / ONNX Runtime', popular: 'llama.cpp', description: '端侧模型部署' },
  ]

  const renderTable = (data: typeof frontendData, title: string, gradient: string) => (
    <div className="mb-8">
      {title && (
        <h3 className="text-lg font-semibold mb-3 text-gray-700 dark:text-gray-200">
          {title}
        </h3>
      )}
      <div className="overflow-x-auto rounded-xl border border-gray-200 dark:border-gray-700 shadow-lg">
        <table className="w-full text-sm">
          <thead>
            <tr className="bg-gray-50 dark:bg-gray-800">
              <th className="px-4 py-3 text-left font-semibold text-gray-700 dark:text-gray-200 border-b border-gray-200 dark:border-gray-700 w-28 whitespace-nowrap">分类</th>
              <th className="px-4 py-3 text-left font-semibold text-gray-700 dark:text-gray-200 border-b border-gray-200 dark:border-gray-700">主流技术</th>
              <th className="px-4 py-3 text-left font-semibold text-gray-700 dark:text-gray-200 border-b border-gray-200 dark:border-gray-700 w-36 whitespace-nowrap">热门推荐</th>
              <th className="px-4 py-3 text-left font-semibold text-gray-700 dark:text-gray-200 border-b border-gray-200 dark:border-gray-700 w-40">说明</th>
            </tr>
          </thead>
          <tbody>
            {data.map((row, index) => (
              <tr
                key={index}
                className="hover:bg-blue-50 dark:hover:bg-gray-700/50 transition-colors"
              >
                <td className="px-4 py-2.5 font-medium text-gray-800 dark:text-gray-100 border-b border-gray-100 dark:border-gray-700 whitespace-nowrap text-sm">
                  {row.category}
                </td>
                <td className="px-4 py-2.5 text-gray-600 dark:text-gray-300 border-b border-gray-100 dark:border-gray-700">
                  <div className="flex flex-wrap gap-1">
                    {row.tech.split(' / ').map((tech, i) => (
                      <span
                        key={i}
                        className="inline-block px-2 py-0.5 bg-gray-100 dark:bg-gray-600 rounded text-xs text-gray-700 dark:text-gray-200"
                      >
                        {tech}
                      </span>
                    ))}
                  </div>
                </td>
                <td className="px-4 py-2.5 border-b border-gray-100 dark:border-gray-700">
                  <span className="inline-block px-2.5 py-0.5 bg-green-100 dark:bg-green-900/50 text-green-700 dark:text-green-300 rounded-full text-xs font-semibold whitespace-nowrap">
                    {row.popular}
                  </span>
                </td>
                <td className="px-4 py-2.5 text-gray-500 dark:text-gray-400 border-b border-gray-100 dark:border-gray-700 text-xs">
                  {row.description}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )

  const renderSection = (title: string, icon: string) => (
    <h3 className="text-lg font-semibold mb-3 mt-6 flex items-center gap-2 text-gray-800 dark:text-gray-100 bg-gray-100 dark:bg-gray-800 px-4 py-2 rounded-lg">
      <span className="text-xl">{icon}</span>
      <span>{title}</span>
    </h3>
  )

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900 py-8 px-4">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="text-center mb-10">
          <h1 className="text-4xl md:text-5xl font-bold mb-4">
            <span className="bg-gradient-to-r from-blue-600 via-purple-600 to-pink-600 bg-clip-text text-transparent">
              2025 主流技术栈全景图
            </span>
          </h1>
          <p className="text-gray-600 dark:text-gray-300 text-lg max-w-3xl mx-auto">
            前端 · 后端 · AI 开发技术对比,助您全面了解当前技术生态
          </p>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-10">
          <div className="bg-white dark:bg-gray-800 rounded-xl p-5 shadow-lg border border-gray-100 dark:border-gray-700">
            <div className="text-3xl font-bold text-blue-600 dark:text-blue-400 mb-1">20+</div>
            <div className="text-gray-500 dark:text-gray-400 text-sm">前端技术领域</div>
          </div>
          <div className="bg-white dark:bg-gray-800 rounded-xl p-5 shadow-lg border border-gray-100 dark:border-gray-700">
            <div className="text-3xl font-bold text-purple-600 dark:text-purple-400 mb-1">5</div>
            <div className="text-gray-500 dark:text-gray-400 text-sm">后端语言生态</div>
          </div>
          <div className="bg-white dark:bg-gray-800 rounded-xl p-5 shadow-lg border border-gray-100 dark:border-gray-700">
            <div className="text-3xl font-bold text-pink-600 dark:text-pink-400 mb-1">40+</div>
            <div className="text-gray-500 dark:text-gray-400 text-sm">AI 技术领域</div>
          </div>
          <div className="bg-white dark:bg-gray-800 rounded-xl p-5 shadow-lg border border-gray-100 dark:border-gray-700">
            <div className="text-3xl font-bold text-green-600 dark:text-green-400 mb-1">20+</div>
            <div className="text-gray-500 dark:text-gray-400 text-sm">基础设施组件</div>
          </div>
        </div>

        {/* Frontend Section */}
        <div className="mb-10">
          <h2 className="text-2xl font-bold mb-4 bg-gradient-to-r from-blue-500 to-cyan-500 bg-clip-text text-transparent flex items-center gap-2">
            <span className="text-2xl">🖥️</span> 前端技术栈 (Frontend)
          </h2>
          {renderTable(frontendData, '', 'from-blue-500 to-cyan-500')}
        </div>

        {/* Backend Section */}
        <div className="mb-10">
          <h2 className="text-2xl font-bold mb-4 bg-gradient-to-r from-purple-500 to-indigo-500 bg-clip-text text-transparent flex items-center gap-2">
            <span className="text-2xl">⚙️</span> 后端技术栈 (Backend)
          </h2>

          {renderSection('Node.js / TypeScript 生态', '🟢')}
          {renderTable(nodejsData, '', 'from-green-500 to-emerald-500')}

          {renderSection('Python 生态', '🐍')}
          {renderTable(pythonData, '', 'from-yellow-500 to-amber-500')}

          {renderSection('Go 生态', '🐹')}
          {renderTable(goData, '', 'from-cyan-500 to-teal-500')}

          {renderSection('Java 生态', '☕')}
          {renderTable(javaData, '', 'from-orange-500 to-red-500')}

          {renderSection('Rust 生态', '🦀')}
          {renderTable(rustData, '', 'from-orange-600 to-amber-600')}

          {renderSection('基础设施与通用组件', '🏗️')}
          {renderTable(infrastructureData, '', 'from-slate-500 to-gray-500')}
        </div>

        {/* AI Section */}
        <div className="mb-10">
          <h2 className="text-2xl font-bold mb-4 bg-gradient-to-r from-pink-500 to-rose-500 bg-clip-text text-transparent flex items-center gap-2">
            <span className="text-2xl">🤖</span> AI 开发技术栈 (AI Development)
          </h2>

          {renderSection('核心模型', '🧠')}
          {renderTable(aiCoreData, '', 'from-violet-500 to-purple-500')}

          {renderSection('LLM 应用开发', '💬')}
          {renderTable(aiLLMData, '', 'from-pink-500 to-rose-500')}

          {renderSection('推理与部署', '🚀')}
          {renderTable(aiInfraData, '', 'from-blue-500 to-indigo-500')}

          {renderSection('训练与微调', '🔬')}
          {renderTable(aiTrainData, '', 'from-green-500 to-teal-500')}

          {renderSection('评估与监控', '📊')}
          {renderTable(aiEvalData, '', 'from-amber-500 to-orange-500')}

          {renderSection('开发工具与平台', '🛠️')}
          {renderTable(aiToolsData, '', 'from-gray-500 to-slate-500')}
        </div>

        {/* Footer */}
        <div className="text-center mt-10 pt-6 border-t border-gray-200 dark:border-gray-700">
          <p className="text-gray-500 dark:text-gray-400 text-sm">
            💡 提示:技术选型需根据项目需求、团队经验和生态成熟度综合考量
          </p>
        </div>
      </div>
    </div>
  )
}
