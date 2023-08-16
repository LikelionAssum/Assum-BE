package com.aledma.hackathonBEfinal.service;


import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

@RequiredArgsConstructor
@Service
public class ChatGptService {

    private final WebScrapingRepository webScrapingRepository;

    @Value("${chatgpt.api.endpoint}")
    private String chatGptApiEndpoint;

    @Value("${chatgpt.api.key}")
    private String chatGptApiKey;

    public String summarizeText(String url, String inputText) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(chatGptApiKey);

        int maxOutputTokens = 1500;

        Map<String, Object> requestJson = new HashMap<>();
        requestJson.put("model", "gpt-3.5-turbo-16k");

        List<Map<String, String>> messages = new ArrayList<>();

        // 시스템 메세지 부여 삭제
//        Map<String, String> systemMessage = new HashMap<>();
//        systemMessage.put("role", "system");
//        systemMessage.put("content", "Your role is to summarize");

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", "다음 문자열을 최대한 많이 요약해서 알려줘\n " +
                "이때 문자열내에서 제목, 키워드 4개도 찾아서 알려줘. 응답의 형식은\n" +
                " 제목 : \n" +
                "키워드 : \n" +
                "요약글 : \n 이런 형식으로 해줘.\n" + inputText);

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

        // 요약된 텍스트까지 합쳐서 DB에 저장
        WebScrapingDto responseDto = new WebScrapingDto();
        responseDto.setUrl(url);
        responseDto.setSum_text(generatedText);
        WebScraping webScraping = WebScraping.of(responseDto);
        this.webScrapingRepository.save(webScraping);
        
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
            return "No generated text available";
        }
    }

}


