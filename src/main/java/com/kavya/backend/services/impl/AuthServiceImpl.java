package com.kavya.backend.services.impl;

import com.kavya.backend.dto.LoginRequest;
import com.kavya.backend.dto.LoginResponce;
import com.kavya.backend.dto.UserReuqest;
import com.kavya.backend.entities.User;
import com.kavya.backend.repositories.UserRepository;
import com.kavya.backend.security.CustomUserDetalis;
import com.kavya.backend.services.AuthService;
import com.kavya.backend.services.JwtService;
import com.kavya.backend.util.Validation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validation validator;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // register user
    @Override
    public Boolean register(UserReuqest userRequest, String url) throws Exception {
        log.info("AuthServiceImpl : register() : Execution Start");
        validator.userValidation(userRequest);
        modelMapper.map(UserReuqest.class, User.class);
        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userRepository.save(user);
        log.info("Message : {}","User Register success");
        if (!ObjectUtils.isEmpty(saveUser)) {
            log.info("AuthServiceImpl : register() : Execution End");
            return true;
        }
        log.info("Error : {}","user not saved");
        return false;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }
    // login logic
    @Override
    public LoginResponce login(LoginRequest loginRequest) throws Exception {
        log.info("AuthServiceImpl : login() : Execution Start");
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
        if (authenticate.isAuthenticated()){
            CustomUserDetalis customUserDetalis=(CustomUserDetalis) authenticate.getPrincipal();

            String token= jwtService.generateToken(customUserDetalis.getUser());

            LoginResponce login = LoginResponce.builder()
                    .user(modelMapper.map(customUserDetalis.getUser(), UserReuqest.class))
                    .token(token)
                    .build();
            log.info("AuthServiceImpl : login() : Execution End");
            return login;
        }
        return null;
    }
}
