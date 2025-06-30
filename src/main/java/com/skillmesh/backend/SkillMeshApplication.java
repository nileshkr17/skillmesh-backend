package com.skillmesh.backend;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.skillmesh.backend.model.GameMode;
import com.skillmesh.backend.model.Player;
import com.skillmesh.backend.repository.PlayerRepository;

@SpringBootApplication
public class SkillMeshApplication implements org.springframework.boot.CommandLineRunner {

	@Autowired private PlayerRepository playerRepository;

	public static void main(String[] args) {
		SpringApplication.run(SkillMeshApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// playerRepository.save(new Player("player1", 1500, "NA", GameMode.ONE_VS_ONE, Instant.now(), "PlayerOne", true));
	// 	 @Id
    // private String playerId;
    // private int skillRating;
    // private String region;
    // private GameMode gameMode;
    // private Instant joinTime;
    // private String username;
    // private boolean isActive;
	//with all inputs 
		Player player = Player.builder()
				.playerId("player1")
				.skillRating(1500)
				.region("NA")
				.gameMode(GameMode.ONE_VS_ONE)
				.joinTime(Instant.now())
				.username("PlayerOne")
				.isActive(true)
				.build();

		playerRepository.save(player);
		System.out.println("Player saved successfully!");
		
	}
}
