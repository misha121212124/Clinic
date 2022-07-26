package com.inspirit.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Accessors(chain = true)
public class UpdateUserRequest {

    @NotBlank
    private String username;

    private Set<String> authorities;

}
