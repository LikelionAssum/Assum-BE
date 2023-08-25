package com.aledma.hackathonBEfinal.domain;


import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // 무한 순환 참조 방지
public class WebScraping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String url;

    @Column(columnDefinition = "LONGTEXT")
    private String sum_text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User user;


    public static WebScraping of(WebScrapingDto scrapingDto){
        return WebScraping.builder()
                .sum_text(scrapingDto.getSum_text())
                .url(scrapingDto.getUrl())
                .build();
    }

}
