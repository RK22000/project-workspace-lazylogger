package application.control;

import application.model.Logger2;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoggerReportController extends GeneralController{
	@FXML Label displayBox;
	@FXML ScrollPane HScrollBox;
	@FXML HBox DayLogBox;
	@FXML VBox ActivityBox;
	
	@FXML
	private void initialize() {
		Logger2 peerMentorLogger = getLogger();
		displayBox.setText("The logger toString is \n" + peerMentorLogger.toString());
		
		Label date = new Label();
		date.setText("Date");
		Label day = new Label();
		day.setText("Day");
		ActivityBox.getChildren().addAll(date, new Separator(), day, new Separator());
		
		for (String activity : peerMentorLogger.getValidActivities()) {
			Label label = new Label();
			label.setText(activity);
			ActivityBox.getChildren().add(label);
			ActivityBox.getChildren().add(new Separator());
		}
		
		peerMentorLogger.entrySet().forEach(entry -> {
			DayLogBox.getChildren().add(entry.getValue().getView(peerMentorLogger.getValidActivities()));
			DayLogBox.getChildren().add(new Separator(Orientation.VERTICAL));
		});
		
		HScrollBox.setMinHeight(DayLogBox.getPrefHeight()+138);
		HScrollBox.setPrefWidth(600);
	}
	
	public void openLoggerWidget() {
		openWidgetView();
	}

}
