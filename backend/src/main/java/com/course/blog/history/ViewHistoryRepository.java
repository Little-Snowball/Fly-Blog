package com.course.blog.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {
    List<ViewHistory> findByUserIdOrderByViewedAtDesc(Long userId);

    Optional<ViewHistory> findByUserIdAndArticleId(Long userId, Long articleId);
}
