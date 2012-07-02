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

package org.oxbow.swingbits.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

public final class ActionGroup extends AbstractAction implements Collection<Action> {

	private static final long serialVersionUID = -8373261802979340928L;

	private final List<Action> actions = new ArrayList<Action>();
	private boolean collapsed = true;


	protected ActionGroup( String name, Icon icon ) {
		super( name, icon );
	}

	public ActionGroup actions( Collection<Action> actions ) {
		addAll(actions);
		return this;
	}

	public ActionGroup actions( Action... actions ) {
		return actions( Arrays.asList(actions));
	}

	public ActionGroup collapsed( boolean collapsed ) {
		this.collapsed = collapsed;
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	public boolean isCollapsed() {
		return collapsed;
	}

	protected void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public String getName() {
		return (String) getValue(Action.NAME);
	}

	public Icon getIcon() {
		return (Icon) getValue(Action.SMALL_ICON);
	}

	/// Collection methods

	@Override
	public int size() { return actions.size(); }

	@Override
	public boolean isEmpty() { return actions.isEmpty(); }

	@Override
	public boolean contains(Object o) { return actions.contains(o); }

	@Override
	public Iterator<Action> iterator() { return actions.iterator(); }

	@Override
	public Object[] toArray() { return actions.toArray(); }

	@Override
	public <T> T[] toArray(T[] a) { return actions.toArray(a); }

	@Override
	public boolean add(Action a) { return actions.add(a); }

	@Override
	public boolean remove(Object o) { return actions.remove(o); }

	@Override
	public boolean containsAll(Collection<?> c) { return actions.containsAll(c);}

	@Override
	public boolean addAll(Collection<? extends Action> c) { return actions.addAll(c); }

	@Override
	public boolean removeAll(Collection<?> c) { return actions.removeAll(c); }

	@Override
	public boolean retainAll(Collection<?> c) { return actions.retainAll(c); }

	@Override
	public void clear() { actions.clear(); }

}
