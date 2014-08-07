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
		new DetectFaceDemo().run();
		//double d = rgb.getColorLevel("Gray40Pink40", "Gray");
		//System.out.println(d);
	}

}
