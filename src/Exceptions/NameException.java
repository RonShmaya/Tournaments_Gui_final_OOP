package Exceptions;

public class NameException extends Exception {

	public NameException() {
		super("Exception---> Name Cannot Be Empty");
	}
	public NameException(String name) {
		super("Exception---> Name Cannot get Digit, the name---> "+name+", not ok");
	}

}
