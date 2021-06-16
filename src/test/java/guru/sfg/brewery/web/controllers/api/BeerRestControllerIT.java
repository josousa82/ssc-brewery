package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@SpringBootTest
class BeerRestControllerIT extends BaseIT {

	private final String URL_WITH_ID = "/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311";


	@Test
	void findBeersTest() throws Exception {
		mockMvc.perform(get("/api/v1/beer/"))
			   .andExpect(status().isOk());
	}

	@Test
	void findBeerById() throws Exception {
		mockMvc.perform(get(URL_WITH_ID))
			   .andExpect(status().isOk());
	}

	@Test
	void findBeerByUpc() throws Exception {
		mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
			   .andExpect(status().isOk());
	}

}
