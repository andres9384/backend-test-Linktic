# Imagen base con Java 21
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el jar generado por Maven
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto donde corre la app
EXPOSE 8081

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
