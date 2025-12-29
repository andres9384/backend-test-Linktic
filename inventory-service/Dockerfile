# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /build/target/*jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
