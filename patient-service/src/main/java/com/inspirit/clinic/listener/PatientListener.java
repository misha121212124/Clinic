package com.inspirit.clinic.listener;

import com.inspirit.clinic.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class PatientListener extends AbstractMongoEventListener<Patient> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Patient> event) {
        super.onBeforeConvert(event);
        log.info("In Patient listener");
        if (event.getSource().getMasterPatientIdentifier() == null) event.getSource().setMasterPatientIdentifier(UUID.randomUUID().toString());
    }
}
