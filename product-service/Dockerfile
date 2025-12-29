# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /build

# Copiamos archivos necesarios para Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Construimos el jar
RUN ./mvnw clean package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos SOLO el jar generado
COPY --from=builder /build/target/*jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
