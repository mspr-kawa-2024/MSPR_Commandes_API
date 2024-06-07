package com.commandApi.command;

import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommandService {

    private final CommandRepository commandRepository;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public List<Command> getCommands() {
        return commandRepository.findAll();
    }

    public Command getCommandById(Long id) {
        return commandRepository.findById(id).orElse(null);
    }


    public void addNewCommand(Command command, Long clientId) {
        command.setClientId(clientId);
        commandRepository.save(command);
    }

    public void updateCommand(Long commandId, String name) {
        Command command = commandRepository.findById(commandId)
                .orElseThrow(() -> new IllegalStateException("Command with id " + commandId + " does not exist"));

        if (name != null && name.length() > 0 && !name.equals(command.getName())) {
            command.setName(name);
            commandRepository.save(command);
        }
    }

    public void deleteCommand(Long commandId) {
        boolean exists = commandRepository.existsById(commandId);
        if (!exists) {
            throw new IllegalStateException("Command with id " + commandId + " does not exist");
        }
        commandRepository.deleteById(commandId);
    }

    public Map<String, Object> getOrderDetailsById(Long orderId) {
        // Logic to retrieve order details from the database
        // For the sake of example, returning a mock order details
        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("orderId", orderId);
        orderDetails.put("clientId", 1L); // mock clientId
        orderDetails.put("orderDescription", "Sample order description");
        return orderDetails;
    }

    public String sendProducts() {
        return "Message of products";
    }

    @RabbitListener(queues = "orderProductQueue")
    public Optional<Command> getProductsByOrderId(Long orderId) {
        // Logique pour récupérer les produits associés à une commande spécifique
        Optional<Command> products = commandRepository.findById(orderId);
        // Logique pour envoyer les produits via RabbitMQ ou autre moyen de communication
        return products;
    }

    public String commandOfASpecificClient() {
        String orderIdReceived = rabbitMQReceiver.getReceivedMessage();
        Long orderIdReceivedLongFormat = Long.parseLong(orderIdReceived);
        Command command = commandRepository.findById(orderIdReceivedLongFormat)
                .orElseThrow(() -> new IllegalStateException(
                        "Command with id " + orderIdReceivedLongFormat + " does not exist"));

        try {
            String commandJson = objectMapper.writeValueAsString(command);
            rabbitMQSender.sendOrderToClient(commandJson);
            return commandJson;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting command to JSON", e);
        }
    }


    public Optional<Command> getProductsByOrderId(String ids) {
        String[] idArray = ids.split(",");
        if (idArray.length != 2) {
            throw new IllegalArgumentException("Invalid format for client and order IDs: " + ids);
        }

        String a = idArray[0];
        System.out.println("***************************");
        System.out.println(a);

        Long clientId = Long.parseLong(a);
        System.out.println("***************************");
        System.out.println(clientId);
        System.out.println("***************************");
        Long orderId = Long.parseLong(idArray[1]);
        System.out.println("***************************");
        System.out.println(orderId);
        System.out.println("***************************");

        // Logique pour récupérer les produits associés à une commande spécifique
        Optional<Command> products = commandRepository.findById(orderId);
        // Logique pour envoyer les produits via RabbitMQ ou autre moyen de communication
        return products;
    }


}
