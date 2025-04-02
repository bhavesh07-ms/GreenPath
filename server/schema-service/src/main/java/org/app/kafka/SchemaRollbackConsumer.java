package org.app.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SchemaRollbackConsumer {
    @KafkaListener(topics = "schema-change-topic", groupId = "rollback-group")
    public void rollbackSchemaChange(String schemaChangeRequest) {
        System.out.println("🔄 Rolling Back Schema: " + schemaChangeRequest);
        // TODO: Implement rollback logic
    }
}
