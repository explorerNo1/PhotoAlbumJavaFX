package model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * StorageObject is an object which allows for the storage of users
 * @author Niles Ball, Scott Lahart
 *
 */
public class storageObject  implements Serializable {
	
	/**
	 * Serial ID for users
	 */
	private static final long serialVersionUID = 1069244445892394220L;
	/**
	 * Arraylist of users in the program
	 */
	public ArrayList<User> userList;
	
	/**
	 * This function makes a copy of the user data. The copy in use is static and thus is not Serializable. 
	 * @param u user array list
	 */
	public storageObject(ArrayList<User> u) {
		
		userList = new ArrayList<User>(u);
					
		}
			
	

}
