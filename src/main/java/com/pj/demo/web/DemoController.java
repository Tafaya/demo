package com.pj.demo.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.demo.model.LogEntry;
import com.pj.demo.repository.LogRepository;
import com.pj.demo.service.DemoService;

@RestController
public class DemoController {

	private final DemoService demoService;

	public DemoController(DemoService demoService) {
		this.demoService = demoService;
	}

	@GetMapping("getUnique")
	public ResponseEntity<String> getUniqueFiles(String path, String extension) throws IOException {
		return ResponseEntity.ok(demoService.getUniqueFiles(path, extension));
	}

	@GetMapping("history")
	public ResponseEntity<String> getHistory() {
		return ResponseEntity.ok(demoService.getHistory());
	}

	@GetMapping("doc")
	public ResponseEntity<String> getDoc() {
		return ResponseEntity.ok(demoService.getDoc());
	}

	@PostMapping("gen")
	public ResponseEntity<String> gen() {
		return ResponseEntity.ok(demoService.gen());
	}

}
