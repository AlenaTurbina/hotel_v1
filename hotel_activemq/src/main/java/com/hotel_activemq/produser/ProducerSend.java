package com.hotel_activemq.produser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_activemq.message.Messages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
@AllArgsConstructor
@Slf4j
public class ProducerSend {

    private static final String QUEUE = Messages.getMessage("hotel.activemq.queueName");
    private static final String TOPIC = Messages.getMessage("hotel.activemq.topicName");

    private JmsTemplate jmsTemplate;

    public void sendToQueue(Object obj) {
        try {
            String jsonObj = new ObjectMapper().writer().writeValueAsString(obj);
            jmsTemplate.send(QUEUE, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });
        }
        catch (Exception ex) {
            log.error("ERROR in sending message to queue", ex.getMessage());
        }
    }

    public void sendToTopic(Object obj) {
        try {
            String jsonObj = new ObjectMapper().writer().writeValueAsString(obj);
            jmsTemplate.send(TOPIC, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });
        }
        catch (Exception ex) {
            log.error("ERROR in sending message to topic", ex.getMessage());
        }
    }

}
