package com.kavya.backend.services.impl;

import com.kavya.backend.dto.LoginRequest;
import com.kavya.backend.dto.LoginResponce;
import com.kavya.backend.dto.UserReuqest;
import com.kavya.backend.entities.Role;
import com.kavya.backend.entities.User;
import com.kavya.backend.repositories.RoleRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

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
        modelMapper.typeMap(UserReuqest.class, User.class).addMappings(m -> m.skip(User::setRoles));
        User user = modelMapper.map(userRequest, User.class);
        setRole(userRequest, user);
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

    private void setRole(UserReuqest userRequest, User user) {
        List<Integer> roleIds = userRequest.getRoles().stream()
                .map(UserReuqest.RoleDto::getRoleId)
                .toList();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        user.setRoles(roles);
    }
}
