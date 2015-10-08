package addressbook;

public enum InputValidationCode {

	NONE(" "), TEXT("t"), NUMBER("n"), PHONE("p"), EMAIL("e"), ADDRESS("a");
	
	private String data;
	
	InputValidationCode(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
}
