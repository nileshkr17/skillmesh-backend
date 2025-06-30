package com.skillmesh.backend.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.skillmesh.backend.model.Match;
import com.skillmesh.backend.model.Player;

@Service
public class MatchmakingEngine {

    private final Map<String, PriorityQueue<Player>> regionQueues = new ConcurrentHashMap<>();

    public synchronized void addPlayer(Player player) {
        regionQueues
            .computeIfAbsent(player.getRegion(), k -> new PriorityQueue<>(Comparator.comparingInt(Player::getSkillRating)))
            .add(player);
    }

    public synchronized Optional<Match> tryMatch(String region) {
        PriorityQueue<Player> queue = regionQueues.get(region);
        if (queue == null || queue.size() < 2) return Optional.empty();

        Player p1 = queue.poll();
        Player p2 = queue.poll();

        Match match = Match.builder()
                .matchId(UUID.randomUUID().toString())
                .playerIds(List.of(p1.getPlayerId(), p2.getPlayerId()))
                .region(region)
                .createdAt(Instant.now())
                .build();

        return Optional.of(match);
    }

    public synchronized void removePlayer(String playerId) {
        for (PriorityQueue<Player> queue : regionQueues.values()) {
            queue.removeIf(p -> p.getPlayerId().equals(playerId));
        }
    }
}

