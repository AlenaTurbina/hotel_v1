FROM openjdk:11

ARG JAR_FILE=hotel_room_manager/build/libs/hotel_room_manager-0.0.1-SNAPSHOT.jar

COPY $JAR_FILE hotel_room_manager.jar

ENTRYPOINT ["java", "-jar", "hotel_room_manager.jar"]