package addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Field {

	// hard-coded indices: follow these for default address books
	public static final int CITY = 0;
	public static final int STATE = 1;
	public static final int ZIP = 2;
	public static final int DELIVERY = 3;
	public static final int SECOND = 4;
	public static final int LASTNAME = 5;
	public static final int FIRSTNAME = 6;
	public static final int PHONE = 7;
	public static final int NUM_DEFAULT = 8;

	// maps hard-coded indices to friendly names to use for display purposes
	public static final HashMap<Integer, String> friendlyNames = new HashMap<Integer, String>(NUM_DEFAULT) {
		private static final long serialVersionUID = 1L;
	{
		put(CITY, "City");
		put(STATE, "State");
		put(ZIP, "Zip");
		put(DELIVERY, "Address 1");
		put(SECOND, "Address 2");
		put(LASTNAME, "Last Name");
		put(FIRSTNAME, "First Name");
		put(PHONE, "Phone Number");		
	}};

	// maps hard-coded indices to Tab Separated Value formatted file columns
	public static final HashMap<Integer, String> tsvNames = new HashMap<Integer, String>(NUM_DEFAULT) {
		private static final long serialVersionUID = 1L;
	{
		put(CITY, "CITY");
		put(STATE, "STATE");
		put(ZIP, "ZIP");
		put(DELIVERY, "Delivery");
		put(SECOND, "Second");
		put(LASTNAME, "LastName");
		put(FIRSTNAME, "FirstName");
		put(PHONE, "Phone");
	}};
	
	// this field's data
	public String name;

	// constructor
	public Field(String data) {
		this.name = data;
	}
	
	// utility
	public static ArrayList<Field> createDefaultFieldList() {
		return new ArrayList<Field>(NUM_DEFAULT) {
			private static final long serialVersionUID = 1L;
		{
			for( int i = 0; i < NUM_DEFAULT; i++ ) add( new Field( tsvNames.get(i) ) );
		}};
	}
	
	// --- Public Class Methods ---
	@Override
	public String toString() {
		return name;
	}
	

}
