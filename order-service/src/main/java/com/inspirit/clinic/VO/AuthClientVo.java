package com.inspirit.clinic.VO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("authorization-service")
public interface AuthClientVo {

    @PostMapping("/users/check")
    public ResponseEntity<UserVo> check(@RequestHeader("Authorization") String bearerToken);

}



