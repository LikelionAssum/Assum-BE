package com.aledma.hackathonBEfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty(message = "이메일은 필수 값 입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 값 입니다.")
    private String password;
}
