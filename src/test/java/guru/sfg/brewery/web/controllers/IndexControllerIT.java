package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@WebMvcTest
public class IndexControllerIT extends BaseIT {

	@Test
	void testGetIndexSlash() throws Exception {
		mockMvc.perform(get("/"))
			   .andExpect(status().isOk());
	}
}
