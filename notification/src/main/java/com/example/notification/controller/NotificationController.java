package com.example.notification.controller;

import com.example.notification.mq.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final KafkaConsumer kafkaConsumer;

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter sse = new SseEmitter(600 * 1000L);
        kafkaConsumer.setSseEmitter(sse);
        try {
            sse.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(sse);
    }
}
