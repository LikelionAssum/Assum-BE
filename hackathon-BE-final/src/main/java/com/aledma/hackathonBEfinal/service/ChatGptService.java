package com.aledma.hackathonBEfinal.service;


//import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ChatGptService {

//    private final ChatgptService chatgptService;
//
//    public String summarizeText(String inputText) {
//        String sum_text = chatgptService.sendMessage("다음의 글을 요약해서 정리해주세요.\n" + inputText);
//        return sum_text;
//    }


    @Value("${chatgpt.api.endpoint}")
    private String chatGptApiEndpoint;

    @Value("${chatgpt.api.key}")
    private String chatGptApiKey;

    public String summarizeText(String inputText) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"messages\": [{\"role\":\"system\",\"content\":\"You: " + inputText + "\"}]}");
        Request request = new Request.Builder()
                .url(chatGptApiEndpoint)
                .post(body)
                .addHeader("Authorization", "Bearer " + chatGptApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                // Parsing the response JSON to get the summarized content
                // Here you need to extract the relevant information from the response
                return responseBody;
            } else {
                throw new IOException("Unexpected response from ChatGPT API: " + response);
            }
        }
    }



}
