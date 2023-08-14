package com.aledma.hackathonBEfinal.service;


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

    @Value("${chatgpt.api.endpoint}")
    private String chatGptApiEndpoint;

    @Value("${chatgpt.api.key}")
    private String chatGptApiKey;

    public String summarizeText(String inputText) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(chatGptApiKey);

        // 4097은 입력 토큰 + 출력 토큰의 최대 수
        int maxOutputTokens = 1000;

        Map<String, Object> requestJson = new HashMap<>();
        requestJson.put("model", "gpt-3.5-turbo-16k");

        List<Map<String, String>> messages = new ArrayList<>();

//        Map<String, String> systemMessage = new HashMap<>();
//        systemMessage.put("role", "system");
//        systemMessage.put("content", "Your role is to summarize");

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", "다음 문자열을 최대한 많이 요약해서 알려줘\n" + inputText);

//        messages.add(systemMessage);
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

        return generatedText;
    }

    private String extractGeneratedText(String responseBody) throws IOException {
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


