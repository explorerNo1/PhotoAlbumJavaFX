package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.image.Image;
/**
 * Photo is an object which contains the values of a photo to be stored in the album; the path 
 * to the image, the date the image was captured, its list of tags, and its caption
 * @author Niles Ball, Scott Lahart
 *
 */
public class Photo implements Serializable{
	/**
	 * svuid for photo
	 */
	private static final long serialVersionUID = -739446461717260617L;
	/**
	 * The list of tags for the photo
	 */
	private ArrayList<Tag> tagList;
	/**
	 * The caption of the photo
	 */
	private String caption;
	/**
	 * The calendar object associated with the photo
	 */
	private Calendar cal;
	/**
	 * The filepath to the photos image
	 */
	private String path;

	/**
	 * Creates and initializes a photo object given the path and last modified date
	 * @param path path to image
	 * @param lastModified last modified date in mili
	 */
	public Photo(String path, long lastModified) {
		
		this.path = path;
		
		tagList = new ArrayList<Tag>();
			
		caption = "";
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		cal.setTimeInMillis(lastModified);
	}
	
	/**
	 * Adds a tag to the photo's tag list
	 * @param tag new tag
	 */
	public void addTag(Tag tag) {
		if (tagList==null) {
			tagList = new ArrayList<Tag>();
		}
		tagList.add(tag);
	}
	
	/**
	 * Returns the image which the photo path points to
	 * @return photo image
	 */
	public Image getPhoto() {
		Image photo = new Image(path);
		return photo;
	}
	/**
	 * Returns the list of tags this photo has
	 * @return tagList
	 */
	public ArrayList<Tag> getTagList() {
		return tagList;
	}
	/**
	 * Adds or renames the caption of this photo
	 * @param caption new caption
	 */
	public void addCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * Removes a tag from this photo's tag list
	 * @param t tag to remove
	 */
	public void removeTag(Tag t) {
		tagList.remove(t);
	}
	/**
	 * Returns the filepath of this photo
	 * @return file path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * prints the caption of this photo
	 */
	public String toString() {
		return caption;
	}
	/**
	 * returns the last modified date of this photo
	 * @return last modified date in miliseconds
	 */
	public long getDate() {
		return cal.getTimeInMillis();
	}
	
	/**
	 * Returns the last modified date of this photo as a string
	 * @return last modified date
	 */
	public String getDateString() {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		return month+"/"+day+"/"+year;
	}
	/**
	 * compares two photo objects based on their paths and tag lists
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Photo) && o==null) {
			return false;
		}
		Photo other = (Photo)o;
		/*
		if ((caption.equals(other.toString())&&(other.getPath().equals(path)))) {
			if (other.getTagList().equals(tagList)) {
				return true;
			}
		}	
		*/
		
		if (path.equals(other.getPath())) {
			return true;
		}
		
		return false;
	}
	
}
