package control;


import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.User;
import view.ErrorPopup;
import view.confirmationBox;
import view.getStringPopup;

/**
 * AlbumViewer is a class which allows the user to view the list of albums they currently have, 
 * and perform actions on these albums such as opening them and renaming them
 * @author Niles Ball, Scott Lahart
 *
 */
public class AlbumViewer {

	
	//call albumController on selected album
	
	/**
	 * FXML Button for adding an album to the users list
	 */
	@FXML Button addAlbum;
	/**
	 * FXML Button for viewing the selected album
	 */
	@FXML Button viewAlbum;
	/**
	 * FXML Button for deleting the album from the list
	 */
	@FXML Button deleteAlbum;
	/**
	 * FXML Button for renaming the selected album
	 */
	@FXML Button renameAlbum;
	/**
	 * FXML Button for quitting the program
	 */
	@FXML Button quit;
	/**
	 * FXML Button for logging out of the system
	 */
	@FXML Button logout;
	/**
	 * FXML Button for adding a new tag type to the user
	 */
	@FXML Button newTag;
	/**
	 * FXML Button for searching the user's albums for specific photos
	 */
	@FXML Button search;
	
	/**
	 * Textfield showing the current album's name
	 */
	@FXML TextField fieldName;
	/**
	 * Textfield showing the current album's photo count
	 */
	@FXML TextField fieldPhotoCount;
	/**
	 * Textfield showing the earliest photo date in the current album
	 */
	@FXML TextField fieldFDate;
	/**
	 * Textfield showing the latest photo date in the current album
	 */
	@FXML TextField fieldLDate;
	/**
	 * Listview of the albums the user possesses
	 */
	@FXML ListView<Album> albums;
	/**
	 * Actual list of the user's albums
	 */
	private ObservableList<Album> albumList;
	
	/**
	 * The current stage
	 */
	Stage thisStage;
	/**
	 * The user viewing this album
	 */
	User u;
	
