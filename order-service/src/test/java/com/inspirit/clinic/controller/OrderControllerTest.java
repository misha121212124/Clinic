package com.inspirit.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirit.clinic.enums.OrderState;
import com.inspirit.clinic.model.Order;
import com.inspirit.clinic.model.OrderId;
import com.inspirit.clinic.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void clean(){
        orderRepository.deleteAll();
    }


    @Test
    void getAllOrders() throws Exception {
        Order testOrder = new Order().setOrderComment("for get all 1").setId(new OrderId());
        orderRepository.save(testOrder);
        testOrder = new Order().setOrderComment("for get all 2").setId(new OrderId());
        orderRepository.save(testOrder);

        this.mockMvc.perform(get("/orders")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("for get all 1")))
                .andExpect(content().string(containsString("for get all 2")));
    }

    @Test
    void findAllActiveOrder() throws Exception {
        Order testOrder = new Order().setOrderComment("for find active 1").setState(OrderState.ACTIVE).setId(new OrderId());
        orderRepository.save(testOrder);
        testOrder = new Order().setOrderComment("for find active 2").setState(OrderState.DECLINED).setId(new OrderId());
        orderRepository.save(testOrder);

        this.mockMvc.perform(get("/orders/active")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("for find active 1")))
                .andExpect(content().string(not(containsString("for find active 2"))));

    }



    @Test
    void deleteOrder() throws Exception {
        Order testOrder = new Order().setOrderComment("for delete").setState(OrderState.ACTIVE).setId(new OrderId());
        testOrder = orderRepository.save(testOrder);

        this.mockMvc.perform( delete("/orders/"+testOrder.getId().getMasterPatientIdentifier()
                +"/"+testOrder.getId().getOrderId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform( get("/orders/"+testOrder.getId().getMasterPatientIdentifier()
                +"/"+testOrder.getId().getOrderId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Order was not found")));

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}