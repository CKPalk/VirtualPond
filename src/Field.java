/** Stores information on each field (column) in the address book.
 */
public class Field {
	/** Construct a Field not yet associated with any FieldList.
	 *  <p>
	 *  Should be added to FieldList before further use.
	 *
	 *  @param t Type of field
	 *  @param n Name to call field
	 */
	public  Field(FieldType t, String n) {}
	/** Construct a Field using a .pond file header entry.
	 *
	 *  @param header A String representing this Field
	 *
	 *  @see #formatString
	 */
	public  Field(String header) {}

	/** Type of field for validation, etc.
	 */
	private final FieldType type;
	/** Unique field identifier.
	 *  <p>
	 *  If set to <code>-1</code>, indicates Entry is not associated with a FieldList.
	 */
	private int     id = -1;

	/** Display name of the field.
	 *  <p>
	 *  Also used for identification in .pond file header.
	 */
	private String  name;
	/** Whether the field can be removed by the user.
	 *  <p>
	 *  No effect on {@link FieldList#removeField(int)}, just UI.
	 */
	private boolean required;

	/** Get unique ID of this Field.
	 *  <p>
	 *  Package visibility as we should mainly be passing around entire Field objects.
	 *
	 *  @return {@link #id}
	 */
	        int     getID() {}
	/** Reset {@link #id} to new value.
	 *  <p>
	 *  Use with care as will break association with data in {@link Entry} objects.
	 *
	 *  @param newID New ID for the Field
	 */
	        void    setID(int newID) {}

	/** Get display name of this Field.
	 *
	 *  @return {@link #name}
	 */
	public  String  getName() {}
	/** Change the display name of this Field.
	 *  <p>
	 *  Will probably want to run sanity check to ensure no invalid charactern
	 *    (eg. tab or null) are displayed.
	 *
	 *  @param n The new name for this Field
	 */
	public  void    setName(String n) {}

	/** Generates a String suitable for representing this Field in a .pond header.
	 *  <p>
	 *  Should include {@link FieldType#typeCode} and {@link #name}
	 *
	 *  @return Properly formatted String representation
	 */
	public  String  formatString() {}
	
	/** Whether the field can be removed by the user.
	 *  <p>
	 *  No effect on {@link FieldList#removeField(int)}, just UI.
	 *
	 *  @return {@link #required}
	 */
	public  boolean canDelete() {}

	/** Generate copy of this Field to add to another FieldList.
	 *  <p>
	 *  Should reset {@link #id} of copy to <code>-1</code> to dissassociate
	 *    from any previous FieldList.
	 */
	public  Field   copy() {}

	/** Test the string for validity given the type of this field.
	 *  <p>
	 *  Should switch on {@link #type} for relevant implementation.
	 *
	 *  @param test The String to test
	 *
	 *  @return Whether <code>test</code> is valid
	 */
	public  boolean validate(String test) {}
}
