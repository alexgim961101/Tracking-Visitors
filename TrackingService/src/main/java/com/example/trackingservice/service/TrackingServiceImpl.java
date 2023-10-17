package com.example.trackingservice.service;

import com.example.trackingservice.domain.badges.Badges;
import com.example.trackingservice.domain.badges.BadgesRepo;
import com.example.trackingservice.domain.views.ViewRepo;
import com.example.trackingservice.domain.views.Views;
import com.example.trackingservice.mq.KafkaProducer;
import com.example.trackingservice.web.dto.BadgeDto;
import com.example.trackingservice.web.dto.BadgeReqDto;
import com.example.trackingservice.web.dto.ViewCountOneDay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService{
    private final BadgesRepo badgesRepo;
    private final ViewRepo viewRepo;
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional
    public BadgeDto createBadge(BadgeReqDto badgeReqDto) {
        log.info("===== createBadge() 진입 =====");

        Badges badge = Badges.createBadge(badgeReqDto.getUrl(), badgeReqDto.getCntBg(), badgeReqDto.getTitle(), badgeReqDto.getTitleBgColor(), badgeReqDto.getBorder(), badgeReqDto.getIconColor(), badgeReqDto.getIcon());
        Badges entity = badgesRepo.save(badge);
        log.info("===== badge 저장 =====");

        List<Views> viewEntity = viewRepo.findAllByUrlAndViewAt(badgeReqDto.getUrl(), LocalDate.now());
        log.info("===== 오늘 조회수 불러오기 =====");

        return BadgeDto.fromEntity(entity, Long.parseLong(String.valueOf(viewEntity.size())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getTop10() {
        // 상위 10개를 골라내기 위한 Pageable 객체 생성
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Badges> lists = badgesRepo.findAllByOrderByCntDesc(pageRequest);

        List<String> result = new ArrayList<>();
        int idx = 1;
        for(Badges b : lists) {
            result.add("["+ idx++ +"] " + b.getUrl());
        }

        return result;
    }

    @Override
    public List<ViewCountOneDay> viewCountBetweenOneMonth(String url) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(1);

        List<Object[]> result = viewRepo.countViewsGroupedByDate(url, start, end);
        List<ViewCountOneDay> resp = new ArrayList<>();
        for(Object[] r : result) {
            resp.add(ViewCountOneDay.builder()
                    .day(r[0].toString())
                    .count(Long.parseLong(r[1].toString()))
                    .build());
        }

        return resp;
    }

    @Override
    @Transactional
    public BadgeDto increaseViewCount(String url) {
        // 전체 조회수 증가
        Badges badgesEntity = badgesRepo.findBadgesByUrl(url).orElseThrow(NoSuchElementException::new);
        badgesEntity.setCnt(badgesEntity.getCnt() + 1);

        // 일일 조회수 증가
        Views view = Views.createView(url);
        viewRepo.save(view);
        List<Views> viewEntityList = viewRepo.findAllByUrlAndViewAt(url, LocalDate.now());

        return BadgeDto.fromEntity(badgesEntity, Long.parseLong(String.valueOf(viewEntityList.size())));
    }

    @Override
    @Transactional
    public BadgeDto nowInfo(String url) {
        Badges badgesEntity = badgesRepo.findBadgesByUrl(url).orElseThrow(NoSuchElementException::new);
        List<Views> viewEntityList = viewRepo.findAllByUrlAndViewAt(url, LocalDate.now());
        return BadgeDto.fromEntity(badgesEntity, Long.parseLong(String.valueOf(viewEntityList.size())));
    }
}
