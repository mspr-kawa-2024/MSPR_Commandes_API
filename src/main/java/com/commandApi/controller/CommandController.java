package com.commandApi.controller;

import com.commandApi.service.CommandService;
import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import com.commandApi.model.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

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
        commandService.addNewCommand(command);
    }

    @PutMapping("/orderId/{id}/clientIds/{clientTds}")
    public ResponseEntity<String> associateCommandToSpecificClients(@PathVariable String id,
                                                                    @PathVariable String clientTds){
        if (!verifierSiIds(clientTds)){
            return ResponseEntity.ok("you should put only integers for ids");
        }
        Long commandId = Long.parseLong(id);
        Command command = commandService.getCommandById(commandId);
        if (command == null) {
            return ResponseEntity.ok("This order does not exist");
        }else {
            rabbitMQSender.sendClientIdsToClient(clientTds);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }

            String response = rabbitMQReceiver.getReceivedMessageForClientIds();
            if ("ok".equals(response)) {
                command.setClientsId(supprimerDoublons(command.getClientsId() + "," + clientTds));
                //save the order in the bdd
                command = commandService.saveCommand(command);

                return ResponseEntity.ok("Clients white ids " + clientTds
                        + " associated successfly in the order Id " + commandId.toString());
            }
            return ResponseEntity.ok("A client that you set does not exist");
        }
    }

    @PutMapping("/orderId/{id}/productIds/{productsTds}")
    public ResponseEntity<String> associateProductsToTheCommand(@PathVariable String id,
                                                                @PathVariable String productsTds){
        if (!verifierSiIds(productsTds)){
            return ResponseEntity.ok("you should put only integers for ids");
        }
        Long commandId = Long.parseLong(id);
        Command command = commandService.getCommandById(commandId);
        if (command == null) {
            return ResponseEntity.ok("This order does not exist");
        }else {
            rabbitMQSender.sendProductIdsToProduct(productsTds);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }

            String response = rabbitMQReceiver.getReceivedMessageForProductIds();
            System.out.println(response);
            if ("ok".equals(response)) {
                command.setProductsId(supprimerDoublons(command.getProductsId() + "," + productsTds));
                //save the order in the bdd
                command = commandService.saveCommand(command);

                return ResponseEntity.ok("Products white ids " + productsTds
                        + " associated successfly in the Order Id " + id);
            }
            return ResponseEntity.ok("A product that you set does not exist");
        }
    }

    public static String supprimerDoublons(String input) {
        String[] elements = input.split(",");
        Set<String> uniques = new LinkedHashSet<>();
        for (String element : elements) {
            element = element.trim();
            uniques.add(element);
        }
        StringJoiner joiner = new StringJoiner(", ");
        for (String element : uniques) {
            joiner.add(element);
        }
        return joiner.toString();
    }

    private static boolean verifierSiIds(String input) {
        String[] elements = input.split(",");
        for (String element : elements) {
            element = element.trim();
            try {
                Integer.parseInt(element);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
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

}
