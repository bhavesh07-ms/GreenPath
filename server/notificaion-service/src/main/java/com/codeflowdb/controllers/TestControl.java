package com.codeflowdb.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
public class TestControl {

    @GetMapping("/signup")
    public ResponseEntity<Boolean> testGet() {
        // UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(true);
    }
}
