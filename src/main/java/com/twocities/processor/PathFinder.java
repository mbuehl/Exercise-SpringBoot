package com.twocities.processor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Buehl
 */
public final class PathFinder
{
    int recursCnt = 0;
    Set<String> visited;

    Map<String, Set<String> > ties;

/** Constructor:  passing map as an input parm with each city connections:  <city name, Set <String> connections>
 *
  * @param ties
 */

    public PathFinder(final Map<String, Set<String> > ties)
    {
        this.ties = ties;
        visited = new HashSet<>();
    }

/** Making recursive calls to determine about whether there is a path between origin and destination city
 *  EXIT ON:
 *      - found destination city
 *      - exhausted list of 'ties' map entries
 *
 * @param origin
 * @param nextTgt           - maintain the current point of origin during recursive calls
 * @param destination
 * @return
 */
    public boolean areCitiesConected(String origin, String nextTgt, String destination)
    {
        boolean res = false;

//Hard stop
        if(ties.get(origin) == null || ties.get(destination) == null)
            return false;
//All is well
        if(nextTgt == null)         //first time call
        {
            recursCnt = 0;          //variable reflecting depth of the call
            visited.clear();
        }
        else
        {
            recursCnt++;
            visited.add(nextTgt!=null?nextTgt:origin);
        }

//test   System.out.printf("%,d ", recursCnt);

        if(ties.get((nextTgt!=null?nextTgt:origin)).contains(destination))
            res = true;                                                     //found it OK
        else
        {
            for(String linked: ties.get((nextTgt!=null?nextTgt:origin)))
            {
                if(!linked.equals(origin) && !visited.contains(linked))
                    if((res = areCitiesConected(origin, linked, destination)))
                        break;
            }
        }
        return res;
    }

    public Map<String, Set<String>> getTies() {
        return ties;
    }
}
