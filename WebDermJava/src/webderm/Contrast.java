package webderm;

public class Contrast {
	
	public double calcColorfulness(double hue, double colorLevel) {
		double colorfn=0;
		colorfn = colorLevel * hue;
		return colorfn;
	}
	
	public double calcContrast(double hue1, double hue2, String color1, String color2) {
		RGB rgb = RGB.getInstance();
		double grayHue= -1.1;
		double colorLevel1=0, colorLevel2=0;
		double cHue1, cHue2;
		double colorfn1, colorfn2;
		int count1=0, count2=0;
		for(int i=0; i<rgb.mainColors.size(); i++) {
			cHue1 = hue1;
			cHue2 = hue2;
			if(color1.contains(rgb.mainColors.get(i))) {
				
			}
		}
		return count2;
	}
}
