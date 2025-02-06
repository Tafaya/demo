package com.pj.demo.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pj.demo.service.DemoService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

	@InjectMocks
	private DemoController subject;

	@Mock
	private DemoService demoService;

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
	void getDoc() {
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