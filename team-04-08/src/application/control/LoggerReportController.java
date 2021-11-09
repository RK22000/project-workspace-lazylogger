package application.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoggerReportController extends GeneralController{
	@FXML Label displayBox;
	
	@FXML
	private void initialize() {
		displayBox.setText(peerMentorLogger.toString());
	}
	
	public void openLoggerWidget() {
		openWidgetView();
	}

}
