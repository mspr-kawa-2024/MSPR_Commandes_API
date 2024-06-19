package com.commandApi.config;

import com.commandApi.model.Order;
import com.commandApi.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class CommandConfig {

    @Bean
    CommandLineRunner commandLineRunner(OrderRepository repository)  {
        // Have access to our repository
        return args -> {
            Order order1 = new Order(
                    1L,
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "1,2",
                    "1,2"
            );

            Order order2 = new Order(
                    2L,
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "1,2",
                    "3,4"
            );

            Order order3 = new Order(
                    3L,
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "1",
                    "5"
            );

            Order order4 = new Order(
                    4L,
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "4",
                    "6,8"
            );

            Order order5 = new Order(
                    5L,
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "4",
                    ""
            );

            // Save Clients into Database
            repository.saveAll(
                    List.of(order1, order2, order3, order4, order5)
            );
        };
    }

    // Configuration de RestTemplate
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
