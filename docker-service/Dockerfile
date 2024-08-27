# Gunakan image dasar dengan OpenJDK 11
FROM openjdk:11-jre-slim

# Tentukan direktori kerja
WORKDIR /app

# Salin file JAR ke dalam container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Tentukan command untuk menjalankan JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
