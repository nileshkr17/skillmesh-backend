package com.skillmesh.backend;

import java.time.Instant;
import java.util.Arrays;

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
		boolean seed = false;
		if (seed) {
			if (playerRepository.count() == 0) {
				playerRepository.saveAll(Arrays.asList(
					Player.builder().playerId("p1").skillRating(1200).region("NA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Alpha").isActive(true).build(),
					Player.builder().playerId("p2").skillRating(1300).region("EU").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Bravo").isActive(true).build(),
					Player.builder().playerId("p3").skillRating(1100).region("ASIA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Charlie").isActive(false).build(),
					Player.builder().playerId("p4").skillRating(1400).region("NA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Delta").isActive(true).build(),
					Player.builder().playerId("p5").skillRating(1250).region("EU").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Echo").isActive(true).build(),
					Player.builder().playerId("p6").skillRating(1350).region("ASIA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Foxtrot").isActive(false).build(),
					Player.builder().playerId("p7").skillRating(1150).region("NA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Golf").isActive(true).build(),
					Player.builder().playerId("p8").skillRating(1450).region("EU").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Hotel").isActive(true).build(),
					Player.builder().playerId("p9").skillRating(1500).region("ASIA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("India").isActive(false).build(),
					Player.builder().playerId("p10").skillRating(1550).region("NA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Juliet").isActive(true).build(),
					Player.builder().playerId("p11").skillRating(1600).region("EU").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Kilo").isActive(true).build(),
					Player.builder().playerId("p12").skillRating(1650).region("ASIA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Lima").isActive(false).build(),
					Player.builder().playerId("p13").skillRating(1700).region("NA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Mike").isActive(true).build(),
					Player.builder().playerId("p14").skillRating(1750).region("EU").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("November").isActive(true).build(),
					Player.builder().playerId("p15").skillRating(1800).region("ASIA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Oscar").isActive(false).build(),
					Player.builder().playerId("p16").skillRating(1850).region("NA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Papa").isActive(true).build(),
					Player.builder().playerId("p17").skillRating(1900).region("EU").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Quebec").isActive(true).build(),
					Player.builder().playerId("p18").skillRating(1950).region("ASIA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Romeo").isActive(false).build(),
					Player.builder().playerId("p19").skillRating(2000).region("NA").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Sierra").isActive(true).build(),
					Player.builder().playerId("p20").skillRating(2050).region("EU").gameMode(GameMode.ONE_VS_ONE).joinTime(Instant.now()).username("Tango").isActive(true).build()
				));
				System.out.println("20 Players seeded successfully!");
			} else {
				System.out.println("Players already exist, skipping seeding.");
			}
		} else {
			System.out.println("Seeding skipped. Set SEED_PLAYERS=true to enable seeding.");
		}
	}
}
