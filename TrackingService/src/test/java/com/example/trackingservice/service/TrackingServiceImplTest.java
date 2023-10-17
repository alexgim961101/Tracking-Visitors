package com.example.trackingservice.service;

import com.example.trackingservice.DummyObject;
import com.example.trackingservice.domain.badges.Badges;
import com.example.trackingservice.domain.badges.BadgesRepo;
import com.example.trackingservice.domain.views.ViewRepo;
import com.example.trackingservice.domain.views.Views;
import com.example.trackingservice.web.dto.BadgeDto;
import com.example.trackingservice.web.dto.BadgeReqDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackingServiceImplTest extends DummyObject {

    @InjectMocks
    private TrackingServiceImpl trackingService;
    @Mock
    private BadgesRepo badgesRepo;
    @Mock
    private ViewRepo viewRepo;

    @Test
    @DisplayName(value = "뱃지 생성")
    void createBadge() {
        // given
        BadgeReqDto dto = BadgeReqDto.builder()
                .url("https://www.alex.com")
                .title("title")
                .icon("")
                .cntBg("green")
                .iconColor("black")
                .titleBgColor("white")
                .border("border")
                .build();

        // stub1
        Badges badges = newMockBadge(1L, "https://www.alex.com", 0L, "", "green", "title", "white", "border", "black", LocalDateTime.now());
        when(badgesRepo.save(any())).thenReturn(badges);

        // stub2
        List<Views> viewsList = new ArrayList<>();
        when(viewRepo.findAllByViewAt(LocalDate.now())).thenReturn(viewsList);

        // when
        BadgeDto badgeDto = trackingService.createBadge(dto);

        // then
        assertEquals(badgeDto.getCnt(), badgeDto.getCntAll());
    }

    @Test
    @DisplayName(value = "현재 조회수 Top 10 출력")
    void getTop10() {
        // given


        // when


        // then
    }

    @Test
    @DisplayName(value = "이전 한 달간 일일 조회수 출력")
    void viewCountBetweenOneMonth() {
    }

    @Test
    @DisplayName(value = "방문 시 조회수 증가")
    void increaseViewCount() {
        // given
        String url = "https://www.alex.com";

        // stub1
        Badges badges = newMockBadge(1L, "https://www.alex.com", 0L, "", "green", "title", "white", "border", "black", LocalDateTime.now());
        when(badgesRepo.findBadgesByUrl(url)).thenReturn(Optional.ofNullable(badges));

        // stub2
        List<Views> list = new ArrayList<>();
        list.add(Views.createView(url));
        when(viewRepo.findAllByViewAt(LocalDate.now())).thenReturn(list);

        // when
        BadgeDto badgeDto = trackingService.increaseViewCount(url);

        // then
        assertEquals(badgeDto.getCnt(), 1L);
        assertEquals(badgeDto.getCntAll(), 1L);
    }
}