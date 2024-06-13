package com.commandApi;

import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

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


    public void addNewCommand(Command command) {
        commandRepository.save(command);
    }

    public Command saveCommand(Command command) {
        return commandRepository.save(command);
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

    @RabbitListener(queues = "orderQueue")
    public void handleOrderRequest(String ids) {

        Long orderIdReceivedLongFormat = extractOrderId(ids);
        Optional<Command> optionalCommand = commandRepository.findById(orderIdReceivedLongFormat);

        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();

            if (!verificationIfClientHaveTheOrder(command.getClientsId(), extractClientId(ids))) {
                rabbitMQSender.sendOrderToClient("This client doesn't have that order");
            } else {
                sendProductsId(command.getProductsId());

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread was interrupted", e);
                }

                try {
                    String commandJson = objectMapper.writeValueAsString(command);
                    rabbitMQSender.sendOrderToClient(handlProductsToSend());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error converting command to JSON", e);
                }
            }
        } else {
            rabbitMQSender.sendOrderToClient("Command with id " + orderIdReceivedLongFormat + " does not exist");
        }
    }

    private void sendProductsId(String productsId){
        rabbitMQSender.sendProductsIdToProduct(productsId);
    }

    private String handlProductsToSend(){
        return rabbitMQReceiver.getReceivedMessage();
    }

    public static Long extractOrderId(String str) {
        String[] ids = str.split(",");
        Long orderId = Long.parseLong(ids[1].trim());
        return orderId;
    }

    public static String extractClientId(String str) {
        String[] ids = str.split(",");
        return ids[0];
    }

    public boolean verificationIfClientHaveTheOrder(String clientsId, String clientId){
        String[] ids = clientsId.split(",");
        for (String id : ids) {
            if (id.trim().equals(clientId.trim())) {
                return true;
            }
        }
        return false;
    }
}
