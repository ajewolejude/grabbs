package com.example.grabbs.repository;

import com.example.grabbs.model.Tyre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TyreRepository extends JpaRepository<Tyre, Long> {
    Tyre findTyreBySerialNumber(String serialNumber);
    Tyre findTyreByTakId(String takId);
    //List<Tyre> findTyreByState(String state);
    List<Tyre> findTyresByState(String state);
    int countByCreatedDateBetween(LocalDateTime atStartOfDay, LocalDateTime atTime);

    List<Tyre> getTyresByState(String state);

    List<Tyre> findByStateOrderByBrandDesc(String state);
}
