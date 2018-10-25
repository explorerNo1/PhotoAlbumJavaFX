package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Album is an object which stores a list of photos, and has a specific and unique name
 * @author Niles Ball, Scott Lahart
 *
 */
public class Album implements Serializable{

	/**
	 * SVUID for album
	 */
	private static final long serialVersionUID = 3960150915572722329L;
	/**
	 * The specific name of the album
	 */
	String albumName;
	/**
	 * The number of photos in the album
	 */
	int photoCount;
	/**
	 * The date of the earliest photo found in the album
	 */
	long earlyDate;
	/**
	 * The date of the last photo found in the album
	 */
	long lateDate;
	/**
	 * The list of photos in the album
	 */
	private ArrayList<Photo> photoList;
	
	/**
	 * Album constructor; creates and initializes an album object
	 * @param name name of the album
	 * @param photoCount number of photos in album
	 * @param earlyDate earliest date of photos
	 * @param lateDate latest date of photos
	 */
	public Album(String name, int photoCount, long earlyDate, long lateDate) {
	this.albumName = name;
	this.photoCount = photoCount;

	this.earlyDate = earlyDate;
	this.lateDate = lateDate;
	photoList = new ArrayList<Photo>();

	}
	
	/**
	 * Updates the photocount, earlyDate and lateDate when photos are added/deleted
	 */
	public void updateAlbum() {
		photoCount = photoList.size();
		for(int x = 0; x < photoList.size(); x++) {
			// initial
			if (earlyDate==-1) {
				earlyDate = photoList.get(x).getDate();
			}
			if (lateDate==-1) {
				lateDate = photoList.get(x).getDate();
			}
			
			if(photoList.get(x).getDate() < earlyDate) {earlyDate = photoList.get(x).getDate();}
			if(photoList.get(x).getDate() > lateDate) {lateDate = photoList.get(x).getDate();}
			
		}
		
		
	}
	/**
	 * Returns the name of the album
	 * @return albumname
	 */
	public String getAlbumName() {
		return albumName;
	}
	
	/**
	 * Returns the number of photos in the album
	 * @return photoCount
	 */
	public int getPhotoCount() {
		return photoCount;
	}
	
	/**
	 * Prints the album name
	 */
	public String toString() {

		return albumName;
	}
	
	/**
	 * Returns the list of photos in the album
	 * @return photoList
	 */
	public ArrayList<Photo> getPhotos() {
		return photoList;
	}
	
	/**
	 * Sets the list of photos in the album to the passed value
	 * @param p list of photos
	 */
	public void setPhotos(ArrayList<Photo> p) {
		photoList = p;
		updateAlbum();
	}
	
	/**
	 * Renames the album
	 * @param newName new album name
	 */
	public void rename(String newName) {
		albumName = newName;
	}
	
	// simple add photo; don't know if duplicates are allowed
	/**
	 * Adds a photo to the album list of photos
	 * @param p new photo
	 */
	public void addPhoto(Photo p) {
		if (photoList.contains(p)) {
			// do nothing
		}
		else {
			photoList.add(p);
			updateAlbum();
		}
	}
	
	/**
	 * Delets a photo from the album's list of photos
	 * @param p photo to delete
	 */
	public void deletePhoto(Photo p) {
		photoList.remove(p);
		updateAlbum();
	}
	
	/**
	 * Returns the earliest date in the album as a String date
	 * @return earlyDate
	 */
	public String getEarlyDate() {
		if (earlyDate==-1) {
			return "No photos";
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		cal.setTimeInMillis(earlyDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		return month+"/"+day+"/"+year;
	}
	
	/**
	 * Returns the latest date in the album as a String date
	 * @return lateDate
	 */
	public String getLateDate() {
		if (lateDate==-1) {
			return "No photos";
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		cal.setTimeInMillis(lateDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		return month+"/"+day+"/"+year;
	}
	
	//Comparing Albums only matters to prevent duplicate album names. 
	@Override
	/**
	 * compares two albums by their album name
	 */
	public boolean equals(Object obj) {
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Album a = (Album)obj;
		return(this.albumName.equals(a.albumName));
		
	}

}
