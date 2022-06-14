package com.hotel_room_manager.listener;


import com.hotel_room_manager.message.Messages;
import com.hotel_server.service.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
@AllArgsConstructor
@ComponentScan(basePackages = {"com.hotel_server", "com.hotel_activemq", "com.hotel_commons", "com.hotel_dto"})
@Slf4j
public class RoomManagerTopicListener {

    private static final String EMAIL_BOOKING_TEST = Messages.getMessage("server.booking.testEmail");

    private EmailSenderService emailSenderService;

    @JmsListener(destination = "${hotel.activemq.topicName}", subscription = "TopicPrintLogs")
    public void receiveMessageFromTopicPrintLogs(final javax.jms.Message jsonMessage) throws JMSException {
        String messageData;
        if (jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) jsonMessage;
            messageData = textMessage.getText();
            log.info("TextMessage" + messageData);
        }
        log.info("jsonMessage is not instanceof TextMessage");
    }

    @JmsListener(destination = "${hotel.activemq.topicName}", subscription = "TopicEmailSender")
    public void receiveMessageFromTopicSendUser(final javax.jms.Message jsonMessage) throws JMSException {
        String messageData;
        if (jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) jsonMessage;
            messageData = textMessage.getText();
            emailSenderService.sendSimpleEmail(EMAIL_BOOKING_TEST, messageData, "New room");
            log.info("TextMessage" + messageData);
        }
        log.info("jsonMessage is not instanceof TextMessage");
    }
}
