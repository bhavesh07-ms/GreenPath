package com.codeflowdb.controllers;




import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/carbon")
public class TestController {

    @PostMapping("/save")
    public String applySchemaChange(@RequestBody String schemaChange) {
         return "Schema Change Request Sent!";
    }

    @GetMapping("/summary")
    public String applySchemaChage(@RequestBody String schemaChange) {
        return "Schema Change Request Sent!";
    }
}
