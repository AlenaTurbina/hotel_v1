FROM openjdk:11

ARG JAR_FILE=hotel_server/build/libs/hotel_server-0.0.1-SNAPSHOT.jar

COPY $JAR_FILE hotel_server.jar

ENTRYPOINT ["java", "-jar", "hotel_server.jar"]