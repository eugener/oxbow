package org.oxbow.swingbits.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;


import static junit.framework.Assert.*;

public class CollectionUtilsTests {
	
	@Test 
	public void nullCollectionIsEmpty() {
		Collection<?> c = null;
		assertEquals( true, CollectionUtils.isEmpty(c));
	}

	@Test 
	public void emptyCollectionIsEmpty() {
		Collection<?> c = Collections.emptyList();
		assertEquals( true, CollectionUtils.isEmpty(c));
	}
	
	@Test 
	public void nonEmptyCollectionIsNotEmpty() {
		Collection<?> c = Arrays.asList("123");
		assertEquals( false, CollectionUtils.isEmpty(c));
	}
	
}
