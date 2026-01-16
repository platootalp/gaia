#!/bin/bash

set -e

# 项目根目录
BASE_DIR=$(cd "$(dirname "$0")/.." && pwd)

# Compose 文件组合
COMPOSE_FILE_BASE="docker-compose.yml"
COMPOSE_DEV="${COMPOSE_FILE_BASE}:docker-compose.override.yml"
COMPOSE_PROD="${COMPOSE_FILE_BASE}:docker-compose.prod.yml"

# 环境变量路径
ENV_DEV="${BASE_DIR}/env/.env.dev"
ENV_PROD="${BASE_DIR}/env/.env.prod"

usage() {
  echo ""
  echo "Usage: ./scripts/deploy.sh {dev|prod|clean}"
  echo "  dev    启动开发环境"
  echo "  prod   启动生产环境"
  echo "  clean  停止并删除容器、网络和数据卷（慎用）"
  exit 1
}

if [ $# -ne 1 ]; then
  usage
fi

MODE=$1

cd "$BASE_DIR" || exit 1

case $MODE in
  dev)
    echo "🔧 启动开发环境..."
    docker-compose \
      --env-file "$ENV_DEV" \
      -f docker-compose.yml \
      -f docker-compose.override.yml \
      up -d
    ;;

  prod)
    echo "🚀 启动生产环境..."
    docker-compose \
      --env-file "$ENV_PROD" \
      -f docker-compose.yml \
      -f docker-compose.prod.yml \
      up -d
    ;;

  clean)
    echo "🧹 清理容器与卷..."
    docker-compose down -v
    ;;

  *)
    usage
    ;;
esac

