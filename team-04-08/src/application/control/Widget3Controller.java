package application.control;

import java.time.LocalDate;

import application.model.Log;
import application.model.Logger2;
import application.model.PeerMentorLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Widget3Controller extends WidgetController {
	
	@FXML Label timeDisplay;
	@FXML Button startBtn;
	@FXML Button resumeBtn;
	@FXML Button stopBtn;
	private int currentTimeElapsed = 0;
	private int minuteDisplay;
	private double startTime;
	private double resumeTime;
	private double endTime;

	private Logger2 peerMentorLogger;
	
	@FXML
	private void initialize() {
		peerMentorLogger = getLogger();
		ObservableList<String> validActivities = FXCollections.observableList(peerMentorLogger.getValidActivities());
		dropDownBox.setItems(validActivities);
		timeDisplay.setText("" + 0);
		resumeBtn.setVisible(false);
		stopBtn.setVisible(false);
	}
	
	// Sets the stop resume button to take the place of the stop button when pressed and vice versa. 
	public void swapResumeStopStatus() {
		if (stopBtn.isVisible()) {
			stopBtn.setVisible(false);
			resumeBtn.setVisible(true);
		}
		else {
			resumeBtn.setVisible(false);
			stopBtn.setVisible(true);
		}
	}
	
	// Takes the time at the moment of press and disables the start button while allowing users to see the pause button.
	public void startTimer() {
		if (dropDownBox.getValue() == null) {
			System.out.println("No activity selected");
			return;
		}
		startBtn.setVisible(false);
		stopBtn.setVisible(true);
		timeDisplay.setText("Running");
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Calculates the time passed between pressing start, stop and resume.
	 * 
	 * Uses the start button's time once then switches to the resume button's time.
	 */
	public void timerCounting() {
		if (currentTimeElapsed == 0)
			currentTimeElapsed = currentTimeElapsed + (int)Math.ceil(endTime - startTime);
		else
			currentTimeElapsed = currentTimeElapsed + (int)Math.ceil(endTime - resumeTime);
		minuteDisplay = currentTimeElapsed/60000;
		timeDisplay.setText("" + minuteDisplay + " min");
	}
	
	public void resumeTimer() {
		swapResumeStopStatus();
		timeDisplay.setText("Running");
		resumeTime = System.currentTimeMillis();
	}
	
	// Similar to the startTimer method except in reverse and gets a "cut off" point to calculate the total time elapsed.
	public void stopTimer() {
		swapResumeStopStatus();
		endTime = System.currentTimeMillis();
		timerCounting();
	}
	
	@Override
	public void recordTimeLog() {
		// Verifies the user has selected something in the drop box.
		if (dropDownBox.getValue() == null) {
			System.out.println("No activity selected");
			return;
		}
		
		// Only allows users to log events that were at least one minute in length.
		if (minuteDisplay < 1) {
			System.out.println("Not enough time has passed to warrent logging");
			return;
		}
		
		// Refuses to allow users to log new events if the timer is still active.
		if (stopBtn.isVisible()) {
			System.out.println("Please stop the timer to log the appropriate amount of time passed.");
			return;
		}
		
		Log newLog = PeerMentorLog.createLog(dropDownBox.getValue(), minuteDisplay, commentBox.getText());
		newLog.setLogDate(LocalDate.now());
		if (newLog != null)
			System.out.println("New log created: \n" + newLog);
			peerMentorLogger.add(newLog);
		System.out.println("Recorded log:\n" + peerMentorLogger);
		
		// Reset all values to avoid conflicting data.
		dropDownBox.setValue(null);
		commentBox.setText(null);
		currentTimeElapsed = 0;
		minuteDisplay = 0;
		startTime = 0;
		resumeTime = 0;
		endTime = 0;
		timeDisplay.setText("Timer");
		resumeBtn.setVisible(false);
		startBtn.setVisible(true);
		
		saveLogger(peerMentorLogger);
	}
	
	public void openOtherLoggerWidget() {
		openWidgetView();
	}

}
