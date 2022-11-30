package com.hotel_room_manager.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_activemq.produser.ProducerSend;
import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.RoomDto;
import com.hotel_server.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
@AllArgsConstructor
@ComponentScan(basePackages = {"com.hotel_server", "com.hotel_activemq", "com.hotel_commons", "com.hotel_dto"})
public class RoomManagerSave {

    private RoomService roomService;
    private ProducerSend roomProducer;

    @JmsListener(destination = "${hotel.activemq.queueName}", subscription = "RoomManager")
    public void receiveMessageFromQueue(final javax.jms.Message jsonMessage) throws JMSException, JsonProcessingException {
        RoomDto roomDTO = null;
        if(jsonMessage instanceof TextMessage) {
            String messageString = ((TextMessage) jsonMessage).getText();
            ObjectMapper mapper = new ObjectMapper();
            roomDTO = mapper.readValue(messageString, RoomDto.class);
        }
        Room room = roomService.saveRoom(roomDTO);

        String messageToTopic = "New room was created: " + room.getName();
        roomProducer.sendToTopic(messageToTopic);
        }
}
