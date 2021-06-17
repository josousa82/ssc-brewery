package guru.sfg.brewery;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery
 **/

@Component
public class Util {

	@Autowired
	BeerRepository beerRepository;

//	public static final String UID_KEY = "493410b3-dd0b-4b78-97bf-289f50f6e74f";

	public Beer saveAndFlushUtilBeer () {
		Random rand = new Random();
		return beerRepository.saveAndFlush(Beer.builder()
											   .beerName("Delete Me Beer")
											   .beerStyle(BeerStyleEnum.IPA)
											   .minOnHand(12)
											   .quantityToBrew(200)
											   .upc(String.valueOf(rand.nextInt(99999999)))
											   .build());
	}
}
