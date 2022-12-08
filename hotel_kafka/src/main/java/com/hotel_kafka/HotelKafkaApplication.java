package com.hotel_kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
public class HotelKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelKafkaApplication.class, args);
    }

}
