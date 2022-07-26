package com.inspirit.clinic.VO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("patient-service")
public interface PatientClientVo {


//    @PostMapping
//    public ResponseEntity<Patient> savePatient(@RequestBody CreatePatientDto createPatientDto);

    @GetMapping("/patients")
    public ResponseEntity<List<PatientVo>> getAllPatients(@RequestHeader("Authorization") String bearerToken);

//    @GetMapping("/active_orders")
//    public ResponseEntity<List<PatientDto>> getAllPatientsWithActiveOrders();

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientVo> getPatientById(@PathVariable String id,
                                                    @RequestHeader("Authorization") String bearerToken);

//    @PutMapping("/{id}")
//    public ResponseEntity<Patient> updatePatient(@PathVariable String id,
//                                                 @RequestBody CreatePatientDto createPatientDto);

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id,
                                              @RequestHeader("Authorization") String bearerToken);

}



