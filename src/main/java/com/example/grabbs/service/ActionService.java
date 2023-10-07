package com.example.grabbs.service;

import com.example.grabbs.model.Action;
import com.example.grabbs.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionService {

    private final ActionRepository actionRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public List<Action> getAllActions() {
        return actionRepository.findAll();
    }

    public Optional<Action> getActionById(Long id) {
        return actionRepository.findById(id);
    }

    public List<Action> getByTyreIdOrderByCreatedDateDesc(Long id) {
        return actionRepository.getByTyreIdOrderByCreatedDateDesc(id);
    }

    public Action save(Action action) {
        // You can add business logic or validation before saving
        return actionRepository.save(action);
    }

    public void deleteAction(Long id) {
        actionRepository.deleteById(id);
    }
}
