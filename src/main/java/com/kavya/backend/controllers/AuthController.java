package com.kavya.backend.controllers;

import com.kavya.backend.dto.LoginRequest;
import com.kavya.backend.dto.LoginResponce;
import com.kavya.backend.dto.UserReuqest;
import com.kavya.backend.services.AuthService;
import com.kavya.backend.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserReuqest userReuqest, HttpServletRequest  request) throws Exception {
        log.info("AuthController : registerUser() : Execution Start");
        String url = CommonUtil.getUrl(request);
        Boolean register = authService.register(userReuqest, url);
        if (register) {
            log.info("Success : {}","Register Success");
            return CommonUtil.createBuildResponse("Register Success", HttpStatus.OK);
        }
        log.info("AuthController : registerUser() : Exceution End");
        return CommonUtil.createErrorResponse("Register Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        log.info("AuthController : loginUser() : Execution Start");
        LoginResponce loginResponce = authService.login(loginRequest);
        if (ObjectUtils.isEmpty(loginResponce)) {
            log.info("Error : {}","Login Unsuccessful");
            return CommonUtil.createErrorResponseMessage("Login Failed Bad Credentials", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuildResponse(loginResponce, HttpStatus.OK);
    }
}
