package com.hotel_database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@EntityScan(basePackages="com.hotel_domain")
@SpringBootApplication
public class HotelDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelDatabaseApplication.class, args);
    }

}
