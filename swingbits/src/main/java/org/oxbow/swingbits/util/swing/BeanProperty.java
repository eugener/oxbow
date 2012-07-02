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
