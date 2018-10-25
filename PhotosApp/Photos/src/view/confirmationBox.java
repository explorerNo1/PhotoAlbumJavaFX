

//Name1: Niles Ball		Name2: Scott LaHart
package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * A confirmation box for confirming delete actions taken by the user
 * @author Niles Ball, Scott Lahart
 *
 */
public class confirmationBox {

	/**
	 * Boolean saying what the user has answered
	 */
	static boolean yesNo;
	/**
	 * Method to show the message confirming if the user wants to delete the specified object
	 * @param deletedObject object to be deleted
	 * @return true if yes, false otherwise
	 */
	public static boolean show(String deletedObject) {
		
		
		Stage alertWindow = new Stage();
		
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setMinWidth(215);
		alertWindow.setMinHeight(125);
		alertWindow.setTitle("CONFIRM");
		
		Label label = new Label("Are you sure you want to delete this "+deletedObject+"?");
		
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		label.setLayoutX(0);
		label.setLayoutY(15);
		
		yesButton.setLayoutX(50);
		yesButton.setLayoutY(40);
		
		noButton.setLayoutX(150);
		noButton.setLayoutY(40);
		
		yesButton.setOnAction(e -> {
			yesNo = true;
			alertWindow.close();
		});
		noButton.setOnAction(e -> {
			yesNo = false;
			alertWindow.close();
		});
		
		Pane layout = new Pane();
		layout.getChildren().addAll(yesButton, noButton, label);
		
		Scene scene = new Scene(layout);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
		
		return yesNo;
	}
	
}
