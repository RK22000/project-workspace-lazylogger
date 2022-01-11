package application.model;

import java.time.LocalDate;

/**
 * A basic implementation of the Log interface.
 * @author rahul
 *
 */
public class BasicLog implements Log {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1299531102858320942L;
	
	private String 		jobActivity;
	private LocalDate	logDate;
	private int			duration;
	private String		comment;
	
	public BasicLog(String jobActivity, LocalDate logDate, int duration, String comment) {
		super();
		this.jobActivity = jobActivity;
		this.logDate = logDate;
		this.duration = duration;
		this.comment = comment;
	}

	@Override
	public String getJobActivity() {
		return jobActivity;
	}

	@Override
	public LocalDate getLogDate() {
		return logDate;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void setLogDate(LocalDate date) {
		logDate = date;
	}
	
	@Override
	public String toString() {
		return "\n" + duration + " minutes of " + jobActivity + " with the following comments:\n" + comment + "\nat " + logDate + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Log otherLog = (Log) obj;
			return isSimilarTo(otherLog);
		} catch (ClassCastException e) {
			return false;
		}
	}

}
