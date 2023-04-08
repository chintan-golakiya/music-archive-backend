package com.chintan.musicarchivebackend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StorageService {
	
	public List<String> getSongNames() {
		List<String> list = new ArrayList();
		list.add("kesariya");
		list.add("yeh sham mastani");
		list.add("Blue Eyes");
		
		return list;
	}
	
	public List<String> getSongFilesNames() {
		List<String> list = new ArrayList();
		list.add("Kesariya(PagalWorld.com.se).mp3");
		list.add("old_kishore_Kumar_collection (30).mp3");
		list.add("Blue_Eyes.mp3");
		
		return list;
	}
}
