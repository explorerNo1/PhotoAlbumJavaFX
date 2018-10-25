

//Name1: Niles Ball		Name2: Scott LaHart
package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A popup which shows the user an error for invalid actions
 * @author Niles Ball, Scott Lahart
 *
 */
public class ErrorPopup {

	/**
	 * Displays the input message in an error popup
	 * @param errorMessage message to be displayed
	 */
	public static void show(String errorMessage) {
		
		
		Stage alertWindow = new Stage();
		
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setMinWidth(250);
		alertWindow.setMinHeight(125);
		alertWindow.setTitle("Error");
		
		Label label = new Label(errorMessage);
		
		Button endButton = new Button("Ok");
		
		label.setLayoutX(10);
		label.setLayoutY(15);
		
		endButton.setLayoutX(105);
		endButton.setLayoutY(40);
		
		endButton.setOnAction(e -> {
			alertWindow.close();
		});
		
		Pane layout = new Pane();
		layout.getChildren().addAll(endButton, label);
		
		Scene scene = new Scene(layout);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
	}
	
}
