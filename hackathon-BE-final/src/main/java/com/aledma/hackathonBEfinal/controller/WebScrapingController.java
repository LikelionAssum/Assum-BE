package com.aledma.hackathonBEfinal.controller;

import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.service.ChatGptService;
import com.aledma.hackathonBEfinal.service.WebScrapingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;

@Api(tags = "WebScraping", description = "url에서 텍스트 스크래핑")
@RequiredArgsConstructor
@RestController
public class WebScrapingController {

    private final WebScrapingService webScrapingService;

    private final ChatGptService chatGptService;


    @ApiOperation(value = "text추출", notes = "url에서 text를 추출하는 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "텍스트 추출 성공"),
            @ApiResponse(code = 400, message = "텍스트 추출 실패, 어떤 오류인지 살펴보길 바람")
    })
    @PostMapping("/{userId}/url")
    public ResponseEntity<String> extractText(@PathVariable Long userId, String url) {
        try {
            String text = this.webScrapingService.extractTextFromUrl(url);
            String sum_text = this.chatGptService.summarizeText(userId, url, text);
            return new ResponseEntity<>(sum_text, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @ApiOperation(value = "사용자가 가진 것중 검색", notes = "user가 가진 WebScraping중 필요한 것만 검색하는 api")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "검색 성공"),
//            @ApiResponse(code = 400, message = "검색 실패, 코드 문제일 수도 있음")
//    })
//    @GetMapping("/{userId}/search")
//    public ResponseEntity<List<WebScraping>> searchWebScrapings(@PathVariable Long userId, @RequestParam String keyword) {
//        try{
//            List<WebScraping> searchList = webScrapingService.searchWebScrapingsByUserIdAndKeyword(userId, keyword);
//            return new ResponseEntity<>(searchList, HttpStatus.OK);
//        }catch (DataNotFoundException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

}
