package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Tag;
import model.User;
import view.ErrorPopup;
/**
 * SearchAddTagController is a class which controls the addition of tags to the list of tags 
 * the user wishes to search their albums for
 * @author Niles Ball, Scott Lahart
 *
 */
public class SearchAddTagController {
	
	/**
	 * The stage of this class
	 */
	private Stage newStage;
	/**
	 * The previous stage
	 */
	private Stage oldStage;
	/**
	 * The list of tags the user has
	 */
	private ObservableList<Tag> tagList;
	/**
	 * The list of boolean expressions by which the user wishes to search
	 */
	private ObservableList<String> boolList;
	/** 
	 * The types of tags the user can search with
	 */
	private ObservableList<String> tagTypes;
	
	/** 
	 * A box of the types of tags a user can search by
	 */
	@FXML ComboBox<String> typeBox;
	/**
	 * A box which allows the user to search with an and or or expression
	 */
	@FXML ComboBox<String> bool;
	/**
	 * The value of the tag the user wishes to search
	 */
	@FXML TextField valueField;
	
	/**
	 * Boolean checking whether this is the first tag to be added to the search list
	 */
	private boolean isFirst;
	/**
	 * The user who is conducting the search
	 */
	User user;
	
	/**
	 * Method which starts the SearchAddTagController, setting the stage and the lists
	 * @param oldStage the old stage
	 * @param newStage the current stage
	 * @param user the current user
	 * @param tagList the list of tags the user has
	 * @param boolList the list of boolean expressions, and or
	 */
	public void start(Stage oldStage, Stage newStage, ObservableList<Tag> tagList, ObservableList<String> boolList, User user) {
		this.oldStage = oldStage;
		this.newStage = newStage;
		this.boolList = boolList;
		this.tagList = tagList;
		this.user = user;
		ObservableList<String> logicTypes = FXCollections.observableArrayList();
		logicTypes.add("and");
		logicTypes.add("or");
		
		tagTypes = FXCollections.observableArrayList();
		bool.setItems(logicTypes);
		bool.getSelectionModel().selectFirst();
		
		ObservableList<Tag> userTagList = FXCollections.observableArrayList(user.getTagValues());
		
		for(int x = 0; x < userTagList.size(); x++) {
			tagTypes.add(userTagList.get(x).getType());
		}
		
		typeBox.setItems(tagTypes);
		typeBox.getSelectionModel().selectFirst();
		if (tagList.isEmpty()) {
			bool.setVisible(false);
			isFirst = true;
		}
	}
	/**
	 * Adds the new tag to the search list; if it is the first, the boolean check is not added
	 * @param e button press
	 */
	
	public void acceptAdd(ActionEvent e) {
		String tagType = typeBox.getValue();
		String value = valueField.getText().trim().toLowerCase();
		
		//check if input is empty
		if(value == null) {return;}
		if(value.isEmpty()) {
			ErrorPopup.show("Cannot add blank tag");
			return;
		}
		//isSingular is set to false as the tag comparator doesnt check the boolean value
		Tag tag = new Tag(tagType, value, false);
		if(tagList.contains(tag)) {
			ErrorPopup.show("Tag already added");
			return;
		}
		
		if (!isFirst) {
			String logic = bool.getValue();
			boolList.add(logic);
		}
		
		tagList.add(tag);
		newStage.hide();
		oldStage.show();
		isFirst = false;
	}
	
	/**
	 * Returns to the previous stage
	 * @param e button press
	 */
	public void decline(ActionEvent e) {
		newStage.close();
		oldStage.show();
	}
	
}
