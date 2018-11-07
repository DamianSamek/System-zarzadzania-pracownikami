FROM openjdk:8
ADD project.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar", "app.jar"]