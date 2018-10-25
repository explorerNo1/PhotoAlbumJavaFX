package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
import model.User;
import view.ErrorPopup;

/**
 * AddTagController is the controller for adding a tag to the selected photo
 * in the album page
 * @author Niles Ball, Scott Lahart
 *
 */

public class AddTagController extends GenericController {
	
	/**
	 * A combo box from which the user can select which type of tag they wish
	 * to add to the photo
	 */
	@FXML ComboBox<String> typeBox;
	/**
	 * A textfield which allows the user to select what value they wish to add
	 * for the tag
	 */
	@FXML TextField valueField;
	
	/**
	 * This is the list of tags which the user has defined
	 */
	private ObservableList<Tag> tagList;
	/**
	 * The observable list which stores the values found in typeBox
	 */
	private ObservableList<String> tagTypes;
	
	/**
	 * AddTag control method which sets up the stage and photos which will be used by this class
	 * @param oldStage the passed in old stage
	 * @param newStage the passed in new stage
	 * @param newPhoto the photo which was selected in the albumcontroller
	 * @param user the user to whom the photo and album belong
	 */
	public void start(Stage oldStage, Stage newStage, Photo newPhoto, User user) {
		super.start(oldStage, newStage, newPhoto, user);
		newStage.setTitle("Add Tag");
		
		
		tagTypes = FXCollections.observableArrayList();
		this.user = user;
		tagList  = FXCollections.observableArrayList(user.getTagValues());

		for(int x = 0; x < tagList.size(); x++) {
			tagTypes.add(tagList.get(x).getType());
		}
		
		
		typeBox.setItems(tagTypes);
		typeBox.getSelectionModel().selectFirst();
	}
	
	/**
	 * Called when the accept button is pressed, adds the new tag to the photo if allowed,
	 * else throws an error message
	 * @param e button press
	 */
	public void acceptAdd(ActionEvent e) {
		
		String type = typeBox.getSelectionModel().getSelectedItem();
		String value = valueField.getText();
		if(value.isEmpty()) {
			ErrorPopup.show("Error: Tag cannot be blank");
			return;
		}
		boolean b = false;
		//Checks the user master tag list for what type of tag it is (unique)
		for(int x = 0; x < tagList.size(); x++) {
			if(tagList.get(x).getType().equals(type)) {
				b = tagList.get(x).isSingle();
			}
		}
		
		Tag newTag = new Tag(type,value, b);
		if (photo.getTagList().contains(newTag)) {
			ErrorPopup.show("Tag already exists");
			return;
		}
		//Below check if the tag is unique type
		for(int x = 0; x < photo.getTagList().size(); x++) {
			//if the type(name) of the tag is the same
			if(photo.getTagList().get(x).getType().equals(newTag.getType())){
				//and that type is unique, error
				if(photo.getTagList().get(x).isSingle()) {
					ErrorPopup.show("Error: Tag type is unique and already exists");
					return;
					
				}
				
			}
		}
		
		
		photo.addTag(newTag);
		newStage.close();
		oldStage.show();
	}

}