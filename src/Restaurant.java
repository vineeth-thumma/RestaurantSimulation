import java.io.*;
import java.util.*;

public class Restaurant {
	
	static int no_of_diners;
	static int no_of_cooks;
	static int no_of_tables;
		
	public static void main(String args[]) throws FileNotFoundException {
		
		File file = new File(args[0]);
		Scanner input = new Scanner(file);
		no_of_diners = input.nextInt();
		no_of_tables = input.nextInt();
		no_of_cooks =  input.nextInt();
		
		initializeRestaurant();
		
		for(int i = 0; i< no_of_diners; i++) {
			int arrival_time = input.nextInt();
			int burgers = input.nextInt();
			int fries  = input.nextInt();
			int coke = input.nextInt();
			int sundae = input.nextInt();
			Order order = new Order(burgers, fries, coke, sundae, i);
			Diner diner = new Diner(i, arrival_time, order);
			initializeDiner(i, diner);
		}
		input.close();
		openRestaurant();
			
	}
	
	public static void initializeRestaurant() {
		Manager.getStaticInstance().initialize(no_of_diners, no_of_tables, no_of_cooks);
	}
	
	public static void initializeDiner(int diner_id, Diner diner) {
		Manager.getStaticInstance().initalizeDiner(diner_id, diner);
	}
	
	public static void openRestaurant() {
		Manager.getStaticInstance().runRestaurant();
	}
		
}
