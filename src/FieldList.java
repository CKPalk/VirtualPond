import java.util.ArrayList;


/** List of Field objects to encapsulate implementation.
 */
public class FieldList {
	/** Construct from .pond header line.
	 *  <p>
	 *  Due to .tsv standard, only single line may be header (ie. not data)
	 *
	 * @param header Tab-separated list of Field names and type prefixes
	 */
	public  FieldList(String header) {}

	/** Core list of Field objects.
	 */
	private ArrayList<Field> fields;
	/** Counter for generating unique field IDs.
	 *  <p>
	 *  Should be increased by one each time to ensure uniqueness, and
	 *    likewise never decreased.
	 */
	private int              availableID = 0;

	/** Counts number of Field objects in the list.
	 *
	 *  @return {@link ArrayList#size() fields.size()}
	 */
	public  int   size() {}

	/** Gets the Field at the specified index location.
	 *
	 *  @param index Index of the field to retrieve
	 *
	 *  @return The requested Field
	 */
	public  Field getField(int index) {}
	/** Insert the given field at the specified index location.
	 *  <p>
	 *  Set {@link Field#id} from {@link #availableID} if necessary.
	 *
	 *  @param index The index location to insert <code>f</code>
	 *  @param f     The field to add
	 */
	public  void  addField(int index, Field f) {}
	/** Removes the Field at the specified index location.
	 *  <p>
	 *  Don't reset Field.id as that should remain the same if ever restored
	 *    to correctly link back with Entry data. While this would technically
	 *    be bad behavior, we need to have some way to ensure proper association.
	 *
	 *  @param index Index of the field to remove
	 *
	 *  @return The removed field to facilitate eg. undo
	 */
	public  Field removeField(int index) {}
}
