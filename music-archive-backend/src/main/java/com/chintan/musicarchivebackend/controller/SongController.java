package com.chintan.musicarchivebackend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chintan.musicarchivebackend.model.Song;
import com.chintan.musicarchivebackend.repository.SongRepository;
import com.chintan.musicarchivebackend.services.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class SongController {
	
	private final FileService fileService;
	private final SongRepository songRepository;
	
	@Autowired
	public SongController(FileService fileService, SongRepository songRepository) {
		this.fileService = fileService;
		this.songRepository = songRepository;
	}
	
	@GetMapping("/songs")
	public ResponseEntity<List<Song>> getSongs(){
		return ResponseEntity.ok(songRepository.findAll());
	}
	
	@GetMapping("/song/{id}")
	public ResponseEntity<Song> getSong(@PathVariable("id") String id) {
		Optional<Song> songOptional =  songRepository.findById(id);
		if(songOptional.isPresent()) {
			return ResponseEntity.ok(songOptional.get());
		}
		else {
			return ResponseEntity.notFound().build(); 
		}
	}
	
	@PostMapping(path="/song", consumes= {"multipart/form-data"})
	public ResponseEntity<?> createSong(@RequestPart("song") String songData, @RequestPart("file") MultipartFile file) throws IOException {
		ObjectMapper objectmapper = new ObjectMapper();
		Song song = objectmapper.readValue(songData, Song.class);
		if (songRepository.existsSongByTitleEquals(song.getTitle()) || songRepository.existsSongByFileNameEquals(file.getOriginalFilename())) {
			return ResponseEntity.badRequest().body("taken");
		}
		else {
			System.out.println("uploading file");
			try {
				fileService.save(file);
			} catch(Exception e) {
				System.out.println("Problem in File Upload Error : "+ e.getMessage());
				return ResponseEntity.internalServerError().build();
			}
			song.setFileName(file.getOriginalFilename());
			Song insertedSong = songRepository.insert(song);
			
			return new ResponseEntity<Song>(insertedSong, HttpStatus.CREATED);
		}
	}
	
	@PutMapping(path="/song/{id}")
	public ResponseEntity<Song> updataSong(@PathVariable("id") String id, @RequestBody Song songData) {
		Optional<Song> songOptional = songRepository.findById(id);
		
		if(songOptional.isPresent()) {
			Song song = songOptional.get();
			if(songData.getTitle()!= null) {
				song.setTitle(songData.getTitle());
			}
			song.setFavorited(songData.isFavorited());
			songRepository.save(song);
			
			return ResponseEntity.ok(song);
			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping(path="/song/{id}")
	public ResponseEntity<Song> deleteSong(@PathVariable("id") String id) {
		if(songRepository.existsById(id)) {
			songRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
