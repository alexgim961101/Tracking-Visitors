package com.example.trackingservice.domain.views;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ViewRepo extends JpaRepository<Views, Long> {
    List<Views> findAllByViewAt(LocalDate date);

    List<Views> findAllByUrlAndViewAt(String url, LocalDate date);

    // select count(*) from Views group by view_at where view_at between 한달 전 and 현재 order by view_at asc
    @Query("SELECT v.viewAt, COUNT(v) FROM Views v WHERE v.url = :url AND v.viewAt BETWEEN :start AND :end GROUP BY v.viewAt ORDER BY v.viewAt ASC")
    List<Object[]> countViewsGroupedByDate(@Param("url") String url, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
