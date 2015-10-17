package addressbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Contact implements Iterable<String> {

	// this.valCode = validationCode;
	// --- Private Data Members ---
	private List<String> contactDataArray;

	// --- Constructors ---
	public Contact() {
		contactDataArray = new ArrayList<>();
	}
	public Contact(int numFields) {
		contactDataArray = new ArrayList<>();
		for( int i = 0; i < numFields; i++ ) contactDataArray.add("");
	}
	public Contact(ArrayList<String> contactDataArray) {
		this.contactDataArray = contactDataArray;
	}
	
	
	// --- Public Class Methods ---
	
	// FUNCTIONALITY:
	void newCell() {
		contactDataArray.add("");
	}
	void newCellAtIndex(int index) {
		contactDataArray.add(index, "");
	}
	void newDataAtIndex(String newData, int index) {
		contactDataArray.add(index, newData);
	}
	void editDataAtIndex(String newData, int index) {
		contactDataArray.set(index, newData);
	}

	// SETTERS:
	public void setContactDataArray(ArrayList<String> contactDataArray) {
		this.contactDataArray = contactDataArray;
	}
	
	// GETTERS:
	public List<String> getContactDataArray() {
		return contactDataArray;
	}
	public String getContactDataAt(int index) {
		if( this != null && contactDataArray != null && index < contactDataArray.size() ) {
			return contactDataArray.get(index);
		}
		return "";
	}
	
	// toString()
	public String toString() {
		return String.join(" ", this.contactDataArray);
	}
	
	
	// ITERABLE
	public List<String> getContacts() {
		return contactDataArray;
	}
	
	@Override
	public Iterator<String> iterator() {
		return new ContactIterator(this);
	}

	
}
