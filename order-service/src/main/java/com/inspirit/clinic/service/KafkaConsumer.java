package com.inspirit.clinic.service;

import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    OrderService orderService;

    @KafkaListener(topics="patient_deactivated", groupId="order_group")
    public void getMessage(String deletedPatientId){
        List<Order> orderList =  orderService.getAllOrdersByMaster_patient_identifier(deletedPatientId);
        for (Order temp: orderList) {
            orderService.declineOrder(temp.setState(OrderState.DECLINED));
            log.info("Order with id:" + temp.getId()+ " was declined");
        }

    }
}
