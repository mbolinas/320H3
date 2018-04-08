package pkgMain;

import java.util.HashSet;
import java.util.Set;

public class Graph {
	Set<City> cities = new HashSet<>();
	
	public void add_city(City c) {
		cities.add(c);
	}
}
