package com.kavya.backend.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserReuqest {

    private String fullName;
    private String email;
    private String password;
}
