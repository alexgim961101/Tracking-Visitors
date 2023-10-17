package com.example.notification.mq;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class KafkaConsumer {

    private SseEmitter sseEmitter;

    @KafkaListener(topics = "view")
    public void listen(String msg) throws IOException {
        log.info("Kafka message ==> {}", msg);
        sseEmitter.send(SseEmitter.event()
                .name("view")
                .data(msg));
    }
}
