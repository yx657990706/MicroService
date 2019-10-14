#mvn clean package dockerfile:build -DskipTests
FROM frolvlad/alpine-java:jdk8-slim
# /tmp目录用来持久化到 Docker 数据文件夹，因为 Spring Boot 使用的内嵌 Tomcat 容器默认使用/tmp作为工作目录
VOLUME /tmp
# 增加bash便于交互
RUN apk add bash
ADD target/app-web-service-0.0.1-SNAPSHOT.jar app-web-service-0.0.1-SNAPSHOT.jar
# 声明暴露的端口，-P默认映射时使用该端口
EXPOSE 8081
# 移除安装包，减小镜像体积
RUN apk del bash
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app-web-service-0.0.1-SNAPSHOT.jar"]