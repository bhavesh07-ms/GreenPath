package org.app.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SchemaNotificationConsumer {
    @KafkaListener(topics = "schema-alert-topic", groupId = "codeflowdb-group")
    public void sendSchemaAlert(String schemaAlert) {
        System.out.println("📢 Schema Alert: " + schemaAlert);
        // TODO: Send Email/SMS Notification
    }
}

