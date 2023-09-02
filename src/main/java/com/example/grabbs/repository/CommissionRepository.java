package com.example.grabbs.repository;

import com.example.grabbs.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
    // You can add custom query methods here if needed
}
