package com.innowise.userinfoservice.repository;

import com.innowise.userinfoservice.model.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OperationRepository extends JpaRepository<Operation, Integer>, JpaSpecificationExecutor<Operation> {
}
