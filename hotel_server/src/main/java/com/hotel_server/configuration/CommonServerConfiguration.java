package com.hotel_server.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.hotel_database"})
@EntityScan(basePackages = {"com.hotel_domain"})
@ComponentScan(basePackages = {"com.hotel_dto"})
@EnableTransactionManagement
public class CommonServerConfiguration {

}
