FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copie des fichiers
COPY . .

# Permissions et Build (on ignore les tests pour le déploiement)
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

# Port de l'API
EXPOSE 8080

# Lancement
ENTRYPOINT ["sh", "-c", "java -jar build/libs/*.jar"]