package application.view;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class ViewHelper {
	public static VBox getVList(List<String> items) {
		VBox root = new VBox();

		for(String s: items) {
			Label itemLabel = new Label();
			itemLabel.setText(s);	
			root.getChildren().add(itemLabel);
			root.getChildren().add(new Separator());
		}
		
		return root;
	}
	public static void fillVBox(VBox root, List<String> items) {
		for(String s: items) {
			Label itemLabel = new Label();
			itemLabel.setText(s);	
			root.getChildren().add(itemLabel);
			root.getChildren().add(new Separator());
		}
	}
}
