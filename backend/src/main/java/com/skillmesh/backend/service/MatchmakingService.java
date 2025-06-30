package com.skillmesh.backend.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillmesh.backend.model.Match;
import com.skillmesh.backend.model.Player;
import com.skillmesh.backend.repository.MatchRepository;
import com.skillmesh.backend.repository.PlayerRepository;

@Service
public class MatchmakingService {

    @Autowired private MatchmakingEngine engine;
    @Autowired private RegionGraphService regionGraph;
    @Autowired private MatchRepository matchRepository;
    @Autowired private PlayerRepository playerRepository;

    public void join(Player player) {
        player.setJoinTime(Instant.now());
        player.setActive(true);
        playerRepository.save(player);
        engine.addPlayer(player);
    }

    public Optional<Match> tryMatch(Player player) {
        List<String> candidateRegions = regionGraph.getNearbyRegions(player.getRegion(), 150);
        for (String region : candidateRegions) {
            Optional<?> matchOpt = engine.tryMatch(region);
            if (matchOpt.isPresent() && matchOpt.get() instanceof Match) {
                Match match = (Match) matchOpt.get();
                matchRepository.save(match);
                return Optional.of(match);
            }
        }
        return Optional.empty();
    }

    public void leave(String playerId) {
        engine.removePlayer(playerId);
        playerRepository.findById(playerId).ifPresent(p -> {
            p.setActive(false);
            playerRepository.save(p);
        });
    }
}

