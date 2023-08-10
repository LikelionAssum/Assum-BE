package com.aledma.hackathonBEfinal.webscraping;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/scraping")
@RestController
public class WebScrapingController {

    private final WebScrapingService webScrapingService;


    // 수정이 다소 필요한 듯한 느낌.
    @PostMapping("/url")
    public ResponseEntity<?> extractText(String url) {
        try {
            String text = this.webScrapingService.extractTextFromUrl(url);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
