package com.skillmesh.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.skillmesh.backend.model.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {
    // Custom query methods can be defined here if needed
    // For example, to find players by region or skill rating
}