package application.control;

import java.io.File;
import java.io.IOException;

import application.control.data_layer.LoggerInputStream;
import application.control.data_layer.LoggerOutputStream;
import application.model.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GeneralController {
	
	private File file = new File("data/saveFile");
	private LoggerOutputStream los = new LoggerOutputStream(file);
	private LoggerInputStream  lis = new LoggerInputStream(file);
	
	protected Logger peerMentorLogger = application.Main.mainLogger;
	private Stage mainWindow = application.Main.mainWindow;
	
	private static Stage stage2 = new Stage();
	
	// Variables to calculate displacement for drag event.
		private double xOffSet = 0;
		private double yOffset = 0;
	
	protected void openReportView() {
		try {
			
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("../view/LoggerReportView.fxml"));
			Scene scene = new Scene(root, root.getMaxWidth(), root.getMaxHeight());
			stage2.setMaximized(true);
			stage2.setScene(scene);
			stage2.show();
			
			
//			mainWindow.initStyle(StageStyle.DECORATED);
//			mainWindow.setScene(scene);
			mainWindow.close();
			
//			mainWindow.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void openWidgetView() {
		GridPane root;
		try {
			root = (GridPane)FXMLLoader.load(getClass().getResource("../view/widgets/widget3.fxml"));
			root.setOnMousePressed(mouseEvent -> {
				xOffSet = mouseEvent.getSceneX();
				yOffset = mouseEvent.getSceneY();
			});
			
			root.setOnMouseDragged(mouseEvent -> {
				mainWindow.setX(mouseEvent.getScreenX() - xOffSet);
				mainWindow.setY(mouseEvent.getScreenY() - yOffset);
			});
			Scene scene = new Scene(root, root.getMaxWidth(), root.getMaxHeight());
//			mainWindow.initStyle(StageStyle.UNDECORATED);
			mainWindow.setScene(scene);
			mainWindow.show();
			stage2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public Logger getLogger() {
		Logger logger = lis.read();
		if (logger != null) return logger;
		else return new Logger();
	}
	
	protected boolean saveLogger(Logger logger) {
		return los.save(logger);
	}
}
