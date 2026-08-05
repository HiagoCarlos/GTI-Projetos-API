# ---- Etapa 1: build ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copia primeiro só o necessário para baixar as dependências (aproveita cache do Docker)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Agora copia o código e builda o jar
COPY src src
RUN ./mvnw clean package -DskipTests -B

# ---- Etapa 2: runtime (imagem final, bem mais leve) ----
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
