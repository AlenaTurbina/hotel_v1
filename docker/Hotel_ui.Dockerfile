FROM openjdk:11

ARG JAR_FILE=hotel_ui/build/libs/hotel_ui-0.0.1-SNAPSHOT.jar

COPY $JAR_FILE hotel_ui.jar

ENTRYPOINT ["java", "-jar", "hotel_ui.jar"]