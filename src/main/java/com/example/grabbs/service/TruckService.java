package com.example.grabbs.service;


import com.example.grabbs.model.Truck;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.repository.TruckRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TruckService{

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TruckRepository truckRepository;


    @Autowired
    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAllByOrderByCreatedDateDesc();
    }

    public Optional<Truck> getTruckById(Long id) {
        return truckRepository.findById(id);
    }

    public Truck findTruckByVin(String vin) {
        return truckRepository.findTruckByVin(vin);
    }

    public Truck save(Truck truck) {
        return truckRepository.save(truck);
    }

    public Truck update( Truck truck) throws NotFoundException {
        Truck existingTruck = truckRepository.findById(truck.getId())
                .orElseThrow(() -> new NotFoundException("Tyre not found with id: " + truck.getId()));

        existingTruck.setBrand(truck.getBrand());
        existingTruck.setModel(truck.getModel());
        existingTruck.setTruckType(truck.getTruckType());
        existingTruck.setLicensePlateNumber(truck.getLicensePlateNumber());
        existingTruck.setVin(truck.getVin());
        existingTruck.setBrand(truck.getBrand());
        existingTruck.setManufactureDate(truck.getManufactureDate());
        existingTruck.setPurchaseDate(truck.getPurchaseDate());
        existingTruck.setOdometer(truck.getOdometer());
        existingTruck.setTakId(truck.getTakId());
        existingTruck.setState(truck.getState());
        existingTruck.setComments(truck.getComments());

        return truckRepository.save(existingTruck);
    }

    public Truck assignTyre( Long truck_id, Tyre tyre) throws NotFoundException {
        Truck existingTruck = truckRepository.findById(truck_id)
                .orElseThrow(() -> new NotFoundException("Truck not found with id: " + truck_id));
        List<Tyre> tyreList = existingTruck.getTyres();
        tyreList.add(tyre);
        existingTruck.setTyres(tyreList);

        return truckRepository.save(existingTruck);
    }

    public Truck unassignTyre( Long truck_id, Tyre tyreToRemove) throws NotFoundException {
        Truck existingTruck = truckRepository.findById(truck_id)
                .orElseThrow(() -> new NotFoundException("Truck not found with id: " + truck_id));
        List<Tyre> tyreInTruckList = existingTruck.getTyres();
        // Using removeIf with a lambda expression
        tyreInTruckList.removeIf(tyreInTruck ->
                Objects.equals(tyreInTruck.getTakId(), tyreToRemove.getTakId()) &&
                        Objects.equals(tyreInTruck.getSerialNumber(), tyreToRemove.getSerialNumber()));


        existingTruck.setTyres(tyreInTruckList);

        return truckRepository.save(existingTruck);
    }

    public void delete(Long id) {
        if (!truckRepository.existsById(id)) {
            throw new IllegalArgumentException("Truck with ID " + id + " does not exist.");
        }

        truckRepository.deleteById(id);
    }

    public int countTrucksCreatedBetween(LocalDate startDate, LocalDate endDate) {
        return truckRepository.countTrucksByCreatedDateBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }

    public Truck findTruckByTakId(String takId) {
        return truckRepository.findTruckByTakId(takId);
    }


    public Optional<Truck> findTruckById(Long id) {
        return truckRepository.findById(id);
    }
}
