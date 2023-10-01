package com.example.grabbs.service;

import com.example.grabbs.model.Decommission;
import com.example.grabbs.model.Truck;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.repository.DecommissionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DecommissionService {

    @Autowired
    private final DecommissionRepository decommissionRepository;

    @Autowired
    private final TyreService tyreService;
    @Autowired
    private final TruckService truckService;

    public DecommissionService(DecommissionRepository decommissionRepository, TyreService tyreService, TruckService truckService) {
        this.decommissionRepository = decommissionRepository;
        this.tyreService = tyreService;
        this.truckService = truckService;
    }

    public Decommission save(Decommission decommission) throws NotFoundException {
        Tyre existingTyre = tyreService.findTyreById(decommission.getTyre().getId()).get();
        // Step 2: Update the state attribute
        existingTyre.setState("DECOMMISSIONING");
        // Step 3: Save the updated entity back to the database
        tyreService.update(existingTyre);
        decommission.setTyre(existingTyre);
        decommission.setState("SUBMITTED");
        return decommissionRepository.save(decommission);
    }

    public List<Decommission> getAllDecommissions() {
        return decommissionRepository.findAll();
    }

    public Optional<Decommission> getDecommissionById(Long id) {
        return decommissionRepository.findById(id);
    }

    public Decommission update( Decommission decommission) throws NotFoundException {
        Decommission existingDecommission = decommissionRepository.findById(decommission.getId())
                .orElseThrow(() -> new NotFoundException("Decommission not found with id: " + decommission.getId()));
;
        existingDecommission.setApprovalComments(decommission.getApprovalComments());
// Update properties of existingDecommission with values from decommission
        existingDecommission.setTyre(decommission.getTyre());
        existingDecommission.setReasonForDecommissioning(decommission.getReasonForDecommissioning());
        existingDecommission.setDateOfDecommissioning(decommission.getDateOfDecommissioning());
        existingDecommission.setOdometer(decommission.getOdometer());
        existingDecommission.setConditionReport(decommission.getConditionReport());
        existingDecommission.setResponsibleOfficer(decommission.getResponsibleOfficer());
        existingDecommission.setState(decommission.getState());
        existingDecommission.setApprovalComments(decommission.getApprovalComments());

        decommissionRepository.save(existingDecommission);

        return decommissionRepository.save(existingDecommission);
    }

    public void approve(Decommission decommission) {
        decommission.setState("APPROVED");
        decommissionRepository.save(decommission);
    }
    public void complete(Decommission decommission) throws NotFoundException {
        Tyre existingTyre = tyreService.findTyreById(decommission.getTyre().getId()).get();
        // Step 2: Update the state attribute
        existingTyre.setState("UNDER MAINTENANCE");

        tyreService.update(existingTyre);
        decommission.setTyre(existingTyre);
        decommission.setState("COMPLETED");
        decommissionRepository.save(decommission);
    }

    public void cancel(Decommission decommission) throws NotFoundException {
        Tyre existingTyre = tyreService.findTyreById(decommission.getTyre().getId()).get();
        existingTyre.setState("COMMISSIONED");
        // Step 3: Save the updated entity back to the database
        tyreService.update(existingTyre);
        decommission.setState("CANCELLED");
        decommissionRepository.save(decommission);
    }

    public List<Decommission> getByState(String state) {
        return decommissionRepository.getByState(state);
    }
    public List<Decommission> getByTruckId(Long truck_id) {
        return decommissionRepository.getByTruckId(truck_id);
    }
}
