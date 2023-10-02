package com.example.grabbs.repository;

import com.example.grabbs.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    List<Truck> findAllByOrderByCreatedDateDesc();

    Truck findTruckByVin(String vin);

    Truck findTruckByTakId(String takId);

    int countTrucksByCreatedDateBetween(LocalDateTime atStartOfDay, LocalDateTime atTime);
}