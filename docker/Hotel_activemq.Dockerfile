FROM openjdk:11

ARG JAR_FILE=hotel_activemq/build/libs/hotel_activemq-0.0.1-SNAPSHOT.jar

COPY $JAR_FILE hotel_activemq.jar

ENTRYPOINT ["java", "-jar", "hotel_activemq.jar"]