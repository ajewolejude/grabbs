package com.example.grabbs.service;

import com.example.grabbs.model.Commission;
import com.example.grabbs.model.Truck;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.repository.CommissionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommissionService {

    @Autowired
    private final CommissionRepository commissionRepository;

    @Autowired
    private final TyreService tyreService;
    @Autowired
    private final TruckService truckService;

    public CommissionService(CommissionRepository commissionRepository, TyreService tyreService, TruckService truckService) {
        this.commissionRepository = commissionRepository;
        this.tyreService = tyreService;
        this.truckService = truckService;
    }

    public Commission save(Commission commission) throws NotFoundException {
        Tyre existingTyre = tyreService.findTyreById(commission.getTyre().getId()).get();
        Truck existingTruck = truckService.getTruckById(commission.getTruck().getId()).get();
        // Step 2: Update the state attribute
        existingTyre.setState("COMMISSIONED");
        // Step 3: Save the updated entity back to the database
        tyreService.update(existingTyre);
        commission.setTyre(existingTyre);
        commission.setTruck(existingTruck);
        commission.setState("SUBMITTED");
        return commissionRepository.save(commission);
    }

    public List<Commission> getAllCommissions() {
        return commissionRepository.findAll();
    }

    public Optional<Commission> getCommissionById(Long id) {
        return commissionRepository.findById(id);
    }

    public Commission update( Commission commission) throws NotFoundException {
        Commission existingCommission = commissionRepository.findById(commission.getId())
                .orElseThrow(() -> new NotFoundException("Commission not found with id: " + commission.getId()));
;
        existingCommission.setApprovalComments(commission.getApprovalComments());
// Update properties of existingCommission with values from commission
        existingCommission.setTyre(commission.getTyre());
        existingCommission.setReasonForCommissioning(commission.getReasonForCommissioning());
        existingCommission.setDateOfCommissioning(commission.getDateOfCommissioning());
        existingCommission.setOdometer(commission.getOdometer());
        existingCommission.setConditionReport(commission.getConditionReport());
        existingCommission.setResponsibleOfficer(commission.getResponsibleOfficer());
        existingCommission.setState(commission.getState());
        existingCommission.setApprovalComments(commission.getApprovalComments());

        commissionRepository.save(existingCommission);

        return commissionRepository.save(existingCommission);
    }

    public void approve(Commission commission) {
        commission.setState("APPROVED");
        commissionRepository.save(commission);
    }
    public void complete(Commission commission) {
        commission.setState("COMPLETED");
        commissionRepository.save(commission);
    }

    public void cancel(Commission commission) throws NotFoundException {
        Tyre existingTyre = tyreService.findTyreById(commission.getTyre().getId()).get();
        existingTyre.setState("AVAILABLE");
        // Step 3: Save the updated entity back to the database
        tyreService.update(existingTyre);
        commission.setState("CANCELLED");
        commissionRepository.save(commission);
    }
}
