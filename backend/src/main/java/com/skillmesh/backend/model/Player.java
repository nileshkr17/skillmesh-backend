package com.skillmesh.backend.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//why we use @Document?
// It indicates that this class is a MongoDB document, which means it will be stored in a MongoDB collection.
@Document(collection = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Player {
    @Id
    private String playerId;
    private String username;
    private int skillRating;
    private String region;
    private GameMode gameMode;
    private Instant joinTime;
    private boolean isActive;
    
}
