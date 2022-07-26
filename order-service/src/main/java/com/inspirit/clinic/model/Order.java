package com.inspirit.clinic.model;

import com.inspirit.clinic.enums.OrderState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@EqualsAndHashCode(callSuper = true)
@Document(collection = "orders")
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class Order extends BaseModel{
    @Id
    private OrderId id;

    private String orderComment;
    private OrderState state;

}


