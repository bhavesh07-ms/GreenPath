package com.codeflowdb.controllers;

import com.codeflowdb.payloads.SignUpDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpDto signUpDto) {
       // UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(true);
    }

}
