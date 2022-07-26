package com.inspirit.clinic.controller;

import com.inspirit.clinic.VO.UserVo;
import com.inspirit.clinic.dto.CreatePatientDto;
import com.inspirit.clinic.dto.PatientDto;
import com.inspirit.clinic.enums.PatientState;
import com.inspirit.clinic.model.Patient;
import com.inspirit.clinic.exceptions.IncorrectPatientId;
import com.inspirit.clinic.service.KafkaProducer;
import com.inspirit.clinic.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
@Slf4j
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private KafkaProducer producer;


    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody CreatePatientDto createPatientDto,
                                               @RequestHeader("Authorization") String bearerToken){
        log.info("In patient_controller save new patient");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.AUTHORIZATION,bearerToken);
        return new ResponseEntity<> (patientService.savePatient(createPatientDto), responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<UserVo> checkUser(@RequestHeader("Authorization") String bearerToken){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body((UserVo)authentication.getPrincipal());
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(@RequestHeader("Authorization") String bearerToken){
        log.info("In patient_controller get all patients");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken).body(patientService.getAllPatients());
    }

    @GetMapping("/active_orders")
    public ResponseEntity<List<PatientDto>> getAllPatientsWithActiveOrders(
            @RequestHeader("Authorization") String bearerToken){
        log.info("In patient_controller get all patients with active orders");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(patientService.getAllPatientsWithActiveOrders(bearerToken));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id,
                                                  @RequestHeader("Authorization") String bearerToken){
        log.info("In patient_controller get patient by id");
        Optional<Patient> optionalPatient = patientService.findById(id);
        Patient patient = optionalPatient.orElseThrow(IncorrectPatientId::new);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken).body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id,
                                                 @RequestBody CreatePatientDto createPatientDto,
                                                 @RequestHeader("Authorization") String bearerToken){
        log.info("In patient_controller update patient");
        Optional<Patient> optionalPatient = patientService.findById(id);

        if (!optionalPatient.isPresent()) {
            throw new IncorrectPatientId("There is no Patient with such id. Try to save them instead of update");
        }

        Patient patientToUpdate = optionalPatient.get()
                .setFirstName(createPatientDto.getFirstName())
                .setLastName(createPatientDto.getLastName())
                .setState(createPatientDto.getState());

        Patient patientUpdated =  patientService.savePatient(patientToUpdate);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken).body(patientUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable String id,
                                                 @RequestHeader("Authorization") String bearerToken){
        log.info("In patient_controller delete patient");
        Optional<Patient> patient = patientService.findById(id);
        if (!patient.isPresent()) throw new IncorrectPatientId("Patient wasn't found on deactivate");
        if (patient.get().getState() == PatientState.INACTIVE) return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        Patient patientToDeactivate = patientService.deactivateById(patient.get());
        this.producer.writeMessage(id);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken).body(patientToDeactivate);
    }

}
