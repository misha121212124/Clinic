package com.inspirit.clinic.VO;

import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class OrderVo extends BaseModel {
    private OrderIdVo id;
    private String orderComment;
    private OrderState state;
}
