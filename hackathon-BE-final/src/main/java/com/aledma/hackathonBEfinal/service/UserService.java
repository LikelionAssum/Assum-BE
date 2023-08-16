package com.aledma.hackathonBEfinal.service;

import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.domain.WebScraping;
import com.aledma.hackathonBEfinal.dto.UserDto;
import com.aledma.hackathonBEfinal.exception.DataNotFoundException;
import com.aledma.hackathonBEfinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void signUp(UserDto userDto){
        User user = User.of(userDto);
        this.userRepository.save(user);
    }

    public Long login(UserDto userDto){
        User user = User.of(userDto);
        String email = user.getEmail();
        String password = user.getPassword();

        Optional<User> optionalUser = this.userRepository.findByEmailAndPassword(email, password);
        User findUser = optionalUser.orElseThrow(() -> new DataNotFoundException("회원정보 없음"));

        return findUser.getId();
    }

    public List<WebScraping> getUserwebscrapingList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));

        return user.getWebScrapings();
    }

}
