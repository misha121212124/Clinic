package com.inspirit.clinic.repository;


import com.inspirit.clinic.dto.GroupedOrders;
import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, OrderId> {

    List<Order> findAllById_MasterPatientIdentifier(String masterPatientIdentifier);
    List<Order> findAllById_MasterPatientIdentifierAndState(String masterPatientIdentifier, OrderState state);

    List<Order> findAllByState(OrderState active);




        @Aggregation(pipeline = { "{$match:{state:'ACTIVE'}}","{ '$group': { '_id' : '$_id.masterPatientIdentifier'," +
                "'orders': {$push: {"+
                "'_id':'$_id'"+
                "'orderComment':'$orderComment'"+
                "'state':'$state'"+
                "'createdAt':'$createdAt'"+
                "'updatedAt':'$updatedAt'"+
                "'_class':'$_class'"+
                "}} } }" })
        List<GroupedOrders> findAllActiveOrdersGroupByPatientId();

}
