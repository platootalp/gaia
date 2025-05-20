#!/bin/bash

set -e

# 脚本根目录（保证无论从哪里执行都能正确定位）
BASE_DIR=$(cd "$(dirname "$0")" || exit; pwd)

# 默认环境文件
DEV_ENV_FILE="${BASE_DIR}/.env"
PROD_ENV_FILE="${BASE_DIR}/.env.prod"

usage() {
  echo "Usage: $0 {dev|prod|clean}"
  echo "  dev   - 启动开发环境（使用 docker-compose.override.yml 和 .env）"
  echo "  prod  - 启动生产环境（使用 docker-compose.prod.yml 和 .env.prod）"
  echo "  clean - 停止并删除所有容器，清理数据卷（谨慎操作）"
  exit 1
}

if [ $# -ne 1 ]; then
  usage
fi

MODE=$1

case $MODE in
  dev)
    echo "启动开发环境..."
    docker-compose --env-file "$DEV_ENV_FILE" up -d
    ;;

  prod)
    echo "启动生产环境..."
    if [ ! -f "$PROD_ENV_FILE" ]; then
      echo "生产环境环境变量文件 $PROD_ENV_FILE 不存在，退出"
      exit 1
    fi
    docker-compose --env-file "$PROD_ENV_FILE" -f docker-compose.yml -f docker-compose.prod.yml up -d
    ;;

  clean)
    echo "停止并删除所有容器..."
    docker-compose down -v
    echo "清理完毕"
    ;;

  *)
    usage
    ;;
esac
