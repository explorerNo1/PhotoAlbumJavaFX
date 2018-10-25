package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UserDataStorage;
import view.ErrorPopup;

/**
 * LoginPage is the controller for the login.fxml; allows for the input of a username and the 
 * login as either a user or an admin
 * @author Niles Ball, Scott Lahart
 *
 */
public class LoginPage {

	/**
	 * Button for logging in
	 */
	@FXML Button loginButton;
	/**
	 * Textfield where the user inputs their username
	 */
	@FXML TextField usernameBox;
	/**
	 * The current stage of the program
	 */
	Stage thisStage;
	/**
	 * The selected user
	 */
	User user;  
	
	/**
	 * Method which sets up the loginpage and its stage
	 * @param primaryStage the stage of this class
	 */
	public void start(Stage primaryStage) {
		
		thisStage = primaryStage;
		
		// method for allowing photos to be shown as images
		usernameBox.setText("");
		        
		//Checks if this is the first run, creates the stock user and creates the userlist
		if(!UserDataStorage.listExist()) {UserDataStorage.startList();}
	}
	
	/**
	 * On button press, this button logs the user in with the given username; sends them to the 
	 * admin page if they input admin, the user page if they have a valid username, or shows
	 * an error message
	 * @param e button press
	 */
	public void loginButton(ActionEvent e) {

		String s = usernameBox.getText().toLowerCase().trim();
		if(s.equals("admin")) {
			adminMode();
		}
		else {
		user = UserDataStorage.getUser(s);

		//if invalid user input
		if(user == null) {
			ErrorPopup.show("Invalid Username");
			return;
		}
		
		
		Stage newStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/userpage.fxml"));
		try {
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
		} catch (IOException a) {
			a.printStackTrace();
		}
		newStage.setOnCloseRequest(c -> GenericController.closeProgram());
		AlbumViewer v = loader.getController();
		v.start(thisStage, newStage, user);
		}
	}
	
	/**
	 * Method which sets up the admin controller/page
	 */
	public void adminMode() {
		
		Stage newStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/adminpage.fxml"));
		try {
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
		} catch (IOException a) {
			a.printStackTrace();
		}
		newStage.setOnCloseRequest(c -> GenericController.closeProgram());
		AdminPage p = loader.getController();
		p.start(thisStage, newStage);
		
	}
	
	/**
	 * Method which throws the serialized list in
	 * @throws IOException catches IOException for reading serialized data
	 * @throws ClassNotFoundException catches classnotfoundexception when reading in data
	 */
	public static void readApp() throws IOException, ClassNotFoundException {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream("photoData.dat"));
			model.storageObject o = ((model.storageObject)ois.readObject());
			UserDataStorage.loadUserList(o);
			ois.close();
			} 
	catch(IOException e) {};
	}
}
