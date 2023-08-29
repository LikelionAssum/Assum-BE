package com.aledma.hackathonBEfinal.repository;

import com.aledma.hackathonBEfinal.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findByKeywordDateAfter(LocalDateTime dateTime);
}
