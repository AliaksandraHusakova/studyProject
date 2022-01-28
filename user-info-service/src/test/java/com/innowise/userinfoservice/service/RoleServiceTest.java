package com.innowise.userinfoservice.service;

import com.innowise.userinfoservice.exception.RoleNotFoundException;
import com.innowise.userinfoservice.model.entity.Operation;
import com.innowise.userinfoservice.model.entity.Role;
import com.innowise.userinfoservice.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Test for {@link RoleService}
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RoleService.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    private static final Integer DEFAULT_ID = 1;
    private static final String ROLE_NAME = "USER";
    private static final String OPERATION_NAME = "READ";

    private final Role expectedRole = buildRole();

    private Role buildRole() {

        Set<Operation> operations = Set.of(
                Operation.builder()
                        .id(DEFAULT_ID)
                        .name(OPERATION_NAME)
                        .build()
        );

        Role role = Role.builder()
                .id(DEFAULT_ID)
                .name(ROLE_NAME)
                .operations(operations)
                .build();

        return role;
    }

    /**
     * {@link RoleService#findRoleById(Integer)}
     */
    @Test
    void returnRole_IfId_IsCorrect() {

        Operation operation = new Operation(1, "Test operation 1");
        Operation operation2 = new Operation(2, "Test operation 2");

        Set<Operation> operations = new HashSet<>();

        operations.add(operation);
        operations.add(operation2);

        Role role = new Role(1, "Test role", operations);

        doReturn(Optional.of(role)).when(roleRepository).findById(1);

        Role returnedRole = roleService.findRoleById(1);

        Assertions.assertNotNull(returnedRole, "Role was not found");
        Assertions.assertSame(returnedRole, role, "The role returned was not the same as the mock");
    }

    /**
     * {@link RoleService#findRoleById(Integer)}
     */
    @Test
    void testFindEmployeeByIdNotFound() {
        when(roleRepository.findById(1)).thenThrow(RoleNotFoundException.class);
    }
}
