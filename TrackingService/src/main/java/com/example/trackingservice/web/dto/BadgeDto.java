package com.example.trackingservice.web.dto;


import com.example.trackingservice.domain.badges.Badges;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BadgeDto {
    private String url;
    private Long cnt;
    private Long cntAll;
    private String icon;
    private String cntBg;
    private String title;
    private String titleBg;
    private String border;
    private String iconColor;
    private LocalDateTime createdAt;

    @Builder
    public BadgeDto(String url, Long cnt, Long cntAll, String icon, String cntBg, String title, String titleBg, String border, String iconColor, LocalDateTime createdAt) {
        this.url = url;
        this.cnt = cnt;
        this.cntAll = cntAll;
        this.icon = icon;
        this.cntBg = cntBg;
        this.title = title;
        this.titleBg = titleBg;
        this.border = border;
        this.iconColor = iconColor;
        this.createdAt = createdAt;
    }


    //== 생성 메서드 ==//
    public static BadgeDto fromEntity(Badges entity, Long cnt) {
        return BadgeDto.builder()
                .url(entity.getUrl())
                .cnt(cnt)
                .cntAll(entity.getCnt())
                .icon(entity.getIcon())
                .cntBg(entity.getCntBg())
                .title(entity.getTitle())
                .titleBg(entity.getTitleBg())
                .border(entity.getBorder())
                .iconColor(entity.getIconColor())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
