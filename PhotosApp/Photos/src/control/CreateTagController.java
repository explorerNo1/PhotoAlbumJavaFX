package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Tag;
import model.User;
import view.ErrorPopup;

/**
 * CreateTagController controls the page for which users can create new tag types 
 * for their photos
 * @author Niles Ball, Scott Lahart
 *
 */
public class CreateTagController{
	
	/**
	 * The current stage
	 */
	private Stage newStage;
	/**
	 * The previous stage
	 */
	private Stage oldStage;
	/**
	 * The list of existing tags for the user
	 */
	private ObservableList<Tag> tagList;
	
	/**
	 * Combobox allowing the user to decide whether or not this tag will allow multiple values
	 */
	@FXML ComboBox<String> Singular;
	/**
	 * The field containing name of the new type of tag
	 */
	@FXML TextField valueField;
	/**
	 * FXML Button for accepting the new tag type
	 */
	@FXML Button accept;
	/**
	 * FXML Button for declining the create tag
	 */
	@FXML Button decline;
	
	/**
	 * The currently selected user
	 */
	User user;

	/**
	 * Method which sets the current stage and initializes the current user and user tag values
	 * @param oldStage the previous stage
	 * @param newStage the new stage of the controller
	 * @param user the currently selected user
	 */
	public void start(Stage oldStage, Stage newStage, User user) {
		this.oldStage = oldStage;
		this.newStage = newStage;
		
		
		this.tagList = FXCollections.observableArrayList(user.getTagValues());
		this.user = user;
		
	
		
		Singular.setItems(FXCollections.observableArrayList("Yes", "No"));
		
		
	}
	
	/**
	 * On button press, allows the user to add the new tag type to the list if the value does 
	 * not already exist
	 * @param e button press
	 */
	public void acceptAdd(ActionEvent e) {
		
		
		String value = valueField.getText().trim().toLowerCase();
		String s = Singular.getValue();
		if(s == null) {
			ErrorPopup.show("Error: Please select yes or no");
			return;
			
		}
		Boolean b;
		if(s.equals("Yes")) {b = false;}
		else {b=true;}
		
		Tag tag = new Tag(value, "", b);
		
		if(tag.getType().isEmpty()) {
			ErrorPopup.show("Error: tag name blank");
			return;
		}
		
		for(int x = 0; x<tagList.size(); x++) {
			
			if(tagList.get(x).getType().equals(tag.getType())) {
				ErrorPopup.show("Error: tag type already exists");
				return;
			}
		}
		
		user.addTagType(tag);
		newStage.hide();
		oldStage.show();
	}
	
	/**
	 * On button press, returns the user to the previous page
	 * @param e button press
	 */
	public void decline(ActionEvent e) {
		newStage.close();
		oldStage.show();
	}
	
}
