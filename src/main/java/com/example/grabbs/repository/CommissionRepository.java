package com.example.grabbs.repository;

import com.example.grabbs.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
    List<Commission> getByTyreIdAndStateOrderByCreatedDateDesc(Long tyre_id, String state);
    List<Commission> findAllByOrderByCreatedDateDesc();
    List<Commission> getByState(String state);
    List<Commission> getByTruckId(Long truck_id);

    List<Commission> getByTruckIdOrderByCreatedDateDesc(Long truck_id);
    // You can add custom query methods here if needed
}
