package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Popup with a textfield for allowing user input
 * @author Niles Ball, Scott Lahart
 *
 */
public class getStringPopup {

	/**
	 * The string returned by the user
	 */
	static String returnString;
	/**
	 * Method for showing the popup and allowing the user to input a value
	 * @param function title of the popup
	 * @param originalName original name of the new object
	 * @return the input user string
	 */
	public static String show(String function, String originalName) {
		
		returnString = null;
		Stage alertWindow = new Stage();
		Label label = new Label();
		
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setMinWidth(300);
		alertWindow.setMinHeight(150);
		alertWindow.setTitle(function);
		
		if(function.equals("Rename Album")) {
			label.setText("Enter new name for Album : "+originalName);
		}
		if(function.equals("Create Album")) {
			label.setText("Enter new album name");
		}
		Button yesButton = new Button("Accept");
		Button noButton = new Button("Cancel");
		TextField tField = new TextField();
		tField.setLayoutX(60);
		tField.setLayoutY(50);
		label.setLayoutX(70);
		label.setLayoutY(20);
		
		yesButton.setLayoutX(60);
		yesButton.setLayoutY(85);
		
		noButton.setLayoutX(160);
		noButton.setLayoutY(85);
		
		yesButton.setOnAction(e -> {
			returnString = tField.getText().trim().toLowerCase();
			alertWindow.close();
		});
		noButton.setOnAction(e -> {
			returnString = null;
			alertWindow.close();
		});
		
		Pane layout = new Pane();
		layout.getChildren().addAll(yesButton, noButton, label, tField);
		
		Scene scene = new Scene(layout);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		
		return returnString;
	}
	
}
