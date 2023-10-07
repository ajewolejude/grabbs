package com.example.grabbs.repository;

import com.example.grabbs.model.Action;
import com.example.grabbs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> getByTyreIdOrderByCreatedDateDesc(Long tyre_id);
}