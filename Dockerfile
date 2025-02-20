# Imagen base optimizada de OpenJDK 11
#FROM eclipse-temurin:11.0.24_8-jdk

# Argumentos
#ARG JAR_FILE=target/LIDER.jar

# Copiar el archivo JAR al contenedor
#COPY ${JAR_FILE} app.jar

# Exponer el puerto 8080
#EXPOSE 8080

# Comando para ejecutar la aplicación
#ENTRYPOINT ["java", "-jar", "app.jar"]


# Imagen base optimizada de OpenJDK 11
FROM eclipse-temurin:11.0.24_8-jdk

# Crear directorio para las fuentes
RUN mkdir -p /app/fonts

# Copiar las fuentes al contenedor
COPY src/main/resources/fonts /app/fonts


# Argumento para el JAR de la aplicación
ARG JAR_FILE=target/LIDER.jar

# Copiar el archivo JAR al contenedor
COPY ${JAR_FILE} app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
