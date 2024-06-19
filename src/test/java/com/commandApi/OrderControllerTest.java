package com.commandApi;

import com.commandApi.controller.OrderController;
import com.commandApi.model.Order;
import com.commandApi.service.OrderService;
import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @Mock
    private RabbitMQReceiver rabbitMQReceiver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testGetCommands() throws Exception {
        when(orderService.getCommands()).thenReturn(Arrays.asList(new Order(), new Order()));

        mockMvc.perform(get("/api/v1/command")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(orderService, times(1)).getCommands();
    }

    @Test
    public void testRegisterNewCommand() throws Exception {
        doNothing().when(orderService).addNewCommand(any(Order.class));

        mockMvc.perform(post("/api/v1/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientsId\":\"1,2\",\"productsId\":\"3,4\"}"))
                .andExpect(status().isOk());

        verify(orderService, times(1)).addNewCommand(any(Order.class));
    }

    @Test
    public void testUpdateCommand() throws Exception {
        doNothing().when(orderService).updateCommand(anyLong(), anyString());

        mockMvc.perform(put("/api/v1/command/1")
                        .param("productsId", "3,4,5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).updateCommand(1L, "3,4,5");
    }

    @Test
    public void testDeleteCommand() throws Exception {
        doNothing().when(orderService).deleteCommand(anyLong());

        mockMvc.perform(delete("/api/v1/command/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteCommand(1L);
    }

    @Test
    public void testAssociateCommandToSpecificClients() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setClientsId("1,2");
        order.setProductsId("3,4");
        order.setCreationDate(LocalDate.now());
        order.setUpdateDate(LocalDate.now());

        when(orderService.getCommandById(anyLong())).thenReturn(order);
        when(rabbitMQReceiver.getReceivedMessageForClientIds()).thenReturn("ok");

        mockMvc.perform(put("/api/v1/command/orderId/1/clientIds/3,4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Clients white ids 3,4 associated successfully in the order Id 1"));

        verify(orderService, times(1)).saveCommand(any(Order.class));
    }

    @Test
    public void testAssociateProductsToTheCommand() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setClientsId("1,2");
        order.setProductsId("3,4");
        order.setCreationDate(LocalDate.now());
        order.setUpdateDate(LocalDate.now());

        when(orderService.getCommandById(anyLong())).thenReturn(order);
        when(rabbitMQReceiver.getReceivedMessageForProductIds()).thenReturn("ok");

        mockMvc.perform(put("/api/v1/command/orderId/1/productIds/5,6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Products white ids 5,6 associated successfully in the Order Id 1"));

        verify(orderService, times(1)).saveCommand(any(Order.class));
    }
}
