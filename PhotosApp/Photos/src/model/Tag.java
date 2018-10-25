package model;

import java.io.Serializable;

/**
 * Tag is an object which can be added to a photo, allowing for a unique identifier for the photo. 
 * A tag consits of a type, a value, and a boolean sayingwhether or not only 
 * one of these types can exist
 * @author Niles Ball, Scott Lahart
 *
 */
public class Tag implements Serializable{
	/**
	 * svuid for tags
	 */
	private static final long serialVersionUID = 3878777436570028476L;
	/**
	 * The type of the tag
	 */
	private String type;
	/**
	 * The value the tag has
	 */
	private String value;
	/**
	 * Whether or not this tag type allows for multiples: false for actual tags
	 */
	boolean isSingular;
	
	/**
	 * Creates a tag object given a type, a value, and an isSingular boolean
	 * @param type the tag type
	 * @param value the tag value 
	 * @param b the isSingular boolean value
	 */
	public Tag(String type, String value, boolean b) {
		this.type = type;
		this.value = value;
		this.isSingular = b;
	}
	
	/**
	 * Converts the tag into a readable string
	 */
	public String toString() {
		return type + "=" + value;
	}
	/**
	 * Returns the type of the tag
	 * @return tag type
	 */
	public String getType() {
		return type;
	}
	/**
	 * Returns if the boolean is singular
	 * @return isSingular
	 */
	public boolean isSingle() {
		return isSingular;
	}
	
	/**
	 * Equals method which returns true if the tags share a type and value
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Tag)||(o==null)) {
			return false;
		}
		Tag tag = (Tag)o;
		if ((tag.type.equals(type))&&(tag.value.equals(value))) {
			return true;
		}
		return false;
	}
}
