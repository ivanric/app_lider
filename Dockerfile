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

# Definir opciones de la JVM para aumentar memoria
ENV JAVA_OPTS="-Xms512m -Xmx2g"

# Comando para ejecutar la aplicación
#ENTRYPOINT ["java", "-jar", "app.jar"]
# Comando para ejecutar la aplicación con las opciones de memoria
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
