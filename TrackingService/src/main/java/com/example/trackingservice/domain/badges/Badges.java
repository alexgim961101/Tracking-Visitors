package com.example.trackingservice.domain.badges;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Badges {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String url;
    @Column(nullable = false)
    @Setter
    private Long cnt;
    private String icon;
    @Column(nullable = false)
    private String cntBg;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String titleBg;
    @Column(nullable = false)
    private String border;
    @Column(nullable = false)
    private String iconColor;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Badges(Long id, String url, Long cnt, String icon, String cntBg, String title, String titleBg, String border, String iconColor, LocalDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.cnt = cnt;
        this.icon = icon;
        this.cntBg = cntBg;
        this.title = title;
        this.titleBg = titleBg;
        this.border = border;
        this.iconColor = iconColor;
        this.createdAt = createdAt;
    }

    //== 생성 메서드 ==//
    public static Badges createBadge(String url, String cntBg, String title, String titleBg, String border, String iconColor, String icon) {
        if(icon == null) icon = "";
        return Badges.builder()
                .url(url)
                .cnt(0L)
                .cntBg(cntBg)
                .title(title)
                .titleBg(titleBg)
                .border(border)
                .iconColor(iconColor)
                .icon(icon)
                .build();
    }

    //== 누적 cnt 증가 메서드 ==//
    public void addCnt() {
        this.cnt++;
    }
}
