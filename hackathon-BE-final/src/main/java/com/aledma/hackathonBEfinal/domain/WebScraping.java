package com.aledma.hackathonBEfinal.domain;


import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
public class WebScraping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String url;

    public static WebScraping of(WebScrapingDto scrapingDto){
        return WebScraping.builder()
                .id(scrapingDto.getId())
                .url(scrapingDto.getUrl())
                .build();
    }

}
