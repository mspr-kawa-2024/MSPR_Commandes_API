package com.commandApi.service;

/*
import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import com.commandApi.model.Command;
import com.commandApi.repository.CommandRepository;
import com.commandApi.service.CommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommandServiceIntegrationTest {

    @Autowired
    private CommandService commandService;

    @MockBean
    private CommandRepository commandRepository;

    @MockBean
    private RabbitMQSender rabbitMQSender;

    @MockBean
    private RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHandleOrderRequest() throws Exception {
        // Given
        String mockOrderIds = "1,123";
        Command mockCommand = new Command();
        mockCommand.setId(123L);
        mockCommand.setClientsId("1,2,3");
        mockCommand.setProductsId("456,789");

        when(commandRepository.findById(123L)).thenReturn(Optional.of(mockCommand));
        when(rabbitMQReceiver.getReceivedMessage()).thenReturn("ok");

        // When
        commandService.handleOrderRequest(mockOrderIds);

        // Then
        verify(commandRepository, times(1)).findById(123L);
        verify(rabbitMQSender, times(1)).sendProductsIdToProduct("456,789");
        verify(rabbitMQSender, times(1)).sendOrderToClient("ok");
    }
}*/
