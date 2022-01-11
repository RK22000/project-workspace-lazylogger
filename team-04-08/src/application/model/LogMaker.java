package application.model;

import java.io.Serializable;
import java.time.LocalDate;

@FunctionalInterface
public interface LogMaker extends Serializable{
	Log makeLog(String jobActivity, LocalDate logDate, int duration, String comment) throws IllegalArgumentException;
}
