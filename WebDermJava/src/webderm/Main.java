package webderm;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class Main {
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		RGB rgb = RGB.getInstance();
		rgb.importThresholds();
		for(int i=0; i<rgb.absMeanThreshold.size(); i++) {
			for(int j=0; j<rgb.absMeanThreshold.get(i).size(); j++) {
				System.out.println(rgb.absMeanThreshold.get(i).get(j));
			}
		}
	}

}
