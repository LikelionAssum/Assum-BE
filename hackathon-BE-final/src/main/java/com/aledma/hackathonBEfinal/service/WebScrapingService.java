package com.aledma.hackathonBEfinal.service;


import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class WebScrapingService {

    private final WebScrapingRepository webScrapingRepository;

    // url로 부터 text 추출하고 DB에 저장
    public String extractTextFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String text = document.text();
        return text;
    }
}
// db에 결과물 저장해놓기
//        WebScrapingDto responseDto = new WebScrapingDto();
//        responseDto.setUrl(url);
//        WebScraping webScraping = WebScraping.of(responseDto);
//        this.webScrapingRepository.save(webScraping);

//    public void saveTextToFile(String filename, String text) throws IOException {
//        try (FileWriter fileWriter = new FileWriter(filename)) {
//            fileWriter.write(text);
//        }
//    }

    //현재 시간을 기반으로한 파일 이름 생성
//    private String generateTimeBasedFileName() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
//        String timestamp = dateFormat.format(new Date());
//        return "text_" + timestamp + ".txt";
//    }
//}

