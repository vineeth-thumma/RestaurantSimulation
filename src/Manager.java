
public class Manager {
	
	private Diner[] diners;
	private Table[] tables;
	private Cook[] cooks;
	private Timer timer;
	private int no_current_diners;
	private int total_diners_arrived;
	private boolean all_diners_arrived;
	private static Manager instance = null;
	private Object diners_lock = new Object();
	private static Object static_instance_lock = new Object();
	
	public static Manager getStaticInstance() {
		synchronized(static_instance_lock) {
			if(instance == null)
				instance = new Manager();
			return instance;
		}
	}
	
	public void initialize(int no_of_diners, int no_of_tables, int no_of_cooks) {
		
		diners = new Diner[no_of_diners];
		tables = new Table[no_of_tables];
		for(int i=0; i<tables.length; i++) {
			tables[i] = new Table(i);
		}
		cooks = new Cook[no_of_cooks];
		for (int i = 0; i < cooks.length; i++) {
				cooks[i] = new Cook(i);
		}
				
	}
	
	public void initalizeDiner(int diner_id, Diner diner) {
		diners[diner_id] = diner;
	}
	
	public void runRestaurant() {
		timer = Timer.getStaticInstance();
		System.out.println("Time : "+timer.getTime()+"\t"+"Restaurant 6431 is now Open");	
		while(!hasLastDinerLeft()) {
			startDinersArrivedNow();
			timer.increment(); 
			synchronized(timer) {
				timer.notifyAll();
			}
		}
				
	}
	
	public void startDinersArrivedNow() {
		for(int i=0; i<diners.length; i++) {
			if(diners[i].getArrivalTime() == timer.getTime()) {
				diners[i].enterRestaurant();
				System.out.println("Time : "+timer.getTime()+"\t"+"Diner - " + i + " arrived.");
				this.no_current_diners++;
				this.total_diners_arrived++;
				
				if(total_diners_arrived == diners.length) {
					all_diners_arrived = true;
				}
			}
		}
	}
	
	public Table getTableForDiner(Diner diner) {
		synchronized(diners_lock){
		int index = -1;
		while(index == -1) {
			if(isWaiting(diner.getDinerId())) {
				for(int i=0; i<tables.length; i++) {
					if(!tables[i].is_occupied) {
						index = i;
						break;
					}
				}
			}
			try {
				if(index == -1)
					diners_lock.wait();
			} catch(InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		}
			tables[index].is_occupied = true;
			tables[index].diner = diner;
			tables[index].order = diner.getOrder();
			
			diners_lock.notifyAll();
			return tables[index];
		}
	}
	
	public Table getTableForCook(Cook cook) {
		synchronized(diners_lock) {
		int index = -1;
		while(index == -1) {
			for(int i=0; i<tables.length; i++) {
				if(tables[i].is_occupied && !tables[i].cook_assigned) {
					index = i;
				}
			}
			
			if(index == -1) {
				if(no_current_diners == 0)
					break;
				try {
					diners_lock.wait();
				} catch(InterruptedException ie) {
					System.out.println(ie.getMessage());
				}
			}
		}
		if(index == -1)
			return null;
		
		tables[index].assignCook(cook);
		diners_lock.notifyAll();
		return tables[index];
		}
	}
	
	public void releaseTable(int index) {
		synchronized(diners_lock) {
		tables[index].emptyTable();
		leaveRestaurant();
		diners_lock.notifyAll();
		}
	}
	
	
	public boolean isWaiting(int dinerId) {
		for(int i=0; i<diners.length; i++) {
			if(diners[i].isInRestaurant() && diners[i].getSeatingTime() == -1) {
				if(dinerId == i)
					return true;
				else
					return false;
			}
		}
		return false;
	}
	
	public void leaveRestaurant() {
		no_current_diners--;
		if(hasLastDinerLeft()) {
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+"Last Diner: "+Thread.currentThread().getName()+" left the Restaurant");
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+"Restaurant 6431 is now Closed");
			}
	}
	
	public int getNumberOfCurrentDiners() {
		return no_current_diners;
	}
	
	public boolean hasLastDinerLeft() {
		if(all_diners_arrived && no_current_diners == 0) {
			return true;
		}
		else
			return false;
	}
	
}
	
		
	
