# Use a base image with ARMv7 architecture
FROM sgal88/backend:1.0



COPY /smart_park-system-0.0.1-SNAPSHOT.jar /app/smart_park-system.jar

CMD ["java", "-jar", "smart_park-system.jar"]