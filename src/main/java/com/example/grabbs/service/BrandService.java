package com.example.grabbs.service;

import com.example.grabbs.model.Brand;
import com.example.grabbs.model.Brand;
import com.example.grabbs.repository.BrandRepository;
import javassist.NotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public void update(Brand updatedBrand) throws NotFoundException {
        Brand existingBrand = brandRepository.findById(updatedBrand.getId())
                .orElseThrow(() -> new NotFoundException("Brand not found with id: " + updatedBrand.getId()));
        if (brandRepository.existsById(updatedBrand.getId())) {
            existingBrand.setId(updatedBrand.getId());
            existingBrand.setBrandName(updatedBrand.getBrandName());
            existingBrand.setComments(updatedBrand.getComments());
            brandRepository.save(existingBrand);
        }
        // Handle the case where the brand with the given id is not found
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
    

    public Brand findBrandByBrandNameAndType(String brandName, String type) {
        return brandRepository.findBrandByBrandNameAndType(brandName, type);
    }

    public List<Brand> getAllBrandsByTypeAndState(String type, String state) {
        return brandRepository.getAllBrandsByTypeAndState(type,state);
    }
}
