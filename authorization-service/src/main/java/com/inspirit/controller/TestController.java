package com.inspirit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
@Slf4j
public class TestController {

    @GetMapping
    public ResponseEntity<String> printMessage(){

        log.info("In test_controller get all users");
        return ResponseEntity.ok("From security");
    }

}
