package com.example.grabbs.repository;

import com.example.grabbs.model.Decommission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DecommissionRepository extends JpaRepository<Decommission, Long> {
    // You can add custom query methods here if needed
}
