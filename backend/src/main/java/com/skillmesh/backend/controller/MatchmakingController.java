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
        matchmakingService.join(player);
        return ResponseEntity.ok("Player joined successfully");

    }
    
    // Endpoint to leave a player

    @PostMapping("/leave")
    public ResponseEntity<?> leave(@RequestParam String playerId) {
        matchmakingService.leave(playerId);
        return ResponseEntity.ok("Left matchmaking.");
    }

    @GetMapping("/match/{playerId}")
    public ResponseEntity<?> match(@PathVariable String playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        return matchmakingService.tryMatch(player)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(204).body("No match yet"));
    }

}
