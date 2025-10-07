package com.luispiquinrey.user.Configuration.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.ProducerListener;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder.name("user-events-topic")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public ProducerListener<String, String> producerListener() {
        return new ProducerListener<>() {
            @Override
            public void onSuccess(org.apache.kafka.clients.producer.ProducerRecord<String, String> record,
                    org.apache.kafka.clients.producer.RecordMetadata metadata) {
                System.out.printf("✅ Message sent successfully to topic=%s partition=%d offset=%d%n",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }

            @Override
            public void onError(org.apache.kafka.clients.producer.ProducerRecord<String, String> record,
                    org.apache.kafka.clients.producer.RecordMetadata metadata,
                    Exception exception) {
                System.err.printf("❌ Failed to send message: %s due to %s%n", record.value(), exception.getMessage());
            }
        };
    }
}
