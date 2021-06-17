package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Brewery;
import guru.sfg.brewery.security.permissionsCustAnnotations.BreweryReadPermission;
import guru.sfg.brewery.services.BreweryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.web.controllers.api
 **/
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BreweryRestController {

	private final BreweryService breweryService;

	@BreweryReadPermission
	@GetMapping(produces = { "application/json" }, path = "breweries")
	public @ResponseBody List<Brewery> getBreweriesJson (){
		return breweryService.getAllBreweries();
	}
}
