package edu.nju.model.data;

import java.io.*;
import java.util.*;

public class FileReaders {
	
	public int[] readFlie(File file){
		int[] result = new int[2]; 
		try {
			Scanner reader = new Scanner(file);
			result[0] = reader.nextInt();
			result[1] = reader.nextInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	
	}

}
