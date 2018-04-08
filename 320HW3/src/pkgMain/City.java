package pkgMain;

import java.util.HashMap;
import java.util.Map;

public class City {
	final String name;
	int distance = Integer.MAX_VALUE;
	int time = -1;
	Map<City, Train> adjacent_cities = new HashMap<>();
	
	public City(String n) {
		name = n;
	}
	
	public void add_train(City destination, Train t) {
		adjacent_cities.put(destination, t);
	}
	
}
