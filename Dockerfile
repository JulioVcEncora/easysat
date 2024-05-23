FROM openjdk:latest
# Copy the Java truststore from your local machine into the Docker image
COPY ./certs/new-choice-root.cer /etc/ssl/certs/java/new-choice-root.cer
RUN  keytool -importcert -keystore "/usr/local/.keystore" -alias caroot -file /etc/ssl/certs/java/new-choice-root.cer -noprompt -storepass changeit
EXPOSE 8081
EXPOSE 6000
RUN mkdir /opt/application
COPY target/easysat-0.0.1-SNAPSHOT.jar /opt/application
CMD ["java", "-jar", "/opt/application/easysat-0.0.1-SNAPSHOT.jar"]