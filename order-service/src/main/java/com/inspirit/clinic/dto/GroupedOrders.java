package com.inspirit.clinic.dto;

import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedOrders implements Serializable {
    private String _id;
    private List<Order> orders;

    @Override
    public String toString() {
        return "GroupedOrders{" +
                "_id='" + _id + '\'' +
                ", orders=" + orders +
                '}';
    }
}
