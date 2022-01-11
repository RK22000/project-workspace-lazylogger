package application.model;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

/**
 * Logger that tracks all logs using a TreeMap.
 * Key is YYYY-MM-DD as a String, and object is the DayLog.
 * @author Rahul
 *
 */
public class Logger2 extends TreeMap<String, DayLog> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2556802919686325839L;
	private List<String> validActivities;
	private LogMaker logMaker;
	
/**
 * Constructor to create the Logger.
 * @param validActivities A list of valid activities for the logs in this logger.
 * @param logMaker A functional object that can make logs. The logMaker need not check if the job activity is valid for this instance of the logger.
 */
	public Logger2(List<String> validActivities, LogMaker logMaker) {
		super();
		this.validActivities = validActivities;
		this.logMaker = (jobActivity, date, duration, comment) -> {
			if(validActivities.contains(jobActivity)) return logMaker.makeLog(jobActivity, date, duration, comment);
			else throw new IllegalArgumentException("Tried to make a log with an invalid Log Activity for this instance of the Logger");
		};
	}
	
/**
 * Method to get valid activities for this Logger
 * @return A list of Valid activities.
 */
	public List<String> getValidActivities () {
		return validActivities;
	}
	
	

/**
 * Method to add a new Log to the logger, or append to an existing log in the logger.
 * @param log - The log to add to the logger.
 * @return true if a new entry was created in the logger. 
 * @throws IllegalArgumentException if log logs an activity not present in the validActivities list.
 */
	public boolean add(Log log) throws IllegalArgumentException{
		if (!validActivities.contains(log.getJobActivity())) {
			throw new IllegalArgumentException(log.getJobActivity() + "is not a valid log activity for this logger.");
		}
		
		String key = log.getLogDate().toString();
		if(containsKey(key)) {
			return get(key).add(log);
		} else {
			put(key, new DayLog(log));
			return true;
		}
	}
	
/**
 * A method to get a LogMaker that will make logs valid for this instance of the logger.
 * @return A LogMaker that makes valid logs on valid inputs, or throws an exception on invalid input.
 */
	public LogMaker getLogMaker() {
		return logMaker;
	}
	
}
