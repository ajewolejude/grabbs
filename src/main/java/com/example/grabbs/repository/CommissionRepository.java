package com.example.grabbs.repository;

import com.example.grabbs.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
    List<Commission> getByTyreIdAndStateOrderByCreatedDateDesc(Long id, String state);
    // You can add custom query methods here if needed
}
