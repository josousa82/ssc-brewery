package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Random;

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


	private String URL = "/api/v1/beer/";

	@DisplayName("Delete tests")
	@Nested
	class DeleteTests {
		public Beer beerToDelete () {
			Random rand = new Random();
			return beerRepository.saveAndFlush(Beer.builder()
												   .beerName("Delete Me Beer")
												   .beerStyle(BeerStyleEnum.IPA)
												   .minOnHand(12)
												   .quantityToBrew(200)
												   .upc(String.valueOf(rand.nextInt(99999999)))
												   .build());
		}


		@Test
		void deleteBeerWithHttpBasic() throws Exception {
			mockMvc.perform(delete(URL + beerToDelete().getId()).with(httpBasic(ADMIN, ADMIN_PASSWORD)))
				   .andExpect(status().is2xxSuccessful());
		}


		@Test
			void deleteBeerWithHttpBasicUserRole() throws Exception {
				mockMvc.perform(delete(URL + beerToDelete().getId()).with(httpBasic(USER, USER_PASSWORD)))
					   .andExpect(status().isForbidden());
		}

		@Test
		void deleteBeerWithHttpBasicCustomerRole() throws Exception {
			mockMvc.perform(delete(URL + beerToDelete().getId()).with(httpBasic(CUSTOMER, CUSTOMER_PASSWORD)))
				   .andExpect(status().isForbidden());
		}

		@Test
		void deleteBeerWithHttpBasicNoAuth() throws Exception {
			mockMvc.perform(delete(URL + beerToDelete().getId()))
				   .andExpect(status().isUnauthorized());
		}

	}

	@Test
	void findBeersTest() throws Exception {
		mockMvc.perform(get(URL))
			   .andExpect(status().isOk());
	}

	@Test
	void findBeerById() throws Exception {
		mockMvc.perform(get(URL + new DeleteTests().beerToDelete().getId()))
			   .andExpect(status().isOk());
	}

	@Test
	void findBeerByUpc() throws Exception {
		mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
			   .andExpect(status().isOk());
	}

	@Test
	void findBeerFormAdmin() throws Exception {
		mockMvc.perform(get("/beers").param("beerName", "")
									 .with(httpBasic(ADMIN, ADMIN_PASSWORD)))
			   .andExpect(status().isOk());
	}
}
