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
	@FXML Button pauseBtn;
	private int currentTimeElapsed = 0;
	private int minuteDisplay;
	private double startTime;
	private double endTime;

	private Logger2 peerMentorLogger;
	
	@FXML
	private void initialize() {
		peerMentorLogger = getLogger();
		ObservableList<String> validActivities = FXCollections.observableList(peerMentorLogger.getValidActivities());
		dropDownBox.setItems(validActivities);
		timeDisplay.setText("" + 0);
		pauseBtn.setDisable(true);
	}
	
	public void swapStartPauseStatus() {
		if (pauseBtn.isDisabled()) {
			pauseBtn.setDisable(false);
			startBtn.setDisable(true);
		}
		else {
			startBtn.setDisable(false);
			pauseBtn.setDisable(true);
		}
	}
	
	// Takes the time at the moment of press and disables the start button while allowing users to see the pause button.
	public void startTimer() {
		if (dropDownBox.getValue() == null) {
			System.out.println("No activity selected");
			return;
		}
		swapStartPauseStatus();
		startTime = System.currentTimeMillis();
	}
	
	public void timerCounting() {
		currentTimeElapsed = currentTimeElapsed + (int)Math.ceil(endTime - startTime);
		minuteDisplay = currentTimeElapsed/60000;
		timeDisplay.setText("" + minuteDisplay + "min " + (currentTimeElapsed/1000) % 60 + "s");
	}
	
	// Similar to the startTimer method except in reverse and gets a "cut off" point to calculate the total time elapsed.
	public void pauseTimer() {
		swapStartPauseStatus();
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
		
		if (minuteDisplay == 0) {
			minuteDisplay = currentTimeElapsed/60000;
		}
		
		// Only allows users to log events that were at least one minute in length.
		if (minuteDisplay <= 1) {
			System.out.println("Not enough time has passed to warrent logging");
			return;
		}
		
		
		Log newLog = PeerMentorLog.createLog(dropDownBox.getValue(), minuteDisplay, "");
		newLog.setLogDate(LocalDate.now());
		if (newLog != null)
			peerMentorLogger.add(newLog);
		System.out.println("Recorded log:\n" + peerMentorLogger);
		
		dropDownBox.setValue(null);
		currentTimeElapsed = 0;
		swapStartPauseStatus();
		
		saveLogger(peerMentorLogger);
	}
	
	public void openOtherLoggerWidget() {
		openWidgetView();
	}

}
