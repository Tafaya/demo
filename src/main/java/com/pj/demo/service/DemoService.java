package com.pj.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.pj.demo.model.LogEntry;
import com.pj.demo.repository.LogRepository;

@Service
public class DemoService {

	protected static final String HISTORY_HEADER = "Id | User Name | Search Path | Extension | Created\n";

	private final LogRepository logRepository;

	private final ResourceLoader resourceLoader;

	public DemoService(LogRepository logRepository, ResourceLoader resourceLoader) {
		this.logRepository = logRepository;
		this.resourceLoader = resourceLoader;
	}

	/**
	 * Returns the list of unique files with the given extension
	 * on the given path (recursively)
	 *
	 * @param path the root path of the search
	 * @param extension files with this extension will be considered during the search
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
			stream.filter(file -> Files.isRegularFile(file) && file.getFileName().toString().endsWith("." + extension))
				.forEach(regularFilePath -> uniqueFiles.add(regularFilePath.getFileName().toString()));
		}

		List<String> orderedUniqueFiles = uniqueFiles.stream().sorted().toList();

		StringBuilder stringBuilder = new StringBuilder();
		for (String fileName : orderedUniqueFiles) {
			stringBuilder.append(fileName + "\n");
		}

		return stringBuilder.toString();
	}

	/**
	 * Returns the history of previous searches
	 *
	 * @return list of previous searches
	 */
	public String getHistory() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(HISTORY_HEADER);

		for (LogEntry logEntry : logRepository.findAll()) {
			stringBuilder.append(logEntry.toString() + "\n");
		}

		return stringBuilder.toString();
	}

	/**
	 * Returns the documentation for the application endpoints
	 *
	 * @return endpoint documentation
	 */
	public String getDoc() throws IOException {
		ClassPathResource resource = new ClassPathResource("doc/doc.html");
		InputStream inputStream = resource.getInputStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
		}

		reader.close();

		return stringBuilder.toString();
	}

	/**
	 * Generates a sample folder tree with files to the linux user's home folder
	 *
	 * @param extension this extension will appear mainly
	 * @return the generated tree representation
	 */
	public String gen(String extension) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();

		String baseFolder = System.getProperty("user.home") + "/testfolder";

		if (!new File(baseFolder).exists()) {
			Files.createDirectories(Paths.get(baseFolder));

			stringBuilder.append("Test folder: " + baseFolder + "\n\n");
			Random random = new Random();

			int maxDepth = random.nextInt(3) + 2;
			int numOfFiles = random.nextInt(5) + 3;

			generate(random, extension, baseFolder, baseFolder, 1, 0, maxDepth, numOfFiles);

			createTree(new File(baseFolder), stringBuilder, 1);
			return stringBuilder.toString();
		}

		stringBuilder.append("Test folder already exists at: " + baseFolder);
		return stringBuilder.toString();
	}

	private void generate(Random random, String extension, String baseFolder, String actFolder, int actFolderNum, int actDepth, int maxDepth, int numOfFiles)
		throws IOException {
		if (numOfFiles == 0) {
			return;
		}

		if (random.nextInt(3) > 0) {
			if (random.nextInt(4) == 0) {
				// Creating file not with the given extension
				Files.createFile(Path.of(actFolder + "/" + numOfFiles + "." + extension + "x"));
			} else {
				Files.createFile(Path.of(actFolder + "/" + numOfFiles + "." + extension));
			}

			numOfFiles--;
		}

		if (actDepth == maxDepth) {
			actFolder = baseFolder;
			actDepth = 0;
		} else {
			actDepth++;
			actFolder = actFolder + "/folder" + actFolderNum++;

			Files.createDirectories(Paths.get(actFolder));
		}

		generate(random, extension, baseFolder, actFolder, actFolderNum, actDepth, maxDepth, numOfFiles);
	}

	public void createTree(File dir, StringBuilder stringBuilder, int actDepth) throws IOException {
		File[] files = dir.listFiles();
		List<File> orderedFiles = Arrays.stream(files).sorted(Comparator.comparing(file -> file.isDirectory() ? 0 : 1)).toList();

		for (File file : orderedFiles) {
			if (file.isDirectory()) {
				if (actDepth != 1) {
					for (int j = 0; j < actDepth - 1; j++) {
						stringBuilder.append("|   ");
					}
				}
				stringBuilder.append("+---" + file.getName() + "\n");

				createTree(file, stringBuilder, actDepth + 1);
			} else {
				if (actDepth != 1) {
					for (int j = 0; j < actDepth - 1; j++) {
						stringBuilder.append("    ");
					}
				}

				stringBuilder.append("*---" + file.getName() + "\n");
			}
		}
	}

}
