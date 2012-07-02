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

package org.oxbow.swingbits.dialog.task.design;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import org.oxbow.swingbits.dialog.task.ICommandLinkPainter;
import org.oxbow.swingbits.dialog.task.IContentDesign;
import org.oxbow.swingbits.dialog.task.TaskDialog;

public class DefaultContentDesign implements IContentDesign {


	private ICommandLinkPainter commandButtonPainter;

	@Override
	public void updateUIDefaults() {

		UIManager.put( ICON_MORE_DETAILS,  createResourceIcon( "moreDetails.png" ));
		UIManager.put( ICON_FEWER_DETAILS, createResourceIcon( "fewerDetails.png" ));

		UIManager.put( ICON_COMMAND_LINK, createResourceIcon( "arrowGreenRight.png"));

		UIManager.put( COLOR_MESSAGE_BACKGROUND,     SystemColor.window );
		UIManager.put( COLOR_INSTRUCTION_FOREGROUND, SystemColor.textHighlight.darker() ); //???

		UIManager.put( FONT_INSTRUCTION, deriveFont( "Label.font", null, 1.4f ) );
		UIManager.put( FONT_TEXT, deriveFont( "Label.font", null, 1f ) );

		UIManager.put( TEXT_MORE_DETAILS,  TaskDialog.makeKey("MoreDetails") );
		UIManager.put( TEXT_FEWER_DETAILS, TaskDialog.makeKey("FewerDetails") );

	}


	public TaskDialogContent buildContent() {

		TaskDialogContent content = new TaskDialogContent();
		content.setMinimumSize( new Dimension(200, 70));

		content.lbInstruction.setFont( UIManager.getFont(IContentDesign.FONT_INSTRUCTION ));
		content.lbInstruction.setForeground( UIManager.getColor( IContentDesign.COLOR_INSTRUCTION_FOREGROUND ));

		content.lbText.setFont( UIManager.getFont(IContentDesign.FONT_TEXT ));

		content.pComponent.setOpaque(false);

		content.removeAll();
		content.setLayout( createMigLayout("hidemode 3, fill"));

		// message pane
		JPanel pMessage = new JPanel(
			createMigLayout( "ins dialog, gapx 7, hidemode 3", "[][grow]", "[][]10[grow][]"));
		pMessage.setBackground( UIManager.getColor( IContentDesign.COLOR_MESSAGE_BACKGROUND ) );
		pMessage.add( content.lbIcon,        "cell 0 0 0 2, aligny top");
		pMessage.add( content.lbInstruction, "cell 1 0, growx, aligny top");
		pMessage.add( content.lbText,        "cell 1 1, growx, aligny top");
		pMessage.add( content.pExpandable,   "cell 1 2, grow");
		pMessage.add( content.pComponent,    "cell 1 3, grow");

		content.setBackground( pMessage.getBackground());
		content.add( pMessage, "dock center");

		// footer
		content.pFooter.setLayout( createMigLayout("ins dialog"));
		content.pFooter.add( new JSeparator(), "dock north");
		content.pFooter.add( content.lbFooter, "dock center" );

		content.add( content.pFooter, "dock south" );

		// command pane
		content.pCommandPane.setLayout(
			createMigLayout( "ins dialog, gapy 2, hidemode 3", "[pref!][grow]", "[pref][pref]"));
		content.pCommandPane.add( content.pCommands,     "cell 1 0, alignx right" );
		content.pCommandPane.add( content.cbDetails,     "cell 0 0");
		content.pCommandPane.add( content.cbFooterCheck, "cell 0 1");
		content.pCommandPane.add( new JSeparator(), "dock north");

		content.add( content.pCommandPane, "dock south");

		return content;

	}

	@Override
	public boolean isCommandButtonSizeLocked() {
		return true;
	}

	private final static String fixDebug( final String lc ) {
		if ( !TaskDialog.isDebugMode() ) return lc;
		return lc.toLowerCase().indexOf("debug") < 0? "debug," + lc: lc;
	}
	
	protected MigLayout createMigLayout( String layoutConstraints ) {
		return new MigLayout( fixDebug(layoutConstraints));
	}

	protected MigLayout createMigLayout( String layoutConstraints, String colConstraints, String rowConstraints ) {
		return new MigLayout( fixDebug(layoutConstraints), colConstraints, rowConstraints );
	}
	
	
	@Override
	public ICommandLinkPainter getCommandLinkPainter() {
		if (commandButtonPainter == null) {
			commandButtonPainter = new DefaultCommandLinkPainter();
		}
		return commandButtonPainter;
	}


	/**
	 * Creates icon from the image file
	 * @param name
	 * @return
	 */
	protected static final Object createResourceIcon( final String name ) {

		return new UIDefaults.ActiveValue() {

			@Override
			public Object createValue(UIDefaults table) {
				return new ImageIcon( TaskDialog.class.getResource(name));
			}

		};

	}

	/**
	 * Derives font from UIDefaults resource
	 * @param name resource name
	 * @param style font style, if null resource's style is used
	 * @param sizeFactor
	 * @return
	 */
	protected final Object deriveFont( final String name, final Integer style, final float sizeFactor ) {

		return new UIDefaults.ActiveValue() {

			@Override
			public Object createValue(UIDefaults table) {
				Font font = UIManager.getFont(name);
				float factor = sizeFactor == 0f? 1: sizeFactor;
				if ( style == null && factor == 1f ) return font;
				return font.deriveFont( style == null? font.getStyle(): style, font.getSize2D() * factor );
			}

		};

	}


}
