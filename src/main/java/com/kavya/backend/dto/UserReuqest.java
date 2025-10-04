package com.kavya.backend.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserReuqest {

    private String firstName;
    private String lastName;
    private String email;
    private String mobNo;
    private String password;
    private Set<RoleDto> roles;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class RoleDto {
        private Integer roleId;
        private String roleName;
    }
}
