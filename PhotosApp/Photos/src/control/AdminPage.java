package control;

import java.io.IOException;

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
import model.User;
import model.UserDataStorage;
import view.ErrorPopup;
/**
 * AdminPage controls the administrators page after login; allows the admin to add and 
 * delete users in the app
 * @author Niles Ball, Scott Lahart
 *
 */
public class AdminPage {

	//admin page. create / delete users. cant delete stock user
	

		
		//call albumController on selected album
		
		/**
		 * FXML Button for adding a user
		 */
		@FXML Button addUser;
		/**
		 * FXML Button for deleting a user
		 */
		@FXML Button deleteUser;
		/**
		 * FXML Button for logging out of the system
		 */
		@FXML Button logout;
		/**
		 * FXML Button for quitting the program
		 */
		@FXML Button quit;
		
		/**
		 * Textfield for the name of a new user
		 */
		@FXML TextField usernameBox;
		/**
		 * List of users existing in the app
		 */
		@FXML ListView<User> users;
	
		/**
		 * The observable list of users
		 */
		ObservableList<User> u;
		/**
		 * The current stage the user is in
		 */
		Stage thisStage;
		/**
		 * the current user/admin
		 */
		User user;
		
		/**
		 * Start method for this controller, setting the stage and the list of users
		 * @param oldStage The logout stage
		 * @param newStage The current stage
		 */
		public void start(Stage oldStage, Stage newStage) {
			
			thisStage = newStage;
			u = FXCollections.observableArrayList(UserDataStorage.getUserList());
			users.setItems(u);
			users.getSelectionModel().selectFirst();
			oldStage.hide();
			newStage.show();
			// method for allowing photos to be shown as images
			
			    
			
		}
		
		/**
		 * On button press, adds a new user to the list, or sends a message if the username 
		 * is taken
		 * @param e button press
		 */
		public void addUser(ActionEvent e) {
			
			boolean b = UserDataStorage.addUser(usernameBox.getText().trim().toLowerCase());
			if(!b) {ErrorPopup.show("Error: duplicate user name");}
			u = FXCollections.observableArrayList(UserDataStorage.getUserList());
			users.setItems(u);
		}
		
		// option to enter a user instead of exiting from admin page
		/**
		 * On button press, logs the admin out of the system, bringing them to the login page
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
		 * On button press, deletes the selected user from the list
		 * @param e button press
		 */
		public void deleteUser(ActionEvent e) {
		
			User delUser = users.getSelectionModel().getSelectedItem();
			//check for if trying to delete stock
			if(delUser.getUserName().equals("stock")) {
				ErrorPopup.show("Cannot delete stock acccount");
				return;}
			
			UserDataStorage.deleteUser(delUser);
			u = FXCollections.observableArrayList(UserDataStorage.getUserList());
			users.setItems(u);
			users.getSelectionModel().select(0);
		}
		/**
		 * On button press, quits the program
		 * @param e button press
		 */
		public void quit(ActionEvent e) {
			GenericController.closeProgram();
		}
		
	
}
