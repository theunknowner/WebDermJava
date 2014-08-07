package webderm;

import java.util.ArrayList;
import java.util.Arrays;

public class PushColor {
	
	private double dist = 0;
	private int ind = 0;
	
	private static PushColor push = new PushColor();
	private PushColor(){}
	
	public static PushColor getInstance() {
		return push;
	}
	
	private double normEucDist(double red, double green, double blue, ArrayList<Double> vec) {
		double result=0;
		double[] color = {red,green,blue};
		double[] normVals = new double[3];
		for(int i=0; i<3; i++) {
			normVals[i] = color[i]/(color[0]+color[1]+color[2]);
			normVals[i] -= vec.get(i);
			normVals[i] *= normVals[i];
			result += normVals[i];
		}
		result = Math.sqrt(result);
		return result;
	}
	
	private double absEucDist(double red, double green, double blue, ArrayList<Double> vec) {
		double result=0;
		double[] color = {red,green,blue};
		for(int i=0; i<3; i++) {
			color[i] -=vec.get(i);
			color[i] *= color[i];
			result += color[i];
		}
		result = Math.sqrt(result);
		return result;
	}
	
	public String pushColor(int red, int green, int blue) {
		RGB rgb = RGB.getInstance();
		final int length = 7;
		double[] smallest = new double[length];
		double[] val = new double[length];
		int[] index = new int[length];
		int[] colorIndex = new int[rgb.rgbColors.size()];
		double[] normDistVals = new double[rgb.normMeanThreshold.size()];
		double[] absDistVals = new double[rgb.absMeanThreshold.size()];
		Arrays.fill(smallest, 0);
		Arrays.fill(val, 0);
		Arrays.fill(index, 0);
		Arrays.fill(colorIndex, 0);
		for(int i=0; i<rgb.normMeanThreshold.size(); i++) {
			normDistVals[i] = normEucDist(red,green,blue, rgb.normMeanThreshold.get(i));
			absDistVals[i] = absEucDist(red,green,blue, rgb.absMeanThreshold.get(i));
		}
		smallest[0] = normDistVals[0];
		smallest[1] = absDistVals[0];
		smallest[2] = absDistVals[0] * Math.pow(normDistVals[0],2);
		smallest[3] = absDistVals[0] * normDistVals[0];
		smallest[4] = absDistVals[0] * Math.pow(normDistVals[0],0.25);
		smallest[5] = absDistVals[0] * Math.pow(normDistVals[0],0.45);
		smallest[6] = absDistVals[0] * Math.pow(normDistVals[0],0.65);
		for(int i=0; i<rgb.normMeanThreshold.size(); i++) {
			val[0] = normDistVals[i];
			val[1] = absDistVals[i];
			val[2] = absDistVals[i] * Math.pow(normDistVals[i],2);
			val[3] = absDistVals[i] * normDistVals[i];
			val[4] = absDistVals[i] * Math.pow(normDistVals[i],0.25);
			val[5] = absDistVals[i] * Math.pow(normDistVals[i],0.45);
			val[6] = absDistVals[i] * Math.pow(normDistVals[i],0.65);
			for(int j=0; j<length; j++) {
				if(val[j]<smallest[j]) {
					smallest[j] = val[j];
					index[j] = i;
				}
			}
		}
		for(int i=0; i<length; i++) {
			for(int j=0; j<rgb.rgbColors.size(); j++) {
				if(rgb.rgbColors.get(index[i])==rgb.rgbColors.get(j)) {
					++colorIndex[j];
				}
			}
		}
		int greatest=0;
		for(int i=0; i<rgb.rgbColors.size(); i++) {
			if(colorIndex[i]>=greatest) {
				greatest = colorIndex[i];
				index[0] = i;
			}
		}
		dist = absDistVals[index[0]];
		ind = index[0];
		return rgb.rgbColors.get(index[0]);
	}
	
	public double getDist() {
		return dist;
	}
	
	public int getIndex() {
		return ind;
	}
}
