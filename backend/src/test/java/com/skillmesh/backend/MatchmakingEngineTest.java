package com.skillmesh.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.skillmesh.backend.model.GameMode;
import com.skillmesh.backend.model.Match;
import com.skillmesh.backend.model.Player;
import com.skillmesh.backend.service.MatchmakingEngine;

@SpringBootTest
public class MatchmakingEngineTest {

    @Autowired
    private MatchmakingEngine engine;
    private GameMode gameMode = GameMode.ONE_VS_ONE;

    @Test
    void testSimple1v1Match() {
        Player p1 = Player.builder().playerId("p1").skillRating(1000).region("India/Asia").gameMode(gameMode).build();
        Player p2 = Player.builder().playerId("p2").skillRating(1020).region("India/Asia").gameMode(gameMode).build();

        engine.addPlayer(p1);
        engine.addPlayer(p2);

        Optional<Match> match = engine.tryMatch("India/Asia");

        assertTrue(match.isPresent());
        assertEquals(2, match.get().getPlayerIds().size());
    }
}
