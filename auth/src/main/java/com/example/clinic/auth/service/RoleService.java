package com.example.clinic.auth.service;


import com.example.clinic.auth.entity.RoleEntity;
import com.example.clinic.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    RoleEntity getByRoleId(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    RoleEntity getByRoleName(String roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }
}
