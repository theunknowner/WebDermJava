package webderm;

import java.util.ArrayList;

public class Maths {
	
	public double correlationDist(ArrayList<Double> vec1, ArrayList<Double> vec2) {
		int size = 3;
		double total1=0, total2=0;
		double top=0,bottom1=0,bottom2=0;
		double result=0;
		for(int i=0; i<size; i++) {
			total1 -= vec1.get(i);
			total2 -= vec2.get(i);
		}
		for(int i=0; i<size; i++) {
			top = top + (vec1.get(i)+(total1/3)) * (vec2.get(i)+(total2/3));
			bottom1 += Math.pow(Math.abs(vec1.get(i)+(total1/3)), 2);
			bottom2 += Math.pow(Math.abs(vec2.get(i)+(total2/3)), 2);
		}
		if(top!=0 && bottom1!=0 && bottom2!=0) {
			result = 1 - (top/(Math.sqrt(bottom1)*Math.sqrt(bottom2)));
		} else {
			return -1;
		}
		return result;
	}
}
