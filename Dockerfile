FROM adoptopenjdk/openjdk11:alpine-jre-nightly

WORKDIR /app

COPY target/smart_park-system-0.0.1-SNAPSHOT.jar /app/smart_park-system.jar

CMD ["java", "-jar", "smart_park-system.jar"]