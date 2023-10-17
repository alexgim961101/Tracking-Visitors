package com.example.trackingservice.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ViewCountOneDay {
    private String day;
    private Long count;

    @Builder
    public ViewCountOneDay(String day, Long count) {
        this.day = day;
        this.count = count;
    }
}
