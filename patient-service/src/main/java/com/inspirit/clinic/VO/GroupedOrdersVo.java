package com.inspirit.clinic.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedOrdersVo implements Serializable {
    private String _id;
    private List<OrderVo> orders;

    @Override
    public String toString() {
        return "GroupedOrders{" +
                "_id='" + _id + '\'' +
                ", orders=" + orders +
                '}';
    }
}
