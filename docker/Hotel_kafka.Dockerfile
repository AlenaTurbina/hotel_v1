FROM openjdk:11

ARG JAR_FILE=hotel_kafka/build/libs/hotel_kafka-0.0.1-SNAPSHOT.jar

COPY $JAR_FILE hotel_kafka.jar

ENTRYPOINT ["java", "-jar", "hotel_kafka.jar"]