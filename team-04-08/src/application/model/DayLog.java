package application.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TreeMap;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DayLog extends TreeMap<String, Log> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Instant day;
	
	public VBox getView() {
		VBox root = new VBox();
		
		Label dateBox = new Label();
		dateBox.setText(day.toString().substring(5, 10));
		root.getChildren().add(dateBox); 
		
		Label dayBox = new Label();
		dayBox.setText(LocalDateTime.ofInstant(day, ZoneId.systemDefault()).getDayOfWeek().toString());
		root.getChildren().add(dayBox); 
		
		
		
		for(String s: PeerMentorLog.getValidActivities()) {
			Label durationBox = new Label();
			Log log = get(s);
			if(log != null) {
				durationBox.setText(Integer.toString(log.getDuration()));				
			} else {
				durationBox.setText(" ");	
			}
			root.getChildren().add(durationBox);
		}
		
		return root;
	}	


}
