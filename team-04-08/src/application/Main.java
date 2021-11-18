package application;
	
import application.control.GeneralController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	public static Stage mainWindow;
	
	
	/**
	 * Sets a scene containing a TimeLoging widget on to a JavaFX Stage.
	 * 
	 * The widget can be dragged around. It will be opened and closed from widget menu (to be implemented).
	 * @param primaryStage The JavaFX stage on which to set the Scene
	 */
	@Override
	public void start(Stage primaryStage) {
		mainWindow = primaryStage;
		GeneralController controller = new GeneralController();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		controller.openWidgetView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
