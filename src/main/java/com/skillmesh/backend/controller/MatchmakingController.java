package com.skillmesh.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillmesh.backend.model.Player;
import com.skillmesh.backend.repository.PlayerRepository;
import com.skillmesh.backend.service.MatchmakingService;

@RestController
@RequestMapping("/api/matchmaking")
public class MatchmakingController {
    
    @Autowired private MatchmakingService matchmakingService;
    @Autowired private PlayerRepository playerRepository;


    // Endpoint to join a player
    @PostMapping("/join")
    public ResponseEntity<String> joinPlayer(@RequestBody Player player) {
        if (player == null || player.getPlayerId() == null) {
            return ResponseEntity.badRequest().body("Player or playerId missing");
        }
        if (matchmakingService.isInPool(player.getPlayerId())) {
            return ResponseEntity.status(409).body("Player already in matchmaking pool");
        }
        // Save to DB if not exists
        playerRepository.save(player);
        matchmakingService.join(player);
        return ResponseEntity.ok("Player joined successfully");

    }
    
    // Endpoint to leave a player
    @PostMapping("/leave")
    public ResponseEntity<?> leave(@RequestParam String playerId) {
        if (!matchmakingService.isInPool(playerId)) {
            return ResponseEntity.status(404).body("Player not in matchmaking pool");
        }
        matchmakingService.leave(playerId);
        return ResponseEntity.ok("Left matchmaking.");
    }

    // Try to match a player
    @GetMapping("/match/{playerId}")
    public ResponseEntity<?> match(@PathVariable String playerId) {
        Player player = playerRepository.findById(playerId).orElse(null);
        if (player == null) {
            return ResponseEntity.status(404).body("Player not found");
        }
        if (!matchmakingService.isInPool(playerId)) {
            return ResponseEntity.status(409).body("Player not in matchmaking pool");
        }
        return matchmakingService.tryMatch(player)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(204).body("No match yet"));
    }

    // Endpoint to join all seeded players to the matchmaking pool
    @PostMapping("/join-seeded")
    public ResponseEntity<String> joinAllSeededPlayers() {
        playerRepository.findAll().forEach(matchmakingService::join);
        return ResponseEntity.ok("All seeded players joined the matchmaking pool.");
    }

    // Endpoint to list all players currently in the matchmaking pool
    @GetMapping("/pool")
    public ResponseEntity<?> getMatchmakingPool() {
        return ResponseEntity.ok(matchmakingService.getPoolPlayers());
    }

    // Health check endpoint for API alive status
    @GetMapping("/alive")
    public ResponseEntity<String> alive() {
        //logs
        System.out.println("API is alive check at " + System.currentTimeMillis());
        return ResponseEntity.ok("API is alive");
    }

}
