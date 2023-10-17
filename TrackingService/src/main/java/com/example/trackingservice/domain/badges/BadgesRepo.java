package com.example.trackingservice.domain.badges;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BadgesRepo extends JpaRepository<Badges, Long> {
    Optional<Badges> findBadgesByUrl(String url);
    List<Badges> findAllByOrderByCntDesc(Pageable pageable);
}
