package com.commandApi.command;

import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A class to set the control to the user recovered from the database
 */
@RestController
@RequestMapping(path = "api/v1/command")
@CrossOrigin(origins = "http://localhost:3001",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE})
public class CommandController {

    private final CommandService commandService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping
    public List<Command> getCommands() {
        return commandService.getCommands();
    }

    @PostMapping
    public void registerNewCommand(@RequestBody Command command) {
        //String clientId = rabbitMQReceiver.receiveClientIdAndOrderId();
        // command.getClientId().toString()


        //commandService.addNewCommand(command, Long.parseLong(clientId));
        rabbitMQSender.sendProductsInOrder(command);
    }

    @PutMapping(path = "{commandId}")
    public void updateCommand(@PathVariable("commandId") Long commandId,
                              @RequestParam(required = false) String name) {
        commandService.updateCommand(commandId, name);
    }

    @DeleteMapping(path = "{commandId}")
    public void deleteCommand(@PathVariable("commandId") Long commandId) {
        commandService.deleteCommand(commandId);
    }

    @GetMapping("/order-with-client")
    public Optional<Command> sendOrderWithClient() {
        String clientIdAndOrderId = (String) rabbitMQReceiver.receiveClientIdAndOrderId();
        return commandService.getProductsByOrderId(clientIdAndOrderId);
    }


}
