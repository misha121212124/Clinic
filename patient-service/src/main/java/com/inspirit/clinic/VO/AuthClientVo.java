package com.inspirit.clinic.VO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("authorization-service")
public interface AuthClientVo {

    @PostMapping("/users/check")
    public ResponseEntity<UserVo> check(@RequestHeader("Authorization") String bearerToken);

}



