package com.commandApi;

import com.commandApi.model.Order;
import com.commandApi.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setClientsId("1,2");
        order.setProductsId("3,4");
        order.setCreationDate(LocalDate.now());
        order.setUpdateDate(LocalDate.now());
        order = orderRepository.save(order);
    }

    @Test
    public void testSaveOrder() {
        Order newOrder = new Order();
        newOrder.setClientsId("5,6");
        newOrder.setProductsId("7,8");
        newOrder.setCreationDate(LocalDate.now());
        newOrder.setUpdateDate(LocalDate.now());

        Order savedOrder = orderRepository.save(newOrder);
        assertNotNull(savedOrder.getId());
        assertEquals("5,6", savedOrder.getClientsId());
        assertEquals("7,8", savedOrder.getProductsId());
    }

    @Test
    public void testFindById() {
        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals(order.getClientsId(), foundOrder.get().getClientsId());
        assertEquals(order.getProductsId(), foundOrder.get().getProductsId());
    }

    @Test
    public void testUpdateOrder() {
        order.setProductsId("9,10");
        Order updatedOrder = orderRepository.save(order);

        assertEquals("9,10", updatedOrder.getProductsId());
    }

    @Test
    public void testDeleteOrder() {
        orderRepository.deleteById(order.getId());
        Optional<Order> deletedOrder = orderRepository.findById(order.getId());
        assertFalse(deletedOrder.isPresent());
    }

    @Test
    public void testDeleteOrderNotFound() {
        Long nonExistingId = 999L;
        assertThrows(EmptyResultDataAccessException.class, () -> orderRepository.deleteById(nonExistingId));
    }

    @Test
    public void testFindAllOrders() {
        Iterable<Order> orders = orderRepository.findAll();
        assertTrue(orders.iterator().hasNext());
    }
}
