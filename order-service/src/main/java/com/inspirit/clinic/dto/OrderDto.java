package com.inspirit.clinic.dto;

import com.inspirit.clinic.VO.PatientVo;
import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.BaseModel;
import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class OrderDto extends BaseModel{

    private OrderId id;

    private String orderComment;
    private OrderState state;

    private PatientVo patient;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.orderComment = order.getOrderComment();
        this.state = order.getState();

        this.setCreatedAt(order.getCreatedAt()).setUpdatedAt(order.getUpdatedAt());

    }

}
