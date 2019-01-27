package com.twocities.service;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;

import com.twocities.processor.PathFinder;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author Michael Buehl
 */
@Component
public class CitiesPathFinderServiceImpl implements ICitiesPathFinderService
{
	private volatile PathFinder pathFinder;
	private Map<String, Set<String>> ties;

	@Value("${cities.graph.file}")
	private String file;


/**	Create 'ties' map for each city of origin with Set of other city connections:
 * <city of origin, Set <String> connections>
*
* @throws IOException
*/
	@PostConstruct
	public void initializeResource() throws IOException
	{
		ties = new HashMap<>();

//Initiate Map of city ties
		Resource resource = new ClassPathResource(file);
		Scanner scn = new Scanner( resource.getInputStream());
		buildCityTiesMap(scn, ties);

//Create processor
		pathFinder = new PathFinder(ties);
	}

/**
 *
 * @param scn
 * @throws IOException
 */
	public void buildCityTiesMap(final Scanner scn, final Map<String, Set<String>> ties) throws IOException
	{
		while (scn.hasNext())
		{
			String line = scn.nextLine();
			String[] split = line.split(",");

			addToTies(split[0],split[1],ties);
			addToTies(split[1],split[0],ties);
		}
		scn.close();
	}


/**	ADD city2 to Set of city1 connections
 *
 * @param city1
 * @param city2
 */
	public void addToTies(final String city1, final String city2, final Map<String, Set<String>> p_ties)
	{
		Set<String> connectedCities;

		if(p_ties.get(city1) != null)
			connectedCities = p_ties.get(city1);
		else
			connectedCities = new HashSet<>();

		connectedCities.add(city2);
		p_ties.put(city1, connectedCities);
	}

/**
 * Call to processor method
 *
 * @see com.twocities.service.ICitiesPathFinderService#isPathPresent(java.lang.String, java.lang.String)
 */
	@Override
	public boolean isPathPresent(String origin, String destination)
	{
		return pathFinder.areCitiesConected(origin, null, destination);
	}

//Getters / setters

	public PathFinder getPathFinder() {
		return pathFinder;
	}
}
