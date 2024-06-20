package com.commandApi;

import com.commandApi.model.Command;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {



    @Test
    void testToString() {

        Command command = new Command();
        command.setId(1L);
        command.setName("John");
        LocalDate currentDate = LocalDate.now();
        LocalDate updateDate = LocalDate.now();
        command.setClientsId("1,2,3");
        command.setProductsId("4,5,6");

        command.setCreationDate(currentDate);
        command.setUpdateDate(updateDate);

        // Création de l'objet Client avec les valeurs attendues
        Command expectedCommand = new Command(1L, "John", currentDate, updateDate, "1,2,3", "4,5,6");

        // Assert
        assertEquals(expectedCommand.toString(), command.toString());
    }
}
