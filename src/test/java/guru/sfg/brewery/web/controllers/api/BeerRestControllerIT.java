package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.Util;
import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@WebMvcTest
class BeerRestControllerIT extends BaseIT {

	private final String DELETE_URL = "/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311";

	@Test
	void deleteBeerUrl() throws Exception{
		mockMvc.perform(delete(DELETE_URL).param("apiKey", "spring").param("apiSecret",  "guru"))
			   .andExpect(status().isOk());
	}

	@Test
	void deleteBeerUrlBadCreds() throws Exception{
		mockMvc.perform(delete(DELETE_URL).param("apiKey", "spring").param("apiSecret", "guruXXX"))
			   .andExpect(status().isUnauthorized());
	}

	@Test
	void deleteBeer() throws Exception {
		mockMvc.perform(delete(DELETE_URL)
								.header("Api-Key", "spring")
					   			.header("Api-Secret", "guru")
					   ).andExpect(status().isOk());
	}

	@Test
	void deleteBeerBadCred() throws Exception {
		mockMvc.perform(delete(DELETE_URL)
								.header("Api-Key", "spring")
								.header("Api-Secret", "guruxxxx")
					   ).andExpect(status().isUnauthorized());
	}

	@Test
	void deleteBeerNoAuth() throws Exception {
		mockMvc.perform(delete(DELETE_URL)
					   ).andExpect(status().isUnauthorized());
	}

	@Test
	void deleteBeerHttpBasic() throws Exception {
		mockMvc.perform(delete(DELETE_URL)
				.with(httpBasic("spring", "guru"))
					   ).andExpect(status().is2xxSuccessful());
	}



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
