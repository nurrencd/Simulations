package ShoppingMartSimulatorProject;

import java.util.Random;

public class Clerk {
	private int timeRunning;
	private int timeServing;
	private int customersServed;
	private double serviceRate;

	private Customer currentCust;

	private Line line;
	private Random rng;

	public Clerk(Line l, double serviceRate) {
		this.timeServing = 0;
		this.timeRunning = 0;
		this.customersServed = 0;
		this.serviceRate = serviceRate;
		this.line = l;
		this.rng = new Random();
		
		this.currentCust = null;

	}

	public boolean run() {
		boolean served = false;
		// check if service finishes
		if (this.currentCust != null) {
			if (rng.nextDouble() < this.serviceRate) {
				// clerk finished service, gets next customer
				if (this.currentCust != null) {
					currentCust.addServiceTime();
					this.currentCust = null;
					served = true;
				}
			}
		}
		// gets next customer if doesn't have one
		if (this.currentCust != null) {
			this.currentCust.addServiceTime();
			this.timeServing++;
		} else {
			this.currentCust = this.line.getNextInLine();
		}
		this.timeRunning++;
		return served;

	}

	public double getUtilizationRate() {
		return ((double) timeServing) / timeRunning;
	}
}
