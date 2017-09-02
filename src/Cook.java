
public class Cook implements Runnable {

	private int cook_id;		
	private Table table_serving;
	private Order order;
	private Thread cook_thread;
		
	public Cook(int cook_id) {
		this.cook_id = cook_id;
		cook_thread = new Thread(this, "Cook-"+this.cook_id);
		cook_thread.start();
	}
	
	public int getId() {
		return this.cook_id;
	}
	
	public void run() {
		while(!Manager.getStaticInstance().hasLastDinerLeft()) {
				table_serving = Manager.getStaticInstance().getTableForCook(this);
				if(table_serving != null) {
								
					order = table_serving.getOrder();
					
					while(order.burgers !=0 || order.fries != 0 || order.coke != 0 || order.sundae != 0) {
						
						if(order.burgers != 0 && !PrepareOrder.getStaticInstance().isBurgerMachineBusy()) {
							PrepareOrder.getStaticInstance().makeBurger(order);
						}
						
						if(order.fries != 0 && !PrepareOrder.getStaticInstance().isFriesMachineBusy()) {
							PrepareOrder.getStaticInstance().makeFries(order);
						}
						
						if(order.coke != 0 && !PrepareOrder.getStaticInstance().isCokeMachineBusy()) {
								PrepareOrder.getStaticInstance().makeCoke(order);
						}
						
						if(order.sundae != 0 && !PrepareOrder.getStaticInstance().isSundaeMachineBusy() ) {
								PrepareOrder.getStaticInstance().makeSundae(order);
						}
																								
					}
					
					table_serving.serveFood();
				}
		}
	}

}
