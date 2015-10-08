package addressbook;

import java.util.ArrayList;
import java.util.Iterator;

public class ContactIterator implements Iterator<String> {

	private ArrayList<String> contacts;
	private int position;
	
	public ContactIterator(Contact contact) {
		this.contacts = contact.getContacts();
	}
	
	
	@Override
	public boolean hasNext() {
		return position < contacts.size();
	}

	@Override
	public String next() {
		String contactObj = contacts.get(position);
		position++;
		return contactObj;
	}
	
}
