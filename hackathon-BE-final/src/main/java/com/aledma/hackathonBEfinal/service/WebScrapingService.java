package com.aledma.hackathonBEfinal.service;


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

    // url로 부터 text 추출하고 DB에 저장
    public String extractTextFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String text = document.body().text().trim().replaceAll("\\s+", " ");
        return text;
    }
    
    // 프론트에서 정리해서 post한 것을 DB에 저장
    public void saveOrganizeText(Long id, WebScrapingDto scrapingDto){
        // user 객체 가져오기
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("해당 id로 찾을 수 없습니다 : "));;

        // Dto에서 데이터 추출
        WebScraping webScraping = WebScraping.of(scrapingDto);
        webScraping.setUser(user);
        webScraping.setCreateDate(LocalDateTime.now());

        /*
        이 부분에 webScraping에 저장된 createDate랑, keyword 배열 / id를 통해서 user의 age를 가져와서
        새로운 keyword entity 만든것에 저장하면 될듯? 아마
         */
        
        
        this.webScrapingRepository.save(webScraping);

    }
}

//        if (id == null) {
//            WebScrapingDto responseDto = new WebScrapingDto();
//            responseDto.setUrl(url);
//            responseDto.setSum_text(generatedText);
//            WebScraping webScraping = WebScraping.of(responseDto);
//            this.webScrapingRepository.save(webScraping);
//        }else {
//            Optional<User> user = this.userRepository.findById(id);
//            User findUser = user.get();
//
//            // 요약된 텍스트까지 합쳐서 DB에 저장, 개개인 User 별로 WebScraping 저장
//            WebScrapingDto responseDto = new WebScrapingDto();
//            responseDto.setUrl(url);
//            responseDto.setSum_text(generatedText);
//            WebScraping webScraping = WebScraping.of(responseDto);
//            webScraping.setUser(findUser);
//            this.webScrapingRepository.save(webScraping);
//        }

