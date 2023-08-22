package com.aledma.hackathonBEfinal.repository;

import com.aledma.hackathonBEfinal.domain.WebScraping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WebScrapingRepository extends JpaRepository<WebScraping, Long> {
}
