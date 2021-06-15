package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@WebMvcTest
class BeerRestControllerIT extends BaseIT{



	@Test
	void findBeersTest() throws Exception {
		mockMvc.perform(get("/api/v1/beer/"))
			   .andExpect(status().isOk());
	}

	@Test
	void findBeerById() throws Exception {
		mockMvc.perform(get("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
			   .andExpect(status().isOk());
	}

	@Test
	void findBeerByUpc() throws Exception {
		mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
			   .andExpect(status().isOk());
	}

}
