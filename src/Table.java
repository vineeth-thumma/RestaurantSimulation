
public class Table {
	
	
		public int table_id;
		public boolean is_occupied;
		public boolean cook_assigned;
		public boolean food_served;
		public Diner diner;
		public Order order;
		public Cook cook;
		public int time_order_served;
		
		
		public Table(int table_id) {
			
			this.table_id = table_id;
			this.is_occupied = false;
			this.cook_assigned = false;
			this.food_served = false;
			this.order = null;
			this.diner = null;
		}
		
		public void emptyTable() {
			this.is_occupied = false;
			this.cook_assigned = false;
			this.food_served = false;
			this.order = null;
			this.diner = null;
		}
		
		public Order getOrder() {
			return order;
		}
		
		public synchronized void waitOnCookAssigned() {
			try {
				while(!cook_assigned)
					this.wait();
			} catch(InterruptedException ie) {}
		}
		
		public synchronized void assignCook(Cook cook) {
			this.cook = cook;
			this.cook_assigned = true;
			this.notifyAll();
		}
			
		public synchronized void waitTillFoodServed() {
			try {
				while(!food_served)
					this.wait();
			} catch(InterruptedException ie) {}
		}
		
		public synchronized void serveFood() {
			food_served = true;
			time_order_served = Timer.getStaticInstance().getTime();
			this.notifyAll();
		}
				
}
