package com.aledma.hackathonBEfinal.domain;


import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
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
    private User user;

//    //추가
//    @ManyToMany(mappedBy = "favoriteWebScrapings")
//    private List<User> favoritedByUsers = new ArrayList<>();

    public static WebScraping of(WebScrapingDto scrapingDto){
        return WebScraping.builder()
                .sum_text(scrapingDto.getSum_text())
                .url(scrapingDto.getUrl())
                .build();
    }

}
