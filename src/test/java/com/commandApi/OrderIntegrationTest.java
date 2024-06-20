package com.commandApi;

import com.commandApi.model.Order;
import com.commandApi.repository.OrderRepository;
import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
@AutoConfigureMockMvc
public class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private RabbitMQSender rabbitMQSender;

    @MockBean
    private RabbitMQReceiver rabbitMQReceiver;

    @BeforeEach
    public void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    public void testCreateOrder() throws Exception {
        String orderJson = "{\"clientsId\":\"1,2\",\"productsId\":\"3,4\"}";

        mockMvc.perform(post("/api/v1/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk());

        Optional<Order> foundOrder = orderRepository.findAll().stream().findFirst();
        Assertions.assertTrue(foundOrder.isPresent());
        Assertions.assertEquals("1,2", foundOrder.get().getClientsId());
        Assertions.assertEquals("3,4", foundOrder.get().getProductsId());
    }

    @Test
    public void testGetOrders() throws Exception {
        Order order1 = new Order();
        order1.setClientsId("1,2");
        order1.setProductsId("3,4");
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setClientsId("5,6");
        order2.setProductsId("7,8");
        orderRepository.save(order2);

        mockMvc.perform(get("/api/v1/command")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Order order = new Order();
        order.setClientsId("1,2");
        order.setProductsId("3,4");
        order = orderRepository.save(order);

        mockMvc.perform(put("/api/v1/command/" + order.getId())
                        .param("productsId", "3,4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());
        Assertions.assertTrue(updatedOrder.isPresent());
        Assertions.assertEquals("3,4", updatedOrder.get().getProductsId());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        Order order = new Order();
        order.setClientsId("1,2");
        order.setProductsId("3,4");
        order = orderRepository.save(order);

        mockMvc.perform(delete("/api/v1/command/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Order> deletedOrder = orderRepository.findById(order.getId());
        assertFalse(deletedOrder.isPresent());
    }

    @Test
    public void testAssociateCommandToSpecificClients() throws Exception {
        Order order = new Order();
        order.setClientsId("1,2");
        order.setProductsId("3,4");
        order = orderRepository.save(order);

        when(rabbitMQReceiver.getReceivedMessageForClientIds()).thenReturn("ok");

        mockMvc.perform(put("/api/v1/command/orderId/" + order.getId() + "/clientIds/3,4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Clients white ids 3,4 associated successfully in the order Id " + order.getId()));

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());
        Assertions.assertTrue(updatedOrder.isPresent());
        Assertions.assertEquals("1, 2, 3, 4", updatedOrder.get().getClientsId());
    }

    @Test
    public void testAssociateProductsToTheCommand() throws Exception {
        Order order = new Order();
        order.setClientsId("1,2");
        order.setProductsId("3,4");
        order = orderRepository.save(order);

        when(rabbitMQReceiver.getReceivedMessageForProductIds()).thenReturn("ok");

        mockMvc.perform(put("/api/v1/command/orderId/" + order.getId() + "/productIds/5,6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Products white ids 5,6 associated successfully in the Order Id " + order.getId()));

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());
        Assertions.assertTrue(updatedOrder.isPresent());
        Assertions.assertEquals("3, 4, 5, 6", updatedOrder.get().getProductsId());
    }
}
