package com.commandApi.service;

import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import com.commandApi.model.Command;
import com.commandApi.repository.CommandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandServiceTest {

    @Mock
    private CommandRepository commandRepository;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @Mock
    private RabbitMQReceiver rabbitMQReceiver;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CommandService commandService;

    private Command command;



    @BeforeEach
    void setUp() {
        command = new Command();
        command.setId(1L);
        command.setName("Test Command");
        command.setClientsId("1,2,3");  // Définissez une valeur pour clientsId
        command.setProductsId("101,102");
    }

    @Test
    void testGetCommands() {
        when(commandRepository.findAll()).thenReturn(List.of(command));
        List<Command> commands = commandService.getCommands();
        assertEquals(1, commands.size());
        verify(commandRepository, times(1)).findAll();
    }

    @Test
    void testGetCommandById() {
        when(commandRepository.findById(1L)).thenReturn(Optional.of(command));
        Command foundCommand = commandService.getCommandById(1L);
        assertNotNull(foundCommand);
        assertEquals("Test Command", foundCommand.getName());
        verify(commandRepository, times(1)).findById(1L);
    }

    @Test
    void testAddNewCommand() {
        commandService.addNewCommand(command);
        verify(commandRepository, times(1)).save(command);
    }

    @Test
    void testSaveCommand() {
        when(commandRepository.save(command)).thenReturn(command);
        Command savedCommand = commandService.saveCommand(command);
        assertNotNull(savedCommand);
        assertEquals(command, savedCommand);
        verify(commandRepository, times(1)).save(command);
    }

    @Test
    void testUpdateCommand() {
        when(commandRepository.findById(1L)).thenReturn(Optional.of(command));
        commandService.updateCommand(1L, "Updated Command");
        verify(commandRepository, times(1)).save(command);
        assertEquals("Updated Command", command.getName());
    }

    @Test
    void testUpdateCommandThrowsExceptionWhenCommandNotFound() {
        when(commandRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            commandService.updateCommand(1L, "Updated Command");
        });
        assertEquals("Command with id 1 does not exist", exception.getMessage());
    }

    @Test
    void testDeleteCommand() {
        when(commandRepository.existsById(1L)).thenReturn(true);
        commandService.deleteCommand(1L);
        verify(commandRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCommandThrowsExceptionWhenCommandNotFound() {
        when(commandRepository.existsById(1L)).thenReturn(false);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            commandService.deleteCommand(1L);
        });
        assertEquals("Command with id 1 does not exist", exception.getMessage());
    }

    @Test
    void testExtractOrderId() {
        Long orderId = CommandService.extractOrderId("1,2");
        assertEquals(2L, orderId);
    }

    @Test
    void testExtractClientId() {
        String clientId = CommandService.extractClientId("1,2");
        assertEquals("1", clientId);
    }

    @Test
    void testVerificationIfClientHaveTheOrder() {
        boolean result = commandService.verificationIfClientHaveTheOrder("1,2,3", "2");
        assertTrue(result);
    }

    @Test
    void testVerificationIfClientHaveTheOrderFalse() {
        boolean result = commandService.verificationIfClientHaveTheOrder("1,2,3", "4");
        assertFalse(result);
    }
}
