package com.aledma.hackathonBEfinal.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
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
        headers.set("Authorization", "Bearer " + chatGptApiKey);

        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject requestJson = new JSONObject();
        requestJson.put("prompt", "Summarize the following text:\n" + inputText);
        requestJson.put("max_tokens", 50);
        requestJson.put("temperature", 0.7);

        String requestData = objectMapper.writeValueAsString(requestJson);

//        String requestData = "{\"prompt\": \"Summarize the following text:\\n" + JSONObject.quote(inputText) + "\", \"max_tokens\": 50, \"temperature\": 0.7}";
//        String requestData = "{\"prompt\": \"Summarize the following text:\\n" + inputText.replace("\"", "\\\"") + "\", \"max_tokens\": 50, \"temperature\": 0.7}";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                chatGptApiEndpoint,
                HttpMethod.POST,
                new HttpEntity<>(requestData, headers),
                String.class
        );

        String responseBody = response.getBody();
        String generatedText = extractGeneratedText(responseBody);

        return generatedText;
    }

    private String extractGeneratedText(String responseBody) {
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray choicesArray = jsonResponse.getJSONArray("choices");
        if (choicesArray.length() > 0) {
            JSONObject firstChoice = choicesArray.getJSONObject(0);
            String generatedText = firstChoice.getString("text");
            return generatedText;
        } else {
            return "No generated text available";
        }
    }

}
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + chatGptApiKey);
//
//        String prompt = "Summarize the following text:\n" + inputText;
//
//        String requestData = "{\"prompt\": \"Summarize the following text:\\n" + inputText.replace("\"", "\\\"") + "\", \"max_tokens\": 50, \"temperature\": 0.7}";
//
//        String requestData = "{\"messages\": [{\"role\": \"system\", \"content\": \"Your role is to summarize the string.\"}, {\"role\": \"user\", \"content\": \"이 긴 문자열을 필요없는 내용은 버리고 요약해줘 " + inputText.replace("\"", "\\\"") + "\"}]}";
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(
//                chatGptApiEndpoint,
//                HttpMethod.POST,
//                new HttpEntity<>(requestData, headers),
//                String.class
//        );
//
//        String responseBody = response.getBody();
//        String generatedText = extractGeneratedText(responseBody);
//
//        return generatedText;
//    }
//
//    private String extractGeneratedText(String responseBody) {
//        JSONObject jsonResponse = new JSONObject(responseBody);
//        JSONArray choicesArray = jsonResponse.getJSONArray("choices");
//        if (choicesArray.length() > 0) {
//            JSONObject firstChoice = choicesArray.getJSONObject(0);
//            String generatedText = firstChoice.getString("text");
//            return generatedText;
//        } else {
//            return "No generated text available";
//        }
//    }

    // text-davinci-003 사용 429에러 계속 발생
//    public String summarizeText(String inputText) throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + chatGptApiKey);
//
//        String requestData = "{\"messages\": [{\"role\": \"system\", \"content\": \"Your role is to summarize the string.\"}, {\"role\": \"user\", \"content\": \"이 긴 문자열을 필요없는 내용은 버리고 요약해줘 " + inputText.replace("\"", "\\\"") + "\"}]}";
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(
//                chatGptApiEndpoint,
//                HttpMethod.POST,
//                new HttpEntity<>(requestData, headers),
//                String.class
//        );
//        long millisecondsToWait = 30 * 1000; // 30 seconds in milliseconds
//        try {
//            Thread.sleep(millisecondsToWait);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Waited for 30 seconds.");
//        String responseBody = response.getBody();
//        String generatedText = extractGeneratedText(responseBody);
//
//        return generatedText;
//    }
//
//    private String extractGeneratedText(String responseBody) {
//        JSONObject jsonResponse = new JSONObject(responseBody);
//        String generatedText = jsonResponse.getString("text");
//        return generatedText;
//    }



