package com.inspirit.clinic.VO;

import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.BaseModel;
import com.inspirit.clinic.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class OrderDtoVo extends BaseModel {

    private OrderIdVo id;

    private String orderComment;
    private OrderState state;

    private Patient patient;

    public OrderDtoVo(OrderVo order) {
        this.id = order.getId();
        this.orderComment = order.getOrderComment();
        this.state = order.getState();

        this.setCreatedAt(order.getCreatedAt()).setUpdatedAt(order.getUpdatedAt());

    }

}
