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
	
	private List<String> validActivities;
	
	public Logger2(List<String> validActivities) {
		super();
		this.validActivities = validActivities;
	}
	
/**
 * Method to get valid activities for this Logger
 * @return A list of Valid activities.
 */
	public List<String> getValidActivities () {
		return validActivities;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	
	
	
	public static void main(String[] args) {
		Log log1 = PeerMentorLog.createLog(PeerMentorLog.validActivities.get(0), 7, "no comment");
		System.out.println(log1);
		
		Log log2 = PeerMentorLog.createLog(PeerMentorLog.validActivities.get(0), 7, "no comment");
		System.out.println(log1.equals(log2));
	}
}
