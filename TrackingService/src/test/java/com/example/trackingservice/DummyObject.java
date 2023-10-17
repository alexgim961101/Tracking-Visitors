package com.example.trackingservice;

import com.example.trackingservice.domain.badges.Badges;
import com.example.trackingservice.domain.views.Views;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DummyObject {
    protected Badges newMockBadge(Long id, String url, Long cnt, String icon, String cntBg, String title, String titleBg, String border, String iconColor, LocalDateTime createdAt) {
        return Badges.builder()
                .id(id)
                .url(url)
                .cnt(cnt)
                .icon(icon)
                .cntBg(cntBg)
                .title(title)
                .titleBg(titleBg)
                .border(border)
                .iconColor(iconColor)
                .createdAt(createdAt)
                .build();
    }

    protected Views newMockView(Long id, String url, LocalDate date) {
        return Views.builder()
                .id(id)
                .url(url)
                .viewAt(date)
                .build();
    }
}
