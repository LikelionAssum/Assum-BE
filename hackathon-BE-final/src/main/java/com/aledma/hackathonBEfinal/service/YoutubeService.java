package com.aledma.hackathonBEfinal.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.CaptionListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


@Service
public class YoutubeService {

    @Value("${youtube.api.key}")
    private static String youtubeApiKey;

    private static YouTube youtubeService;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public List<Caption> getCaptions(String videoId) throws IOException, GeneralSecurityException {
        YouTube youtubeService = getYoutubeService();

        YouTube.Captions.List request = youtubeService.captions().list(Collections.singletonList("snippet"), videoId);
        System.out.println("Request: " + request);
        request.setKey(youtubeApiKey);
        CaptionListResponse response = request.execute();
        System.out.println("Response: " + response);
        return response.getItems();
    }

    public static YouTube getYoutubeService() throws IOException, GeneralSecurityException {
        if (youtubeService == null) {
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            youtubeService = new YouTube.Builder(httpTransport, JSON_FACTORY, getHttpRequestInitializer())
                    .setApplicationName("assum")
                    .build();
        }
        return youtubeService;
    }

    private static HttpRequestInitializer getHttpRequestInitializer() {
        return request -> {
            // Set API key in the request
            request.getHeaders().set("key", youtubeApiKey);
        };
    }

    //    public List<Video> getVideos(String videoId) throws IOException, GeneralSecurityException {
//        YouTube youtubeService = getYoutubeService();
//
//        YouTube.Videos.List request = youtubeService.videos().list("snippet");
//        request.setId(videoId);
//        VideoListResponse response = request.execute();
//
//        return response.getItems();
//    }
}
