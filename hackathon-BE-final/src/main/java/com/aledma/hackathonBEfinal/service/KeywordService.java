package com.aledma.hackathonBEfinal.service;

import com.aledma.hackathonBEfinal.domain.Keyword;
import com.aledma.hackathonBEfinal.dto.AgeKeywordRankDTO;
import com.aledma.hackathonBEfinal.dto.KeywordRankDTO;
import com.aledma.hackathonBEfinal.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public List<AgeKeywordRankDTO> getKeywordRanking(){
        LocalDateTime dayAgo = LocalDateTime.now().minusHours(24);
        List<Keyword> keywords = keywordRepository.findByKeywordDateAfter(dayAgo);

        Map<Integer, Map<String, Long>> ageKeywordCountMap = new HashMap<>();

        for(Keyword keyword: keywords){
            int ageGroup = keyword.getAge()/10 *10;
            String[] keywordArray = {keyword.getKeyword1(), keyword.getKeyword2(), keyword.getKeyword3(), keyword.getKeyword4()};

            ageKeywordCountMap.putIfAbsent(ageGroup,new HashMap<>());

            for (String keywordStr : keywordArray){
                if(keywordStr != null && !keywordStr.isEmpty()){
                    ageKeywordCountMap.get(ageGroup).merge(keywordStr, 1L, Long:: sum);
                }
            }

        }

        List<AgeKeywordRankDTO> ageKeywordRanks = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, Long>> ageEntry : ageKeywordCountMap.entrySet()) {
            AgeKeywordRankDTO ageKeywordRank = new AgeKeywordRankDTO();
            ageKeywordRank.setAge(ageEntry.getKey());

            List<KeywordRankDTO> keywordRanks = ageEntry.getValue().entrySet().stream()
                    .map(entry -> {
                        KeywordRankDTO keywordRank = new KeywordRankDTO();
                        keywordRank.setKeyword(entry.getKey());
                        keywordRank.setCount(entry.getValue());
                        return keywordRank;
                    })
                    .sorted(Comparator.comparingLong(KeywordRankDTO::getCount).reversed())
                    .collect(Collectors.toList());

            ageKeywordRank.setKeywordRanks(keywordRanks);
            ageKeywordRanks.add(ageKeywordRank);
        }

        return ageKeywordRanks;

    }

    public List<AgeKeywordRankDTO> getTotalKeywordRanking() {
        List<Keyword> allKeywords = keywordRepository.findAll();

        Map<String, Long> totalKeywordCountMap = new HashMap<>();

        for (Keyword keyword : allKeywords) {
            String[] keywordArray = {keyword.getKeyword1(), keyword.getKeyword2(), keyword.getKeyword3(), keyword.getKeyword4()};

            for (String keywordStr : keywordArray) {
                if (keywordStr != null && !keywordStr.isEmpty()) {
                    totalKeywordCountMap.merge(keywordStr, 1L, Long::sum);
                }
            }
        }

        AgeKeywordRankDTO totalAgeKeywordRank = new AgeKeywordRankDTO();
        totalAgeKeywordRank.setAge(0);

        List<KeywordRankDTO> totalKeywordRanks = totalKeywordCountMap.entrySet().stream()
                .map(entry -> {
                    KeywordRankDTO keywordRank = new KeywordRankDTO();
                    keywordRank.setKeyword(entry.getKey());
                    keywordRank.setCount(entry.getValue());
                    return keywordRank;
                })
                .sorted(Comparator.comparingLong(KeywordRankDTO::getCount).reversed())
                .collect(Collectors.toList());

        totalAgeKeywordRank.setKeywordRanks(totalKeywordRanks);

        List<AgeKeywordRankDTO> ageKeywordRanks = new ArrayList<>();
        ageKeywordRanks.add(totalAgeKeywordRank);

        return ageKeywordRanks;
    }


}
