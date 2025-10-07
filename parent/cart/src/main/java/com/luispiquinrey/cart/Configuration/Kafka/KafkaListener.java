package com.luispiquinrey.cart.Configuration.Kafka;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.luispiquinrey.cart.Event.UserCreatedOwnCartEvent;

@Component
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(
            id = "user-events",
            topics = "user-events-topic",
            containerFactory = "kafkaManualAckListenerContainerFactory"
    )
    public void listen(UserCreatedOwnCartEvent event, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {
        ack.acknowledge();
    }
}
