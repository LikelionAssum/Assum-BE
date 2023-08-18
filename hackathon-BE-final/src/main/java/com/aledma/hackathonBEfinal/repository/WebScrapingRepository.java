package com.aledma.hackathonBEfinal.repository;

import com.aledma.hackathonBEfinal.domain.WebScraping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WebScrapingRepository extends JpaRepository<WebScraping, Long> {

    // user가 가진 sum_text중 원하는 키워드를 가진 것만 db에서 가져오는 메소드.
//    @Query("SELECT w FROM WebScraping w WHERE w.user.id = :userId AND w.sumText LIKE %:keyword%")
//    List<WebScraping> findByUserIdAndSumTextContainingKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);
}
