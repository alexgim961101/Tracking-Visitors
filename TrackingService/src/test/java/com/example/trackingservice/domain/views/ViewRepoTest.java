package com.example.trackingservice.domain.views;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ViewRepoTest {
    @Autowired
    private ViewRepo viewRepo;

    @Test
    @DisplayName(value = "저장 테스트")
    void saveTest() {
        // given
        Views view = Views.createView("http://www.naver.com");

        // when
        Views viewEntity = viewRepo.save(view);
        viewRepo.flush();


        // then
        assertTrue(view.getUrl().equals(viewEntity.getUrl()));
    }

    @Test
    @DisplayName(value = "조회 테스트")
    void getTest() {
        // given
        Views view = Views.createView("http://www.naver.com");
        Views viewEntity1 = viewRepo.save(view);

        // when
        Views viewEntity2 = viewRepo.findById(view.getId()).get();

        // then
        assertEquals(viewEntity1, viewEntity2);
    }

    @Test
    @DisplayName(value = "수정 테스트")
    void updateTest() {
        // given
        Views view = Views.createView("http://www.naver.com");
        Views viewEntity = viewRepo.save(view);
        viewEntity.setUrl("http://www.google.com");

        // when
        Views views = viewRepo.findById(viewEntity.getId()).get();

        // then
        assertTrue(views.getUrl().equals("http://www.google.com"));
    }

    @Test
    @DisplayName(value = "삭제 테스트")
    void deleteTest() {
        // given
        Views view = Views.createView("http://www.naver.com");
        Views viewEntity = viewRepo.save(view);

        // when
        viewRepo.delete(viewEntity);

        // then
        assertThrows(NoSuchElementException.class, () -> viewRepo.findById(viewEntity.getId()).get());

    }
}