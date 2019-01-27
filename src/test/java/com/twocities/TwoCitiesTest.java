package com.twocities;

import java.io.IOException;
import java.util.*;

import com.twocities.processor.PathFinder;
import com.twocities.service.CitiesPathFinderServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael Buehl
 */

public class TwoCitiesTest {

    Map<String, Set<String>> ties = new HashMap<>();
    String input = "Boston,New York\nPhiladelphia,Newark\nNewark,Boston\nTrenton,Albany\n";

    @Before
    public void init()
    {
        Scanner scn = new Scanner( input).useDelimiter("\\s*\n");

        CitiesPathFinderServiceImpl svc = new CitiesPathFinderServiceImpl();

        while (scn.hasNext())
        {
            String line = scn.nextLine();
            String[] split = line.split(",");

            svc.addToTies(split[0],split[1], ties);
            svc.addToTies(split[1],split[0], ties);
        }
        scn.close();
    }


	@Test
	public void testPathFinderCityTies()
    {
        PathFinder pathFinder = new PathFinder(ties);


		Assert.assertTrue("Failed 1",pathFinder.areCitiesConected("Boston", null, "Newark"));
		Assert.assertTrue("Failed 2",pathFinder.areCitiesConected("Newark", null,"Boston"));

		Assert.assertTrue("Failed 3",pathFinder.areCitiesConected("Boston", null,"Philadelphia"));
		Assert.assertTrue("Failed 4",pathFinder.areCitiesConected("Philadelphia", null,"Boston"));
		
		Assert.assertTrue("Failed 5",pathFinder.areCitiesConected("New York", null,"Newark"));
		Assert.assertTrue("Failed 6",pathFinder.areCitiesConected("Newark", null,"New York"));

		Assert.assertTrue("Failed 7",pathFinder.areCitiesConected("Boston", null,"New York"));
		Assert.assertTrue("Failed 8",pathFinder.areCitiesConected("New York", null,"Boston"));

		Assert.assertTrue("Failed 9",pathFinder.areCitiesConected("Newark", null,"Philadelphia"));
		Assert.assertTrue("Failed 10",pathFinder.areCitiesConected("Philadelphia",null, "Newark"));

		Assert.assertFalse("Failed 11",pathFinder.areCitiesConected("Philadelphia", null,"Albany"));
		Assert.assertFalse("Failed 12",pathFinder.areCitiesConected("Albany", null, "Philadelphia"));
	}

    @Test
    public void negTestForLetterCaseUsedInFinder()
    {
        PathFinder pathFinder = new PathFinder(ties);

        Assert.assertFalse("Failed origin letter case test",
                pathFinder.areCitiesConected("boston", null, "Newark")      //'boston' is not within input sample
                        &&
                !pathFinder.areCitiesConected("Boston", null, "Newark")
        );

    }


    @Test(timeout = 2000)
    public void testTiesMapServiceInitializer() throws IOException
    {
        ties.clear();
        Assert.assertTrue("(1) 'ties' map size test failed", (ties.size() == 0));

        Scanner scn = new Scanner(input).useDelimiter("\\s*\n");

        CitiesPathFinderServiceImpl svc = new CitiesPathFinderServiceImpl();

        svc.buildCityTiesMap(scn, ties);

        Assert.assertTrue("(2) 'ties' map size test failed", (ties.size() == 6));

    }

    @After
    public void tearDown(){
        ties.clear();
    }

}
