package control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Photo;
import model.User;
import model.UserDataStorage;

/**
 * GenericController is an abstract class which sets up the generic structure found
 * in all controllers called by the AlbumController class
 * @author Niles Ball, Scott Lahart
 *
 */

public abstract class GenericController {
	/**
	 * The stage of the controller which called this class
	 */
	Stage oldStage;
	/**
	 * The stage of this GenericController class
	 */
	Stage newStage;
	/**
	 * Button for accepting
	 */
	@FXML Button acceptButton;
	/**
	 * Button for declining / going back to the previous screen/stage
	 */
	@FXML Button declineButton;
	AlbumController control;
	/**
	 * The selected photo from the previous album page
	 */
	protected Photo photo;
	/**
	 * The user to whom the photo and album belong
	 */
	User user;
	public static final String storeDir = "dat";
	public static final String storeFile = "photos.dat"; 
	
	/**
	 * Generic control method which sets up the stage and photos which will be used by this class
	 * @param oldStage the passed in old stage
	 * @param newStage the passed in new stage
	 * @param newPhoto the photo which was selected in the albumcontroller
	 * @param user the user to whom the photo and album belong
	 */
	public void start(Stage oldStage, Stage newStage, Photo newPhoto, User user) {
		photo = newPhoto;
		this.oldStage = oldStage;
		this.newStage = newStage;
		this.user = user;
		newStage.setResizable(false);
		oldStage.hide();
		newStage.show();
		newStage.setOnCloseRequest((WindowEvent event) -> {
	       oldStage.show();
	    });
		newStage.setOnCloseRequest(c -> GenericController.closeProgram());
	}
	
	/**
	 * When the decline/back button is clicked, this method is called and it 
	 * closes the current stage while removing the showing stage
	 * @param e button press
	 */
	public void decline(ActionEvent e) {
		newStage.close();
		oldStage.show();
	}
	
	/**
	 * Method allowing for safe exit of the program
	 */
	public static void closeProgram(){

		model.storageObject o = new model.storageObject(UserDataStorage.getUserList());
		try {
			 writeProgram(o);
		}catch(IOException e) {};
		
		System.exit(0);
	}
	/**
	 * Method for logging out of the user to go to the login screen
	 */
	public static void logout(){
		

		model.storageObject o = new model.storageObject(UserDataStorage.getUserList());
		
		try {
			 writeProgram(o);
		}catch(IOException e) {e.printStackTrace();}
		
	
	}
	/**
	 * Writes out the userlist which contents house all user data. Called by logout and quit
	 * @throws IOException catches IOException for serialization
	 * @param o Passes a storageObject to write to
	 */
	public static void writeProgram(model.storageObject o) throws IOException{
		
		
		 ObjectOutputStream oos = new ObjectOutputStream(
				 new FileOutputStream("photoData.dat"));
				 oos.writeObject(o);
				oos.close(); 
	
	}
	 
	
	
		
	
}
