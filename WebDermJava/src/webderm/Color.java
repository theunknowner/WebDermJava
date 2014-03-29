package webderm;

import java.util.ArrayList;

public class Color {
	
	public ArrayList<String> extractColorsFromString(String color) {
		RGB rgb = RGB.getInstance();
		ArrayList<String> vec = new ArrayList<String>();
		for(int i=0; i<rgb.mainColors.size(); i++) {
			if(color.contains(rgb.mainColors.get(i))) {
				vec.add(rgb.mainColors.get(i));
			}
		}
		return vec;
	}
}
