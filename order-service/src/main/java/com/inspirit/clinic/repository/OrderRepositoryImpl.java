//package com.inspirit.clinic.repository;
//
//import com.inspirit.clinic.enums.OrderState;
//import com.inspirit.clinic.model.Order;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.aggregation.Aggregation;
//import org.springframework.data.mongodb.core.aggregation.AggregationResults;
//import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
//import org.springframework.data.mongodb.core.query.Criteria;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public abstract class OrderRepositoryImpl implements OrderRepository{
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public List<List<Order>> findAllByStateGroupByPatient(OrderState active) {
//
//        TypedAggregation<Order> studentAggregation =
//                Aggregation.newAggregation(Order.class,
//                        Aggregation.match(Criteria.where("state").is("ACTIVE")),
//                        Aggregation.group("orderComment"));
//
//        AggregationResults<OrderList> results = mongoTemplate.
//                aggregate(studentAggregation, OrderList.class);
//
//        List<OrderList> studentResultsList = results.getMappedResults();
//
//        return studentResultsList.stream().map(OrderList::getOrderList).collect(Collectors.toList());
////        return null;
//    }
//}
//@Data
//class OrderList{
//    private List<Order> orderList;
//
//}
