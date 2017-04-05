package ShoppingMartSimulatorProject;

import java.util.ArrayList;
import java.util.Random;

public class StoreSimulator {
	public static double timePerSecond = 1000;
	private Clerk c1;
	private Clerk c2;
	private ArrayList<Line> lines;
	private ArrayList<Customer> customers;

	private int custCount;
	private int maxCustCount;
	private int cumulativeCustCount;
	private int currentTime;
	private int endTime;
	private Random rng;

	public StoreSimulator(int totalTime, double entranceRate, double serviceRate1, double serviceRate2, int lineCount) {
		this.endTime = totalTime * (int) timePerSecond;
		this.currentTime = 0;

		this.lines = new ArrayList<Line>();
		this.customers = new ArrayList<Customer>();
		Customer.setEntranceRate(entranceRate / timePerSecond);
		this.rng = new Random();

		if (lineCount == 1) {
			Line l1 = new Line();
			this.c1 = new Clerk(l1, serviceRate1/timePerSecond);
			this.c2 = new Clerk(l1, serviceRate2/timePerSecond);
			this.lines.add(l1);
		} else {
			Line l1 = new Line();
			Line l2 = new Line();
			this.c1 = new Clerk(l1, serviceRate1/timePerSecond);
			this.c2 = new Clerk(l2, serviceRate2/timePerSecond);
			this.lines.add(l1);
			this.lines.add(l2);
		}

	}

	public void addCustomer() {
		Line shortestLine = this.lines.get(0);
		int shortest = shortestLine.size();
		if (this.lines.size() == 2) {
			Line l2 = this.lines.get(1);
			if (l2.size()<shortestLine.size()){
				shortestLine = l2;
			}
			else if (l2.size()==shortestLine.size()){
				/*
				 * This randomly assigns a line to the customer if the two 
				 * lines are of the same length. The real-life accuracy of this
				 * assumption is questionable.
				 */
				if (this.rng.nextDouble() < .5){ 
					shortestLine = l2;
				}
			}
		}
		Customer c = new Customer();
		shortestLine.addCustomer(c);
		this.customers.add(c);

	}

	// main function of the store
	public void run() {
		while (currentTime < endTime) {
			// add customer if the entrance number has been met...
			if (this.rng.nextDouble() < Customer.entranceRate) {
//				System.out.println("Customer Added at time slice: " + (currentTime / timePerSecond) + ".");
				this.addCustomer();
				this.custCount++;
			}
			// Clerks operate
			this.runClerks();
			// Increase waiting time for Customers
			this.addWaitingTime();
			// Check max size for extra statistics
			int size = 0;
			for (Line l : this.lines) {
				size += l.size();
			}
			if (size > this.maxCustCount) {
				maxCustCount = size;
			}
			this.cumulativeCustCount += size;

			// increment time
			currentTime += 1;
		}
		while (this.custCount > 0) {
			this.runClerks();
			currentTime++;
		}
	}

	private void runClerks() {
			if (c1.run()) {
//				System.out.println("Customer Served at time slice: " + (currentTime / timePerSecond) + ".");
				this.custCount--;
			}
			if (c2.run()){
//				System.out.println("Customer Served at time slice: " + (currentTime / timePerSecond) + ".");
				this.custCount--;
			}
		
	}

	private void addWaitingTime() {
		for (Line l : this.lines) {
			for (Customer c : l.getLine()) {
				c.addLineTime();
			}
		}
	}
	public double[] getResultsArray(){
		double[] toReturn = new double[6];
		toReturn[0] = (double) this.cumulativeCustCount / this.currentTime;
		toReturn[1] = (double) this.maxCustCount;
		int serviceTimeSum = 0;
		for (Customer c : this.customers) {
			serviceTimeSum += c.getServiceTime();
		}
		toReturn[2] =  (double) serviceTimeSum / this.customers.size() / timePerSecond;
		int lineTimeSum = 0;
		for (Customer c : this.customers) {
			lineTimeSum += c.getLineTime();
		}
		toReturn[3] = ((double) lineTimeSum) / this.customers.size() / timePerSecond;
		toReturn[4] = 100*c1.getUtilizationRate();
		toReturn[5] = 100*c2.getUtilizationRate();
		
		
		return toReturn;
	}

	public void getResults() {
		/*
		 * Used for general testing -- getArrayResults is used for actual data gathering
		 * avg number in system, time in service, time in line, server
		 * utilization
		 */
		System.out.println(
				"AVERAGE NUMBER OF CUSTOMERS IN SYSTEM: " + (double) this.cumulativeCustCount / this.currentTime);
		int serviceTimeSum = 0;
		for (Customer c : this.customers) {
			serviceTimeSum += c.getServiceTime();
		}
		System.out.println(
				"HIGHEST NUMBER OF CUSTOMERS IN SYSTEM: " + this.maxCustCount);
		System.out.println(
				"AVERAGE SERVICE TIME OF CUSTOMERS:     " + (double) serviceTimeSum / this.customers.size() / 1000);

		int lineTimeSum = 0;
		for (Customer c : this.customers) {
			lineTimeSum += c.getLineTime();
		}
		System.out.println(
				"AVERAGE LINE TIME OF CUSTOMERS:        " + ((double) lineTimeSum) / this.customers.size() / 1000);

		System.out.println("SERVER UTILIZATION TIME:");
		System.out.println("------------------------ Clerk1:       " + 100*c1.getUtilizationRate() + "%");
		System.out.println("------------------------ Clerk2:       " + 100*c2.getUtilizationRate() + "%");
	}

}
