package com.aledma.hackathonBEfinal.controller;

import com.aledma.hackathonBEfinal.dto.AgeKeywordRankDTO;
import com.aledma.hackathonBEfinal.service.KeywordService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Keyword", description = "keyword 관련 로직 작성")
@RestController
@RequiredArgsConstructor
@RequestMapping("/keywords")
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/keywordRanking")
    public ResponseEntity<List<AgeKeywordRankDTO>> getKeywordRanking(){
        List<AgeKeywordRankDTO> ageKeywordRankDTO =keywordService.getKeywordRanking();

        return ResponseEntity.ok(ageKeywordRankDTO);
    }
}
