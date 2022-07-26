package com.inspirit.clinic.VO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class OrderIdVo implements Serializable {
    private String masterPatientIdentifier;
    private String orderId;
}
