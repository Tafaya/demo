package com.pj.demo.web;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.demo.model.LogEntry;
import com.pj.demo.repository.LogRepository;

@RestController
public class DemoController {

	private final LogRepository logRepository;

	public DemoController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@GetMapping("getUnique")
	public String getUniqueFiles(String path, String extension) throws IOException {
		LogEntry logEntry = new LogEntry();
		logEntry.setSearchPath(path);
		logEntry.setExtension(extension);
		logEntry.setUserName(System.getProperty("user.name"));
		logRepository.save(logEntry);

		Set<String> uniqueFiles = new HashSet<>();

		try (Stream<Path> stream = Files.walk(Paths.get(path))) {
			stream.filter(file -> Files.isRegularFile(file) && file.getFileName().toString().endsWith(extension))
				.forEach(regularFilePath -> uniqueFiles.add(regularFilePath.getFileName().toString()));
		}

		List<String> orderedUniqueFiles = uniqueFiles.stream().sorted().toList();

		StringBuilder sb = new StringBuilder();
		for (String fileName : orderedUniqueFiles) {
			sb.append(fileName + "\n");
		}

		return sb.toString();
	}

}
