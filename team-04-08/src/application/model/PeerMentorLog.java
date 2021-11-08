package application.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeerMentorLog implements Log {
	
	private static String[] jobActivities = {
			"Mentoring Class",
			"Facilitating/Planning Workshops", 
			"Meeting with Professor", 
			"Meeting with Supervisor", 
			"Scheduled Hours / Meeting with Student", 
			"Publicity Event", 
			"E-Mails/Administrative Work Not Completed in Scheduled Hours",
			"Training Projects (If Applicable)", 
			"Staff Meeting/Training"
			};
	static List<String> validActivities = new ArrayList<>(Arrays.asList(jobActivities));
	public static List<String> getValidActivities(){
		return validActivities;
	}
	
	
	public static PeerMentorLog createLog(String jobActivity, int duration, String comment) {
		// Return null for invalid log creation
		if(!validActivities.contains(jobActivity)) {
			return null;
		}
		return new PeerMentorLog(jobActivity, duration, comment);
		
	}
	
	private String 	jobActivity;
	private Instant	logInstant;
	private int		duration;
	private String	comment;
	

	@Override
	public String getJobActivity() {
		return jobActivity;
	}

	@Override
	public Instant getLogInstant() {
		return logInstant;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String getComment() {
		return comment;
	}

	public List<String> getValidJobActivities() {
		return validActivities;
	}

	
	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	
	private PeerMentorLog(String jobActivity, int duration, String comment) {
		this.jobActivity = jobActivity;
		this.duration = duration;
		this.comment = comment;
		logInstant = Instant.now();
	}

	@Override
	public String toString() {
		return "\n" + duration + " minutes of " + jobActivity + " with the following comments:\n" + comment + "\nat " + logInstant + "\n";
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
