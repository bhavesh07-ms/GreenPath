package org.app.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchemaChangeProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;



    public void publishSchemaChange(String schemaChangeRequest) {
        kafkaTemplate.send("schema-change-topic", schemaChangeRequest);
        System.out.println("✅ Schema Change Published: " + schemaChangeRequest);
    }
}
