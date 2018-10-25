package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.ErrorPopup;
/**
 * FileController is a class which allows for a file to be selected in order for the file 
 * to be converted into a photo
 * @author Niles Ball, Scott Lahart
 *
 */
public class FileController {
	
	/**
	 * Opens the file manager, allows for a file to be selected, and if it is an image, 
	 * creates a photo object
	 * @return the newly created photo, or null
	 */
	public Photo createPhoto() {
		FileChooser fileChooser = new FileChooser();
		Stage stage = new Stage();
		File file = fileChooser.showOpenDialog(stage);
		if (file==null) {
			return null;
		}
		try {
			String type = Files.probeContentType(file.toPath());
			if (type==null) {
				ErrorPopup.show("Invalid file type selected");
				return null;
			}
			if (type.split("/")[0].equals("image")) {
				Photo picture = new Photo(file.toURI().toString(), file.lastModified());
				return picture;
			}
			ErrorPopup.show("Invalid file type selected");
			
		} catch (IOException e) {
			return null;
		}
		return null;
	}

}
