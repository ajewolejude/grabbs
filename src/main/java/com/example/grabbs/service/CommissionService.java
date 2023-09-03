package com.example.grabbs.service;

import com.example.grabbs.model.Commission;
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

    public CommissionService(CommissionRepository commissionRepository, TyreService tyreService) {
        this.commissionRepository = commissionRepository;
        this.tyreService = tyreService;
    }

    public Commission save(Commission commission) throws NotFoundException {
        Tyre existingTyre = tyreService.findTyreById(commission.getTyre().getId()).get();
        // Step 2: Update the state attribute
        existingTyre.setState("COMMISSIONED");
        // Step 3: Save the updated entity back to the database
        tyreService.update(existingTyre);
        commission.setTyre(existingTyre);
        commission.setApprovalStatus("SUBMITTED");
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
        existingCommission.setApprovalStatus(commission.getApprovalStatus());
        existingCommission.setApprovalComments(commission.getApprovalComments());

        commissionRepository.save(existingCommission);

        return commissionRepository.save(existingCommission);
    }

}
