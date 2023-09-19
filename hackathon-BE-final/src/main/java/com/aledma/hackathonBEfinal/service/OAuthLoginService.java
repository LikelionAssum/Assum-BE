package com.aledma.hackathonBEfinal.service;

import com.aledma.hackathonBEfinal.JWT.AuthTokens;
import com.aledma.hackathonBEfinal.JWT.AuthTokensGenerator;
import com.aledma.hackathonBEfinal.OAuth.OAuthInfoResponse;
import com.aledma.hackathonBEfinal.OAuth.OAuthLoginParams;
import com.aledma.hackathonBEfinal.OAuth.RequestOAuthInfoService;
import com.aledma.hackathonBEfinal.domain.User;
import com.aledma.hackathonBEfinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long userId = findOrCreateUser(oAuthInfoResponse);
        return authTokensGenerator.generate(userId);
    }

    private Long findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newUser(oAuthInfoResponse));
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return userRepository.save(user).getId();
    }
}
