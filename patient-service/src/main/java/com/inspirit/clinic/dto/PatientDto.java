package com.inspirit.clinic.dto;

import com.inspirit.clinic.VO.OrderVo;
import com.inspirit.clinic.enums.PatientState;
import com.inspirit.clinic.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PatientDto {

    private String masterPatientIdentifier;
    private String firstName;
    private String lastName;
    private PatientState state;
    private Date createdAt;
    private Date updatedAt;

    private List<OrderVo> orderList;


    public Patient toPatient() {
        return (Patient) new Patient().setMasterPatientIdentifier(masterPatientIdentifier).setFirstName(firstName)
                .setLastName(lastName).setState(state).setCreatedAt(createdAt).setUpdatedAt(updatedAt);
    }

    public PatientDto(Patient patient){
        this.masterPatientIdentifier = patient.getMasterPatientIdentifier();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.state = patient.getState();
        this.createdAt = patient.getCreatedAt();
        this.updatedAt = patient.getUpdatedAt();
    }
}
