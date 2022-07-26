package com.inspirit.clinic.listener;

import com.inspirit.clinic.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class BaseListener extends AbstractMongoEventListener<BaseModel> {
  @Override
  public void onBeforeConvert(BeforeConvertEvent<BaseModel> event) {
    super.onBeforeConvert(event);
    log.info("In BaseModel listener");
    Date dateNow = new Date();

    if (event.getSource().getCreatedAt() == null) event.getSource().setCreatedAt(dateNow);
    event.getSource().setUpdatedAt(dateNow);
  }
}
