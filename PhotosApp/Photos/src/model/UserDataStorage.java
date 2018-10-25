package model;


import java.io.File;
import java.util.ArrayList;

/**
 * This is the class that holds user data.
 * Used for admin user management and accessing user storage
 * @author Niles Ball, Scott Lahart
 *
 */
public class UserDataStorage {
	
	/**
	**static list of users. Used in tracking album ownership and for data persistance
	*/
	private static ArrayList<User> userList;
	
	/**
	 * Private constructor to prevent making instances
	 */
	private UserDataStorage() {
		
	}
	/**
	 * Returns the arraylist of users
	 * @return userlist
	 */
	public static ArrayList<User> getUserList(){
		return userList;
	}
	
	/**
	 * Loads the userlist from the storageObject
	 * @param o storageObject
	 */
	public static void loadUserList(storageObject o) {
		
		
		userList = o.userList;
		
		//Code to turn rest of sub arrays into obs arrays
		
		
	}
	
	/**
	 * Checks if the user list exists
	 * @return true if exists, false otherwise
	 */
	public static boolean listExist() {
		if(userList != null) {return true;}
		return false;
	}
	
	/**
	 * Adds the stock user in case of first startup
	 */
	public static void startList() {
		userList = new ArrayList<User>();
	
		User u = new User("stock");
		Album a = new Album("stock", 0,-1,-1);
		
		String path = u.getClass().getResource("/StockPhotos/Test.jpg").toString();
		File f = new File(path);
		long date = f.lastModified();

		Photo p1 = new Photo(path, date);

		a.addPhoto(p1);
		
		path = u.getClass().getResource("/StockPhotos/test3.png").toString();
		f = new File(path);
		date = f.lastModified();
		Photo p2 = new Photo(path, date);
		
		path = u.getClass().getResource("/StockPhotos/testphoto.png").toString();
		f = new File(path);
		date = f.lastModified();
		Photo p3 = new Photo(path, date);
		
		path = u.getClass().getResource("/StockPhotos/test4.jpg").toString();
		f = new File(path);
		date = f.lastModified();
		Photo p4 = new Photo(path, date);
		
		path = u.getClass().getResource("/StockPhotos/test5.jpg").toString();
		f = new File(path);
		date = f.lastModified();
		Photo p5 = new Photo(path, date);
		
		a.addPhoto(p2);
		a.addPhoto(p3);
		a.addPhoto(p4);
		a.addPhoto(p5);
		
		u.aList.add(a);
		userList.add(u);
	
	}
	
	/**
	 * Adds a user to the datastorage list
	 * @param s the new username
	 * @return true if added, false if name exists
	 */
	public static boolean addUser(String s) {
		
		//return if duplicate, otherwise add
		for(int x = 0; x < userList.size(); x++) {if(userList.get(x).userName.equals(s)) {return false;}}
		User u = new User(s);
		userList.add(u);
		return true;
		
	}
	/**
	 * Deletes a user from the list
	 * @param u user to be deleted
	 */
	public static void deleteUser(User u) {
		userList.remove(u);
	}
	
	/**
	 * Returns a user object which has this specific username
	 * @param name username
	 * @return user associated with username
	 */
	public static User getUser(String name) {
		
		User u = null;
		for(int x = 0; x < userList.size(); x++) {if(userList.get(x).userName.equals(name)) {return userList.get(x);}}
		return u;
	
	}
	
}
