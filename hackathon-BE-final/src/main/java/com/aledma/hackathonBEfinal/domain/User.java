package com.aledma.hackathonBEfinal.domain;

import com.aledma.hackathonBEfinal.dto.UserDto;
import com.aledma.hackathonBEfinal.dto.UserLoginDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // 무한 순환 참조 방지, 양쪽 모두 직렬화를 유지
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Email
    private String email;

    @Column
    private String password;

    @Column
    private int age;

    // CascadeType.ALL 설정 해야하나?
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WebScraping> webScrapings = new ArrayList<>();


    public static User of(UserDto userDto){
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .age(userDto.getAge())
                .build();
    }
    public static User of(UserLoginDto loginDto){
        return User.builder()
                .email(loginDto.getEmail())
                .password(loginDto.getPassword())
                .build();
    }
}
