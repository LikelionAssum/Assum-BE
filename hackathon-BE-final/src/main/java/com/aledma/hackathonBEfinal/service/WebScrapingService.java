package com.aledma.hackathonBEfinal.service;


import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WebScrapingService {

    private final WebScrapingRepository webScrapingRepository;

    // url로 부터 text 추출하고 DB에 저장
    public String extractTextFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String text = document.body().text().trim().replaceAll("\\s+", " ");
        return text;
    }
}

