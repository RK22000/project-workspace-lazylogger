package application.control.data_layer;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import application.model.Logger;

public class LoggerInputStream {
	
	private File file;
	
	public LoggerInputStream(File file) {
		this.file = file;
	}
	
	public Logger read() {
		if(!file.isFile()) return null;
		try {
			FileInputStream 	fis = new FileInputStream(file);
			ObjectInputStream 	ois = new ObjectInputStream(fis);
			Logger logger = (Logger) ois.readObject();
			ois.close();
			return logger;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
