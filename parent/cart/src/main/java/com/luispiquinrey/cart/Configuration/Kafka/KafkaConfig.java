package com.luispiquinrey.cart.Configuration.Kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import com.luispiquinrey.Event.UserCreatedOwnCartEvent;

@Configuration
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedOwnCartEvent> kafkaManualAckListenerContainerFactory(
            ConsumerFactory<String, UserCreatedOwnCartEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserCreatedOwnCartEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @KafkaListener(
            id = "user-events",
            topics = "user-events-topic",
            containerFactory = "kafkaManualAckListenerContainerFactory"
    )
    public void listen(UserCreatedOwnCartEvent event, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {
        log.info("Received event: {}", event);
        ack.acknowledge();
    }

    @Bean
    public KafkaListenerErrorHandler eh(DeadLetterPublishingRecoverer recoverer) {
        return (msg, ex) -> {
            if (msg.getHeaders().get(KafkaHeaders.DELIVERY_ATTEMPT, Integer.class) > 9) {
                recoverer.accept(msg.getHeaders().get(KafkaHeaders.RAW_DATA, ConsumerRecord.class), ex);
                return "FAILED";
            }
            throw ex;
        };
    }
}
