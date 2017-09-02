
public class PrepareOrder {
	private boolean burger_busy;
	private boolean coke_busy;
	private boolean fries_busy;
	private boolean sundae_busy;
	private static PrepareOrder instance = null;
	private static Object static_instance_lock = new Object();
	
	Machine Burger = new Machine("Burger",5);
	Machine Fries = new Machine("Fries",3);
	Machine Coke = new Machine("Coke",2);
	Machine Sundae = new Machine("Sundae",1);
	
	private PrepareOrder() {
		burger_busy = false;
		coke_busy = false;
		fries_busy = false;
		sundae_busy = false;
	}
	
	public static PrepareOrder getStaticInstance() {
		synchronized(static_instance_lock) {
			if(instance == null)
				instance = new PrepareOrder();
			return instance;
		}
	}
	
	public boolean isBurgerMachineBusy() {
		synchronized(Burger) {
			if(burger_busy) {
				return true;
			}
			else {
				burger_busy = true;
				return false;
			}
		}
	}
		
	public boolean isCokeMachineBusy() {
		synchronized(Coke) {
			if(coke_busy) {
				return true;
			}
			else {
				coke_busy = true;
				return false;
			}
		}
	}
	
	public boolean isFriesMachineBusy() {
		synchronized(Fries) {
			if(fries_busy) {
				return true;
			}
			else {
				fries_busy = true;
				return false;
			}
		}
	}
	
	public boolean isSundaeMachineBusy() {
		synchronized(Sundae) {
			if(sundae_busy) {
				return true;
			}
			else {
				sundae_busy = true;
				return false;
			}
		}
	}
	
	public void makeBurger(Order order) {
			
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" started making Burger for Diner-"+order.diner_id);
			
			int start_time = Timer.getStaticInstance().getTime();
			int finish_time = start_time + (order.burgers * Burger.preparation_time);
			
			while(Timer.getStaticInstance().getTime() < finish_time) {
				try {
					synchronized(Timer.getStaticInstance()) {
						Timer.getStaticInstance().wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" prepared Burger for Diner-"+order.diner_id);
			
			synchronized(Burger) {
				order.burgers = 0;
				burger_busy = false;
			}
		
	}
	
	public void makeFries(Order order) {
		
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" started making Fries for Diner-"+order.diner_id);
		
			int start_time = Timer.getStaticInstance().getTime();
			int finish_time = start_time + (order.fries * Fries.preparation_time);
			
			while(Timer.getStaticInstance().getTime() < finish_time) {
				try {
					synchronized(Timer.getStaticInstance()) {
						Timer.getStaticInstance().wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" prepared Fries for Diner-"+order.diner_id);
		
			synchronized(Fries) {
				order.fries = 0;
				fries_busy = false;
		}
		
	}
	
	public void makeCoke(Order order) {
			
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" started making Coke for Diner-"+order.diner_id);
			
			int start_time = Timer.getStaticInstance().getTime();
			int finish_time = start_time + (order.coke * Coke.preparation_time);
			
			while(Timer.getStaticInstance().getTime() < finish_time) {
				try {
					synchronized(Timer.getStaticInstance()) {
						Timer.getStaticInstance().wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" prepared Coke for Diner-"+order.diner_id);
			
			synchronized(Coke) {
				order.coke = 0;
				coke_busy = false;
			}
				
	}
	
	public void makeSundae(Order order) {
			
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" started making Sundae for Diner-"+order.diner_id);
			
			int start_time = Timer.getStaticInstance().getTime();
			int finish_time = start_time + (order.sundae * Sundae.preparation_time);
			
			while(Timer.getStaticInstance().getTime() < finish_time) {
				try {
					synchronized(Timer.getStaticInstance()) {
						Timer.getStaticInstance().wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+"\t"+Thread.currentThread().getName()+" prepared Sundae for Diner-"+order.diner_id);
		
			synchronized(Sundae) {
			order.sundae = 0;
			sundae_busy = false;
		}
		
	}

}
