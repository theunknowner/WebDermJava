package webderm;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

public class Gui {

	public void showImage(String windowName, Mat img) {
		MatOfByte mByte = new MatOfByte();
		 Highgui.imencode(".jpg", img, mByte); 
		 byte[] byteArray = mByte.toArray();
		 BufferedImage bufImage = null;
		 try {
			 InputStream in = new ByteArrayInputStream(byteArray);
			 bufImage = ImageIO.read(in);
			 JFrame frame = new JFrame(windowName);
		     frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     frame.setLocationRelativeTo(null);
		     frame.pack();
		     frame.setVisible(true);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
}
