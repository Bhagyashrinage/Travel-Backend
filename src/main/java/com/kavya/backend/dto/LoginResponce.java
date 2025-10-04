package com.kavya.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponce {

    private UserReuqest user;
    private String token;
}
