package fr.fms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiTrainingsApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	/*@Test
	void contextLoads() {
	}*/
	@Test
	void testGetTrainingAndTestName() throws Exception{
		this.mockMvc.perform(get("/api/trainings"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Java"));
	}
	@Test
	void testGetTrainingWithId() throws Exception {
		this.mockMvc
				.perform(get("/api/trainings/{id}", "1"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.name").value("Java"));
	}

}
