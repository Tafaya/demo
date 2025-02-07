package com.pj.demo.web;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pj.demo.request.GenerateRequest;
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
		try {
			String doc = demoService.getDoc();

			return ResponseEntity.ok(doc);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().body("Error while getting documentation!");
		}
	}

	@PostMapping("gen")
	public ResponseEntity<String> gen(@RequestBody GenerateRequest generateRequest) {
		try {
			String result = demoService.gen(generateRequest.getExtension());
			return ResponseEntity.ok(result);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().body("Error while creating test folder!");
		}
	}

}
