/*
 * Copyright (c) 2009-2011, EzWare
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.Redistributions
 * in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.Neither the name of the
 * EzWare nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.oxbow.swingbits.util.swing;

import java.beans.PropertyChangeSupport;

/**
 * Incupsulates Bean Property concept. 
 * Includes automated property change notifications
 * @author Eugene Ryzhikov
 *
 * @param <T>
 */
public abstract class BeanProperty<T> {

    private PropertyChangeSupport notificator;
    private String propertyName;
    private boolean forceChange;

    public BeanProperty( PropertyChangeSupport notificator, String propertyName, boolean forceSet ) {
        this.notificator = notificator;
        this.propertyName = propertyName;
        this.forceChange = forceSet;
    }
    
    public BeanProperty( PropertyChangeSupport notificator, String propertyName) {
        this( notificator, propertyName, false );
    }
    
    /**
     * Returns property value
     * @return
     */
    public abstract T get();
    
    /**
     * Sets property value
     * @param value
     */
    protected abstract void setValue( T value );
    
    /**
     * Sets property value. Sends property change notification if value is actually changed
     * @param newValue
     * @return true if value was changed
     */
    public final boolean set( T newValue ) {
        
        boolean result;
        T oldValue = get();
        if ( result = forceChange || !sameValues( oldValue, newValue )) {
            setValue(newValue);
            notificator.firePropertyChange( propertyName, oldValue, newValue );
        }
        return result;
        
    }
    
    private boolean sameValues( T a, T b ) {
       return a == b || ( a != null && b != null && a.equals( b ));
    }
    
    
}
