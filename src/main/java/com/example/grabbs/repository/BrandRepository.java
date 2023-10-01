package com.example.grabbs.repository;

import com.example.grabbs.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findBrandByBrandNameAndType(String brandName, String type);

    List<Brand> getAllBrandsByTypeAndState(String type, String state);
    // You can add custom query methods if needed
}
