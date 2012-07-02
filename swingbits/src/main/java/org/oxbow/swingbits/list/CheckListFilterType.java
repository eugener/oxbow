package org.oxbow.swingbits.list;

public enum CheckListFilterType {

	STARTS_WITH {
		
		@Override
		public boolean include( String element, String filter ) {
			
			if ( element == null || filter == null ) return false;
			return element.startsWith(filter);
			
		}
		
	},
	
	CONTAINS {
	
		@Override
		public boolean include( String element, String filter ) {
			
			if ( element == null || filter == null ) return false;
			return element.contains(filter);
			
		}
		
	};
	
	public abstract boolean include( String element, String filter );
	
}
