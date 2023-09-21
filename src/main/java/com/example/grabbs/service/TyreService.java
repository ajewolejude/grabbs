package com.example.grabbs.service;

import com.example.grabbs.model.Tyre;
import com.example.grabbs.repository.TyreRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TyreService {


    @Autowired
    private final TyreRepository tyreRepository;

    public TyreService(TyreRepository tyreRepository) {
        this.tyreRepository = tyreRepository;
    }


    public List<Tyre> getAllTyres() {
        return tyreRepository.findAll();
    }

    public Optional<Tyre> findTyreById(Long id) {
        return tyreRepository.findById(id);
    }

    public Tyre findTyreBySerialNumber(String serialNumber) {
        return tyreRepository.findTyreBySerialNumber(serialNumber);
    }


    public List<Tyre> findTyresByState(String state) {
        return tyreRepository.findTyresByState(state);
    }

    public Tyre save(Tyre tyre) {
        tyre.setCreatedDate(LocalDateTime.now());
        return tyreRepository.save(tyre);
    }

    public Tyre update(Tyre tyre) throws NotFoundException {
        Tyre existingTyre = tyreRepository.findById(tyre.getId())
                .orElseThrow(() -> new NotFoundException("Tyre not found with id: " + tyre.getId()));

        existingTyre.setBrand(tyre.getBrand());
        existingTyre.setModel(tyre.getModel());
        existingTyre.setSize(tyre.getSize());
        existingTyre.setTreadDepth(tyre.getTreadDepth());
        existingTyre.setManufactureDate(tyre.getManufactureDate());
        existingTyre.setPurchaseDate(tyre.getPurchaseDate());
        existingTyre.setPurchasePrice(tyre.getPurchasePrice());
        existingTyre.setPosition(tyre.getPosition());
        existingTyre.setOdometer(tyre.getOdometer());
        existingTyre.setTakId(tyre.getTakId());
        existingTyre.setCommissioningDate(tyre.getCommissioningDate());
        existingTyre.setDecommissioningDate(tyre.getDecommissioningDate());
        existingTyre.setComments(tyre.getComments());
        existingTyre.setState(tyre.getState());

        // Set other properties here...

        return tyreRepository.save(existingTyre);
    }

    public void delete(Long id) {
        tyreRepository.deleteById(id);
    }

    public int countTyresCreatedBetween(LocalDate startDate, LocalDate endDate) {
        return tyreRepository.countByCreatedDateBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }
    public Tyre findTyreByTakId(String takId) {
        return tyreRepository.findTyreByTakId(takId);
    }

    public List<Tyre> getTyresByState(String state) {
        return tyreRepository.getTyresByState(state);
    }

}
