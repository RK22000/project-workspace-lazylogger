package application.model;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Model to track all the logs on a particular day. Key is the jobActivity of
 * log, and object is the log.
 * 
 * @author Rahul
 *
 */
public class DayLog extends TreeMap<String, Log> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDate day;

	public DayLog(LocalDate day) {
		super();
		this.day = day;
	}

	/**
	 * Constructs a new DayLog with its first Log entry.
	 * 
	 * @param log - The first log for this day log.
	 */
	public DayLog(Log log) {
		super();
		day = log.getLogDate();
		put(log.getJobActivity(), log);
	}

	/**
	 * Method to add a Log to the DayLog. If a similar log is present then the log
	 * is appended to it, else a new key log pair is added.
	 * 
	 * @param log
	 * @return true if a new key log pair was added.
	 */
	public boolean add(Log log) {
		String key = log.getJobActivity();
		if (containsKey(key)) {
			get(key).append(log);
			return false;
		} else {
			put(key, log);
			return false;
		}
	}

	public LocalDate getDay() {
		return day;
	}

	public int getTotalMinutes() {
		int sum = 0;
		for (java.util.Map.Entry<String, Log> entry : entrySet()) {
			sum += entry.getValue().getDuration();
		}
		return sum;
	}

}
