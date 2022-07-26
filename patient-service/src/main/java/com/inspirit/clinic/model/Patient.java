package com.inspirit.clinic.model;

import com.inspirit.clinic.enums.PatientState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@EqualsAndHashCode(callSuper = true)
@Document(collection = "patients")
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class Patient extends BaseModel {

//    @MongoId(FieldType.OBJECT_ID)
    @Id
    private String masterPatientIdentifier;               // /*MASTER_PATIENT_IDENTIFIER*/ = UUID.randomUUID().toString().replace("-","");
    private String firstName;                               // STRING(MAX)
    private String lastName;                                // STRING(MAX)
                                                            //    ZonedDateTime createDateTimeGMT /*CREATE_DATE_TIME_GMT*/;             // TIMESTAMP -- Should be set as CURRENT_TIMESTAMP upon creation
                                                            //    ZonedDateTime updateDateTimeGMT/* UPDATE_DATE_TIME_GMT*/;             // TIMESTAMP -- Shoud be set as CURRENT_TIMESTAMP upon creation, updated upon each update
    private PatientState state;                             // -- ACTIVE/INACTIVE/PENDING
}
