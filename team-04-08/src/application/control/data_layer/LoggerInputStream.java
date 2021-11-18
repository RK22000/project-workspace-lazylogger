package application.control.data_layer;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import application.model.Logger2;

public class LoggerInputStream {
	
	private File file;
	
	public LoggerInputStream(File file) {
		this.file = file;
	}
	
/**
 * Reads a Logger from the file it holds.
 * 
 * @return the Logger read from the file, or a new Logger if o logger is read.
 */
	public Logger2 read() {
		if(!file.isFile()) return null;
		try {
			FileInputStream 	fis = new FileInputStream(file);
			ObjectInputStream 	ois = new ObjectInputStream(fis);
			Logger2 logger = (Logger2) ois.readObject();
			ois.close();
			return logger;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
