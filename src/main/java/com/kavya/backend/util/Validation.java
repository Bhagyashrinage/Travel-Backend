package com.kavya.backend.util;

import com.kavya.backend.dto.UserReuqest;
import com.kavya.backend.exception.ExistDataException;
import com.kavya.backend.repositories.RoleRepository;
import com.kavya.backend.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class Validation {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Validation(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void userValidation(UserReuqest userRequest) throws Exception {

        if (!StringUtils.hasText(userRequest.getFirstName())) {
            throw new IllegalArgumentException("first name is invalid");
        }

        if (!StringUtils.hasText(userRequest.getLastName())) {
            throw new IllegalArgumentException("last name is invalid");
        }

        if (!StringUtils.hasText(userRequest.getEmail()) || !userRequest.getEmail().matches(ConstantsUtil.EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is invalid");
        } else {
            boolean userEmail = userRepository.existsByEmail(userRequest.getEmail());
            if (userEmail) {
                throw new ExistDataException("Email already exist");
            }
        }

        if (!StringUtils.hasText(userRequest.getMobNo()) || !userRequest.getMobNo().matches(ConstantsUtil.MOBNO_REGEX)) {
            throw new IllegalArgumentException("mobno is invalid");
        }

        if (CollectionUtils.isEmpty(userRequest.getRoles())) {
            throw new IllegalArgumentException("role is invalid");
        } else {

            List<Integer> roleIds = roleRepository.findAll().stream().map(r -> r.getRoleId()).toList();

            List<Integer> invalidReqRoleids = userRequest.getRoles().stream().map(r -> r.getRoleId())
                    .filter(roleId -> !roleIds.contains(roleId)).toList();

            if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
                throw new IllegalArgumentException("role is invalid" + invalidReqRoleids);
            }
        }
    }

}
