package application.control;

import application.model.Log;
import application.model.PeerMentorLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class WidgetController extends GeneralController{
	
	@FXML ChoiceBox<String> dropDownBox;
	@FXML TextField timeBox;
	@FXML TextField commentBox;
	
	
	/**
	 * Initializes the dropDownBox with all possible job activities.
	 */
	@FXML
	private void initialize() {
		ObservableList<String> validActivities = FXCollections.observableList(PeerMentorLog.getValidActivities());
		dropDownBox.setItems(validActivities);	
	}
	
	/**
	 * Creates and records a TimeLog to permanent record.
	 * 
	 * For now, only out puts the time log to System output.
	 */
	public void recordTimeLog() {
		int minutes;
		// Checking if duration input is valid.
		try {
			minutes = Integer.parseInt(timeBox.getText());
			if (minutes < 1) {
				System.out.println("Please input a positive number for elapsed time");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Please input a number for the elapsed time");
			return;
		}
		
		// Checking if valid job activity is selected.
		if(dropDownBox.getValue() == null) {
			System.out.println("Please select a Job Activity!");
			return;
		} 
		
		// Input is valid now
		Log newLog = PeerMentorLog.createLog(dropDownBox.getValue(), minutes, commentBox.getText());
		if(newLog != null) {
			peerMentorLogger.add(newLog);
		}
		System.out.println("---\n" + peerMentorLogger);
		
		dropDownBox.setValue(null);
		commentBox.setText(null);
		timeBox.setText(null);
		
		saveLogger(peerMentorLogger);
	}

	public void openLoggerReport() {
		openReportView();
	}
}
