package com.skillmesh.backend.repository;

import com.skillmesh.backend.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {
}
