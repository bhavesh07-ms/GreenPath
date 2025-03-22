package org.app.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SchemaChangeConsumer {
    @KafkaListener(topics = "schema-change-topic", groupId = "codeflowdb-group")
    public void processSchemaChange(String schemaChangeRequest) {
        System.out.println("📂 Applying Schema Change: " + schemaChangeRequest);
        // TODO: Apply schema migration logic
    }
}

