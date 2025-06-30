package com.skillmesh.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document("matches")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Match {
    @Id
    private String matchId;

    private List<String> playerIds;
    private String region;
    private Instant createdAt;
}
