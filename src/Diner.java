
public class Diner implements Runnable {
		
	private int diner_id;
	private int arrival_time;		
	private int seating_time;
	private int served_time;
	boolean is_seated;
	private Table seated_table;	
	private Order order;			 
	private Cook cook;
	private boolean in_restaurant;
	private Thread diner_thread;
	
	
	public Diner(int diner_id, int arrival_time, Order order) {
		this.diner_id = diner_id;
		this.arrival_time = arrival_time;
		this.order = order;
		this.seating_time = -1;
		this.in_restaurant = false;
		diner_thread = new Thread(this, "Diner-"+this.diner_id);
	}
	
	public int getDinerId() {
		return diner_id;
	}

	public int getArrivalTime() {
		return arrival_time;
	}

	public int getSeatingTime() {
		return seating_time;
	}

	public int getServedTime() {
		return served_time;
	}

	public Order getOrder() {
		return order;
	}

	public boolean isInRestaurant() {
		return in_restaurant;
	}

	public void enterRestaurant() {
		in_restaurant = true;
		diner_thread.start();
	}
	
	public void run() {
				
		seated_table = Manager.getStaticInstance().getTableForDiner(this);
		seating_time = Timer.getStaticInstance().getTime();
		
		System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName() + " is seated on Table-" + seated_table.table_id);
		
		seated_table.waitOnCookAssigned();
		cook = seated_table.cook;
		
		System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName() +"'s order on Table-" + seated_table.table_id+" is processed by Cook-"+cook.getId());
		
		while(cook==null){
			System.out.println(Thread.currentThread().getName()+" - while \n");
		}
		
		seated_table.waitTillFoodServed();
		
		this.served_time = Timer.getStaticInstance().getTime();
		System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName() + " is served Food on the table by Cook-"+cook.getId());
		
		while (Timer.getStaticInstance().getTime() < served_time + 30) {
			// eating
			try {
				synchronized(Timer.getStaticInstance()) {
					Timer.getStaticInstance().wait();
				}
			} catch(InterruptedException ie) {}
		}

		System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" finished eating and left the Restaurant");
				
		Manager.getStaticInstance().releaseTable(seated_table.table_id);
		in_restaurant = false;
						
	}
		
}
