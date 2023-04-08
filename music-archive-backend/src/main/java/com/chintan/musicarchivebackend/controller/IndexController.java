package com.chintan.musicarchivebackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chintan.musicarchivebackend.model.Song;
import com.chintan.musicarchivebackend.repository.SongRepository;
import com.chintan.musicarchivebackend.services.FileService;
import com.chintan.musicarchivebackend.services.StorageService;

@Controller
public class IndexController {
	
	private final StorageService storageService;
	private final FileService fileService;
	
	@Autowired
	private SongRepository songRepository;
	
	public IndexController(StorageService storageService, FileService fileService) {
		this.storageService = storageService;
		this.fileService = fileService;
	}



	@GetMapping("/")
	public String getHomepage(Model model) {
		List<Song> songs = songRepository.findAll();
		model.addAttribute("songs",songs);
		return "index";
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file")MultipartFile file, @RequestParam("songName") String songName) {
		System.out.println("To Do : process and save uploaded file");
		fileService.save(file);
		Song s = new Song();
		s.setTitle(songName);
		s.setFileName(fileService.getUploadPath() + file.getOriginalFilename());
		System.out.println("before save :"+s.getTitle());
		Song result = songRepository.save(s);
		System.out.println(result.getTitle());
		return "redirect:/";
	}
}
