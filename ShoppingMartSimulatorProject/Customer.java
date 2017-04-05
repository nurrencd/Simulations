package ShoppingMartSimulatorProject;

public class Customer {
	private int lineTime;
	private int serviceTime;
	public static double entranceRate;
	
	public Customer(){
		this.lineTime = 0;
		this.serviceTime = 0;
		
	}
	
	public static void setEntranceRate(double rate){
		entranceRate = rate;
	}

	public void addServiceTime() {
		this.serviceTime++;
	}
	public void addLineTime(){
		this.lineTime++;
	}
	public int getServiceTime(){
		return this.serviceTime;
	}
	public int getLineTime(){
		return this.lineTime;
	}
}
