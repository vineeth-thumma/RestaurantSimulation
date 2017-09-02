
public class Timer {
	private static Timer timer = null;
	private int time;
	
	public Timer() {
		time = 0;
	}
	
	public int getTime() {
		return time;
	}

	public static Timer getStaticInstance() {
		if(timer == null) {
			timer = new Timer();
		}
		return timer;
	}
	
	public void increment() {
		try {
			Thread.sleep(75);
		} catch(InterruptedException ie) {
			
		}
		time++;
	}
	
}
