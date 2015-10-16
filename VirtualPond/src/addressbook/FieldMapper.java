package addressbook;

import java.util.HashMap;

/**
 * Maps field names to indices, and indices to field names.
 * @author atleebrink
 */
public class FieldMapper {
	private HashMap<String, Integer> nameToIndex;
	private HashMap<Integer, String> indexToName;
	
	public FieldMapper(HashMap<String, Integer> nameToIndex, HashMap<Integer, String> indexToName) {
		this.nameToIndex = nameToIndex;
		this.indexToName = indexToName;
	}
	
	/**
	 * Maps a field name to an index.
	 * Beware: not all 
	 * @param fieldName the standardized field name
	 * @return an index or -1 if the field name is not mapped
	 */
	public int getIndex(String fieldName) {
		if( nameToIndex != null ) {
			Integer index = nameToIndex.get(fieldName);
			if( index != null ) {
				return index;
			}
		}
		return -1;
	}

}
