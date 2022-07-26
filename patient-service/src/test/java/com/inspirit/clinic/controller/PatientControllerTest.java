package com.inspirit.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirit.clinic.dto.CreatePatientDto;
import com.inspirit.clinic.model.Patient;
import com.inspirit.clinic.repository.PatientRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @AfterEach
    void clean(){
        patientRepository.deleteAll();
//        Patient patient = new Patient().setMasterPatientIdentifier("for order tests")
//                .setFirstName("for order tests");
//        patientRepository.save(patient);
    }

    @Test
    void savePatient() throws Exception {
        CreatePatientDto patient = new CreatePatientDto();
        patient.setFirstName("in create test");

        this.mockMvc.perform( post("/patients")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("in create test"));
    }

    @Test
    void getAllPatients() throws Exception {
        Patient testPatient = new Patient().setFirstName("for get all 1");
        patientRepository.save(testPatient);
        testPatient = new Patient().setFirstName("for get all 2");
        patientRepository.save(testPatient);

        this.mockMvc.perform(get("/patients")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("for get all 1")))
                .andExpect(content().string(containsString("for get all 2")));
    }

    @Test
    void getAllPatientsWithActiveOrders() throws Exception {
        Patient testPatient = new Patient().setFirstName("for patients with orders 1");
        patientRepository.save(testPatient);
        testPatient = new Patient().setFirstName("for patients with orders 2");
        patientRepository.save(testPatient);

        ResultActions result = this.mockMvc.perform(get("/patients/active_orders"));
        result.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("orderList")));

    }

    @Test
    void getPatientById() throws Exception {
        Patient testPatient = new Patient().setMasterPatientIdentifier("for get by id").setFirstName("for get by id");
        patientRepository.save(testPatient);
        this.mockMvc.perform( get("/patients/for get by id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("for get by id"));
    }

    @Test
    void updatePatient() throws Exception {
        Patient testPatient = new Patient().setMasterPatientIdentifier("for update").setFirstName("for update");
        testPatient = patientRepository.save(testPatient);

        CreatePatientDto createPatientDto = new CreatePatientDto();
        createPatientDto.setFirstName(testPatient.getFirstName()+"updated");

        this.mockMvc.perform( put("/patients/"+testPatient.getMasterPatientIdentifier())
                .content(asJsonString(createPatientDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(testPatient.getFirstName()+"updated"));

    }

    @Test
    void deletePatient() throws Exception {
        Patient testPatient = new Patient().setMasterPatientIdentifier("for delete").setFirstName("for delete");
        patientRepository.save(testPatient);
        this.mockMvc.perform( delete("/patients/"+testPatient.getMasterPatientIdentifier())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform( get("/patients/"+testPatient.getMasterPatientIdentifier())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("null")));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}