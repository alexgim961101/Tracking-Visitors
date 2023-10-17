package com.example.trackingservice.domain.badges;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

// TODO: JPA 테스트 시 auto_increment값 제어 방법 공부하기
// TODO: JPA 테스트 시 auto_increment로 테스트 하지 말 것 -> 값 제어가 힘듬

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BadgesRepoTest {
    @Autowired
    private BadgesRepo badgesRepo;

    @Test
    @DisplayName(value = "저장 테스트")
    void saveTest() {
        // given
        Badges badges = Badges.createBadge("http://www.naver.com", "white", "hits", "black", "round", "green", "");

        // when
        Badges badgeEntity = badgesRepo.save(badges);

        // then
        assertTrue(badges.getUrl().equals(badgeEntity.getUrl()));
    }

    @Test
    @DisplayName(value = "조회 테스트")
    void getTest() {
        // givem
        Badges badges = Badges.createBadge("http://www.naver.com", "white", "hits", "black", "round", "green", "");
        badgesRepo.save(badges);

        // when
        Badges badgeEntity = badgesRepo.findById(3L).get();

        // then
        assertTrue(badges.getUrl().equals(badgeEntity.getUrl()));
    }

    @Test
    @DisplayName(value = "수정 테스트")
    void updateTest() {
        // given
        Badges badges = Badges.createBadge("http://www.naver.com", "white", "hits", "black", "round", "green", "");
        badgesRepo.save(badges);

        // when
        Badges badgeEntity = badgesRepo.findById(2L).get();
        badgeEntity.addCnt();

        // then
        assertEquals(badgeEntity.getCnt(), 1L);
    }

    @Test
    @DisplayName(value = "삭제 테스트")
    void deleteTest() {
        // given
        Badges badges = Badges.createBadge("http://www.naver.com", "white", "hits", "black", "round", "green", "");
        Long id = badgesRepo.save(badges).getId();

        // when
        badgesRepo.deleteById(id);

        // then
        assertThrows(NoSuchElementException.class, () -> badgesRepo.findById(id).get());
    }
}