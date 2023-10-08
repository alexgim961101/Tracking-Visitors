package com.example.trackingservice.domain.views;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Views {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Setter
    private String url;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime viewAt;

    @Builder
    public Views(Long id, String url, LocalDateTime viewAt) {
        this.id = id;
        this.url = url;
        this.viewAt = viewAt;
    }

    //== 생성 메서드 ==//
    public static Views createView(String url) {
        return Views.builder()
                .url(url)
                .build();
    }
}
