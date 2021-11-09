package application.control.data_layer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import application.model.Logger;

public class LoggerOutputStream {
	
	private File file;
	
	public LoggerOutputStream(File file) {
		this.file = file;
	}
	
	public boolean save(Logger logger) {
		try {
			FileOutputStream 	fos = new FileOutputStream(file);
			ObjectOutputStream 	oos = new ObjectOutputStream(fos);
			oos.writeObject(logger);
			oos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
