package ShoppingMartSimulatorProject;

import java.util.ArrayList;

public class Line {
	private ArrayList<Customer> custLine;
	private int totalCustCount;
	private int maxCustCount;
	private int custCount;
	
	public Line(){
		this.custLine = new ArrayList<Customer>();
		this.totalCustCount = 0;
		this.maxCustCount = 0;
	}
	public Customer getNextInLine(){
		Customer toReturn = null;
		try{
		toReturn =  this.custLine.remove(0);
		}
		catch(IndexOutOfBoundsException e){
		}
		finally{
			return toReturn;
		}
	}
	
	public int size(){
		return this.custLine.size();
	}
	
	public void addCustomer(Customer c){
		this.custLine.add(c);
		this.custCount++;
		if (this.custCount > this.maxCustCount){
			this.maxCustCount = this.custCount;
		}
		
	}
	public ArrayList<Customer> getLine(){
		return this.custLine;
	}
	
	
}
