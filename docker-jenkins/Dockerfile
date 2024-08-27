# Gunakan base image Jenkins LTS dengan dukungan Java 11
FROM jenkins/jenkins:lts-jdk11

# Gunakan user root untuk melakukan instalasi tambahan (jika diperlukan)
USER root

# Install dependencies tambahan jika diperlukan (contoh: git)
RUN apt-get update && apt-get install -y git && apt-get clean

# Set kembali user ke jenkins setelah instalasi
USER jenkins
