package com.aledma.hackathonBEfinal.controller;

import com.aledma.hackathonBEfinal.JWT.AuthTokensGenerator;
import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.service.ChatGptService;
import com.aledma.hackathonBEfinal.service.OAuthLoginService;
import com.aledma.hackathonBEfinal.service.UserService;
import com.aledma.hackathonBEfinal.service.WebScrapingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "WebScraping", description = "url에서 텍스트 스크래핑")
@RequiredArgsConstructor
@RestController
public class WebScrapingController {
    private final WebScrapingService webScrapingService;
    private final ChatGptService chatGptService;
    private final UserService userService;
    private final AuthTokensGenerator authTokensGenerator;


    @ApiOperation(value = "text추출", notes = "url에서 text를 추출하는 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "텍스트 추출 성공"),
            @ApiResponse(code = 400, message = "텍스트 추출 실패, 어떤 오류인지 살펴보길 바람")
    })
    @PostMapping("/url")
    public ResponseEntity<String> extractText(String url) { //@PathVariable Long userId를 파라미터에서 삭제.
        try {
            String text = this.webScrapingService.extractTextFromUrl(url);

            Long userId = authTokensGenerator.extractMemberId();
            String sum_text = this.chatGptService.summarizeText(text);
            return new ResponseEntity<>(sum_text, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "정리된 text 저장하기 버튼", notes = "정리된 텍스트 저장하는 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정리된 텍스트 저장 성공"),
            @ApiResponse(code = 400, message = "정리된 텍스트 저장 실패, 코드 오류 확인할 것, 서버 잘못일 가능성이 높음")
    })
    @PostMapping("/save")
    public ResponseEntity<?> saveOrganizeTextInDb(WebScrapingDto webScrapingDto){
        try {
            this.webScrapingService.saveOrganizeText(webScrapingDto);
            return new ResponseEntity<>("저장에 성공하였습니다.", HttpStatus.OK);
        }catch (DataNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("저장에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 유저가 가진 스크래핑중 키워드 검색해서 반환
    @ApiOperation(value = "키워드 검색 api", notes = "키워드 검색 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "검색 성공"),
            @ApiResponse(code = 400, message = "검색 실패")
    })
    @GetMapping("/findByKeyword")
    public ResponseEntity<List<WebScraping>> getWebScrapingByUserEmailAndKeyword(@RequestBody String keyword) {
        try {
            List<WebScraping> webScrapings = webScrapingService.getWebScrapingByUserEmailAndKeyword(keyword);
            return new ResponseEntity<>(webScrapings, HttpStatus.OK);
        }catch (DataNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
