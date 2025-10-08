package com.luispiquinrey.user.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.luispiquinrey.Event.UserCreatedOwnCartEvent;



@Component
public class ServiceKafka {

    private static final Logger logger = LoggerFactory.getLogger(ServiceKafka.class);
    private final KafkaTemplate<String, UserCreatedOwnCartEvent> kafkaTemplate;

    @Autowired
    public ServiceKafka(KafkaTemplate<String, UserCreatedOwnCartEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserCreatedOwnCartEvent userCreatedOwnCartEvent) {
        String key = UUID.randomUUID().toString();
        CompletableFuture<SendResult<String, UserCreatedOwnCartEvent>> future
                = kafkaTemplate.send("user-events-topic", key, userCreatedOwnCartEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                logger.error("Error sending message: {}", exception.getMessage(), exception);
            } else {
                logger.info("Message sent successfully to topic: {}, partition: {}, offset: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }

}
