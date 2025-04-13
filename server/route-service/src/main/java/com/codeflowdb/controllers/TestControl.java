package com.codeflowdb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/routes")
public class TestControl {

    @PostMapping("/save")
    public String applySchemaChange(@RequestBody String schemaChange) {

        return "Schema Change Request Sent!";
    }

    @GetMapping("/routes/{start}/to/{end}")
    public String applySchemaChage(@RequestBody String schemaChange) {

        return "Schema Change Request Sent!";
    }
}


