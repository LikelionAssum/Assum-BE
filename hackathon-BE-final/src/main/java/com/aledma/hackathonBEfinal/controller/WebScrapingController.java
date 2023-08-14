package com.aledma.hackathonBEfinal.controller;

import com.aledma.hackathonBEfinal.service.ChatGptService;
import com.aledma.hackathonBEfinal.service.WebScrapingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "WebScraping", description = "url에서 텍스트 스크래핑")
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class WebScrapingController {

    private final WebScrapingService webScrapingService;

    private final ChatGptService chatGptService;

    @ApiOperation(value = "text추출", notes = "url에서 text를 추출하는 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "텍스트 추출 성공"),
            @ApiResponse(code = 400, message = "텍스트 추출 실패, 어떤 오류인지 살펴보길 바람")
    })
    @PostMapping("/url")
    public ResponseEntity<String> extractText(String url) {
        try {
            String text = this.webScrapingService.extractTextFromUrl(url);
            String sum_text = this.chatGptService.summarizeText(text);
            return new ResponseEntity<>(sum_text, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
