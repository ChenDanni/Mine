package edu.nju.model.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.xml.internal.ws.Closeable;

public class FileWriters {
	
	public void writeFile(File file,int win,int total){
		String s = String.valueOf(win)+" "+String.valueOf(total);
		try {
			FileWriter writer = new FileWriter(file);
		//	System.out.println(s);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
