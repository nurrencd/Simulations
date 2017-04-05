package ShoppingMartSimulatorProject;

import java.util.ArrayList;

public class Main {
	
	public static void printGap(){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	public static void main(String[] args) {
		int endTime = 200;
		
		double[][] printArray = getFinalResults(
				500, 10,7.5,7.5, 2);
		String line1 = ""; String line2 = "";
		for (int k = 0;k < 6;k++){
			String temp1 = Double.toString(printArray[0][k]);
			String temp2 = Double.toString(printArray[1][k]);
			line1+=temp1.substring(0, (int)Math.min(temp1.length(),5)) + ", ";
			line2+=temp2.substring(0, (int)Math.min(temp2.length(),5)) + ", ";
		}
		System.out.println(line1);
		System.out.println(line2);
	}
	
	public static double[][] getFinalResults(int endTime, double e, double s1, double s2, int lineCount){
		double[][] toReturn = new double[2][6];
		for (int k = 0; k < 6;k++){
			toReturn[0][k] = 0;
			toReturn[1][k] = 0;
		}
		StoreSimulator ss;
		ArrayList<Double> averageCustAr = new ArrayList<>();
		ArrayList<Double> highestCustAr = new ArrayList<>();
		ArrayList<Double> serviceAr = new ArrayList<>();
		ArrayList<Double> lineAr = new ArrayList<>();
		ArrayList<Double> c1utilAr = new ArrayList<>();
		ArrayList<Double> c2utilAr = new ArrayList<>();
		double[] tempResults;
		for (int k = 0; k < 10;k++){
			ss = new StoreSimulator(endTime, e, s1, s2, lineCount);
			ss.run();
			tempResults = ss.getResultsArray();
			averageCustAr.add(tempResults[0]);toReturn[0][0]+=tempResults[0]/10;
			highestCustAr.add(tempResults[1]);toReturn[0][1]+=tempResults[1]/10;
			serviceAr.add(tempResults[2]);toReturn[0][2]+=tempResults[2]/10;
			lineAr.add(tempResults[3]);toReturn[0][3]+=tempResults[3]/10;
			c1utilAr.add(tempResults[4]);toReturn[0][4]+=tempResults[4]/10;
			c2utilAr.add(tempResults[5]);toReturn[0][5]+=tempResults[5]/10;
		}
		toReturn[1][0] = getStdDev(toReturn[0][0], averageCustAr);
		toReturn[1][1] = getStdDev(toReturn[0][1], highestCustAr);
		toReturn[1][2] = getStdDev(toReturn[0][2], serviceAr);
		toReturn[1][3] = getStdDev(toReturn[0][3], lineAr);
		toReturn[1][4] = getStdDev(toReturn[0][4], c1utilAr);
		toReturn[1][5] = getStdDev(toReturn[0][5], c2utilAr);
		
		
		
		
		
		return toReturn;
	}
	
	public static double getStdDev(double mu, ArrayList<Double> ar){
		double sum = 0;
		for (double d : ar){
			sum += Math.pow(mu-d, 2);
		}
		return Math.pow(sum, (double)1/2)/3;
	}

}
