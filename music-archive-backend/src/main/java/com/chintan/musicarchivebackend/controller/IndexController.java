package com.chintan.musicarchivebackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.chintan.musicarchivebackend.model.Song;
import com.chintan.musicarchivebackend.services.StorageService;

@Controller
public class IndexController {
	
	private final StorageService storageService;
	
	public IndexController(StorageService storageService) {
		this.storageService = storageService;
	}



	@GetMapping("/")
	public String getHomepage(Model model) {
		List<String> songFileList = storageService.getSongFilesNames();
		List<String> songList = storageService.getSongNames();
		List<Song> songs = new ArrayList<>();
		for(int i=0;i<songList.size();i++) {
			Song s = new Song();
			s.setTitle(songList.get(i));
			s.setFileName(songFileList.get(i));
			songs.add(s);
		}
		model.addAttribute("songs",songs);
		return "index";
	}

}
