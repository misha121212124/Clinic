package com.inspirit.clinic.dto;

import com.inspirit.clinic.enums.PatientState;
import com.inspirit.clinic.model.Patient;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePatientDto {

    private String firstName;
    private String lastName;
    private PatientState state;


    public Patient toPatient() {
        return new Patient().setFirstName(firstName).setLastName(lastName).setState(state);
    }
}
