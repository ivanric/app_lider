#imagen modelo
FROM eclipse-temurin:11.0.24_8-jdk
#argumentos
ARG JAR_FILE=target/LIDER.jar
#copia del jar
COPY ${JAR_FILE} LIDER.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","LIDER.jar"]