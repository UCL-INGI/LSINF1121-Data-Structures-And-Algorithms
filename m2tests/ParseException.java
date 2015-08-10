@SuppressWarnings("serial")
public class ParseException extends Exception {

	private String err;
	
	public ParseException(String err) {
		this.err = err;
	}
	
	public String toString() {
		return err;
	}
	
}
