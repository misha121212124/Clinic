package com.inspirit.clinic.service;

import com.inspirit.clinic.VO.PatientVo;
import com.inspirit.clinic.VO.PatientClientVo;
import com.inspirit.clinic.dto.CreateOrderDto;
import com.inspirit.clinic.dto.OrderDto;
import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import com.inspirit.clinic.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

//    private MockMvc mvc;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PatientClientVo patientClient;

    private String token = "some token";

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
//        orderService = new OrderService(orderRepository, patientClient);
//        JacksonTester.initFields(this, new ObjectMapper());
//        mvc = MockMvcBuilders.standaloneSetup(orderService)
//                .build();
    }

    @Test
    void saveOrder() {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setOrderComment("test order");
        createOrderDto.setMasterPatientIdentifier("patient id");

        when(orderRepository.save(any(Order.class))).thenReturn(new Order().setOrderComment("test order"));
        when(patientClient.getPatientById("patient id",token)).thenReturn(new ResponseEntity<>(
                new PatientVo().setMasterPatientIdentifier("patient id"), HttpStatus.OK));
        Order createdOrder = orderService.saveOrder(createOrderDto,token);
        assertSame(createOrderDto.getOrderComment(), createdOrder.getOrderComment());
    }

    @Test
    void testSaveOrder_Ok() {
        Order createOrder = new Order().setOrderComment("test order").setId(new OrderId().setOrderId("order id")
                .setMasterPatientIdentifier("patient id"));

        when(orderRepository.save(any(Order.class))).thenReturn(new Order().setOrderComment("test order"));
        when(patientClient.getPatientById("patient id",token)).thenReturn(new ResponseEntity<>(
                new PatientVo().setMasterPatientIdentifier("patient id"), HttpStatus.OK));
        Order createdOrder = orderService.saveOrder(createOrder,token);
        assertSame(createOrder.getOrderComment(), createdOrder.getOrderComment());
    }

    @Test
    void testSaveOrder_Fault() {
        Order createOrder = new Order().setOrderComment("test order").setId(new OrderId().setOrderId("order id")
                .setMasterPatientIdentifier("patient id"));

        when(patientClient.getPatientById("patient id",token)).thenReturn(new ResponseEntity<>(
                null, HttpStatus.OK));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.saveOrder(createOrder,token);
        });

        String expectedMessage = "There no patient with specified id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(new Order(), new Order(), new Order())));
        List<Order> orders = orderService.getAllOrders();
        assertEquals(3, orders.size());
    }

    @Test
    void findById_Ok() {
        when(orderRepository.findById(any(OrderId.class)))
                .thenReturn(Optional.of(new Order().setId(new OrderId().setOrderId("order id")
                        .setMasterPatientIdentifier("patient id"))));

        Optional<Order> order = orderService.findById(new OrderId().setOrderId("order id")
                .setMasterPatientIdentifier("patient id"));

        assertEquals("order id", order.get().getId().getOrderId());
    }

    @Test
    void findById_Fault() {
        when(orderRepository.findById(any(OrderId.class)))
                .thenReturn(Optional.empty());

        Optional<Order> order = orderService.findById(new OrderId().setOrderId("order id").setMasterPatientIdentifier("patient id"));

        assertFalse(order.isPresent());
    }

    @Test
    void getOrderWithPatient_OK() {
        when(orderRepository.findById(any(OrderId.class))).thenReturn(Optional.of(new Order().setId(
                new OrderId().setOrderId("order id").setMasterPatientIdentifier("patient id"))));
        when(patientClient.getPatientById("patient id",token)).thenReturn(new ResponseEntity<>(
                        new PatientVo().setMasterPatientIdentifier("patient id"), HttpStatus.OK));

        OrderDto orderDto = orderService.getOrderWithPatient(new OrderId()
                .setOrderId("order id").setMasterPatientIdentifier("patient id"),token);
        assertEquals("patient id",orderDto.getPatient().getMasterPatientIdentifier());
    }

    @Test
    void getOrderWithPatient_NoOrder() {
        when(orderRepository.findById(any(OrderId.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderWithPatient(new OrderId()
                    .setOrderId("order id").setMasterPatientIdentifier("patient id"),token);
        });

        String expectedMessage = "Order was not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

//    @Test
//    void getAllOrdersByMaster_patient_identifierAndActive() {
//        when(orderRepository.findAllById_MasterPatientIdentifierAndState(any(String.class), any(OrderState.class)))
//                .thenReturn(new ArrayList<>(Arrays.asList(new Order(), new Order(), new Order())));
//        List<Order> orders = orderService.getAllOrdersByMaster_patient_identifierAndActive("patient id");
//
//        assertEquals(3, orders.size());
//    }

//    @Test
//    void getAllOrdersByActive() {
//        when(orderRepository.findAllByState(OrderState.ACTIVE))
//                .thenReturn(new ArrayList<>(Arrays.asList(new Order(), new Order(), new Order())));
//        List<Order> orders = orderService.getAllOrdersByActive();
//
//        assertEquals(3, orders.size());
//
//    }
}