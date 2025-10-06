package com.kavya.backend.util;

import com.kavya.backend.dto.UserReuqest;
import com.kavya.backend.exception.ExistDataException;
import com.kavya.backend.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Validation {

    private final UserRepository userRepository;

    public Validation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void userValidation(UserReuqest userRequest) throws Exception {

        if (!StringUtils.hasText(userRequest.getFullName())) {
            throw new IllegalArgumentException("fullname name is invalid");
        }

        if (!StringUtils.hasText(userRequest.getEmail()) || !userRequest.getEmail().matches(ConstantsUtil.EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is invalid");
        } else {
            boolean userEmail = userRepository.existsByEmail(userRequest.getEmail());
            if (userEmail) {
                throw new ExistDataException("Email already exist");
            }
        }

    }

}
