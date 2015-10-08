/** Main class containing all data required to represent an address book.
 */
public class AddressBook {
	/** Populates {@link #fields} with set of default fields.
	 *  <p>
	 *  Must include all columns from customer-supplied format, but may have
	 *    different names and be in different order:
	 *  <ul>
	 *      <li>First name</li>
	 *      <li>Last name</li>
	 *      <li>Address line 1</li>
	 *      <li>Address line 2</li>
	 *      <li>City</li>
	 *      <li>State</li>
	 *      <li>Zip code</li>
	 *  </ul>
	 */
	public  AddressBook() {}
	/** Load address book from the specified file.
	 *  <p>
	 *  If first line matches customer-supplied format, load using default
	 *    column names and order (above) otherwise assume .pond format
	 *    and load field names and order from file.
	 *  <p>
	 *  If using customer-supplied format with default colums, can
	 *    probably hard-code corespondance when constructing FieldList/Entry.
	 *
	 *  @param file The filename to open
	 */
	public  AddressBook(String file) {}

	/** Filename of the address book.
	 *  <p>
	 *  Should be empty string if address book is newly created and not yet
	 *    associated with any particular file.
	 */
	private String filename;

	/** The fields implemented by this address book.
	 */
	private FieldList fields;
	/** The entries contained in this address book.
	 */
	private EntryList entries;

	/** Gets list of fields in address book.
	 *
	 *  @return Reference to {@link #fields}
	 */
	public  FieldList getFieldList() {}

	/** Gets list of entries in address book.
	 *
	 *  @return Reference to {@link #entries}
	 */
	public  EntryList getEntryList() {}

	/** Checks whether a default save location exists.
	 *
	 *  @return <code>{@link #filename} != ""</code>
	 */
	public  boolean   hasDefaultLocation() {}
	/** Saves address book to default last saved or opened location using .pond format.
	 *  <p>
	 *  Use {@link #fields} to maintain same order as displayed in the UI.
	 */
	public  void      save() {}
	/** Saves address book to specified file in .pond format and updates {@link #filename}.
	 *
	 *  @param filename Location of file to save to
	 */
	public  void      save(String filename) {}
	/** Saves address book to specified file in customer-supplied format.
	 *
	 *  @param filename Location of file to save to
	 */
	public  void      export(String filename) {}

	/* Future features:
	 *   Reorder list of fields
	 *   Import customer-supplied addresses
	 *   Import other .pond files (much more difficult than above)
	 *   Construct queue of changes to implement undo
	 */
}
