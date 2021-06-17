package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.Util;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by sousaJ on 15/06/2021
 * in package - guru.sfg.brewery.web.controllers
 **/
@SpringBootTest
class BeerRestControllerIT extends BaseIT {

	@Autowired
	BeerRepository beerRepository;

	@Autowired
	BeerOrderRepository beerOrderRepository;

	@DisplayName("Delete Beer API Integration tests")
	@Nested
	class DeleteTests {

		private final String DELETE_BEER_URL =  BEER_BASE_API_URL + "beer/" + util.saveAndFlushUtilBeer().getId();

		@Test
		void deleteBeerWithHttpBasic() throws Exception {
			mockMvc.perform(delete(DELETE_BEER_URL).with(httpBasic(ADMIN, ADMIN_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}


		@Test
			void deleteBeerWithHttpBasicUserRole() throws Exception {
				mockMvc.perform(delete(DELETE_BEER_URL).with(httpBasic(USER, USER_PASSWORD)))
					   .andExpect(status().isForbidden());
		}

		@Test
		void deleteBeerWithHttpBasicCustomerRole() throws Exception {
			mockMvc.perform(delete(DELETE_BEER_URL).with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
				   .andExpect(status().isForbidden());
		}

		@Test
		void deleteBeerWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(delete(DELETE_BEER_URL))
				   .andExpect(status().isUnauthorized());
		}

	}

	@DisplayName("Find beer by Id API Integration Tests")
	@Nested
	class FindBeerByIdTests {

		private final String FIND_BEER_BY_ID_URL =  BEER_BASE_API_URL + "beer/" + util.saveAndFlushUtilBeer().getId();

		@Test
		void findBeerByIdWithHttpBasicAdminRole() throws Exception {
			mockMvc.perform(get(FIND_BEER_BY_ID_URL)
									.with(httpBasic(ADMIN, ADMIN_PASSWORD))
						   )
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeerByIdWithHttpBasicUserRole() throws Exception {
			mockMvc.perform(get(FIND_BEER_BY_ID_URL)
									.with(httpBasic(USER, USER_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeerByIdWithHttpBasicCustomerRole() throws Exception {
			mockMvc.perform(get(FIND_BEER_BY_ID_URL)
									.with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeerByIdWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(get(FIND_BEER_BY_ID_URL))
				   .andExpect(status().isUnauthorized());
		}

	}

	@DisplayName("Find Beers API Integration Tests")
	@Nested
	class FindBeersTests {

		private final String FIND_BEERS_URL = BEER_BASE_API_URL + "/beer";

		@Test
		void findBeersTestWithHttpBasicAdminRole() throws Exception {
			mockMvc.perform(get(FIND_BEERS_URL).with(httpBasic(ADMIN, ADMIN_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeersTestWithHttpBasicCustomerRole() throws Exception {
			mockMvc.perform(get(FIND_BEERS_URL).with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeersTestWithHttpBasicUserRole() throws Exception {
			mockMvc.perform(get(FIND_BEERS_URL).with(httpBasic(USER, USER_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeersTestWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(get(FIND_BEERS_URL))
				   .andExpect(status().isUnauthorized());
		}
	}


	@DisplayName("Find Beers By Upc API Integration Tests")
	@Nested
	class FindBeersByUpcTests {

		private final String FIND_BEERS_BY_UPC_URL = BEER_BASE_API_URL + "beerUpc/" + util.saveAndFlushUtilBeer().getUpc();

		@Test
		void findBeersTestWithHttpBasicAdminRole() throws Exception {
			mockMvc.perform(get(FIND_BEERS_BY_UPC_URL).with(httpBasic(ADMIN, ADMIN_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeersTestWithHttpBasicCustomerRole() throws Exception {
			mockMvc.perform(get(FIND_BEERS_BY_UPC_URL).with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeersTestWithHttpBasicUserRole() throws Exception {
			mockMvc.perform(get(FIND_BEERS_BY_UPC_URL).with(httpBasic(USER, USER_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}

		@Test
		void findBeersTestWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(get(FIND_BEERS_BY_UPC_URL))
				   .andExpect(status().isUnauthorized());
		}
	}



	@Test
	void findBeerFormAdmin() throws Exception {
		mockMvc.perform(get("/beers").param("beerName", "")
									 .with(httpBasic(ADMIN, ADMIN_PASSWORD)))
			   .andExpect(status().isOk());
	}
}
