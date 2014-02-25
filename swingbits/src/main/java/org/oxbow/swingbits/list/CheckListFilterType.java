package org.oxbow.swingbits.list;

import java.util.regex.PatternSyntaxException;

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
        
    },

    REGEX {

        @Override
        public boolean include( String element, String filter ) {

            if ( element == null || filter == null ) return false;
            try {
                return element.matches(filter);
            } catch (PatternSyntaxException e) {
                return false;
            }

        }

    };
    
    public abstract boolean include( String element, String filter );
    
}
