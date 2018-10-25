package model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * User is an object which stores all the information of a user accessing the app. Stores the 
 * name of the user and the list of tags and albums the user has created/has access to
 * @author Niles Ball, Scott Lahart
 *
 */
public class User implements Serializable{
		/**
	 	* svuid for users
	 	*/
		private static final long serialVersionUID = -115821793257958721L;
		/**
		 * The username of the user
		 */
		String userName;
		/**
		 * The list of albums of the user
		 */
		ArrayList<Album> aList;
		/**
		 * The list of tag types the user has
		 */
		private ArrayList<Tag> tagValues;
		
		/**
		 * Initializes a user object with an empty list of albums and two base tag types
		 * @param name the new name of the user
		 */
		public User(String name){
			this.userName = name;
			this.aList = new ArrayList<Album>();
			tagValues = new ArrayList<Tag>();
			Tag t = new Tag("person", "", false);
			tagValues.add(t);
			t = new Tag("location", "", true);
			tagValues.add(t);
		}
		
		/**
		 * Returns the username 
		 * @return username
		 */
		public String getUserName() {
			return userName;
		}
		/**
		 * Prints the username of the user
		 */
		public String toString() {
			return userName;
		}
		/**
		 * Equals method which returns true if the users share usernames
		 * @param u user to compare with
		 * @return true if they share usernames, false otherwise
		 */
		public boolean equals(User u) {
			if(this.userName.equals(u.userName)) {return true;}
			else {return false;}
		}
		
		/**
		 * Adds a new tag type for the user
		 * @param tagType new tag type
		 */
		public void addTagType(Tag tagType) {
			tagValues.add(tagType);
		}
		
		/**
		 * Returns a list of tags the user has access to 
		 * @return taglist
		 */
		public ArrayList<Tag> getTagValues() {
			return tagValues;
		}
		/**
		 * Returns the list of albums the user has
		 * @return album list
		 */
		public ArrayList<Album> getAlbums() {
			return aList;
		}
		/**
		 * Adds an album to the users album list
		 * @param a new album
		 */
		public void addAlbum(Album a) {
			aList.add(a);
		}
		public void removeAlbum(Album a) {
			aList.remove(a);
		}
}
	

