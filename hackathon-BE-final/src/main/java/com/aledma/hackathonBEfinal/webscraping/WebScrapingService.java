package com.aledma.hackathonBEfinal.webscraping;


import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class WebScrapingService {

    private final WebScrapingRepository webScrapingRepository;

    // url로 부터 text 추출하고 DB에 저장, 리턴값 수정 필요?
    public String extractTextFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String text = document.text();

        WebScrapingDto responseDto = new WebScrapingDto();
        responseDto.setUrl(url);
//        responseDto.setScraping_text(text);
        WebScraping webScraping = WebScraping.of(responseDto);
        this.webScrapingRepository.save(webScraping);
        return text;
    }
}
