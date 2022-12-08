package com.hotel_kafka.produser;


import com.hotel_dto.dto.RoomDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@ComponentScan(basePackages = {"com.hotel_server", "com.hotel_commons", "com.hotel_dto"})
public class ProducerSend {

    private NewTopic topic;

    private KafkaTemplate<String, RoomDto> kafkaTemplate;

    public void sendMessage(RoomDto roomDto) {
        // create a message
        Message<RoomDto> message = MessageBuilder
                .withPayload(roomDto)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }

}
