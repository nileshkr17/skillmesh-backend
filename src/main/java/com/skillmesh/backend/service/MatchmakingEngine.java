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
        System.out.println("[DEBUG] Region: " + region + ", Queue size: " + (queue == null ? 0 : queue.size()));
        if (queue != null) {
            for (Player p : queue) {
                System.out.println("[DEBUG] Player in queue: " + p.getPlayerId() + ", region: " + p.getRegion() + ", skill: " + p.getSkillRating());
            }
        }
        // Fix: If queue is empty, try all region queues for a match
        if (queue == null || queue.size() < 2) {
            for (String reg : regionQueues.keySet()) {
                PriorityQueue<Player> q = regionQueues.get(reg);
                if (q != null && q.size() >= 2) {
                    System.out.println("[DEBUG] Fallback: Trying region " + reg + " with queue size " + q.size());
                    Player p1 = q.poll();
                    Player p2 = q.poll();
                    Match match = Match.builder()
                            .matchId(UUID.randomUUID().toString())
                            .playerIds(List.of(p1.getPlayerId(), p2.getPlayerId()))
                            .region(reg)
                            .createdAt(Instant.now())
                            .build();
                    System.out.println("[DEBUG] Fallback Match made: " + match.getPlayerIds());
                    return Optional.of(match);
                }
            }
            return Optional.empty();
        }

        Player p1 = queue.poll();
        Player p2 = queue.poll();

        Match match = Match.builder()
                .matchId(UUID.randomUUID().toString())
                .playerIds(List.of(p1.getPlayerId(), p2.getPlayerId()))
                .region(region)
                .createdAt(Instant.now())
                .build();

        System.out.println("[DEBUG] Match made: " + match.getPlayerIds());
        return Optional.of(match);
    }

    public synchronized void removePlayer(String playerId) {
        for (PriorityQueue<Player> queue : regionQueues.values()) {
            queue.removeIf(p -> p.getPlayerId().equals(playerId));
        }
    }

    // Returns true if player is in any region queue
    public boolean isInPool(String playerId) {
        for (PriorityQueue<Player> queue : regionQueues.values()) {
            for (Player p : queue) {
                if (p.getPlayerId().equals(playerId)) return true;
            }
        }
        return false;
    }

    // Returns all players currently in all region queues
    public List<Player> getAllPlayersInPool() {
        List<Player> all = new java.util.ArrayList<>();
        for (PriorityQueue<Player> queue : regionQueues.values()) {
            all.addAll(queue);
        }
        return all;
    }
}

