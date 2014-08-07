package webderm;

import java.util.*;

import org.opencv.core.*;
import org.opencv.imgproc.*;

public class Skin {
	
	public Mat imfill(Mat img) {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 	//holds all the contour points
		Mat hierarchy = new Mat();
		Imgproc.findContours( img, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat drawing = Mat.zeros(img.size(), CvType.CV_8U);
		Imgproc.drawContours( drawing, contours, -1, new Scalar(255), 0, 8, hierarchy, 1, new Point(0,0));
		return drawing;
	}
}
