package com.example.grabbs.controller.rest;

import com.example.grabbs.model.Action;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.service.ActionService;
import com.example.grabbs.service.TyreService;
import com.example.grabbs.util.ActionSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tyre")
public class TyreRestController {

    private final TyreService tyreService;

    private final ActionService actionService;

    @Autowired
    public TyreRestController(TyreService tyreService, ActionService actionService) {
        this.tyreService = tyreService;
        this.actionService = actionService;
    }

    @GetMapping("/{id}")
    public Tyre getTyreById(@PathVariable Long id) {
        return tyreService.findTyreById(id).get();
    }

    // Add other REST endpoints for creating, updating, deleting tyres if needed

    @GetMapping("/{id}/actions")
    public ResponseEntity<List<Action>> getActionsForTyre(@PathVariable Long id) {
        List<Action> actions = actionService.getByTyreIdOrderByCreatedDateDesc(id);  // or any method that returns the list
        return ResponseEntity.ok(actions);
    }
}
