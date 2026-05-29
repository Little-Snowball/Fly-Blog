package com.course.blog.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a where " +
            "(:keyword is null or :keyword = '' or " +
            "lower(a.title) like lower(concat('%', :keyword, '%')) or " +
            "lower(a.summary) like lower(concat('%', :keyword, '%')) or " +
            "lower(a.content) like lower(concat('%', :keyword, '%')) or " +
            "lower(a.tags) like lower(concat('%', :keyword, '%'))) ")
    Page<Article> search(@Param("keyword") String keyword, Pageable pageable);
}
