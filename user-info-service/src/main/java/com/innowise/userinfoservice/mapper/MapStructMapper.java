package com.innowise.userinfoservice.mapper;

import com.innowise.userinfoservice.model.dto.EmployeeDTO;
import com.innowise.userinfoservice.model.dto.EmployeeToCsvDTO;
import com.innowise.userinfoservice.model.dto.OperationDTO;
import com.innowise.userinfoservice.model.dto.RoleDTO;
import com.innowise.userinfoservice.model.dto.UserDTO;
import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.model.entity.Operation;
import com.innowise.userinfoservice.model.entity.Role;
import com.innowise.userinfoservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    OperationDTO operationToOperationDTO(Operation operation);

    RoleDTO roleToRoleDTO(Role role);

    UserDTO userToUserDTO(User user);

    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    Set<OperationDTO> operationsToOperationDTOs(Set<Operation> operations);

    List<EmployeeDTO> employeesToEmployeeDTOs(List<Employee> employees);

    Operation operationDTOToOperation(OperationDTO operationDTO);

    Role roleDTOToRole(RoleDTO roleDTO);

    User userDTOToUser(UserDTO userDTO);

    Employee employeeDTOtoEmployee(EmployeeDTO employeeDTO);

    Set<Operation> operationDTOsToOperations(Set<OperationDTO> operationDTOs);

    @Mappings({
            @Mapping(target = "employeeId", source = "id"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "password", source = "user.password"),
            @Mapping(target = "roleId", source = "user.role.id"),
            @Mapping(target = "roleName", source = "user.role.name")
    })
    EmployeeToCsvDTO employeeToEmployeeToCsvDTO(Employee employee);

    @Mappings({
            @Mapping(target = "id", source = "employeeId"),
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "user.username", source = "username"),
            @Mapping(target = "user.password", source = "password"),
            @Mapping(target = "user.role.id", source = "roleId"),
            @Mapping(target = "user.role.name", source = "roleName")
    })
    Employee employeeToCsvDTOToEmployee(EmployeeToCsvDTO employeeToCsvDTO);

    @Mappings({
            @Mapping(target = "employeeId", source = "id"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "password", source = "user.password"),
            @Mapping(target = "roleId", source = "user.role.id"),
            @Mapping(target = "roleName", source = "user.role.name")
    })
    List<EmployeeToCsvDTO> employeesToEmployeeToCsvDTOs(List<Employee> employees);

    @Mappings({
            @Mapping(target = "id", source = "employeeId"),
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "user.username", source = "username"),
            @Mapping(target = "user.password", source = "password"),
            @Mapping(target = "user.role.id", source = "roleId"),
            @Mapping(target = "user.role.name", source = "roleName")
    })
    List<Employee> employeeToCsvDTOsToEmployees(List<EmployeeToCsvDTO> employeeToCsvDTO);
}
