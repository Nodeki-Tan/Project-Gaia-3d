package toolbox;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Utils {
	
	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		
		try{
			
			FileInputStream br = new FileInputStream(path);
			ObjectInputStream ou = new ObjectInputStream(br);
			
			while(ou.available() > 0)
				builder.append(ou.readInt() + "_");

			ou.close();
		}catch(EOFException e){
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}

}
