package com.innowise.userinfoservice.service;

import com.innowise.userinfoservice.exception.EmployeeNotFoundException;
import com.innowise.userinfoservice.mapper.MapStructMapper;
import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.model.entity.Operation;
import com.innowise.userinfoservice.model.entity.Role;
import com.innowise.userinfoservice.model.entity.User;
import com.innowise.userinfoservice.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

/**
 * Test for {@link EmployeeService}
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EmployeeService.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private MapStructMapper mapper;

    private static final Integer DEFAULT_ID = 1;
    private static final String LAST_NAME = "Ivanov";
    private static final String FIRST_NAME = "Ivan";
    private static final String MIDDLE_NAME = "Ivanovich";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1970, 1, 1);
    private static final String USERNAME = "ivanIvanov";
    private static final String PASSWORD = "12345";
    private static final String ROLE_NAME = "USER";
    private static final String OPERATION_NAME = "READ";

    private final Employee expectedEmployee = buildEmployee();

    private Employee buildEmployee() {

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

        User user = User.builder()
                .id(DEFAULT_ID)
                .username(USERNAME)
                .password(PASSWORD)
                .role(role)
                .build();

        Employee employee = Employee.builder()
                .id(DEFAULT_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .middleName(MIDDLE_NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .user(user)
                .build();

        return employee;
    }

    /**
     * {@link EmployeeService#findEmployeeById(Integer)}
     */
    @Test
    void returnEmployee_whenId_isCorrect() {

        //Mock
        Mockito.when(employeeRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(expectedEmployee));

        //Test
        Employee returnedEmployee = employeeService.findEmployeeById(DEFAULT_ID);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(returnedEmployee),
                () -> Assertions.assertEquals(expectedEmployee, returnedEmployee)
        );
    }

    /**
     * {@link EmployeeService#findEmployeeById(Integer)}
     */
    @Test
    void throwsEmployeeNotFoundException_whenEmployeeId_isNotCorrect() {

        //Mock
        Mockito.when(employeeRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        //Test Then
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeById(DEFAULT_ID));
    }

    /**
     * {@link EmployeeService#addEmployee(Employee)}
     */
    @Test
    void addEmployeeInDB_whenEmployee_isCorrect() {

        //Mock
        Mockito.when(employeeRepository.save(any())).thenReturn(expectedEmployee);

        //Test
        Employee returnedEmployee = employeeService.addEmployee(expectedEmployee);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(returnedEmployee),
                () -> Assertions.assertEquals(expectedEmployee, returnedEmployee)
        );
    }
}
