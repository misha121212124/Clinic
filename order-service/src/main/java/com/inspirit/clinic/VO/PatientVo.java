package com.inspirit.clinic.VO;

import com.inspirit.clinic.enums.PatientState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PatientVo {

    protected String masterPatientIdentifier;
    private String firstName;
    private String lastName;
    private PatientState state;
}
