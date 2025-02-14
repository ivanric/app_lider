# Imagen base optimizada de OpenJDK 11
FROM eclipse-temurin:11.0.24_8-jdk

# Argumentos
ARG JAR_FILE=target/LIDER.jar

# Copiar el archivo JAR al contenedor
COPY ${JAR_FILE} app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
