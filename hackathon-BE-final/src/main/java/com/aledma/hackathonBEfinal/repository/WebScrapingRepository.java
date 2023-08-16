package com.aledma.hackathonBEfinal.repository;

import com.aledma.hackathonBEfinal.domain.WebScraping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebScrapingRepository extends JpaRepository<WebScraping, Long> {
}
