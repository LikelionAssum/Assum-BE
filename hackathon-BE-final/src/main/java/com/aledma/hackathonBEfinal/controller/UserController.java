package com.aledma.hackathonBEfinal.controller;

import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.UserDto;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Api(tags = "User", description = "User관련 로직 작성")
public class UserController {
    private final UserService userService;

    // 로그인시 사용자 나이 저장하기 위한 api
    @ApiOperation(value = "사용자 age 저장하기", notes = "set age api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "나이 저장 성공"),
            @ApiResponse(code = 400, message = "나이 저장 실패")
    })
    @PostMapping("/setAge")
    public ResponseEntity<?> setAge(@RequestParam("num") int age) {
        try{
            this.userService.setUserAge(age);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAge")
    @ApiOperation(value = "사용자 age 가져오기", notes = "get age api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "나이 불러오기 성공"),
            @ApiResponse(code = 400, message = "나이 불러오기 실패")
    })
    public ResponseEntity<Integer> getAge() {
        Integer age = this.userService.getUserAge();
        return ResponseEntity.ok(age);
    }

    @ApiOperation(value = "WebScraping list", notes = "WebScraping list api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 가져오기 성공"),
            @ApiResponse(code = 400, message = "리스트 가져오기 실패")
    })
    @GetMapping("/all")
    public ResponseEntity<List<WebScraping>> getUserQuestions() {
        try{
            List<WebScraping> list = userService.getUserwebscrapingList();
            return ResponseEntity.ok(list);
        }catch (DataNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "WebScraping list 최근파일 5개", notes = "WebScraping Recent list api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 가져오기 성공"),
            @ApiResponse(code = 400, message = "리스트 가져오기 실패")
    })
    @GetMapping("/recent")
    public ResponseEntity<List<WebScraping>> getRecentUserQuestions() {
        List<WebScraping> list = userService.getUserwebscrapingList();
        try {
            // 최근 파일 5개 불러오기
            List<WebScraping> recentList = list.subList(list.size() - 5, list.size());
            return new ResponseEntity<>(recentList, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            // 예외 발생은 인덱스 바운드에서 나니까 5개보다 적을 경우를 의미, 따라서 list 자체를 반환
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }
    


}
