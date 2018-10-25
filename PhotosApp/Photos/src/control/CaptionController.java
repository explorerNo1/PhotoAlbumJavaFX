package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Photo;
import model.User;

/**
 * CaptionController is the controller for the caption page, allowing the user to 
 * input a caption into their photo
 * @author Niles Ball, Scott Lahart
 *
 */
public class CaptionController extends GenericController {
	
	/**
	 * Field where the user will input a new caption for the photo
	 */
	@FXML TextField captionField;
	/**
	 * The albumController which started this class
	 */
	AlbumController control;

	/**
	 * Caption control method which sets up the stage and photos which will be used by this class
	 * @param oldStage the passed in old stage
	 * @param newStage the passed in new stage
	 * @param newPhoto the photo which was selected in the albumcontroller
	 * @param control the album controller from which this method was called
	 * @param user the user to whom the photo and album belong
	 */
	public void start(Stage oldStage, Stage newStage, Photo newPhoto, AlbumController control, User user) {
		super.start(oldStage, newStage, newPhoto, user);
		newStage.setTitle("Caption Page");
		this.control = control;
	}
	
	/**
	 * Adds the caption to the selected photo and updates the previous photo list
	 * @param e button press
	 */
	public void acceptCaption(ActionEvent e) {
		String caption = captionField.getText();
		photo.addCaption(caption);
		newStage.close();
		oldStage.show();
		control.update();
	}
}
