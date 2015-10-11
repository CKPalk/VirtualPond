package addressbook;

import java.util.ArrayList;
import java.util.Iterator;


public class Contact implements Iterable<String> {

	// --- Constructors ---
	public Contact(int numFields) {
		contactDataArray = new ArrayList<>();
		for( int i = 0; i < numFields; i++ ) contactDataArray.add("");
	}
	public Contact(ArrayList<String> contactDataArray) {
		this.contactDataArray = contactDataArray;
	}
	// this.valCode = validationCode;
	// --- Private Data Members ---
	private ArrayList<String> contactDataArray;
	
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
	public ArrayList<String> getContactDataArray() {
		return this.contactDataArray;
	}
	public String getContactDataAt(int index) {
		return this.contactDataArray.get(index);
	}
	
	// toString()
	public String toString() {
		return String.join(" ", this.contactDataArray);
	}
	
	
	// ITERABLE
	public ArrayList<String> getContacts() {
		return contactDataArray;
	}
	
	@Override
	public Iterator<String> iterator() {
		return new ContactIterator(this);
	}

	
}
