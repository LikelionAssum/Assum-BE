package com.aledma.hackathonBEfinal.webscraping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebScrapingDto {

    private Long id;

    @NotEmpty(message = "Url은 필수 입력 값입니다.")
    // url 정규표현식 어떻게 구현?
    private String url;

//    private String scraping_text;
}
