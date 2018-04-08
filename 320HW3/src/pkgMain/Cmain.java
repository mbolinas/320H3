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
		String line = reader.readLine();
		HashMap<String, City> cities = new HashMap<>();
		
		int example_count = Integer.parseInt(line);
		int answers[] = new int[example_count];
		
		for(int j = 0; j < example_count; j++) {
			line = reader.readLine();
			int train_count = Integer.parseInt(line);
			
			for(int i = 0; i < train_count; i++) {
				line = reader.readLine();
				String result[] = line.split(" ");
				if(!cities.containsKey(result[0])) {
					cities.put(result[0], new City(result[0]));
				}
				if(!cities.containsKey(result[1])) {
					cities.put(result[1], new City(result[1]));
				}
				Train t = new Train(Integer.parseInt(result[2]), Integer.parseInt(result[3]));
				cities.get(result[0]).adjacent_cities.put(cities.get(result[1]), t);	
			}
			line = reader.readLine();
			String destination[] = line.split(" ");
			answers[j] = (calculate_fuel_usage(cities.get(destination[0]), cities.get(destination[1])));
		}
		reader.close();
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		for(int i = 0; i < answers.length; i++) {
			writer.write("Test Case " + (i + 1));
			writer.newLine();
			writer.write("Vladimir needs " + answers[i] + " litre(s) of blood");
			writer.newLine();
		}
		
		writer.close();
		
		
		
//		City l = new City("lugoj");
//		City s = new City("sibiu");
//		City m = new City("medias");
//		City r = new City("reghin");
//		City b = new City("bacau");
//		
//		//l.add_train(s, new Train(12, 6));
//		l.add_train(s, new Train(18, 6));
//		l.add_train(s, new Train(24, 5));
//		l.add_train(m, new Train(22, 8));
//		l.add_train(m, new Train(18, 8));
//		//l.add_train(r, new Train(17, 4));
//		
//		s.add_train(r, new Train(19, 9));
//		s.add_train(m, new Train(20, 3));
//		
//		r.add_train(m, new Train(20, 4));
//		r.add_train(b, new Train(0, 6));
//		
//		System.out.println(calculate_fuel_usage(l,b));
	}
	
	
	
	public static int calculate_fuel_usage(City start, City end) {
		start.distance = 0;
		
		Set<City> settled = new HashSet<>();
		Set<City> unsettled = new HashSet<>();
		unsettled.add(start);
		start.time = 18;
		
		
		while(unsettled.size() > 0) {
			City current_city = find_closest_city(unsettled);
			unsettled.remove(current_city);
			
			
			for(Entry <City, Train> adjacency_pair : current_city.adjacent_cities.entrySet()) {
				City adjacent_city = adjacency_pair.getKey();
				Train t = adjacency_pair.getValue();
				
				
				
				if(settled.contains(adjacent_city) == false) {
					min_distance(adjacent_city, t, current_city);
					unsettled.add(adjacent_city);
				}
			}
			settled.add(current_city);
			if(current_city.name.equals(end.name)) {
				unsettled.clear();
			}
		}
		
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
	
	public static void min_distance(City eval, Train t, City source) {
		int source_distance = source.distance;
		if(source.time == -1) {
			System.out.println("TIME ERROR");
		}
		
		int wait_time = t.start_time - source.time;
		if(wait_time < 0) {
			wait_time += 24;
		}
		
		if(eval.distance > source_distance + t.length + wait_time) {
			eval.distance = source_distance + t.length + wait_time;
			//List<City> c = source.shortestPath;
			//LinkedList<City> shortest_path = new LinkedList<>(source.shortestPath());
			eval.time = (t.start_time + t.length) % 24;
		}
	}
	
}
