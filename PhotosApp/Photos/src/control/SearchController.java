package control;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import view.ErrorPopup;
import view.getStringPopup;
/**
 * SearchController is a class which controls the searching of a user's various albums in order 
 * to find a set of photos. Supports search by date range and search by tags
 * @author Niles Ball, Scott Lahart
 *
 */
public class SearchController {
	
	/**
	 * A listview of photos which is populated by matching photos from the search results
	 */
	@FXML ListView<Photo> photos;
	/**
	 * For search by date, the first date to search by
	 */
	@FXML DatePicker startDate;
	/**
	 * For search by date, the last date to search by
	 */
	@FXML DatePicker endDate;
	/**
	 * The list of tags the user has selected to search by
	 */
	@FXML ListView<Tag> tags;
	/**
	 * The list of boolean expressions managing the search by tag
	 */
	@FXML ListView<String> bools;
	
	/**
	 * Actual list of photos
	 */
	ObservableList<Photo> photoList;
	/**
	 * Actual list of tags
	 */
	ObservableList<Tag> tagList;
	/**
	 * Actual list of boolean expressions
	 */
	ObservableList<String> boolList;
	/**
	 * List of albums the user can search by
	 */
	ObservableList<Album> albums;
	
	/**
	 * the previous stage
	 */
	Stage oldStage;
	/**
	 * the current stage
	 */
	Stage stage;
	/**
	 * The current user
	 */
	User user;
	
	/**
	 * Method which starts the SearchController, setting the stage and the lists
	 * @param oldStage the old stage
	 * @param newStage the current stage
	 * @param user the current user
	 */
	public void start(Stage oldStage, Stage newStage, User user) {
		this.oldStage = oldStage;
		stage = newStage;
		this.user = user;
		albums = FXCollections.observableArrayList(user.getAlbums());
		photoList = FXCollections.observableArrayList();
		photos.setItems(photoList);
		tagList = FXCollections.observableArrayList();
		tags.setItems(tagList);
		boolList = FXCollections.observableArrayList();
		bools.setItems(boolList);
		
		// set the way photos are displayed
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
	
	/**
	 * On button press this method will search for photos found between the dates indicated 
	 * in the date pickers; if the date pickers are empty it will post an error message, otherwise
	 * it will populate the list with all matching photos
	 * @param e button press
	 */
	
	public void dateSearch(ActionEvent e) {
		
		photoList.clear();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		if (startDate.getValue()==null||endDate.getValue()==null) {
			ErrorPopup.show("Must select the dates to search by");
			return;
		}
		
		LocalDate start, end;
		long startMili, endMili;
		for (Album a : albums) {
			for (Photo p : a.getPhotos()) {
				start = startDate.getValue();
				end = endDate.getValue();
				cal.set(start.getYear(), start.getMonthValue()-1, start.getDayOfMonth());
				startMili = cal.getTimeInMillis();
				cal.set(end.getYear(), end.getMonthValue()-1, end.getDayOfMonth());
				endMili = cal.getTimeInMillis();
				
				if ((startMili<=p.getDate())&&(endMili>=p.getDate())) {
					if (photoList.contains(p)) {
						continue;
					}
					photoList.add(p);
				}
	
			}
		}
	}
	
	/**
	 * On button press this method will search for all photos matching the selection of tags
	 * and the boolean expressions input by the user; if successful, all matching photos will 
	 * be added to the list
	 * @param e button press
	 */
	
	public void tagSearch(ActionEvent e) {
		photoList.clear();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		
		if (tagList.isEmpty()) {
			ErrorPopup.show("Cannot search with empty tag list");
			return;
		}
		
		for (Album a : albums) {
			for (Photo p : a.getPhotos()) {
				if (photoSearchLogicIsTrue(p)) {
					if (photoList.contains(p)) {
						continue;
					}
					photoList.add(p);
				}
			}
		}
	}
	/**
	 * Add tag button which calls the SearchAddTagController
	 * @param e button press
	 */
	
	public void addTag(ActionEvent e) {
		Stage newStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/searchaddtag.fxml"));
		try {
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
		} catch (IOException a) {
			a.printStackTrace();
		}
		SearchAddTagController controller = loader.getController();
		controller.start(stage, newStage, tagList, boolList, user);
		oldStage.hide();
		newStage.show();
	}
	
	/**
	 * Method which checks if a given photo has a specific tag
	 * @param p the photo to be checked
	 * @param t1 the tag to be checked
	 * @return true if photo has tag, false otherwise
	 */
	public boolean photoHasTag(Photo p, Tag t1) {
		for (Tag t2 : p.getTagList()) {
			if (t1.equals(t2)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Called on searchbyTag; checks if a specific photo matches the list of tags and 
	 * boolean expressions the user is searching by
	 * @param p the photo to be checked
	 * @return true if logic is right
	 */
	
	public boolean photoSearchLogicIsTrue(Photo p) {
		boolean prev, next;
		ArrayList<Boolean> booleanList = new ArrayList<Boolean>();
		
		prev = photoHasTag(p, tagList.get(0));
		if (tagList.size()==1) {
			return prev;
		}
		int x;
		for (x = 1; x < tagList.size(); x++) {
			Tag tag = tagList.get(x);
			next = photoHasTag(p, tag);
			if (boolList.get(x-1).equals("and")) {
				prev = prev && next;
			}
			else {
				booleanList.add(prev);
				prev = next;
			}
		}
		
		if (booleanList.isEmpty()) {
			return prev;
		}
		return prev || booleanList.contains(true);
	}
	/**
	 * returns the user to the previous stage
	 * @param e button press
	 */
	public void back(ActionEvent e) {
		stage.hide();
		oldStage.show();
		
	}
	/**
	 * On button press, creates an album based off of all photos found in the list; 
	 * throws a message if the list is empty
	 * @param e button press
	 */
	public void createAlbum(ActionEvent e) {
		if (photoList.isEmpty()) {
			ErrorPopup.show("Cannot create album from empty list");
			return;
		}
		Album a = new Album(getStringPopup.show("Create Album", " "), 0, -1, -1);
		if (a.getAlbumName() == null) {return;}
		if (a.getAlbumName().isEmpty()) {
			ErrorPopup.show("Error: Cannot leave name blank");
			return;
		}
		if (user.getAlbums().contains(a)) {
			ErrorPopup.show("Album name taken");
			return;
		}
		ArrayList<Photo> p = new ArrayList<Photo>(photoList);
		a.setPhotos(p);
		user.addAlbum(a);
	}
}