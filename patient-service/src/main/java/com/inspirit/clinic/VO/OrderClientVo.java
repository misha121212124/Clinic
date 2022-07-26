package com.inspirit.clinic.VO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("order-service")
public interface OrderClientVo {

    @GetMapping
    public ResponseEntity<List<OrderVo>> getAllOrders(@RequestHeader("Authorization") String bearerToken);

    @GetMapping("/active/{masterPatientIdentifier}")
    public ResponseEntity<List<OrderVo>> findAllActiveOrderByPatient(@PathVariable String masterPatientIdentifier,
                                                                     @RequestHeader("Authorization") String bearerToken);

    @GetMapping("/orders/active")
    public ResponseEntity<List<GroupedOrdersVo>> findAllActiveOrder(@RequestHeader("Authorization") String bearerToken);

    @DeleteMapping("/{masterPatientIdentifier}/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String masterPatientIdentifier, @PathVariable String orderId,
                                            @RequestHeader("Authorization") String bearerToken);

    @GetMapping("/{masterPatientIdentifier}")
    public ResponseEntity<List<OrderVo>> findOrderByPatient(@PathVariable String masterPatientIdentifier,
                                                            @RequestHeader("Authorization") String bearerToken);

    @GetMapping("/{masterPatientIdentifier}/{orderId}")
    public ResponseEntity<OrderDtoVo> getOrderWithPatient(@PathVariable String masterPatientIdentifier,
                                                          @PathVariable String orderId,
                                                          @RequestHeader("Authorization") String bearerToken);
}


