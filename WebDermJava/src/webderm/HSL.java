package webderm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HSL {
	
	public ArrayList<String> hslColors = new ArrayList<String>();
	public ArrayList<ArrayList<Integer>> hueThresh = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Double>> satThresh = new ArrayList<ArrayList<Double>>();
	public ArrayList<ArrayList<Double>> lumThresh = new ArrayList<ArrayList<Double>>();
	public ArrayList<ArrayList<Double>> satLevel = new ArrayList<ArrayList<Double>>();
	public ArrayList<ArrayList<Double>> lumLevel = new ArrayList<ArrayList<Double>>();
	
	private double H,S,L;
	
	private double minRGB(double red, double green, double blue) {
		if(red<=green && red<=blue) return red;
		if(green<=blue && green<=red) return green;
		
		return blue;
	}
	
	private double maxRGB(double red, double green, double blue) {
		if(red>=green && red>=blue) return red;
		if(green>=blue && green>=red) return green;
		
		return blue;
	}
	
/************************Public Methods**********************/

	@SuppressWarnings("unchecked")
	public void importThresholds() {
		File file = new File("hsl-thresholds.csv");
		try {
			Scanner scanner = new Scanner(file);
			String line;
			char delimiter = ',';
			Functions func = new Functions();
			ArrayList<String> vec = new ArrayList<String>();
			ArrayList<Integer> thresh = new ArrayList<Integer>();
			ArrayList<Double> thresh2 = new ArrayList<Double>();
			ArrayList<Double> thresh3 = new ArrayList<Double>();
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				vec = func.getSubstring(line, delimiter);
				for(int i=0; i<vec.size(); i++) {
					if(i==0) hslColors.add(vec.get(i));
					if(i>=1 && i<=2) thresh.add(Integer.valueOf(vec.get(i)));
					if(i>=3 && i<=4) thresh2.add(Double.valueOf(vec.get(i)));
					if(i>=5 && i<=6) thresh3.add(Double.valueOf(vec.get(i)));
				}
				hueThresh.add((ArrayList<Integer>) thresh.clone());
				satThresh.add((ArrayList<Double>) thresh2.clone());
				lumThresh.add((ArrayList<Double>) thresh3.clone());
				vec.clear(); thresh.clear(); thresh2.clear(); thresh3.clear();
			}
			vec.trimToSize();
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void importLsThreshold() {
		File satFile = new File("sat-thresholds.csv");
		File lumFile = new File("lum-thresholds.csv");
		try {
			Scanner scanner = new Scanner(lumFile);
			scanner.nextLine();
			Functions func = new Functions();
			String line;
			char delimiter = ',';
			ArrayList<String> vec = new ArrayList<String>();
			ArrayList<Double> thresh = new ArrayList<Double>();
			ArrayList<Double> thresh2 = new ArrayList<Double>();
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				vec = func.getSubstring(line, delimiter);
				for(int i=0; i<vec.size(); i++) {
					thresh.add(Double.valueOf(vec.get(i)));
				}
				lumLevel.add(thresh);
				vec.clear(); thresh.clear();
			}
			scanner = new Scanner(satFile);
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				vec = func.getSubstring(line, delimiter);
				for(int i=0; i<vec.size(); i++) {
					thresh2.add(Double.valueOf(vec.get(i)));
				}
				satLevel.add(thresh2);
				vec.clear(); thresh2.clear();
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public double[] rgb2hsl(double red, double green, double blue) {
		double r,g,b;
		double min, max;
		double delta;
		double[] HSL = new double[3];
		r = red/255;
		g = green/255;
		b = blue/255;
		min = minRGB(r,g,b);
		max = maxRGB(r,g,b);
		L = (max+min)/2;
		delta = max - min;
		if(delta==0) {
			H=0; S=0;
		}
		else {
			if(L>0.5) {
				S = delta/(2.0-max-min);
			}
			else {
				S = delta/(max+min);
			}
			if(max==r) {
				H = ((g-b)/delta);
			} else if(max==g) {
				H = ((b-r)/delta) +2;
			} else {
				H = ((r-g)/delta) +4;
			}
			H *= 60;
			if(H<0) H+=360;
		}
		HSL[0] = Math.round(H); 
		HSL[1] = MyMath.roundDecimal(S,2); 
		HSL[2] = MyMath.roundDecimal(L,2);
		return HSL;
	}
	
	public int getHue() {
		return (int)H;
	}
	
	public double getSat() {
		return S;
	}
	
	public double getLum() {
		return L;
	}
	
	private double hue2rgb(double var1, double var2, double vH) {
		if(vH<0) vH+=1;
		if(vH>1) vH-=1;
		if((6*vH)<1) return (var1+(var2-var1)*6*vH);
		if((2*vH)<1) return var2;
		if((3*vH)<2) return (var1+(var2-var1)*(0.666-vH)*6);
		return var1;
	}
	
	public int[] hsl2rgb(double hue, double sat, double lum) {
		double[] RGB = new double[3];
		int[] results = new int[3];
		if(sat==0) {
			RGB[0] = Math.round(lum * 255);
			RGB[1] = Math.round(lum * 255);
			RGB[2] = Math.round(lum * 255);
		}
		else {
			double temp1, temp2;
			if(lum<0.5)
				temp1 = lum*(1+sat);
			else
				temp1 = (lum+sat) - (lum*sat);
			temp2 = (2*lum) - temp1;
			hue /= 360;
			RGB[0] = Math.round(255*hue2rgb(temp2,temp1,(hue+0.333)));
			RGB[1] = Math.round(255*hue2rgb(temp2,temp1,hue));
			RGB[2] = Math.round(255*hue2rgb(temp2,temp1,(hue-0.333)));
		}
		results[0] = (int)RGB[0];
		results[1] = (int)RGB[1];
		results[2] = (int)RGB[2];
		return results;
	}
	
	public double calcLum(int red, int green, int blue) {
		double r,g,b;
		double min, max;
		double lum;
		r = red/255;
		g = green/255;
		b = blue/255;
		min = minRGB(r,g,b);
		max = maxRGB(r,g,b);
		lum = (max+min)/2;
		return lum;
	}
	
}
