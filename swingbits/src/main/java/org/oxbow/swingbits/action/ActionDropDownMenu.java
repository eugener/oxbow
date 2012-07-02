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
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

import org.oxbow.swingbits.util.swing.CompoundIcon;

class ActionDropDownMenu extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static final Icon DROPDOWN_ICON =  new ImageIcon( ActionDropDownMenu.class.getResource("dropdown.png"));

	private JPopupMenu menu = null;
	private final ActionGroup action;

	public ActionDropDownMenu( ActionGroup actionGroup ) {
		super( actionGroup.getName(), createIcon(actionGroup));
		this.action = actionGroup;
	}

	private static Icon createIcon( ActionGroup action ) {
		Icon mainIcon = action.getIcon();
		return mainIcon == null? DROPDOWN_ICON: new CompoundIcon(mainIcon, DROPDOWN_ICON);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if ( !( e.getSource() instanceof Component ) ||
			 action == null || action.isEmpty() ||
			 !action.isEnabled() ) return;

		if ( menu == null ) {
			menu = ActionContainerBuilderFactory.getPopupMenuBuilder().build(action);
		}

		Component cmpt = (Component) e.getSource();
		menu.show( cmpt , 0, cmpt.getHeight() );

	}

}