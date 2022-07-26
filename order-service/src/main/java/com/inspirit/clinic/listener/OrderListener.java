package com.inspirit.clinic.listener;

import com.inspirit.clinic.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class OrderListener extends AbstractMongoEventListener<Order> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Order> event) {
        super.onBeforeConvert(event);
        log.info("In Order listener");
        if (event.getSource().getId().getOrderId() == null)
            event.getSource().getId().setOrderId(UUID.randomUUID().toString());
    }
}
