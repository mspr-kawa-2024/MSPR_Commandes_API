package com.commandApi;

import com.commandApi.model.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {



    @Test
    void testToString() {

        Order order = new Order();
        order.setId(1L);

        LocalDate currentDate = LocalDate.now();
        LocalDate updateDate = LocalDate.now();
        order.setClientsId("1,2,3");
        order.setProductsId("4,5,6");

        order.setCreationDate(currentDate);
        order.setUpdateDate(updateDate);

        // Création de l'objet Client avec les valeurs attendues
        Order expectedOrder = new Order(1L, currentDate, updateDate, "1,2,3", "4,5,6");

        // Assert
        assertEquals(expectedOrder.toString(), order.toString());
    }
}
