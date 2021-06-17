package guru.sfg.brewery.web.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.RequestPostProcessor;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
// web mvc test annotation will only bring the minimal web layer (controllers and so on) to the context
//@WebMvcTest
@SpringBootTest
class BeerControllerIT extends BaseIT{

	@Test
	void initCreationFormWithSpring() throws Exception {
		mockMvc.perform(get("/beers/new").with(httpBasic(ADMIN, ADMIN_PASSWORD)))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/createBeer"))
			   .andExpect(model().attributeExists("beer"));
	}

	@Test
	void initCreationFormWithUser() throws Exception {
		mockMvc.perform(get("/beers/new").with(httpBasic(USER, USER_PASSWORD)))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/createBeer"))
			   .andExpect(model().attributeExists("beer"));
	}

	@Test
	void initCreationFormWithScott() throws Exception {
		mockMvc.perform(get("/beers/new").with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/createBeer"))
			   .andExpect(model().attributeExists("beer"));
	}

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

	@Test
	void findBeersWithAnonymous() throws Exception {
		mockMvc.perform(get("/beers/find").with(anonymous()))
			   .andExpect(status().isOk())
			   .andExpect(view().name("beers/findBeers"))
			   .andExpect(model().attributeExists("beer"));
	}



}
