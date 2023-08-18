package com.aledma.hackathonBEfinal.service;

import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.UserDto;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.repository.UserRepository;
import com.aledma.hackathonBEfinal.repository.WebScrapingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //추가
    private final WebScrapingRepository webScrapingRepository;

    @Transactional
    public void signUp(UserDto userDto){
        User user = User.of(userDto);
        String email = user.getEmail();

        Optional<User> findUser = this.userRepository.findByEmail(email);

        if (findUser.isPresent()){
            throw new DataNotFoundException("이미 존재하는 데이터입니다.");
        } else {
            this.userRepository.save(user);
        }
    }

    @Transactional
    public Long login(UserDto userDto){
        User user = User.of(userDto);
        String email = user.getEmail();
        String password = user.getPassword();

        Optional<User> optionalUser = this.userRepository.findByEmailAndPassword(email, password);
        User findUser = optionalUser.orElseThrow(() -> new DataNotFoundException("회원정보 없음"));

        return findUser.getId();
    }

    @Transactional
    public List<WebScraping> getUserwebscrapingList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 id로 찾을 수 없습니다 : " + userId));

        return user.getWebScrapings();
    }



    // 아래 두개 메소드 추가
//    public void addWebScrapingToFavorites(Long userId, Long webScrapingId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
//        WebScraping webScraping = webScrapingRepository.findById(webScrapingId).orElseThrow(() -> new EntityNotFoundException("WebScraping not found"));
//
//        user.getFavoriteWebScrapings().add(webScraping);
//        userRepository.save(user);
//    }
//
//    public List<WebScraping> getFavoriteWebScrapings(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
//        return user.getFavoriteWebScrapings();
//    }

}
