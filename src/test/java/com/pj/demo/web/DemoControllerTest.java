package com.pj.demo.web;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.pj.demo.service.DemoService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

	@Mock
	private DemoService demoService;

	@InjectMocks
	private DemoController subject;

	@Test
	void getUniqueFiles() throws Exception {
		String path = "/test";
		String extension = "txt";

		String returnValue = "1.txt";

		when(demoService.getUniqueFiles(path, extension)).thenReturn(returnValue);

		ResponseEntity<String> result = subject.getUniqueFiles(path, extension);

		assertNotNull(result);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void getHistory() {
		String returnValue = "history";

		when(demoService.getHistory()).thenReturn(returnValue);

		ResponseEntity<String> result = subject.getHistory();

		assertNotNull(result);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}


	@Test
	void getDoc() throws IOException {
		String returnValue = "docText";

		when(demoService.getDoc()).thenReturn(returnValue);

		ResponseEntity<String> result = subject.getDoc();

		assertNotNull(result);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void gen() throws Exception {
	}

}