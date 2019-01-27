# The two cities 

Provisions
----------
1. Spring Boot v.2.x - pom.xml 
2. Java code - main
3. Java code - test
4. Resource - flat file with comma delimited pairs of cities - representing ties (or paths) between them


Summary
-------
Application main class:  TwoCities
Source packaging includes: controller [TwoCitiesController], service [ICitiesPathFinderService] and processor [PathFinder]


- Intially spring boot builds application context 
- In ServiceImpl - (@PostConstruct) builds 'ties' map for each city of origin with Set of connections to other cities:  
  <city of origin, Set <String> other_cities>
- In ServiceImpl - instatiates processor - PathFinder with 'ties' map
- Then goes into the wait state - to handle requests.

Requests come into controller (@RestController) to be dispatched to ServiceImpl ( internally making 
calls to processor class)

Processor class implements recursive(functional) way of finding paths between cities <origin, destination>

Cities are considered to be connected if there a path or  series of paths that can take caller 
from point of origin to destination.

E.g.:

	Origin -> B,  B -> C, C -> Destination


jUnit tests
------------
There are 3 tests provisioned in TwoCitiesTest, namely:
1. testPathFinderCityTies
2. testTiesMapServiceInitializer
3. negTestForLetterCaseUsedInFinder

 

Performance
-----------
Code has been tested with limited amount of input data, 
only - due to time limitation.


DESCRIPTION


Coding exercise
---------------

Write a program in Java which determines if two cities are connected.

Two cities are considered connected if there a series of roads that can be traveled 
from one city to another.
 

List of roads is available in a file.

File contains a list of city pairs (one pair per line, comma separated), 
which indicates that there's a road between those cities.

 

It will be deployed as a spring-boot app and expose one endpoint:

http://localhost:8080/connected?origin=city1&destination=city2
 

Your program should respond with 'yes' if city1 is connected to city2, 'no' if city1 is not connected to city2.

Any unexpected input should result in a 'no' response.
 

Example:

city.txt content is:

Boston, New York
Philadelphia, Newark
Newark, Boston
Trenton, Albany

 

http://localhost:8080/connected?origin=Boston&destination=Newark

Should return yes

http://localhost:8080/connected?origin=Boston&destination=Philadelphia

Should return yes

http://localhost:8080/connected?origin=Philadelphia&destination=Albany

Should return no
