package com.inspirit.clinic.service;

import com.inspirit.clinic.VO.GroupedOrdersVo;
import com.inspirit.clinic.VO.OrderVo;
import com.inspirit.clinic.VO.OrderClientVo;
import com.inspirit.clinic.VO.OrderIdVo;
import com.inspirit.clinic.dto.PatientDto;
import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.Patient;
import com.inspirit.clinic.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    OrderClientVo orderClient;

    @InjectMocks
    PatientService patientService;

    private String token = "some token";

    @BeforeEach
    void setUp() {
    }


    @Test
    void getAllPatientsWithActiveOrders_Ok() {
        when(orderClient.findAllActiveOrder(token)).thenReturn(new ResponseEntity<>(new ArrayList<>(Arrays.asList(
                new GroupedOrdersVo("patient1", new ArrayList<>(Arrays.asList(
                        new OrderVo().setId(new OrderIdVo().setOrderId("order1")
                                .setMasterPatientIdentifier("patient1")).setState(OrderState.ACTIVE),
                        new OrderVo().setId(new OrderIdVo().setOrderId("order2")
                                .setMasterPatientIdentifier("patient1")).setState(OrderState.ACTIVE)))),
                new GroupedOrdersVo("patient2", new ArrayList<>(Arrays.asList(
                        new OrderVo().setId(new OrderIdVo().setOrderId("order3")
                                .setMasterPatientIdentifier("patient2")).setState(OrderState.ACTIVE),
                        new OrderVo().setId(new OrderIdVo().setOrderId("order4")
                                .setMasterPatientIdentifier("patient2")).setState(OrderState.ACTIVE)))),
                        new GroupedOrdersVo("patient3", new ArrayList<>(Arrays.asList(
                                new OrderVo().setId(new OrderIdVo().setOrderId("order5")
                                        .setMasterPatientIdentifier("patient3")).setState(OrderState.ACTIVE))))
        )),HttpStatus.OK));

        when(patientRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new Patient().setMasterPatientIdentifier("patient1"),
                new Patient().setMasterPatientIdentifier("patient2"),
                new Patient().setMasterPatientIdentifier("patient3"),
                new Patient().setMasterPatientIdentifier("patient4")
        )));

        List<PatientDto> patientDtoList = patientService.getAllPatientsWithActiveOrders(token);

        assertEquals(5, patientDtoList.stream().map(patient -> patient.getOrderList().size()).reduce(0, Integer::sum));


    }

    @Test
    void getAllPatientsWithActiveOrders_NoOrders() {
        when(orderClient.findAllActiveOrder(token)).thenReturn(new ResponseEntity<>(
                new ArrayList<>(Collections.emptyList()),HttpStatus.OK));

        when(patientRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new Patient().setMasterPatientIdentifier("patient1"),
                new Patient().setMasterPatientIdentifier("patient2"),
                new Patient().setMasterPatientIdentifier("patient3"),
                new Patient().setMasterPatientIdentifier("patient4")
        )));

        List<PatientDto> patientDtoList = patientService.getAllPatientsWithActiveOrders(token);

        assertEquals(0, patientDtoList.stream().map(patient -> patient.getOrderList().size()).reduce(0, Integer::sum));


    }

}
