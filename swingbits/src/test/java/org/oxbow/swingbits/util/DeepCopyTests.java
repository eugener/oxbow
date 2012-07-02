package org.oxbow.swingbits.util;

import static junit.framework.Assert.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class DeepCopyTests {

	
	@Test
	public void notSameReferences() {
		
		String a = "String";
		assertNotSame( a, DeepCopy.copy(a) );
		
	}

	@SuppressWarnings("serial")
	static class ObjToCopy implements Serializable {
		
		public String s = "original";
		public int i = 8;
		public List<String> lst = Arrays.asList("aaa","bbb", "ccc");
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i;
			result = prime * result + ((lst == null) ? 0 : lst.hashCode());
			result = prime * result + ((s == null) ? 0 : s.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ObjToCopy other = (ObjToCopy) obj;
			if (i != other.i)
				return false;
			if (lst == null) {
				if (other.lst != null)
					return false;
			} else if (!lst.equals(other.lst))
				return false;
			if (s == null) {
				if (other.s != null)
					return false;
			} else if (!s.equals(other.s))
				return false;
			return true;
		}
		
	}
	
	
	@Test
	public void attributesAreEqual() {
		
		ObjToCopy a = new ObjToCopy();
		assertEquals( a, DeepCopy.copy(a) );
		
	}

	
}
