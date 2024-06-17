package com.commandApi.config;

import com.commandApi.Command;
import com.commandApi.CommandRepository;
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
    CommandLineRunner commandLineRunner(CommandRepository repository)  {
        // Have access to our repository
        return args -> {
            Command command1 = new Command(
                    "command1",
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "1,2",
                    "1,2"
            );

            Command command2 = new Command(
                    "command2",
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "1,2",
                    "3,4"
            );

            Command command3 = new Command(
                    "command3",
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "1",
                    "5"
            );

            Command command4 = new Command(
                    "command4",
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "4",
                    "6,8"
            );

            Command command5 = new Command(
                    "command5",
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31),
                    "4",
                    ""
            );

            // Save Clients into Database
            repository.saveAll(
                    List.of(command1, command2, command3, command4, command5)
            );
        };
    }

    // Configuration de RestTemplate
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
