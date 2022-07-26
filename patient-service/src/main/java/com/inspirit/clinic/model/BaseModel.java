package com.inspirit.clinic.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Setter
@Getter
@Accessors(chain = true)
public abstract class BaseModel {
    private Date createdAt;
    private Date updatedAt;
}
