package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import control.LoginPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.UserDataStorage;



/**
 * Photos is the main method of the program. Sets up the login page and loads the serialized data
 * @author Niles Ball, Scott Lahart
 */
public class Photos extends Application{

	Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/login.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			//checks for first run, does nessisary setup
			if(!UserDataStorage.listExist()) {UserDataStorage.startList();}
			
			try {
				try {
			readApp();
				}catch(ClassNotFoundException r) {}
			
			}catch(IOException e) {}
			
			// start up the calculator
			LoginPage lg = loader.getController();
			lg.start(primaryStage);
			primaryStage.setOnCloseRequest(e -> control.GenericController.closeProgram());
			mainStage.setScene(scene);
			mainStage.setTitle("Login Page");
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * readApp is the method that reads in the serialized data on startup
	 * @throws IOException throws ioexception when trying to read in serialized data
	 * @throws ClassNotFoundException Throws exception on class errors when loading in data
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
		/**
		 * Main method launches program
		 * @param args non used arg
		 */
	public static void main(String[] args) {
		
		launch(args);
	}
}
