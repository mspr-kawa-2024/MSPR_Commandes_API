package com.commandApi.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                    LocalDate.of(2024, Month.JANUARY, 31)
            );

            Command command2 = new Command(
                    "command2",
                    LocalDate.of(2023, Month.DECEMBER, 1),
                    LocalDate.of(2024, Month.JANUARY, 31)
            );

            // Save Clients into Database
            repository.saveAll(
                    List.of(command1, command2)
            );
        };
    }

}
