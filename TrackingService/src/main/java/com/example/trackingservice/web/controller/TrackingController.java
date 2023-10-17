package com.example.trackingservice.web.controller;

import com.example.trackingservice.mq.KafkaProducer;
import com.example.trackingservice.service.TrackingService;
import com.example.trackingservice.web.dto.BadgeDto;
import com.example.trackingservice.web.dto.BadgeReqDto;
import com.example.trackingservice.web.dto.ViewCountOneDay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequestMapping("/tracking")
@RequiredArgsConstructor
@Slf4j
@RestController
public class TrackingController {
    private final TrackingService trackingService;
    private final KafkaProducer kafkaProducer;

    @PostMapping("/badge")
    public ResponseEntity<?> createBadge(@RequestBody BadgeReqDto dto) {
        BadgeDto badgeDto = trackingService.createBadge(dto);
        return ResponseEntity.ok(badgeDto);
    }

    @GetMapping("/rank")
    public ResponseEntity<?> getTop10() {
        List<String> top10 = trackingService.getTop10();
        return new ResponseEntity<>(top10, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<?> totalVisitorBetweenOneMonth(@RequestBody Map<String, String> req) {
        List<ViewCountOneDay> viewCountOneDays = trackingService.viewCountBetweenOneMonth(req.get("url"));
        return new ResponseEntity<>(viewCountOneDays, HttpStatus.OK);
    }

    @PostMapping("/view")
    public ResponseEntity<?> increaseView(@RequestBody Map<String, String> req) {
        BadgeDto badgeDto = trackingService.increaseViewCount(req.get("url"));
        String data = String.format("[%s] %s", LocalDateTime.now().toString(), badgeDto.getUrl());
        kafkaProducer.send("view", data);
        return new ResponseEntity<>(badgeDto, HttpStatus.OK);
    }

    @PostMapping("/see")
    public ResponseEntity<?> getNowStatus(@RequestBody Map<String, String> req) {
        BadgeDto badgeDto = trackingService.nowInfo(req.get("url"));
        return new ResponseEntity<>(badgeDto, HttpStatus.OK);
    }

}
