package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Logger extends ArrayList<Log> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean add(Log log) {
		int i = indexOf(log);
		if(i == -1) {
			return super.add(log);
		} else {
			get(i).append(log);
			return false;
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(this.toArray());
	}
	
	public static void main(String[] args) {
		Log log1 = PeerMentorLog.createLog(PeerMentorLog.validActivities.get(0), 7, "no comment");
		System.out.println(log1);
		
		Log log2 = PeerMentorLog.createLog(PeerMentorLog.validActivities.get(0), 7, "no comment");
		System.out.println(log1.equals(log2));
	}
}
