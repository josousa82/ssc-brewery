package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.web.controllers.api
 **/
@SpringBootTest
public class BreweryRestControllerIT extends BaseIT {

	private static final String TEST_URL = "/api/v1/breweries/";

	@Test
	void listBreweriesJsonAdmin() throws Exception {
		mockMvc.perform(get(TEST_URL).with(httpBasic(ADMIN, ADMIN_PASSWORD)))
			   .andExpect(status().is2xxSuccessful());
	}

	@Test
	void listBreweriesJsonCustomer() throws Exception {
		mockMvc.perform(get(TEST_URL).with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
			   .andExpect(status().isOk());
	}

	@Test
	void listBreweriesJsonUser() throws Exception {
		mockMvc.perform(get(TEST_URL).with(httpBasic(USER, USER_PASSWORD)))
			   .andExpect(status().isForbidden());
	}

	@Test
	void listBreweriesJsonNOAUTH() throws Exception {
		mockMvc.perform(get(TEST_URL))
			   .andExpect(status().isUnauthorized());
	}
}
