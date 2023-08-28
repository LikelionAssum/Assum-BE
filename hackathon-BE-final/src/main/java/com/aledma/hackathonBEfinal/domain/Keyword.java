package com.aledma.hackathonBEfinal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "keywords")
@Setter
@Getter
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="keyword_id")
    private Long keywordId;

    @Column(name ="keyword1")
    private String keyword1;

    @Column(name ="keyword2")
    private String keyword2;

    @Column(name ="keyword3")
    private String keyword3;

    @Column(name ="keyword4")
    private String keyword4;

    @Column(name = "keyword_date")
    private LocalDateTime keywordDate;

    @Column(name = "age")
    private int age;
}
