package org.oxbow.swingbits.list;

import java.util.regex.PatternSyntaxException;

public enum CheckListFilterType implements IListFilter {

    STARTS_WITH {
        
        @Override
        public boolean include( String element, String pattern ) {
            
            if ( element == null || pattern == null ) return false;
            return element.startsWith(pattern);
            
        }
        
    },
    
    CONTAINS {
    
        @Override
        public boolean include( String element, String pattern ) {
            
            if ( element == null || pattern == null ) return false;
            return element.contains(pattern);
            
        }
        
    },

    REGEX {

        @Override
        public boolean include( String element, String pattern ) {

            if ( element == null || pattern == null ) return false;
            try {
                return element.matches(pattern);
            } catch (PatternSyntaxException e) {
                return false;
            }

        }

    };

}
