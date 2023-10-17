package com.example.trackingservice.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BadgeReqDto {
    private String url;
    private String icon;
    private String cntBg;
    private String border;
    private String title;
    private String titleBgColor;
    private String iconColor;

    @Builder
    public BadgeReqDto(String url, String icon, String cntBg, String border, String title, String titleBgColor, String iconColor) {
        this.url = url;
        this.icon = icon;
        this.cntBg = cntBg;
        this.border = border;
        this.title = title;
        this.titleBgColor = titleBgColor;
        this.iconColor = iconColor;
    }
}
