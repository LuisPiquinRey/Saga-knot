package com.luispiquinrey.cart.Configuration.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.event.ConsumerStartedEvent;
import org.springframework.kafka.event.ConsumerStartingEvent;
import org.springframework.kafka.event.ConsumerStoppedEvent;
import org.springframework.stereotype.Component;

@Component
public class KafkaLifecycleListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaLifecycleListener.class);

    @EventListener
    public void onConsumerStarting(ConsumerStartingEvent event) {
        log.info("⚙️  Consumer is starting... listenerId={} partitions={}",
                event.getListenerId(), event.getPartitions());
    }

    @EventListener
    public void onConsumerStarted(ConsumerStartedEvent event) {
        log.info("🚀 Consumer started -> listenerId={}", event.getListenerId());
    }

    @EventListener
    public void onConsumerStopped(ConsumerStoppedEvent event) {
        log.info("🛑 Consumer stopped -> listenerId={}", event.);
    }
}
