package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
import model.User;
import view.ErrorPopup;

/**
 * DeleteTagController is a tag which controls the deletion of a tag from a photo
 * @author Niles Ball, Scott Lahart
 *
 */
public class DeleteTagController extends GenericController {
	
	/**
	 * The list of tags for this photo which the user can delete
	 */
	@FXML ComboBox<Tag> tags;
	
	/**
	 * DeleteTag control method which sets up the stage and photos which will be used by this class
	 * @param oldStage the passed in old stage
	 * @param newStage the passed in new stage
	 * @param newPhoto the photo which was selected in the albumcontroller
	 * @param user the user to whom the photo and album belong
	 */
	public void start(Stage oldStage, Stage newStage, Photo newPhoto, User user) {
		
		super.start(oldStage, newStage, newPhoto, user);
		newStage.setTitle("Delete Tag");
		ObservableList<Tag> tagList = FXCollections.observableArrayList(photo.getTagList());
		tags.setItems(tagList);
		tags.getSelectionModel().selectFirst();
	}
	
	/**
	 * On the delete button, this method is called which deletes the selected tag 
	 * from the list
	 * @param e button press
	 */
	public void acceptDelete(ActionEvent e) {
		Tag selectedTag = tags.getValue();
		if (selectedTag==null) {
			ErrorPopup.show("No tag selected");
			return;
		}
		photo.removeTag(selectedTag);
		newStage.close();
		oldStage.show();
	}
}
