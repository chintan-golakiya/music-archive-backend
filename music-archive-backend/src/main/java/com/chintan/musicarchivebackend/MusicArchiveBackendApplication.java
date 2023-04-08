package com.chintan.musicarchivebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.chintan.musicarchivebackend.repository.SongRepository;

@SpringBootApplication
public class MusicArchiveBackendApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(MusicArchiveBackendApplication.class, args);
	}

}
