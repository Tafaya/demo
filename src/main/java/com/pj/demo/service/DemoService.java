package com.pj.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.pj.demo.model.LogEntry;
import com.pj.demo.repository.LogRepository;

@Service
public class DemoService {

	private final LogRepository logRepository;

	public DemoService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	/**
	 * Returns the list of unique files with the given extension
	 * on the given path (recursively)
	 *
	 * @param path the root path of the search
	 * @param extension only files with this extension will return
	 * @return the list of file names in alphabetical order
	 * @throws IOException - Exception while traversing the file tree
	 */
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

	/**
	 * Returns the history of previous searches
	 *
	 * @return list of previous searches
	 */
	public String getHistory() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id | User Name | Search Path | Extension | Created\n");

		for (LogEntry logEntry : logRepository.findAll()) {
			sb.append(logEntry.toString() + "\n");
		}

		return sb.toString();
	}

	/**
	 * Returns the documentation for the application endpoints
	 *
	 * @return endpoint documentation
	 */
	public String getDoc() {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}

	/**
	 * Generates a sample folder tree with files to the linux user's home folder
	 *
	 * @return the generated tree representation
	 */
	public String gen() {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}
}
