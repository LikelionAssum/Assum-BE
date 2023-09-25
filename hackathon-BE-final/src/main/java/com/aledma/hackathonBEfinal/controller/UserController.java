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

    @ApiOperation(value = "WebScraping list", notes = "WebScraping list api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 가져오기 성공"),
            @ApiResponse(code = 400, message = "리스트 가져오기 실패")
    })
    @GetMapping("/{userId}/all")
    public ResponseEntity<List<WebScraping>> getUserQuestions(@PathVariable Long userId) {
        try{
            List<WebScraping> list = userService.getUserwebscrapingList(userId);
            return new ResponseEntity<>(list, HttpStatus.OK);
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
    @GetMapping("/{userId}/home")
    public ResponseEntity<List<WebScraping>> getRecentUserQuestions(@PathVariable Long userId) {
        List<WebScraping> list = userService.getUserwebscrapingList(userId);
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

//  아마 백엔드쪽에서 유저 id를 쉽게 가져오는 방법인 것 같음.
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    Long userId = ((UserDetails) authentication.getPrincipal()).getId();
//
//    List<WebScraping> list = userService.getUserwebscrapingList(userId);
//        return new RsssesponseEntity<>(list, HttpStatus.OK);
    
    // 기존 로그인 방식
     /*@ApiOperation(value = "회원가입", notes = "회원가입 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 400, message = "회원가입 실패")
    })
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        try {
            this.userService.signUp(userDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
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
    public ResponseEntity<Long> login(@RequestBody UserDto userDto) {
        try {
            Long id = this.userService.login(userDto);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

}
