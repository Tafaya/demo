package com.pj.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.pj.demo.model.LogEntry;
import com.pj.demo.repository.LogRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

	@Mock
	private LogRepository logRepository;

	@Mock
	private ResourceLoader resourceLoader;

	@InjectMocks
	private DemoService subject;

	@Test
	void getUniqueFiles() throws IOException {
		URL resourceUrl = getClass().getClassLoader().getResource("testfolder");
		String expectedReesult = """
			0.txt
			1.txt
			3.txt
			4.txt
			""";

		String result = subject.getUniqueFiles(resourceUrl.getPath(), "txt");
		assertNotNull(result);
		assertEquals(expectedReesult, result);
	}

	@Test
	void getHistory() {
		LogEntry entry1 = new LogEntry();
		entry1.setSearchPath("/searchpath");
		entry1.setExtension("txt");
		entry1.setUserName("user1");

		LogEntry entry2 = new LogEntry();
		entry2.setSearchPath("/searchpath2");
		entry2.setExtension("png");
		entry2.setUserName("user2");

		when(logRepository.findAll()).thenReturn(List.of(entry1, entry2));

		String result = subject.getHistory();
		assertNotNull(result);
		assertEquals(DemoService.HISTORY_HEADER + entry1 + "\n" + entry2 + "\n", result);
	}

	@Test
	void getDoc() throws IOException {
		ClassPathResource resource = new ClassPathResource("doc/doc.html");

		InputStream inputStream = resource.getInputStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
		}

		reader.close();

		assertNotNull(stringBuilder.toString(), subject.getDoc().toString());
	}

}