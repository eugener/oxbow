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

import java.awt.Component;
import java.util.Collection;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;


class ActionBuilderHelper {

	protected <T> T createGroup( MenuAdapter<T> menu, Collection<Action> actions ) {
		int item = 1; // has to be 1 to compare to size
		int size = actions.size();
		for( Action a: actions ) {
			createAction( menu, a, item++ == size );
		}
		return menu.get();
	}

	@SuppressWarnings("unchecked")
	private <T> T createAction( MenuAdapter<T> menu, Action a, boolean isLast ) {
		
		if ( a instanceof Collection ) {

			if ( isCollapsed(a)) {
				JMenu m = createGroup( new JMenuAdapter(new JMenu(a)), (Collection<Action>)a );
				menu.add(m);
			} else {
				menu.addSeparator();
				createGroup( menu, (Collection<Action>)a );
				if ( !isLast ) menu.addSeparator();
			}

		} else {

			if ( Actions.isSeparator(a)) {
				if ( !isLast ) menu.addSeparator();
			} else {
				
				if ( isCheckAction(a)) {
					menu.add(new JCheckBoxMenuItem(prepareCheckAction(a)));
				} else if ( isRadioAction(a)) {
					JMenuItem item = menu.add(new JRadioButtonMenuItem(prepareCheckAction(a)));
					menu.getButtonGroup().add( item );
				} else {
					menu.add(new JMenuItem(a));
				}
			}

		}

		return menu.get();

	}
	
	private Action prepareCheckAction( Action a ) {
		if ( a.getValue(Action.SELECTED_KEY) == null ) {
			a.putValue(Action.SELECTED_KEY, Boolean.FALSE);
		}
		return a;
	}
	
	private boolean isCheckAction( Action a ) {
		return a.getClass().getAnnotation(CheckAction.class) != null;
	}

	private boolean isRadioAction( Action a ) {
		return a.getClass().getAnnotation(RadioAction.class) != null;
	}
	
	private boolean isCollapsed( Action a ) {
		return a instanceof ActionGroup && ((ActionGroup)a).isCollapsed();
	}

	
 /////// ADAPTERS ///////////////////////////////////////////////////////////////////////	
	
	abstract class MenuAdapter<T> {

		protected T target;
		private ButtonGroup bg;

		public MenuAdapter( T target ) {
			this.target = target;
		}

		public T get() { return target;	}

		public void addSeparator() {}
		
		public ButtonGroup getButtonGroup() {
			if (bg == null) {
				bg = new ButtonGroup();
			}
			return bg;
		}

		public abstract JMenuItem add( JMenuItem  menuItem );

		

	}

	class JMenuBarAdapter extends MenuAdapter<JMenuBar>{

		public JMenuBarAdapter( JMenuBar mb ) {
			super( mb );
		}

		@Override
		public JMenuItem add(JMenuItem menuItem) {

			Component c = target.add(menuItem);
			if ( c instanceof JMenu ) {
				((JMenu)c).setIcon(null);
			}
			return menuItem;

		}

	}

	class JToolBarAdapter extends MenuAdapter<JToolBar>{

		public JToolBarAdapter( JToolBar tb ) {
			super( tb );
		}

		@Override
		public JMenuItem add(JMenuItem menuItem) {
			Action action = menuItem.getAction();
//			Action action = menuItem instanceof JMenu? new ActionDropDownMenu((ActionGroup) menuAction): menuAction;
			
			AbstractButton b = add( action, menuItem );
			b.setHorizontalTextPosition(SwingConstants.LEADING);
			b.putClientProperty("hideActionText", action.getValue(Action.SMALL_ICON) != null);
			return menuItem;
		}
		
		private AbstractButton add( Action action, JMenuItem item ) {
			
			if ( item instanceof JCheckBoxMenuItem || item instanceof JRadioButtonMenuItem ) {
				return (AbstractButton) target.add( new JToggleButton( action ));
			} else if ( item instanceof JMenu ) {
				return target.add( new ActionDropDownMenu((ActionGroup) action) );
			} else {
				return target.add( action );
			}
			
		}
		

	}

	class JPopupMenuAdapter extends MenuAdapter<JPopupMenu>{

		public JPopupMenuAdapter( JPopupMenu menu ) {
			super( menu );
		}

		@Override
		public JMenuItem add(JMenuItem menuItem) {
			target.add(menuItem);
			return menuItem;
		}

		@Override
		public void addSeparator() {
			int count = target.getComponentCount();
			boolean canAddSeparator = count != 0 &&
					target.getComponent(count-1).getClass() != JPopupMenu.Separator.class;
			if ( canAddSeparator ) target.addSeparator();
		}

	}

	class JMenuAdapter extends MenuAdapter<JMenu> {

		public JMenuAdapter( JMenu menu ) {
			super(menu);
		}

		@Override
		public JMenuItem add(JMenuItem menuItem) {
			target.add(menuItem);
			return menuItem;
		}

		@Override
		public void addSeparator() {
			int count = target.getMenuComponentCount();
			boolean canAddSeparator = count != 0 &&
					target.getMenuComponent(count-1).getClass() != JPopupMenu.Separator.class;
			if ( canAddSeparator ) target.addSeparator();
		}

	}

}
