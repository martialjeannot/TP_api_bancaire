# On utilise une image qui contient déjà Java 17
FROM eclipse-temurin:17-jdk-jammy

# On se place dans un dossier de travail
WORKDIR /app

# On copie tous les fichiers du projet dans le conteneur
COPY . .

# On donne la permission d'exécuter le constructeur Gradle
RUN chmod +x gradlew

# On construit le fichier .jar de l'application
RUN ./gradlew bootJar

# On expose le port 8080 (celui de ton API)
EXPOSE 8080

# On lance l'application
ENTRYPOINT ["sh", "-c", "java -jar build/libs/*.jar"]