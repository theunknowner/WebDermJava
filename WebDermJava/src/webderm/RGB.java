package webderm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RGB {

	public ArrayList<String> rgbColors = new ArrayList<String>();
	public ArrayList<String> mainColors = new ArrayList<String>();
	public ArrayList<ArrayList<Double>> absMeanThreshold = new ArrayList<ArrayList<Double>>();
	public ArrayList<ArrayList<Double>> normMeanThreshold = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> absThreshold = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> normThreshold = new ArrayList<ArrayList<Double>>();
	
	private static RGB rgb = new RGB();
	private RGB(){}
	
	public static RGB getInstance() {
		return rgb;
	}
	
	@SuppressWarnings("unchecked")
	public void importThresholds() {
		File file = new File("color-thresholds.csv");
		try {
			Scanner scanner = new Scanner(file);
			Functions func = new Functions();
			ArrayList<String> vec = new ArrayList<String>();
			ArrayList<Double> thresh = new ArrayList<Double>();
			ArrayList<Double> thresh2 = new ArrayList<Double>();
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				vec = func.getSubstring(line, ',');
				for(int i=0; i<vec.size(); i++) {
					if(i==0) {
						rgbColors.add(vec.get(i));
					}
					if(i>=1 && i<=6) {
						thresh.add(Double.valueOf(vec.get(i)));
					}
					if(i>=7 && i<=12) {
						thresh2.add(Double.valueOf(vec.get(i)));
					}
				}
				absThreshold.add((ArrayList<Double>) thresh.clone());
				normThreshold.add((ArrayList<Double>) thresh2.clone());
				thresh.clear(); thresh2.clear();
			}
			double absValue, normValue;
			for(int i=0; i<absThreshold.size(); i++) {
				for(int j=0; j<6; j+=2) {
					absValue = (absThreshold.get(i).get(j) + absThreshold.get(i).get(j+1))/2;
					normValue = (normThreshold.get(i).get(j) + normThreshold.get(i).get(j+1))/2;
					thresh.add(absValue); thresh2.add(normValue);
				}
				absMeanThreshold.add((ArrayList<Double>) thresh.clone()); normMeanThreshold.add((ArrayList<Double>) thresh2.clone());
				thresh.clear(); thresh2.clear();
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String checkBlack(int r, int g, int b) {
		if(r<16 && g<16 && b<16) return "Black25";
		
		return "OTHER";
	}
	
	public String pushColor(int red, int green, int blue) {
		PushColor push = PushColor.getInstance();
		String pix = push.pushColor(red, green, blue);
		return pix;
	}
	
	public double getDist() {
		PushColor push = PushColor.getInstance();
		return push.getDist();
	}

	public int getIndex() {
		PushColor push = PushColor.getInstance();
		return push.getIndex();
	}
	
	public double getColorLevel(String color, String mainColor) {
		double level=0;
		String str = null;
		if(color.contains(mainColor)) {
			
		}
		
		return level;
	}
	
	
	
}
