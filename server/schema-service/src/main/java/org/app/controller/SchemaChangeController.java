package org.app.controller;


import lombok.RequiredArgsConstructor;
import org.app.kafka.SchemaChangeProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/schema")
public class SchemaChangeController {
    private final SchemaChangeProducer schemaChangeProducer;


    @PostMapping("/apply")
    public String applySchemaChange(@RequestBody String schemaChange) {
        schemaChangeProducer.publishSchemaChange(schemaChange);
        return "Schema Change Request Sent!";
    }
}
