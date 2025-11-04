# Etapa 1: Build com Maven e JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY ./src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final leve com JDK 21
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Cria grupo e usuário (comandos para Debian-based)
RUN groupadd appgroup && useradd -r -g appgroup appuser

COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup app.jar

ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3307/gestao_estabelecimento?useSSL=false&serverTimezone=America/Sao_Paulo
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=senha123
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV SERVER_PORT=8081

EXPOSE 8081

USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]

# Healthcheck opcional (requere actuator)
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s CMD curl -f http://localhost:8081/actuator/health || exit 1
