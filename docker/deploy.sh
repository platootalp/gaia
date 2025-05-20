#!/bin/bash

set -e

PROJECT_NAME="gaia"
DEV_CMD="docker-compose up -d"
PROD_CMD="docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d"
CLEAN_CMD="docker-compose down -v"

case "$1" in
  dev)
    echo "🚀 启动开发环境..."
    $DEV_CMD
    ;;
  prod)
    echo "🚀 启动生产环境..."
    $PROD_CMD
    ;;
  clean)
    echo "🧼 正在清理所有容器和卷..."
    $CLEAN_CMD
    ;;
  *)
    echo "❗ 使用方式: ./deploy.sh [dev | prod | clean]"
    exit 1
    ;;
esac
