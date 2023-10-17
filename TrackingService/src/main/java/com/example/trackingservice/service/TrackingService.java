package com.example.trackingservice.service;

import com.example.trackingservice.web.dto.BadgeDto;
import com.example.trackingservice.web.dto.BadgeReqDto;
import com.example.trackingservice.web.dto.ViewCountOneDay;

import java.util.List;

public interface TrackingService {
    BadgeDto createBadge(BadgeReqDto badgeReqDto);
    List<String> getTop10();
    List<ViewCountOneDay> viewCountBetweenOneMonth(String url);
    BadgeDto increaseViewCount(String url);
    BadgeDto nowInfo(String url);
}
