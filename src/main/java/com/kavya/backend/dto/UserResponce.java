package com.kavya.backend.dto;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponce {

    private Integer id;
    private String fullName;
    private String email;
}
