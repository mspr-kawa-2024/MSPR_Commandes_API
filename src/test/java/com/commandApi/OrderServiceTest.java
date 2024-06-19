package com.commandApi;

import com.commandApi.model.Order;
import com.commandApi.repository.OrderRepository;
import com.commandApi.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrderById() {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> foundOrder = Optional.ofNullable(orderService.getCommandById(1L));

        assertTrue(foundOrder.isPresent());
        assertEquals(order.getId(), foundOrder.get().getId());
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.saveCommand(order);

        assertNotNull(savedOrder);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getCommands();

        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateCommand() {
        Long commandId = 1L;
        String newProductsId = "Updated Products Id";

        Order order = new Order();
        order.setId(commandId);
        order.setProductsId("Old Products Id");

        when(orderRepository.findById(commandId)).thenReturn(Optional.of(order));

        orderService.updateCommand(commandId, newProductsId);

        assertEquals(newProductsId, order.getProductsId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testUpdateCommandThrowsExceptionWhenOrderNotFound() {
        Long commandId = 1L;
        String newProductsId = "Updated Products Id";

        when(orderRepository.findById(commandId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            orderService.updateCommand(commandId, newProductsId);
        });

        String expectedMessage = "Command with id " + commandId + " does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testDeleteOrder() {
        orderService.deleteCommand(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
