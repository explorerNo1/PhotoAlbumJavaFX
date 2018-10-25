package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import view.ErrorPopup;

/**
 * CopyController is controls copying a photo from one album into another, while keeping
 * it in the album it is currently in
 * @author Niles Ball, Scott Lahart
 *
 */
public class CopyController extends GenericController {
	/**
	 * This list of albums the user can copy the photo into
	 */
	@FXML ChoiceBox<Album> albums;
	/**
	 * The current album the user is in
	 */
	Album currentAlbum;
	/**
	 * The list which holds the items in the choice box
	 */
	ObservableList<Album> albumList;
	
	/**
	 * Generic control method which sets up the stage and photos which will be used by this class
	 * @param oldStage the passed in old stage
	 * @param newStage the passed in new stage
	 * @param newPhoto the photo which was selected in the albumcontroller
	 * @param user the user to whom the photo and album belong
	 * @param album the album the passed in photo was found in
	 */
	public void start(Stage oldStage, Stage newStage, Photo newPhoto, User user, Album album) {
		super.start(oldStage, newStage, newPhoto, user);
		newStage.setTitle("Copy Photo");
		albumList = FXCollections.observableArrayList(user.getAlbums());
		
		currentAlbum = album;
		// cant move to currently selected album
		albumList.remove(currentAlbum);
		albums.setItems(albumList);
		albums.getSelectionModel().selectFirst();
		if (albumList.isEmpty()) {
			oldStage.show();
			newStage.hide();
			albumList.add(currentAlbum);
			ErrorPopup.show("No albums to copy this photo to");
		}
			
	}
	
	/**
	 * On the accept button, this method is called which either shows an error message 
	 * or on success adds the photo to the selected album
	 * @param e button press
	 */
	public void acceptCopy(ActionEvent e) {
		Album copyAlbum = albums.getSelectionModel().getSelectedItem();
		if (copyAlbum.getPhotos().contains(photo)) {
			ErrorPopup.show("Album already has this picture");	
			return;
		}
		else {
			copyAlbum.addPhoto(photo);
			albumList.add(currentAlbum);
		}
		oldStage.show();
		newStage.hide();
	}
	
	/**
	 * On button press, returns the user to previous stage while adding the current album 
	 * back to the list of albums
	 * @param e button press
	 */
	public void decline(ActionEvent e) {
		super.decline(e);
		albumList.add(currentAlbum);
	}
}
