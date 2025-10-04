package com.kavya.backend.services;

import com.kavya.backend.dto.LoginRequest;
import com.kavya.backend.dto.LoginResponce;
import com.kavya.backend.dto.UserReuqest;

public interface AuthService {

    public Boolean register(UserReuqest userRequest, String url) throws Exception;
    public LoginResponce login(LoginRequest loginRequest) throws Exception;

}