	/**
	 * Method which starts the album viewer, setting the stage, populating the lists 
	 * and the textfield values
	 * @param oldStage the previous stage
	 * @param newStage the current stage
	 * @param user the current user
	 */
	public void start(Stage oldStage, Stage newStage, User user) {
		albumList = FXCollections.observableArrayList(user.getAlbums());
		albums.setItems(albumList);
		thisStage = newStage;
		
		u = user;
		albums.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
			public void changed(ObservableValue<? extends Album> observable,
					Album oldAlbum, Album newAlbum) {
						if (newAlbum!=null) {
							fieldName.setText(newAlbum.getAlbumName());
							fieldPhotoCount.setText(Integer.toString(newAlbum.getPhotoCount()));
							fieldFDate.setText(newAlbum.getEarlyDate());
							fieldLDate.setText(newAlbum.getLateDate());
						}
						else {
							fieldName.setText("");
							fieldPhotoCount.setText("");
							fieldFDate.setText("");
							fieldLDate.setText("");
						}
			}
	});
		
		albums.getSelectionModel().selectFirst();
		oldStage.hide();
		newStage.show();
		
		thisStage.setOnShowing(e -> {
			albumList = FXCollections.observableArrayList(user.getAlbums());
			albums.setItems(albumList);
		});
			
		
		
		fieldName.setEditable(false);
		fieldPhotoCount.setEditable(false);
		fieldFDate.setEditable(false);
		fieldLDate.setEditable(false);
	}
	
	/** 
	 * On button press, this method is called and adds an album to the list, if the name 
	 * is not taken
	 * @param e button press
	 */
	public void addAlbum(ActionEvent e) {
	Album a = new Album(getStringPopup.show("Create Album", " "), 0, -1, -1);
	
	if(a.getAlbumName() == null) {return;}
	if(a.getAlbumName().isEmpty()) {
		ErrorPopup.show("Error: Cannot leave name blank");
		return;
	}
	if(albumList.contains(a)) {
		ErrorPopup.show("Album name taken");
		return;
	}
	
	
	u.addAlbum(a);
	albumList = FXCollections.observableArrayList(u.getAlbums());
	albums.setItems(albumList);
	albums.getSelectionModel().select(a);
	
	}
	
	/**
	 * On button press, allows the user to view the currently selected album, if an album 
	 * is selected
	 * @param e button press
	 */
	public void viewAlbum(ActionEvent e) {
		
		Stage nStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/album.fxml"));
		try {
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			nStage.setScene(scene);
		} catch (IOException a) {
			a.printStackTrace();
		}
		nStage.setOnCloseRequest(c -> GenericController.closeProgram());
		AlbumController albumControl = loader.getController();
		
		
		Album a = albums.getSelectionModel().getSelectedItem();
		if (a==null) {
			ErrorPopup.show("No Album selected");
			return;
		}
		albumControl.start(thisStage, nStage, a, u);
		
		
		
	}
	
	/**
	 * On button press, allows the user to delete an album, if an album is currently selected
	 * @param e button press
	 */
	public void deleteAlbum(ActionEvent e) {
		Album a = albums.getSelectionModel().getSelectedItem();
		if (a==null) {
			ErrorPopup.show("No Album to delete selected");
		}
		else {
			boolean wantToDelete = confirmationBox.show("Album");
			if (wantToDelete==false) {
				return;
			}
			
			u.removeAlbum(a);
			albumList = FXCollections.observableArrayList(u.getAlbums());
			albums.setItems(albumList);
			albums.getSelectionModel().selectFirst();
		}
		
	}
	
	/**
	 * On button press, allows the user to rename an album, if an album is selected
	 * @param e button press
	 */
	public void renameAlbum(ActionEvent e) {
		Album a = albums.getSelectionModel().getSelectedItem();
		
		if(a == null) {
			ErrorPopup.show("No Album to rename selected");
			return;
		}
		
		Album b = new Album(getStringPopup.show("Rename Album", a.getAlbumName()), 0, 0, 0);
		
		if(b.getAlbumName() == null) {
			return;
		}
		if(b.getAlbumName().isEmpty()) {
			ErrorPopup.show("Error: input name cant be blank");
			return;
		}
		
		if(albumList.contains(b)) {
			ErrorPopup.show("new Album name taken, cancelling");
			return;
		}
		
		a.rename(b.getAlbumName());
		albums.refresh();
		albums.getSelectionModel().select(a);
	}
	
	/**
	 * On button press, allows the user to logout and return to the login page
	 * @param e button press
	 */
	public void logout(ActionEvent e) {
		
		control.GenericController.logout();
		Stage newStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		try {
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
		} catch (IOException a) {
			a.printStackTrace();
		}
		
		
		LoginPage lg = loader.getController();
		lg.start(newStage);
		newStage.setOnCloseRequest(c -> control.GenericController.closeProgram());
		newStage.show();
		thisStage.hide();
		
	}
	/**
	 * On button press allows the user to exit the program
	 * @param e button press
	 */
	public void quit(ActionEvent e) {
		GenericController.closeProgram();
	}
	
	/**
	 * On button press allows the user to declare a new tag type
	 * @param e button press
	 */
	public void newTag(ActionEvent e) {
		Stage newStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/createTag.fxml"));
		try {
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
		} catch (IOException a) {
			a.printStackTrace();
		}
		
	
		CreateTagController sTag = loader.getController();
		sTag.start(thisStage, newStage, u);
		newStage.setOnCloseRequest(c -> control.GenericController.closeProgram());
		newStage.show();
		thisStage.hide();
	}
	/**
	 * On button press, allows the user to call the SearchController to search 
	 * for photos of various tag type and dates
	 * @param e button press
	 */
	public void Search(ActionEvent e) {
		Stage newStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/search.fxml"));
		try {
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
		} catch (IOException a) {
			a.printStackTrace();
		}
	
		SearchController searchControl = loader.getController();
		searchControl.start(thisStage, newStage, u);
		newStage.setOnCloseRequest(c -> control.GenericController.closeProgram());
		thisStage.hide();
		newStage.show();

	}
	
}
