package application.model;

import java.io.Serializable;
import java.time.LocalDate;

public interface Log extends Serializable{
	// Getters
	String 		getJobActivity();
	LocalDate		getLogDate();
	int 		getDuration();
	String 		getComment();
	// Setters
	void 	setDuration(int duration);
	void 	setComment(String comment);
	void	setLogDate(LocalDate date);
	
	/**
	 * Checks if the two Logs are similar enough that they can be merged into 1 Log.
	 * @param log The Log to compare this Log to
	 * @return true if both Logs are similar enough
	 * 
	 */
	default boolean isSimilarTo(Log log) {
		String thisDate = getLogDate().toString();
		String thatDate = log.getLogDate().toString();
		return thisDate.equals(thatDate) && getJobActivity().equals(log.getJobActivity());
	}
	
	/**
	 * Merges the other Log into the this Log.
	 * @param log The Log to merge into this Log
	 * 
	 */
	default void append(Log log) {
		setDuration(getDuration()+log.getDuration());
		if(!log.getComment().equals("")) {
			setComment(getComment()+"\n"+log.getComment());
		}
	}
	
}
