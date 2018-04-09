/*
 * @Marc Bolinas
 * CISC320 HW3
 * 4/8/18
 */

package pkgMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class Cmain {

	static String input = "K:\\Downloads\\input.txt";
	static String output = "K:\\Downloads\\output.txt";
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(input));
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		String line = reader.readLine();
		
		int example_count = Integer.parseInt(line);
		
		for(int j = 0; j < example_count; j++) {
			HashMap<String, City> cities = new HashMap<>();
			
			line = reader.readLine();
			int train_count = Integer.parseInt(line);
			
			
			for(int i = 0; i < train_count; i++) {
				line = reader.readLine();
				String result[] = line.split(" ");

				//Create the cities and put them in the hashmap if they don't exist yet
				if(!cities.containsKey(result[0])) {
					cities.put(result[0], new City(result[0]));
				}
				if(!cities.containsKey(result[1])) {
					cities.put(result[1], new City(result[1]));
				}
				
				//Make the train, only put it in if the departure and arrival times are all within 6pm to 6am
				Train t = new Train(Integer.parseInt(result[2]), Integer.parseInt(result[3]));
				if((t.start_time >= 18 || t.start_time <= 6) 
						&& ((t.start_time + t.length) % 24 <= 6 || (t.start_time + t.length) % 24 >= 18)
						&& t.length <= (6 - t.start_time + 24) % 24) {
					cities.get(result[0]).adjacent_cities.put(cities.get(result[1]), t);
				}
			}
			
			//Writing to file
			line = reader.readLine();
			String destination[] = line.split(" ");
			writer.write("Test Case " + (j + 1));
			writer.newLine();
			int ans = calculate_blood_usage(cities.get(destination[0]), cities.get(destination[1]));
			if(ans == -1) {
				writer.write("There is no route Vladimir can take");
			}
			else {
				writer.write("Vladimir needs " + ans + " litre(s) of blood");
			}
			writer.newLine();
		}
		reader.close();
		writer.close();
	}
	
	
	/*
	 * Modified Djikstra's
	 * Changes to Djikstra's:
	 * 
	 * Each node now has a corresponding arrival time as well as a distance
	 * The arrival time is used to calculate the distance between adjacent nodes
	 * 
	 * Distance = travel time + time between arrival and train departure + source distance
	 */
	public static int calculate_blood_usage(City start, City end) {
		//Start city has distance 0, and we start at 6pm because that's when good ol' Vlad can start taking trains
		start.distance = 0;
		start.time = 18;
		Set<City> settled = new HashSet<>();
		Set<City> unsettled = new HashSet<>();
		unsettled.add(start);

		
		//While we have cities that we gotta check out
		while(unsettled.size() > 0) {
			City current_city = find_closest_city(unsettled);
			unsettled.remove(current_city);
			
			//For every outgoing train in the current city, update the distance to the destination cities
			for(Entry <City, Train> adjacency_pair : current_city.adjacent_cities.entrySet()) {
				City adjacent_city = adjacency_pair.getKey();
				Train t = adjacency_pair.getValue();
				
				
				
				if(settled.contains(adjacent_city) == false) {
					min_distance(current_city, adjacent_city, t);
					unsettled.add(adjacent_city);
				}
			}
			settled.add(current_city);
			//If we found the final destination city, we can stop because it's the shortest path
			//At least, I think we can
			//I commented it out because I don't like it
			
//			if(current_city.name.equals(end.name)) {
//				unsettled.clear();
//			}
		}
		
		//If the end city could not be reached
		if(end.distance == Integer.MAX_VALUE) {
			return -1;
		}
		
		return end.distance / 24;
		
	}
	
	public static City find_closest_city(Set<City> unsettled) {
		City closest_city = null;
		int lowest_distance = Integer.MAX_VALUE;
		
		for(City c : unsettled) {
			if(c.distance < lowest_distance) {
				lowest_distance = c.distance;
				closest_city = c;
			}
		}
		
		return closest_city;
	}
	
	public static void min_distance(City source, City eval, Train t) {
		int source_distance = source.distance;

		//+24 and %24 to account for when time crosses over past midnight (23 to 0)
		int wait_time = (t.start_time - source.time + 24) % 24;
		
		if(source_distance + t.length + wait_time < eval.distance) {
			eval.distance = source_distance + t.length + wait_time;
			eval.time = (t.start_time + t.length) % 24;
		}
	}
	
}
