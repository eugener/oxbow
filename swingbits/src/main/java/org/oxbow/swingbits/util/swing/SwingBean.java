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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Base for all beans not inherited from JComponent
 *
 * Created on Mar 1, 2010
 * * @author Eugene Ryzhikov
 *
 */
public class SwingBean {

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


	public void addPropertyListener( PropertyChangeListener pl ) {
		propertyChangeSupport.addPropertyChangeListener(pl);
	}

	public void addPropertyListener( String propertyName, PropertyChangeListener pl ) {
		propertyChangeSupport.addPropertyChangeListener( propertyName, pl );
	}

	public void removePropertyListener( PropertyChangeListener pl ) {
		propertyChangeSupport.removePropertyChangeListener(pl);
	}

	public void removePropertyListener( String propertyName, PropertyChangeListener pl ) {
		propertyChangeSupport.removePropertyChangeListener( propertyName, pl );
	}
	
	
	protected abstract class Property<T> extends BeanProperty<T> {
		public Property( String propertyName, boolean forceChange ) {
			super( propertyChangeSupport, propertyName, forceChange );
		}
		
		public Property( String propertyName ) {
			super( propertyChangeSupport, propertyName );
		}
	}

}
