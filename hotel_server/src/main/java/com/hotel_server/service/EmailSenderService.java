package com.hotel_server.service;

import com.hotel_domain.model.entity.OrderBooking;

import javax.mail.MessagingException;

public interface EmailSenderService {
    void sendSimpleEmail(String toEmail, String body, String subject);

    void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException;

    void sendHtmlEmailInvoice(String toEmail, String subject, OrderBooking orderBooking, String template) throws MessagingException;

}

