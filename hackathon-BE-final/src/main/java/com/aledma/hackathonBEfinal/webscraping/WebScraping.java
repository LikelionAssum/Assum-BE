package com.aledma.hackathonBEfinal.webscraping;


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

//    @Column(columnDefinition = "LONGTEXT")
//    private String scraping_text;


    public static WebScraping of(WebScrapingDto scrapingDto){
        return WebScraping.builder()
                .id(scrapingDto.getId())
                .url(scrapingDto.getUrl())
//                .scraping_text(scrapingDto.getScraping_text())
                .build();
    }

}
