package com.hotel_room_manager.listener;


import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.RoomDto;
import com.hotel_server.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
@ComponentScan(basePackages = {"com.hotel_server", "com.hotel_kafka", "com.hotel_commons", "com.hotel_dto"})
public class RoomManagerSave {

    private RoomService roomService;

//        @KafkaListener(topics = "${hotel.kafka.topicName}", groupId = "${spring.kafka.consumer.group-id}")
    @KafkaListener(topics = "topic_hotel", groupId = "group1")
    public void consumer(RoomDto roomDto) {
        Room room = roomService.saveRoom(roomDto);
        log.info(String.format("Room received " + roomDto.getName()));
    }

//    Test for String messages (use with appropriate kind of serialization in properties)
//    @KafkaListener(topics = "topic_hotel", groupId = "group1")
//    public void consumer(String s) {
//        log.info(String.format("Kafka Listener" + s));
//        System.out.println("from listener Kafka Listener" + s);
//    }

}
