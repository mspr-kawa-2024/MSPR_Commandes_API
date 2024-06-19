package com.commandApi.controller;

import com.commandApi.model.Order;
import com.commandApi.service.OrderService;
import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@RestController
@RequestMapping(path = "api/v1/command")
@CrossOrigin(origins = "http://localhost:3001", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class OrderController {

    private final OrderService orderService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getCommands() {
        return orderService.getCommands();
    }

    @PostMapping
    public void registerNewCommand(@RequestBody Order order) {
        orderService.addNewCommand(order);
    }

    @PutMapping("/orderId/{id}/clientIds/{clientTds}")
    public ResponseEntity<String> associateCommandToSpecificClients(@PathVariable String id, @PathVariable String clientTds) {
        if (!verifierSiIds(clientTds)) {
            return ResponseEntity.ok("you should put only integers for ids");
        }
        Long commandId = Long.parseLong(id);
        Order order = orderService.getCommandById(commandId);
        if (order == null) {
            return ResponseEntity.ok("This order does not exist");
        } else {
            rabbitMQSender.sendClientIdsToClient(clientTds);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }

            String response = rabbitMQReceiver.getReceivedMessageForClientIds();
            if ("ok".equals(response)) {
                order.setClientsId(supprimerDoublons(order.getClientsId() + "," + clientTds));
                order = orderService.saveCommand(order);

                return ResponseEntity.ok("Clients white ids " + clientTds + " associated successfully in the order Id " + commandId.toString());
            }
            return ResponseEntity.ok("A client that you set does not exist");
        }
    }

    @PutMapping("/orderId/{id}/productIds/{productsTds}")
    public ResponseEntity<String> associateProductsToTheCommand(@PathVariable String id, @PathVariable String productsTds) {
        if (!verifierSiIds(productsTds)) {
            return ResponseEntity.ok("you should put only integers for ids");
        }
        Long commandId = Long.parseLong(id);
        Order order = orderService.getCommandById(commandId);
        if (order == null) {
            return ResponseEntity.ok("This order does not exist");
        } else {
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
                order.setProductsId(supprimerDoublons(order.getProductsId() + "," + productsTds));
                order = orderService.saveCommand(order);

                return ResponseEntity.ok("Products white ids " + productsTds + " associated successfully in the Order Id " + id);
            }
            return ResponseEntity.ok("A product that you set does not exist");
        }
    }

    private static String supprimerDoublons(String input) {
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
    public void updateCommand(@PathVariable("commandId") Long commandId, @RequestParam(required = false) String productsId) {
        orderService.updateCommand(commandId, productsId);
    }

    @DeleteMapping(path = "{commandId}")
    public void deleteCommand(@PathVariable("commandId") Long commandId) {
        orderService.deleteCommand(commandId);
    }
}
