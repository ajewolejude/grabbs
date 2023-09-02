package com.example.grabbs.service;

import com.example.grabbs.model.Commission;
import com.example.grabbs.model.Truck;
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

    public CommissionService(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    public Commission saveCommission(Commission commission) {
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
                .orElseThrow(() -> new NotFoundException("Tyre not found with id: " + commission.getId()));
;
        existingCommission.setApprovalComments(commission.getApprovalComments());
// Update properties of existingCommission with values from commission
        existingCommission.setTyre(commission.getTyre());
        existingCommission.setReasonForCommissioning(commission.getReasonForCommissioning());
        existingCommission.setDecommissioningRequestId(commission.getDecommissioningRequestId());
        existingCommission.setDateOfCommissioning(commission.getDateOfCommissioning());
        existingCommission.setMileage(commission.getMileage());
        existingCommission.setConditionReport(commission.getConditionReport());
        existingCommission.setResponsibleOfficer(commission.getResponsibleOfficer());
        existingCommission.setApprovalStatus(commission.getApprovalStatus());
        existingCommission.setApprovalComments(commission.getApprovalComments());

        commissionRepository.save(existingCommission);

        return commissionRepository.save(existingCommission);
    }

}
