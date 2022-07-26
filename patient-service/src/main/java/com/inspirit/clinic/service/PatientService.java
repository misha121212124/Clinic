package com.inspirit.clinic.service;

import com.inspirit.clinic.VO.*;
import com.inspirit.clinic.dto.CreatePatientDto;
import com.inspirit.clinic.dto.PatientDto;
import com.inspirit.clinic.enums.PatientState;
import com.inspirit.clinic.model.Patient;
import com.inspirit.clinic.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private OrderClientVo orderClient;

    @Autowired
    private AuthClientVo authClient;

    public UserVo checkUser(){

        return null;
    }

    public Patient savePatient(CreatePatientDto createPatientDto) {
        return savePatient(createPatientDto.toPatient());
    }
    public Patient savePatient(Patient patient) {
        log.info("In patient_service save new patient");
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        log.info("In patient_service get all patients");
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(String id) {
        log.info("In patient_service get by id");
        return patientRepository.findById(id);
    }


    public void deleteById(String id) {
        log.info("In patient_service delete by id");
        patientRepository.deleteById(id);
    }

    public List<PatientDto> getAllPatientsWithActiveOrders(String token) {
        log.info("In patient_service get all patients with active orders");

        List<GroupedOrdersVo> orderList = orderClient.findAllActiveOrder(token).getBody();

        if (orderList == null) return new ArrayList<>();

        Map<String, List<OrderVo>> map = orderList.stream()
                .collect(Collectors.toMap(GroupedOrdersVo::get_id, GroupedOrdersVo::getOrders));


        return getAllPatients().stream().map(patient ->new PatientDto(patient)
                .setOrderList(map.get(patient.getMasterPatientIdentifier())))
                .collect(Collectors.toList());
    }

    public Patient deactivateById(Patient patient) {
        return savePatient(patient.setState(PatientState.INACTIVE));
    }
}

