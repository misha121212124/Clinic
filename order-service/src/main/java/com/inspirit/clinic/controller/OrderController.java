package com.inspirit.clinic.controller;

//import com.inspirit.clinic.VO.ResponseTemplateVO;
import com.inspirit.clinic.dto.CreateOrderDto;
import com.inspirit.clinic.dto.OrderDto;
import com.inspirit.clinic.exceptions.IncorrectOrderId;
import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import com.inspirit.clinic.dto.GroupedOrders;
import com.inspirit.clinic.repository.OrderRepository;
import com.inspirit.clinic.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
//    @Autowired
//    private OrderRepositoryImpl orderRepository;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDto createOrderDto,
                                             @RequestHeader("Authorization") String bearerToken) {
        log.info("In order_controller save new Order");
        Order orderCreated = orderService.saveOrder(createOrderDto.toOrder(),bearerToken);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.AUTHORIZATION,bearerToken);
        return new ResponseEntity<>(orderCreated, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Authorization") String bearerToken){
        log.info("In order_controller get all Orders");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(orderService.getAllOrders());
    }

    @GetMapping("/active/{masterPatientIdentifier}")
    public ResponseEntity<List<Order>> findAllActiveOrderByPatient(@PathVariable String masterPatientIdentifier,
                                                                   @RequestHeader("Authorization") String bearerToken) {
        log.info("In order_controller get all active Orders by masterPatientIdentifier");

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(orderService.getAllOrdersByMaster_patient_identifierAndActive(masterPatientIdentifier));
    }

    @GetMapping("/active")
    public ResponseEntity<List<GroupedOrders>> findAllActiveOrder(@RequestHeader("Authorization") String bearerToken) {
        log.info("In order_controller get all active Orders");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(orderService.findAllActiveOrdersGroupByPatientId());
    }

    @PutMapping("/{masterPatientIdentifier}/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String masterPatientIdentifier,
                                             @PathVariable String orderId,
                                             @RequestBody CreateOrderDto createOrderDto,
                                             @RequestHeader("Authorization") String bearerToken) {
        log.info("In order_controller update Order");
        Optional<Order> optionalOrder = orderService.findById(new OrderId()
                .setMasterPatientIdentifier(masterPatientIdentifier).setOrderId(orderId));

        if (!optionalOrder.isPresent()) {
            throw new IncorrectOrderId("There is no Order with such id. Try to save them instead of update");
        }

        Order orderToUpdate = optionalOrder.get()
                .setOrderComment(createOrderDto.getOrderComment())
                .setState(createOrderDto.getState());

        Order orderUpdated =  orderService.saveOrder(orderToUpdate, bearerToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(orderUpdated);
    }

    @DeleteMapping("/{masterPatientIdentifier}/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String masterPatientIdentifier,
                                            @PathVariable String orderId,
                                            @RequestHeader("Authorization") String bearerToken) {
        log.info("In order_controller delete Order");
        orderService.deleteById(new OrderId().setMasterPatientIdentifier(masterPatientIdentifier).setOrderId(orderId));
        return ResponseEntity.noContent().header(HttpHeaders.AUTHORIZATION, bearerToken).build();
    }

    @GetMapping("/{masterPatientIdentifier}")
    public ResponseEntity<List<Order>> findOrderByPatient(@PathVariable String masterPatientIdentifier,
                                                          @RequestHeader("Authorization") String bearerToken) {
        log.info("In order_controller find Order by masterPatientIdentifier");

        return ResponseEntity.ok(orderService.getAllOrdersByMaster_patient_identifier(masterPatientIdentifier));
    }

    @GetMapping("/{masterPatientIdentifier}/{orderId}")
    public ResponseEntity<OrderDto> getOrderWithPatient(@PathVariable String masterPatientIdentifier,
                                                        @PathVariable String orderId,
                                                        @RequestHeader("Authorization") String bearerToken) {
        log.info("In order_controller find Order with Patient");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(orderService.getOrderWithPatient(new OrderId()
                        .setMasterPatientIdentifier(masterPatientIdentifier).setOrderId(orderId), bearerToken));
    }

}
