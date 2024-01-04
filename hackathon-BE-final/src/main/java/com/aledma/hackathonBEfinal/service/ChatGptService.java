package com.aledma.hackathonBEfinal.service;


import com.aledma.hackathonBEfinal.JWT.AuthTokensGenerator;
import com.aledma.hackathonBEfinal.OAuth.OAuthInfoResponse;
import com.aledma.hackathonBEfinal.domain.Keyword;
import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.aledma.hackathonBEfinal.repository.KeywordRepository;
import com.aledma.hackathonBEfinal.repository.UserRepository;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ChatGptService {
    private final AuthTokensGenerator authTokensGenerator;
    private final WebScrapingRepository webScrapingRepository;
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;

    @Value("${chatgpt.api.endpoint}")
    private String chatGptApiEndpoint;

    @Value("${chatgpt.api.key}")
    private String chatGptApiKey;

    @Transactional
    public String summarizeText(String inputText) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(chatGptApiKey);

        int maxOutputTokens = 1800;

        Map<String, Object> requestJson = new HashMap<>();
        requestJson.put("model", "gpt-3.5-turbo-16k");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", "다음 문자열을 최대한 많이 요약해서 알려줘\n " +
                "이때 문자열내에서 제목, 키워드 4개도 찾아서 알려줘. 응답의 형식은\n" +
                " 제목: \n" +
                "키워드: \n" +
                "요약글: 이런 형식으로 해줘." + inputText);

        messages.add(userMessage);

        requestJson.put("messages", messages);

        // 맥시멈 토큰 수 제한
        requestJson.put("max_tokens", maxOutputTokens);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(
                chatGptApiEndpoint,
                new HttpEntity<>(requestJson, headers),
                String.class
        );

        String responseBody = response.getBody();
        String generatedText = extractGeneratedText(responseBody);

        //키워드 배열형태로 추출한 후 db에 넣어 줌

        String[] keywords = extractKeywordsArray(generatedText);
        Keyword keyword = new Keyword();

        Long userId = authTokensGenerator.extractMemberId();
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();


        keyword.setKeyword1(keywords[0]);
        keyword.setKeyword2(keywords[1]);
        keyword.setKeyword3(keywords[2]);
        keyword.setKeyword4(keywords[3]);
        keyword.setKeywordDate(LocalDateTime.now());
        keyword.setAge(user.getAge());

        keywordRepository.save(keyword);

        return generatedText;
    }


    public String extractGeneratedText(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        JsonNode choicesArray = jsonNode.get("choices");
        if (choicesArray.isArray() && choicesArray.size() > 0) {
            JsonNode firstChoice = choicesArray.get(0);
            JsonNode message = firstChoice.get("message");
            String generatedText = message.get("content").asText();
            return generatedText;
        } else {
            return "생성된 문자열이 없습니다.";
        }
    }

    //generatedText에서 키워드 추출하는 함수
    public String[] extractKeywordsArray(String inputText) {
        String[] lines = inputText.split("\n");

        for (String line : lines) {
            if (line.startsWith("키워드:")) {
                String keywords = line.substring("키워드:".length()).trim();
                return keywords.split(",\\s*"); // ,로 분리
            }
        }

        return new String[0]; // 아무 키워드 없을 시 빈 배열 반환
    }
}


