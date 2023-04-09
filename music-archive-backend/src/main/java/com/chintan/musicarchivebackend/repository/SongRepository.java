package com.chintan.musicarchivebackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chintan.musicarchivebackend.model.Song;

public interface SongRepository extends MongoRepository<Song, String>{
	
	boolean existsSongByFileNameEquals(String fileName);
	boolean existsSongByTitleEquals(String title);
}
