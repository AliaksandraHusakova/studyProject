package com.innowise.userinfoservice.service;

import com.innowise.userinfoservice.exception.RoleNotFoundException;
import com.innowise.userinfoservice.model.entity.Role;
import com.innowise.userinfoservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }
}
