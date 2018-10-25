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
 * MoveController is a class which controls the movement of a photo from one album into another 
 * while deleting it from the first
 * @author Niles Ball, Scott Lahart
 *
 */
public class MoveController extends GenericController {
	/**
	 * The list of albums the user can move the photo to
	 */
	@FXML ChoiceBox<Album> albums;
	/**
	 * The current album the user is in
	 */
	Album currentAlbum;
	/**
	 * The list of albums the user has
	 */
	ObservableList<Album> albumList;
	/**
	 * List of photos the user has
	 */
	ObservableList<Photo> photoList;
	/**
	 * Move control method which sets up the stage and photos which will be used by this class
	 * @param oldStage the passed in old stage
	 * @param newStage the passed in new stage
	 * @param newPhoto the photo which was selected in the albumcontroller
	 * @param photoList list of photos in the album
	 * @param user the user to whom the photo and album belong
	 * @param album current album
	 */
	public void start(Stage oldStage, Stage newStage, Photo newPhoto, User user, Album album, ObservableList<Photo> photoList) {
		super.start(oldStage, newStage, newPhoto, user);
		newStage.setTitle("Move Photo");
		currentAlbum = album;
		albumList = FXCollections.observableArrayList(user.getAlbums());
		// cant move to currently selected album
		albumList.remove(currentAlbum);
		albums.setItems(albumList);
		this.photoList = photoList;
		if (albumList.isEmpty()) {
			oldStage.show();
			newStage.hide();
			albumList.add(currentAlbum);
			ErrorPopup.show("No albums to copy this photo to");
		}
		albums.getSelectionModel().selectFirst();
	}
	
	/**
	 * On the button press, this method is called which either shows an error message or 
	 * successfuly moves the photo into the new album
	 * @param e button press
	 */
	public void acceptMove(ActionEvent e) {
		Album newAlbum = albums.getSelectionModel().getSelectedItem();
		if (newAlbum.getPhotos().contains(photo)) {
			ErrorPopup.show("Album already has this picture");
			return;
		}
		else {
			newAlbum.addPhoto(photo);
			currentAlbum.deletePhoto(photo);
			photoList.remove(photo);
			albumList.add(currentAlbum);
		}
		oldStage.show();
		newStage.hide();
	}
	
	/**
	 * On button press, returns the user to previous stage while adding the current album 
	 * back to the list of albums
	 */
	public void decline(ActionEvent e) {
		super.decline(e);
		albumList.add(currentAlbum);
	}

}
