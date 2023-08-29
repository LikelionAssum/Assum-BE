package com.aledma.hackathonBEfinal.dto;


import lombok.Data;

import java.util.List;

@Data
public class AgeKeywordRankDTO {
    private int age;
    private List<KeywordRankDTO> keywordRanks;
}
