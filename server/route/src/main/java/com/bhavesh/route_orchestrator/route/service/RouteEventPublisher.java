package com.bhavesh.route_orchestrator.route.service;

import com.bhavesh.commonutils.commonutils.dto.RouteEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class RouteEventPublisher {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RouteEventPublisher.class);

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    public RouteEventPublisher(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
        this.objectMapper = new ObjectMapper();
    }

    public void publish(RouteEvent event) {

        try {
            String body = objectMapper.writeValueAsString(event);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(body)
                    .build();

            sqsClient.sendMessage(request);

        } catch (Exception e) {
            // IMPORTANT: Do not fail business flow
            // Log & swallow OR send to fallback queue
            log.error("Failed to publish RouteLifecycleEvent", e);
        }
    }
}
