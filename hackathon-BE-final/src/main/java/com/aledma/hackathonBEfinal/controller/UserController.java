package com.aledma.hackathonBEfinal.controller;

import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.UserDto;
import com.aledma.hackathonBEfinal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User", description = "User관련 로직 작성")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "text 추출", notes = "url에서 text를 추출하는 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 400, message = "회원가입 실패")
    })
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(UserDto userDto){
        try {
            this.userService.signUp(userDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "로그인", notes = "로그인 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "로그인 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<Long> login(UserDto userDto){
        try {
            Long id = this.userService.login(userDto);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "WebScraping list", notes = "WebScraping list api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공")
    })
    @GetMapping("/{userId}/all")
    public ResponseEntity<List<WebScraping>> getUserQuestions(@PathVariable Long userId) {
        List<WebScraping> list = userService.getUserwebscrapingList(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }




}
