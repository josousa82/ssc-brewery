package guru.sfg.brewery.web.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/

@WebMvcTest
class BeerControllerIT extends BaseIT{

	// @WithMockUser("spring") tells spring the user is authenticated, Mocks an authenticated user
	// testing security logic
	@WithMockUser("spring")
	@Test
	void findBeers() throws Exception {
		mockMvc.perform(get("/beers/find"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/findBeers"))
			   .andExpect(model().attributeExists("beer"));
	}

	// authentication with specific user, if user doesn't exists, or is not permitted in the endpoint it fails
	// testing security logic and authentication logic
	@Test
	void findBeersWithHttpBasic() throws Exception {
		mockMvc.perform(get("/beers/find").with(httpBasic("spring", "guru")))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/findBeers"))
			   .andExpect(model().attributeExists("beer"));
	}

	@Test
	void findBeersHasPermitAllAccess() throws Exception {
		mockMvc.perform(get("/beers/find"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/findBeers"))
			   .andExpect(model().attributeExists("beer"));
	}

}
