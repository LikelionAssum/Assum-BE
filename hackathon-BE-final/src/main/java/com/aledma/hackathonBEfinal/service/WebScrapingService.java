package com.aledma.hackathonBEfinal.service;


import com.aledma.hackathonBEfinal.JWT.AuthTokensGenerator;
import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.repository.UserRepository;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WebScrapingService {
    private final WebScrapingRepository webScrapingRepository;
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;

    // url로 부터 text 추출
    public String extractTextFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String text = document.body().text().trim().replaceAll("\\s+", " ");
        return text;
    }
    
    // 프론트에서 정리해서 post한 것을 DB에 저장
    public void saveOrganizeText(WebScrapingDto scrapingDto){
        Long userId = authTokensGenerator.extractMemberId();
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 id로 찾을 수 없습니다 : "));



        // Dto에서 데이터 추출
        WebScraping webScraping = WebScraping.of(scrapingDto);
        webScraping.setUser(user);
        webScraping.setCreateDate(LocalDateTime.now());
        this.webScrapingRepository.save(webScraping);

    }

    public List<WebScraping> getWebScrapingByUserEmailAndKeyword(String keyword) {
        Long userId = authTokensGenerator.extractMemberId();
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 id로 찾을 수 없습니다 : "));



        // 사용자 이메일과 키워드를 사용하여 WebScraping 항목을 찾음
        return webScrapingRepository.findByUserIdAndKeyword(userId, keyword);
    }

}


