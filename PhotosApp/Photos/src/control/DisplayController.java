package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
import model.User;
import view.ErrorPopup;

/**
 * DisplayController manages the display of a selected photo in a view which allows them to
 * see the date the photo was last modified, a list of tags, the caption, and the image
 * @author Niles
 *
 */
public class DisplayController extends GenericController {
	/**
	 * List of tags found for the photo
	 */
	@FXML ListView<Tag> tagList;
	/**
	 * The imageview for the selected photo
	 */
	@FXML ImageView image;
	/**
	 * The caption for the selected photo
	 */
	@FXML TextField caption;
	/**
	 * The date of the selected photo
	 */
	@FXML TextField date;
	
	/**
	 * The list of photos in the album the current photo was found
	 */
	ObservableList<Photo> photos;
	/**
	 * Display control method which sets up the stage and photos which will be used by this class
	 * @param oldStage the passed in old stage
	 * @param newStage the passed in new stage
	 * @param photo the photo which was selected in the albumcontroller
	 * @param photos the list of photos in the album
	 * @param user the user to whom the photo and album belong
	 */
	public void start(Stage oldStage, Stage newStage, Photo photo, ObservableList<Photo> photos, User user) {
		super.start(oldStage, newStage, photo, user);
		newStage.setTitle("Display Photo");
		date.setText(photo.getDateString());
		ObservableList<Tag> t = FXCollections.observableArrayList(photo.getTagList());
		tagList.setItems(t);
		image.setImage(photo.getPhoto());
		caption.setText(photo.toString());
		caption.setEditable(false);
		date.setEditable(false);
		this.photos = photos;
	}
	
	/**
	 * Once the previous button was pressed, this method is called which displays the 
	 * previous image in the list; if it is the first in the list, it displays a message
	 * @param e button press
	 */
	public void previousPhoto(ActionEvent e) {
		if (photo.equals(photos.get(0))) {
			ErrorPopup.show("Selected photo is first photo");
		}
		else {
			int currIndex = photos.indexOf(photo);
			photo = photos.get(currIndex-1);
			start(oldStage, newStage, photo, photos, user);
		}
	}
	
	/**
	 * Once the previous button was pressed, this method is called which displays the 
	 * next image in the list; if it is the last in the list, it displays a message
	 * @param e button press
	 */
	public void nextPhoto(ActionEvent e) {
		if (photo.equals(photos.get(photos.size()-1))) {
			ErrorPopup.show("Selected photo is last photo");
		}
		else {
			int currIndex = photos.indexOf(photo);
			photo = photos.get(currIndex+1);
			start(oldStage, newStage, photo, photos, user);
		}
	}
}
