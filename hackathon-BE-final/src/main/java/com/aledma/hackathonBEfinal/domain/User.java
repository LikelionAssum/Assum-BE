package com.aledma.hackathonBEfinal.domain;

import com.aledma.hackathonBEfinal.OAuth.OAuthProvider;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter // 추가
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // 무한 순환 참조 방지, 양쪽 모두 직렬화를 유지
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private int age;

    private OAuthProvider oAuthProvider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WebScraping> webScrapings = new ArrayList<>();


    @Builder
    public User(String email, OAuthProvider oAuthProvider) {
        this.email = email;
        this.oAuthProvider = oAuthProvider;
    }
    public static User of(UserLoginDto loginDto){
        return User.builder()
                .email(loginDto.getEmail())
                .password(loginDto.getPassword())
                .build();
    }
}
