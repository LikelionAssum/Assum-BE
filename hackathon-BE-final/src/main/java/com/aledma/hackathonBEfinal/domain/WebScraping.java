package com.aledma.hackathonBEfinal.domain;


import com.aledma.hackathonBEfinal.dto.WebScrapingDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // 무한 순환 참조 방지, 양쪽 모두 직렬화를 유지
public class WebScraping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String title;

    @Column
    private String[] keyword;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    //url
    @Column(columnDefinition = "LONGTEXT")
    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    
    // 생성일자 추가
    @CreatedDate
    @Column
    private LocalDateTime createDate;


    public static WebScraping of(WebScrapingDto scrapingDto){
        return WebScraping.builder()
                .title(scrapingDto.getTitle())
                .keyword(scrapingDto.getKeyword())
                .text(scrapingDto.getText())
                .link(scrapingDto.getLink())
                .build();
    }

}
