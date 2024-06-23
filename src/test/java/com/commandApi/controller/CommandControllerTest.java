package com.commandApi.controller;

import com.commandApi.service.CommandService;
import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import com.commandApi.model.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CommandControllerTest {

    @Mock
    private CommandService commandService;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @Mock
    private RabbitMQReceiver rabbitMQReceiver;

    @InjectMocks
    private CommandController commandController;

    private MockMvc mockMvc;

    private Command command;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commandController).build();

        command = new Command();
        command.setId(1L);
        command.setName("Test Command");
        command.setClientsId("1,2,3");
        command.setProductsId("101,102");
    }

    @Test
    void testGetCommands() throws Exception {
        when(commandService.getCommands()).thenReturn(List.of(command));
        mockMvc.perform(get("/api/v1/command"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Command"));
        verify(commandService, times(1)).getCommands();
    }

    @Test
    void testRegisterNewCommand() throws Exception {
        mockMvc.perform(post("/api/v1/command")
                        .contentType("application/json")
                        .content("{\"name\":\"Test Command\",\"clientsId\":\"1,2,3\",\"productsId\":\"101,102\"}"))
                .andExpect(status().isOk());
        verify(commandService, times(1)).addNewCommand(any(Command.class));
    }

    @Test
    void testAssociateCommandToSpecificClientsInvalidIds() throws Exception {
        mockMvc.perform(put("/api/v1/command/orderId/1/clientIds/1,2,abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("you should put only integers for ids"));
        verify(commandService, times(0)).saveCommand(any(Command.class));
    }

    @Test
    void testAssociateProductsToTheCommandInvalidIds() throws Exception {
        mockMvc.perform(put("/api/v1/command/orderId/1/productIds/101,abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("you should put only integers for ids"));
        verify(commandService, times(0)).saveCommand(any(Command.class));
    }

    @Test
    void testUpdateCommand() throws Exception {
        mockMvc.perform(put("/api/v1/command/1?name=Updated Command"))
                .andExpect(status().isOk());
        verify(commandService, times(1)).updateCommand(1L, "Updated Command");
    }

    @Test
    void testDeleteCommand() throws Exception {
        mockMvc.perform(delete("/api/v1/command/1"))
                .andExpect(status().isOk());
        verify(commandService, times(1)).deleteCommand(1L);
    }
}
