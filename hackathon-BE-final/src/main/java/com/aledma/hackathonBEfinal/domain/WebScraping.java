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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column(columnDefinition = "LONGTEXT")
    private String sum_text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static WebScraping of(WebScrapingDto scrapingDto){
        return WebScraping.builder()
                .id(scrapingDto.getId())
                .sum_text(scrapingDto.getSum_text())
                .url(scrapingDto.getUrl())
                .build();
    }

}
