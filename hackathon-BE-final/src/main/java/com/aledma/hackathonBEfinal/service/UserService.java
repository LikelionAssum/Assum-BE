package com.aledma.hackathonBEfinal.service;

import com.aledma.hackathonBEfinal.JWT.AuthTokens;
import com.aledma.hackathonBEfinal.JWT.AuthTokensGenerator;
import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.repository.UserRepository;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final WebScrapingRepository webScrapingRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById((Long.parseLong(id))).orElseThrow(() -> new UsernameNotFoundException(id));

        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), "", authorities);
    }

    @Transactional
    public List<WebScraping> getUserwebscrapingList() {
        Long userId = authTokensGenerator.extractMemberId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 id로 찾을 수 없습니다 : " + userId));

        return user.getWebScrapings();
    }

    // 추가
    public void setUserAge(int age) {
        Long userId = authTokensGenerator.extractMemberId();
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        user.setAge(age);
        userRepository.save(user);
    }

}



    // jwt 사용 후 사용안할 듯한 코드
    /*@Transactional
    public void signUp(UserDto userDto){
        User user = User.of(userDto);
        String email = user.getEmail();

        Optional<User> findUser = userRepository.findByEmail(email);

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

        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);
        User findUser = optionalUser.orElseThrow(() -> new DataNotFoundException("회원정보 없음"));

        return findUser.getId();
    }*/

