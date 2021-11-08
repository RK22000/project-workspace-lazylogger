package application.model;

import java.time.Instant;

public interface Log{
	// Getters
	String 		getJobActivity();
	Instant		getLogInstant();
	int 		getDuration();
	String 		getComment();
	// Setters
	void 	setDuration(int duration);
	void 	setComment(String comment);
	
	/**
	 * Checks if the two Logs are similar enough that they can be merged into 1 Log.
	 * @param log The Log to compare this Log to
	 * @return true if both Logs are similar enough
	 * 
	 */
	default boolean isSimilarTo(Log log) {
		String thisDate = getLogInstant().toString().substring(0, 10);
		String thatDate = log.getLogInstant().toString().substring(0, 10);
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
