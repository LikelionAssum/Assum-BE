package com.aledma.hackathonBEfinal.service;

import com.aledma.hackathonBEfinal.JWT.AuthTokens;
import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.repository.UserRepository;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final WebScrapingRepository webScrapingRepository;

    @Transactional
    public List<WebScraping> getUserwebscrapingList(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        User user = optionalUser.get();

//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new DataNotFoundException("해당 id로 찾을 수 없습니다 : " + userId));

        return user.getWebScrapings();
    }

    // 추가, 액세스 토큰으로부터 사용자 이메일을 가져오는 코드.
    @Transactional
    public String getUserEmailToAccessToken(String token){
        final String kakaoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + token);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\"]");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(kakaoUrl, HttpMethod.POST, request, Map.class);

        Map<String, Object> kakaoAccount = (Map<String, Object>) response.getBody().get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        return email;
    }

    // 추가
    public void setUserAge(String token, int age) {
        String email = this.getUserEmailToAccessToken(token);
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        User user = optionalUser.get();
        user.setAge(age);
    }

}



    // jwt 사용 후 사용안할 듯한 코드
    /*@Transactional
    public void signUp(UserDto userDto){
        User user = User.of(userDto);
        String email = user.getEmail();

        Optional<User> findUser = this.userRepository.findByEmail(email);

        if (findUser.isPresent()){
            throw new DataNotFoundException("이미 존재하는 데이터입니다.");
        } else {
            this.userRepository.save(user);
        }
    }*/

    /*@Transactional
    public Long login(UserDto userDto){
        User user = User.of(userDto);
        String email = user.getEmail();
        String password = user.getPassword();

        Optional<User> optionalUser = this.userRepository.findByEmailAndPassword(email, password);
        User findUser = optionalUser.orElseThrow(() -> new DataNotFoundException("회원정보 없음"));

        return findUser.getId();
    }*/

