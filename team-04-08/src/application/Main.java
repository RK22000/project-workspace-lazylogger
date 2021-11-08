package application;
	
import application.model.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	public static Stage mainWindow;
	public static Logger mainLogger = new Logger();
	public static Pane mainWidgetPain;
	
	// Variables to calculate displacement for drag event.
	private double xOffSet = 0;
	private double yOffset = 0;
	
	/**
	 * Sets a scene containing a TimeLoging widget on to a JavaFX Stage.
	 * 
	 * The widget can be dragged around. It will be opened and closed from widget menu (to be implemented).
	 * @param primaryStage The JavaFX stage on which to set the Scene
	 */
	@Override
	public void start(Stage primaryStage) {
		mainWindow = primaryStage;
		try {
//			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("view/widgets/Widget1.fxml"));
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("view/widgets/Widget2.fxml"));
			mainWidgetPain = root;
			
			root.setOnMousePressed(mouseEvent -> {
				xOffSet = mouseEvent.getSceneX();
				yOffset = mouseEvent.getSceneY();
			});
			
			root.setOnMouseDragged(mouseEvent -> {
				primaryStage.setX(mouseEvent.getScreenX() - xOffSet);
				primaryStage.setY(mouseEvent.getScreenY() - yOffset);
			});
			
			Scene scene = new Scene(root, root.getMaxWidth(), root.getMaxHeight());
//			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
