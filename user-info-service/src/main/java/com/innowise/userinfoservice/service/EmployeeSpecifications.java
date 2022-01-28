package com.innowise.userinfoservice.service;

import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.model.entity.Employee_;
import com.innowise.userinfoservice.model.entity.Role;
import com.innowise.userinfoservice.model.entity.Role_;
import com.innowise.userinfoservice.model.entity.User;
import com.innowise.userinfoservice.model.entity.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;
import java.util.List;

import static java.util.Optional.ofNullable;

public class EmployeeSpecifications {

    public static Specification<Employee> lastNameLike(String lastName) {
        return (root, query, criteriaBuilder) ->
                ofNullable(lastName)
                        .map(n -> criteriaBuilder.like(root.get(Employee_.MIDDLE_NAME), "%" + n + "%"))
                        .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Employee> firstNameLike(String firstName) {
        return (root, query, criteriaBuilder) ->
                ofNullable(firstName)
                        .map(n -> criteriaBuilder.like(root.get(Employee_.MIDDLE_NAME), "%" + n + "%"))
                        .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Employee> middleNameLike(String middleName) {
        return (root, query, criteriaBuilder) ->
                ofNullable(middleName)
                        .map(n -> criteriaBuilder.like(root.get(Employee_.MIDDLE_NAME), "%" + n + "%"))
                        .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Employee> dateOfBirthGreaterThan(LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) ->
                ofNullable(dateOfBirth)
                        .map(d -> criteriaBuilder.greaterThanOrEqualTo(root.get(Employee_.DATE_OF_BIRTH), d))
                        .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Employee> dateOfBirthLessThan(LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) ->
                ofNullable(dateOfBirth)
                        .map(d -> criteriaBuilder.lessThanOrEqualTo(root.get(Employee_.DATE_OF_BIRTH), d))
                        .orElseGet(criteriaBuilder::conjunction);
    }

    public static Specification<Employee> roleIn(List<Integer> roleIds) {
        return (root, query, criteriaBuilder) -> {

            if (roleIds != null) {
                Join<User, Role> rootRoleJoin = root.join(Employee_.USER).join(User_.ROLE);

                return criteriaBuilder.in(rootRoleJoin.get(Role_.ID)).value(roleIds);
            }

            return criteriaBuilder.conjunction();
        };
    }
}
