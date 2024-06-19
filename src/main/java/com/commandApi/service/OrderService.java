package com.commandApi.service;

import com.commandApi.config.RabbitMQReceiver;
import com.commandApi.config.RabbitMQSender;
import com.commandApi.model.Order;
import com.commandApi.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    private ObjectMapper objectMapper;
    @Qualifier("productsIdQueue")
    @Autowired
    private Queue productsIdQueue;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getCommands() {
        return orderRepository.findAll();
    }

    public Order getCommandById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void addNewCommand(Order order) {
        orderRepository.save(order);
    }

    public Order saveCommand(Order order) {
        return orderRepository.save(order);
    }

    public void updateCommand(Long commandId, String productsId) {
        Order order = orderRepository.findById(commandId)
                .orElseThrow(() -> new IllegalStateException("Command with id " + commandId + " does not exist"));

        if (productsId != null && !productsId.isEmpty() && !productsId.equals(order.getProductsId())) {
            order.setProductsId(productsId);
            orderRepository.save(order);
        }
    }

    public void deleteCommand(Long commandId) {
        boolean exists = orderRepository.existsById(commandId);
        if (!exists) {
            throw new IllegalStateException("Command with id " + commandId + " does not exist");
        }
        orderRepository.deleteById(commandId);
    }

    @RabbitListener(queues = "orderQueue")
    public void handleOrderRequest(String ids) {

        Long orderIdReceivedLongFormat = extractOrderId(ids);
        Optional<Order> optionalCommand = orderRepository.findById(orderIdReceivedLongFormat);

        if (optionalCommand.isPresent()) {
            Order order = optionalCommand.get();

            if (!verificationIfClientHaveTheOrder(order.getClientsId(), extractClientId(ids))) {
                rabbitMQSender.sendOrderToClient("This client doesn't have that order");
            } else {
                sendProductsId(order.getProductsId());

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread was interrupted", e);
                }

                try {
                    String commandJson = objectMapper.writeValueAsString(order);
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
