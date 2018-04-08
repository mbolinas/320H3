package pkgMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class City {
	String name;
	int distance = Integer.MAX_VALUE;
	int time = -1;
	List<City> shortestPath = new LinkedList<>();
	Map<City, Train> adjacent_cities = new HashMap<>();
	//ArrayList<Train> trains = new ArrayList<Train>();
	
	public City(String n) {
		name = n;
	}
	
	public void add_train(City destination, Train tr) {
		adjacent_cities.put(destination, tr);
	}
	
}
