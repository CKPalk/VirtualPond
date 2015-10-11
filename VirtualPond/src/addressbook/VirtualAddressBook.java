package addressbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VirtualAddressBook {
	// Final static members
	public static final String DEFAULT_DATA_FILENAME = "address_book_default_data"; // will be *.pond�
	public static final String FILE_EXTENSION = "pond™";
	
	// --- Constructor ---
	public VirtualAddressBook() {
		this(getDefaultBook());
	}
	
	VirtualAddressBook(File addressBookFile) {
		this(new VirtualBookReader(addressBookFile).read());
	}
	
	public VirtualAddressBook(ArrayList<Field> fields, ArrayList<Contact> contactsSet) {
		this.fields = fields;
		this.contacts = contactsSet;
		this.indexToSortBy = 0;
	}
	
	// private copy constructor
	public VirtualAddressBook(VirtualAddressBook vab) {
		this(vab.fields, vab.contacts);
	}
	
	// --- Private Data Members ---
	ArrayList<Field> fields;
	ArrayList<Contact> contacts;
	int indexToSortBy;
	
	
	// --- Public Class Methods ---
	public void switchFields(int index1, int index2) {
		Collections.swap(fields, index1, index2);
		for (Contact contact : contacts) {
			Collections.swap(contact.getContactDataArray(), index1, index2);
		}
	}
	public void sortByFieldAtIndex(int index) {
		// Custom comparator to sort by specified index
		this.indexToSortBy = index;
		Collections.sort(contacts, new Comparator<Contact>() {
			public int compare(Contact contact1, Contact contact2) {
				return contact1.getContactDataAt(index).compareToIgnoreCase(contact2.getContactDataAt(index));
			}
		});
	}
	
	
	// SETTERS:
	public void newField(Field newField) {
		fields.add(newField);
		for (Contact contact : contacts) {
			contact.newCell();
		}
	}
	public void newFieldAtIndex(Field newField, int index) {
		fields.add(index, newField);
		for (Contact contact : contacts) {
			contact.newCellAtIndex(index);
		}
	}
	public void setFieldAtIndex(String newData, int index) {
		fields.get(index).data = newData;
	}
	public void newContact(Contact contact) {
		contacts.add(contact);
		
		// Do not sort when adding a new contact
		// sortByFieldAtIndex(indexToSortBy);
	}
	public void setContactAtIndexes(String newData, int contactIndex, int fieldIndex) {
		contacts.get(contactIndex).editDataAtIndex(newData, fieldIndex);;
	}
	// GETTERS:
	public Field getFieldAtIndex(int index) {
		return fields.get(index);
	}
	public ArrayList<Field> getFields() {
		return fields;
	}
	public ArrayList<Contact> getContacts() {
		return contacts;
	}
	
	// --- Private Class Methods ---
	private static VirtualAddressBook getDefaultBook() {
		return new VirtualBookReader(DEFAULT_DATA_FILENAME, true).read();
	}
	
	
	
}
