package control;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.FileController;
import model.Photo;
import model.User;
import view.ErrorPopup;
import view.confirmationBox;

/**
 * AlbumController is the controller for the page which displays all of the photos and allows
 * the user to do various actions upon them, such as adding a tag or captioning a photo 
 * @author Niles Ball, Scott Lahart
 *
 */
public class AlbumController {
	

	/**
	 * FXML Button for adding a photo
	 */
	@FXML Button addPhoto;
	/**
	 * FXML Button for deleting a photo
	 */
	@FXML Button deletePhoto;
	/**
	 * FXML Button for moving a photo
	 */
	@FXML Button movePhoto;
	/**
	 * FXML Button for copying a photo
	 */
	@FXML Button copyPhoto;
	/**
	 * FXML Button for adding a tag to the photo
	 */
	@FXML Button addTag;
	/**
	 * FXML Button for deleting a tag from the photo
	 */
	@FXML Button deleteTag;
	/**
	 * FXML Button for adding a new caption to the photo
	 */
	@FXML Button captionPhoto;
	/**
	 * FXML Button for displaying the photo and its values
	 */
	@FXML Button displayPhoto;
	
	/**
	 * Listview of photos which exist in this album
	 */
	@FXML ListView<Photo> photos;
	/**
	 * The actual list of photos
	 */
	private ObservableList<Photo> photoList;
	/**
	 * The album which is currently being viewed
	 */
	private Album selectedAlbum;
	
	/**
	 * The previous stage in the program
	 */
	Stage oldStage;
	/**
	 * The currently selected stage
	 */
	Stage stage;
	/**
	 * The currently logged in user
	 */
	User user;
	
	/**
	 * Method which sets up the stage, selected album, and user of the AlbumController
	 * @param oldStage the previous stage
	 * @param primaryStage the current stage
	 * @param selectedAlbum the current album
	 * @param user the current user
	 */
	public void start(Stage oldStage, Stage primaryStage, Album selectedAlbum, User user) {
		
		this.oldStage = oldStage;
		this.selectedAlbum = selectedAlbum;
		this.user = user;
		oldStage.hide();
		primaryStage.show();
		primaryStage.setTitle("Album Page");
		primaryStage.setResizable(false);
		photoList = FXCollections.observableArrayList(selectedAlbum.getPhotos());
		photos.setItems(photoList);
		stage = primaryStage;
		// method for allowing photos to be shown as images
		update();
		photos.getSelectionModel().selectFirst();
		
	}
	
	/**
	 * Adds a photo to the current album's list of photos and displays the photo
	 * @param e button press
	 */
	public void addPhoto(ActionEvent e) {
		FileController fileControl = new FileController();
		Photo newPhoto = fileControl.createPhoto();
		if (newPhoto==null) {
		}
		else if (photoList.contains(newPhoto)) {
			ErrorPopup.show("Photo already exists in album");
			return;
		}
		else {
			//photoList.add(newPhoto);
			selectedAlbum.addPhoto(newPhoto);
			photos.getSelectionModel().select(newPhoto);
		}
		//updates list
		photoList = FXCollections.observableArrayList(selectedAlbum.getPhotos());
		photos.setItems(photoList);
	}
	
	/**
	 * Deletes the photo from the current album's list of photos
	 * @param e button press
	 */
	
	public void deletePhoto(ActionEvent e) {
		Photo deletePhoto = photos.getSelectionModel().getSelectedItem();
		if (deletePhoto==null) {
			ErrorPopup.show("No photo to delete selected");
		}
		else {
			boolean wantToDelete = confirmationBox.show("photo");
			if (wantToDelete==false) {
				return;
			}
			
			selectedAlbum.deletePhoto(deletePhoto);
			photos.getSelectionModel().selectFirst();
		}
		//updates list
		photoList = FXCollections.observableArrayList(selectedAlbum.getPhotos());
		photos.setItems(photoList);
	}
	
	/**
	 * Calls the generic startController method with the add tag page fxml
	 * @param e button press
	 */
	
	public void addTag(ActionEvent e) {
		startController("/view/addtag.fxml");
	}
	
	/**
	 * Calls the generic startController method with the delete tag page fxml
	 * @param e button press
	 */
	public void deleteTag(ActionEvent e) {
		startController("/view/deletetag.fxml");
	}
	/**
	 * Calls the generic startController method with the caption photo page fxml
	 * @param e button press
	 */
	public void captionPhoto(ActionEvent e) {
		startController("/view/caption.fxml");
	}
	/**
	 * Calls the generic startController method with the copy photo page fxml
	 * @param e button press
	 */
	public void copyPhoto(ActionEvent e) {
		startController("/view/copyphoto.fxml");
	}
	/**
	 * Calls the generic startController method with the move photo page fxml
	 * @param e button press
	 */
	public void movePhoto(ActionEvent e) {
		startController("/view/movephoto.fxml");
		
	}
	/**
	 * Calls the generic startController method with the display photo page fxml
	 * @param e button press
	 */
	public void displayPhoto(ActionEvent e) {
		startController("/view/displayphoto.fxml");
	}
	
	/**
		Calls the start method of all the various methods and creates a new stage for this method
		@param fxmlPath the path to the FXML doc which the controller controls
	*/
	public void startController(String fxmlPath) {
		Photo currentPhoto = photos.getSelectionModel().getSelectedItem();
		
		if (currentPhoto==null) {
			ErrorPopup.show("No photo selected");
		}
		else {
			Stage newStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlPath));
			try {
				AnchorPane root = loader.load();
				Scene scene = new Scene(root);
				newStage.setScene(scene);
			} catch (IOException a) {
				a.printStackTrace();
			}
			GenericController controller = loader.getController();
			if (controller instanceof CaptionController) {
				CaptionController caption = (CaptionController)controller;
				caption.start(stage,  newStage,  currentPhoto, this, user);
			}
			else if (controller instanceof DisplayController) {
				DisplayController display = (DisplayController)controller;
				display.start(stage, newStage, currentPhoto, photoList, user);
			}
			else if (controller instanceof CopyController) {
				CopyController copy = (CopyController)controller;
				copy.start(stage, newStage, currentPhoto, user, selectedAlbum);
			}
			
			else if (controller instanceof MoveController) {
				MoveController move = (MoveController)controller;
				move.start(stage, newStage, currentPhoto, user, selectedAlbum, photoList);
			}
			
			else {
				controller.start(stage, newStage, currentPhoto, user);
			}
		}
	}
	
	/**
	 * When pressed this will return the user to the albumView page
	 * @param e button press
	 */
	
	public void back(ActionEvent e) {
		stage.hide();
		oldStage.show();
	//	Album a = new Album("", 0, 0, 0);
	}
	/**
	 * When pressed this will log the user out and return them to the login page
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
		stage.hide();
	}
	
	/**
	 * This method updates the Photo ListView, such that both the image and caption are displayed,
	 * and that any changes to photos will be properly shown in the list
	 */
	public void update() {
		photos.setCellFactory(listView -> new ListCell<Photo>() {
		    private ImageView imageView = new ImageView();

		    @Override
		    public void updateItem(Photo item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty) {
		            setGraphic(null);
		            setText(null);
		        } else {
		            imageView.setImage(item.getPhoto());
		            imageView.setFitHeight(100);
		            imageView.setFitWidth(100);
		            setGraphic(imageView);
		            setText(item.toString());
		        }
		    }
		});
	}
}