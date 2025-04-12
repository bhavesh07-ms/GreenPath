package com.codeflowdb.controllers;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/optimize")
public class TestControllers {


    @PostMapping("/save")
    public String applySchemaChange(@RequestBody String schemaChange) {

        return "Schema Change Request Sent!";
    }

    @GetMapping("//routes/{start}/to/{end}")
    public String applySchemaChage(@RequestBody String schemaChange) {

        return "Schema Change Request Sent!";
    }
}
