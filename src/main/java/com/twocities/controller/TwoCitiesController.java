package com.twocities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twocities.service.ICitiesPathFinderService;

/**
 * @author Michael Buehl
 */

@RestController
public class TwoCitiesController {


	@Autowired
	private ICitiesPathFinderService citiesPathFinderService;

	@RequestMapping(value = "/connected")
	public String areCitiesConnected(@RequestParam("origin") String origin,
									 @RequestParam("destination") String destination)
	{
		boolean pathPresent = citiesPathFinderService.isPathPresent(origin, destination);

		if(pathPresent)
			return "yes";
		else
			return "no";
	}
}
