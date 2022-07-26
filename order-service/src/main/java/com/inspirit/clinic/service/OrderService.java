package com.inspirit.clinic.service;

import com.inspirit.clinic.VO.PatientVo;
import com.inspirit.clinic.VO.PatientClientVo;
//import com.inspirit.clinic.VO.ResponseTemplateVO;
import com.inspirit.clinic.dto.CreateOrderDto;
import com.inspirit.clinic.dto.GroupedOrders;
import com.inspirit.clinic.dto.OrderDto;
import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.exceptions.IncorrectOrderId;
import com.inspirit.clinic.exceptions.IncorrectPatientId;
import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import com.inspirit.clinic.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PatientClientVo patientClient;

    public Order saveOrder(CreateOrderDto createOrderDto,  String token) {
        return saveOrder(createOrderDto.toOrder(),token);
    }
    public Order saveOrder(Order order,  String token) {
        log.info("In order_service save new order");
        PatientVo patient = patientClient.getPatientById(order.getId().getMasterPatientIdentifier(),token).getBody();
        if (patient == null) throw new IncorrectPatientId("There no patient with specified id");
        return orderRepository.save(order);
    }

    public Order declineOrder(Order order) {
        log.info("In order_service decline order");
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        log.info("In order_service get all orders");
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersByMaster_patient_identifier(String masterPatientIdentifier) {
        log.info("In order_service get all orders by masterPatientIdentifier");
        return orderRepository.findAllById_MasterPatientIdentifier(masterPatientIdentifier);
    }

    public Optional<Order> findById(OrderId id) {
        log.info("In order_service get by id");
        return orderRepository.findById(id);
    }


    public void deleteById(OrderId id) {
        log.info("In order_service delete by id");
        orderRepository.deleteById(id);
    }

    public OrderDto getOrderWithPatient(OrderId setOrderId, String token) {
        log.info("In order_service getOrderWithPatient by orderId");
        Optional <Order> order = orderRepository.findById(setOrderId);

        if(!order.isPresent())
            throw new IncorrectOrderId("Order was not found");

        return new OrderDto(order.get()).setPatient(patientClient
                .getPatientById(order.get().getId().getMasterPatientIdentifier(), token).getBody());
    }

    public List<Order> getAllOrdersByMaster_patient_identifierAndActive(String masterPatientIdentifier) {
        log.info("In order_service get all active orders by masterPatientIdentifier");
        return orderRepository.findAllById_MasterPatientIdentifierAndState(masterPatientIdentifier, OrderState.ACTIVE);
    }

    public List<GroupedOrders> findAllActiveOrdersGroupByPatientId() {
        log.info("In order_service get all active orders");
        return orderRepository.findAllActiveOrdersGroupByPatientId();


    }
}
