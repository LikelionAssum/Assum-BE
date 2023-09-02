package com.aledma.hackathonBEfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebScrapingDto {

    private String title;

    private String[] keyword = new String[4];

    private String text;

    // url
    private String link;

    //추가
    private UserDto user;

}
