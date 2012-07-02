package org.oxbow.swingbits.util;

public class Preconditions {

	private Preconditions(){}
	
	public static void checkArgument(boolean expression) {
	    if (!expression) throw new IllegalArgumentException();
    }
	
	public static void checkArgument(boolean expression, String message ) {
	    if (!expression) throw new IllegalArgumentException( String.valueOf(message));
    }
	
	public static <T> T checkNotNull(T ref) {
		if (ref == null) throw new NullPointerException();
		return ref;
	}

	public static <T> T checkNotNull(T ref, String message ) {
		if (ref == null) throw new NullPointerException(String.valueOf(message));
		return ref;
	}
	
	
}
