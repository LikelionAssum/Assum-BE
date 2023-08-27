package com.aledma.hackathonBEfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebScrapingDto {

    @NotEmpty(message = "Url은 필수 입력 값입니다.")
    // url 정규표현식 어떻게 구현?
    private String url;

    private String sum_text;

    private LocalDateTime createDate;

}
