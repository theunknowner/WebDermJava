package webderm;

import java.util.ArrayList;

public class Functions {
	
	@SuppressWarnings("null")
	public ArrayList<String> getSubstring(String str, char delimiter) {
		String temp;
		ArrayList<String> vec = new ArrayList<String>();
		int j=0;
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i)==delimiter) {
				temp = str.substring(j, i);
				vec.add(temp);
				j=i+1;
			}
			if(i==(str.length()-1)) {
				temp = str.substring(j, str.length());
				vec.add(temp);
				j=0;
			}
		}
		return vec;
	}
}
