FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gaia-interfaces/target/*.jar app.jar

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

# 添加SkyWalking代理
RUN mkdir -p /skywalking/agent
COPY skywalking-agent/* /skywalking/agent/

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=30s --retries=3 CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -javaagent:/skywalking/agent/skywalking-agent.jar -jar app.jar"]

