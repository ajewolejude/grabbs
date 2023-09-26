package com.example.grabbs.repository;

import com.example.grabbs.model.Decommission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecommissionRepository extends JpaRepository<Decommission, Long> {
    List<Decommission> getByState(String state);
    // You can add custom query methods here if needed
}
