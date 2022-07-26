package com.inspirit.clinic.dto;

import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateOrderDto {

    private String masterPatientIdentifier;
    private String orderComment;
    private OrderState state;


    public Order toOrder() {
        return new Order().setId(new OrderId().setMasterPatientIdentifier(masterPatientIdentifier))
                .setOrderComment(orderComment).setState(state);
    }
}
