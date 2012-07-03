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

package org.oxbow.swingbits.dialog.task;

import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;

import net.miginfocom.swing.MigLayout;

import org.oxbow.swingbits.dialog.task.TaskDialog.StandardCommand;
import org.oxbow.swingbits.dialog.task.design.CommandLinkButton;
import org.oxbow.swingbits.dialog.task.design.CommandLinkButtonGroup;
import org.oxbow.swingbits.util.Strings;
import org.oxbow.swingbits.util.swing.AncestorAdapter;

/**
 *
 * A set of methods to simplify creation of task dialogs
 *
 * @author Eugene Ryzhikov
 *
 */
public final class TaskDialogs {

	private TaskDialogs() {}

	public static final class TaskDialogBuilder {

		private Window parent = null;
		private String title = null;
		private Icon icon = null;
		private String instruction = null;
		private String text = null;
		private Integer inputColumns = null;

		private TaskDialogBuilder() {}

		/**
		 * Sets dialog parent
		 * @param title
		 * @return
		 */
		public TaskDialogBuilder parent( Window parent ) {
			this.parent = parent;
			return TaskDialogBuilder.this;
		}


		/**
		 * Sets dialog title
		 * @param title
		 * @return
		 */
		public TaskDialogBuilder title( String title ) {
			this.title = title;
			return TaskDialogBuilder.this;
		}

		/**
		 * Sets dialog icon
		 * @param icon
		 * @return
		 */
		public TaskDialogBuilder icon( Icon icon ) {
			this.icon = icon;
			return TaskDialogBuilder.this;
		}

		/**
		 * Sets instruction test
		 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
		 * @return
		 */
		public TaskDialogBuilder instruction( String instruction ) {
			this.instruction = instruction;
			return TaskDialogBuilder.this;
		}

		/**
		 * Sets dialog text
		 * @param text
		 * @return
		 */
		public TaskDialogBuilder text( String text ) {
			this.text = text;
			return TaskDialogBuilder.this;
		}

		/**
		 * Sets number of columns in the text field for input dialog
		 * @param columns
		 * @return
		 */
		public TaskDialogBuilder inputColumns( int columns ) {
			this.inputColumns = columns;
			return TaskDialogBuilder.this;
		}

		private String getTitle( String defaultTitle ) {
			return title == null? defaultTitle: title;
		}

		private Icon getIcon( Icon defaultIcon ) {
			return icon == null? defaultIcon: icon;
		}


		/**
		 * Shows simple message using previously set title, icon and instruction
		 */
		public void message() {
			messageDialog(
					parent,
					title,
					icon,
					instruction,
					text).setVisible(true);
		}


		/**
		 * Shows simple information message
		 */
		public void inform() {
			messageDialog(
					parent,
					getTitle( TaskDialog.makeKey("Information")),
					getIcon( TaskDialog.StandardIcon.INFO ),
					instruction,
					text).setVisible(true);
		}


		/**
		 *  Shows simple error message
		 */
		public void error() {
			messageDialog(
				parent,
				getTitle( TaskDialog.makeKey("Error")),
				getIcon( TaskDialog.StandardIcon.ERROR ),
				instruction,
				text).setVisible(true);
		}

		/**
		 * Shows simple question
		 * @return
		 */
		public boolean ask() {
			return questionDialog(
					parent,
					getTitle( TaskDialog.makeKey("Question")),
					getIcon( TaskDialog.StandardIcon.QUESTION ),
					instruction,
					text).show().equals(StandardCommand.OK);
		}

		/**
		 * Shows simple warning message
		 * @deprecated use isConfirmed instead
		 * @return
		 */
		@Deprecated
		public boolean warn() {
			return questionDialog(
					parent,
					getTitle( TaskDialog.makeKey("Warning")),
					getIcon( TaskDialog.StandardIcon.WARNING ),
					instruction,
					text).show().equals(StandardCommand.OK);
		}


		/**
		 * Shows simple warning message
		 * @return true if accepted
		 */
		public boolean isConfirmed() {
			return questionDialog(
					parent,
					getTitle( TaskDialog.makeKey("Warning")),
					getIcon( TaskDialog.StandardIcon.WARNING ),
					instruction,
					text).show().equals(StandardCommand.OK);
		}


		/**
		 * Show exception dialog
		 * @param ex
		 */
		public void showException( Throwable ex ) {

			TaskDialog dlg = new TaskDialog( parent, getTitle(TaskDialog.makeKey("Exception")));

			String msg = ex.getMessage();
			String className = ex.getClass().getName();
			boolean noMessage = Strings.isEmpty(msg);

	        dlg.setInstruction( noMessage? className: msg );
	        dlg.setText( noMessage? "": className );

	        dlg.setIcon( getIcon( TaskDialog.StandardIcon.ERROR ));
	        dlg.setCommands( StandardCommand.CANCEL.derive(TaskDialog.makeKey("Close")));

			JTextArea text = new JTextArea();
			text.setEditable(false);
			text.setFont( UIManager.getFont("Label.font"));
			text.setText(Strings.stackStraceAsString(ex));
			text.setCaretPosition(0);

			JScrollPane scroller = new JScrollPane( text );
			scroller.setPreferredSize(new Dimension(400,200));
			dlg.getDetails().setExpandableComponent( scroller);
			dlg.getDetails().setExpanded(noMessage);

			dlg.setResizable(true);

			// Issue 22: Exception can be printed by user if required
			// ex.printStackTrace();
			dlg.setVisible(true);

		}

		/**
		 * Simplifies the presentation of choice based on radio buttons
		 * @param defaultChoice initial choice selection
		 * @param choices collection of available choices
		 * @return selection index or -1 if nothing is selected
		 */
		public int radioChoice( int defaultChoice, List<String> choices ) {

			TaskDialog dlg = questionDialog( parent, getTitle( TaskDialog.makeKey("Choice")), null, instruction, text);

			ButtonGroup bGroup = new ButtonGroup();
		    List<ButtonModel> models = new ArrayList<ButtonModel>();

		    JRadioButton btn;
			JPanel p = new JPanel( new MigLayout(""));
			for( String c: choices ) {
				btn = new JRadioButton(c);
				btn.setOpaque(false);
				models.add( btn.getModel());
				bGroup.add(btn);
				p.add( btn, "dock north");
			}

			if ( defaultChoice >= 0 && defaultChoice < choices.size()) {
				bGroup.setSelected(models.get(defaultChoice), true);
			}
			p.setOpaque(false);

			dlg.setIcon( getIcon( TaskDialog.StandardIcon.QUESTION));
			dlg.setFixedComponent(p);

			TextWithWaitInterval twi = new TextWithWaitInterval(instruction);
	        dlg.setCommands( StandardCommand.OK.derive(TaskDialog.makeKey("Select"), twi.getWaitInterval()),
	        		         StandardCommand.CANCEL );

			return dlg.show().equals(StandardCommand.OK)? models.indexOf( bGroup.getSelection()) : -1;

		}

		/**
		 * Simplifies the presentation of choice based on radio buttons
		 * @param defaultChoice initial choice selection
		 * @param choices array of available choices
		 * @return selection index or -1 if nothing is selected
		 */
		public int radioChoice( int defaultChoice, String... choices ) {
			return radioChoice( defaultChoice, Arrays.asList(choices));
		}


		/**
		 * Simplifies the presentation of choice based on command links
		 * @param defaultChoice initial choice selection
		 * @param choices collection of available command links
		 * @return selection index or -1 if nothing is selected
		 */
		public int choice( final int defaultChoice, List<CommandLink> choices ) {

			// NOTE: Task dialog has to be created first to initialize resources
			// Should resource initialization be done somewhere else (like design itself)?
			TaskDialog dlg = questionDialog( 
					parent, 
					getTitle(TaskDialog.makeKey("Choice")), // localized title 
					getIcon(null), // null by default, according to MS ux guidlines 
					instruction, 
					text);
			
			dlg.setCommands( StandardCommand.CANCEL );

			final CommandLinkButtonGroup bGroup = new CommandLinkButtonGroup();
		    List<ButtonModel> models = new ArrayList<ButtonModel>();
		    final List<CommandLinkButton> buttons = new ArrayList<CommandLinkButton>();

		    CommandLinkButton btn;
			JPanel p = new JPanel( new MigLayout(""));
			for( CommandLink link: choices ) {
				btn = new CommandLinkButton(link, TaskDialog.getDesign().getCommandLinkPainter());
				models.add( btn.getModel());
				buttons.add( btn );
				bGroup.add(btn);
				p.add( btn, "dock north");
			}

			if ( defaultChoice >= 0 && defaultChoice < choices.size()) {
				bGroup.setSelected(models.get(defaultChoice), true);

				// make sure that selected button is focused
				p.addAncestorListener( new AncestorAdapter() {

					@Override
					public void ancestorAdded(AncestorEvent event) {
						buttons.get(defaultChoice).requestFocusInWindow();
					}

				});
			}
			p.setOpaque(false);

			dlg.setFixedComponent(p);

			return dlg.show().equals(StandardCommand.CANCEL)?  -1: models.indexOf( bGroup.getSelection());

		}

		/**
		 * Simplifies the presentation of choice based on command links
		 * @param defaultChoice initial choice selection
		 * @param choices array of available command links
		 * @return selection index or -1 if nothing is selected
		 */

		public int choice( int defaultChoice, CommandLink... choices ) {
			return choice( defaultChoice, Arrays.asList(choices));
		}

		/**
		 * Shows simple input dialog with one JFormattedTextField.
		 * @param <T>
		 * @param defaultValue
		 * @return edited value or null if dialog was canceled
		 */
		@SuppressWarnings("unchecked")
		public <T> T input( T defaultValue ) {

			TaskDialog dlg = questionDialog( parent, getTitle( TaskDialog.makeKey("Input")), null, instruction, text);
			dlg.setIcon( getIcon( TaskDialog.StandardIcon.INFO ));

			JFormattedTextField tfInput = new JFormattedTextField();
			tfInput.setColumns( inputColumns == null? 25: inputColumns );
			tfInput.setValue( defaultValue );
			dlg.setFixedComponent( tfInput );

	        dlg.setCommands( StandardCommand.OK, StandardCommand.CANCEL );

			return (T) (dlg.show().equals(StandardCommand.OK)? tfInput.getValue(): null);

		}

	}


	/**
	 * Creates task dialog builder
	 * @return
	 */
	public static TaskDialogBuilder build() {
		return new TaskDialogBuilder();
	}

	/**
	 * Creates task dialog builder
	 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
	 * @param text
	 * @return
	 */
	public static TaskDialogBuilder build( Window parent, String instruction, String text) {
		TaskDialogBuilder builder = new TaskDialogBuilder();
		builder.parent( parent );
		builder.instruction(instruction);
		builder.text(text);
		return builder;
	}

	/**
	 * Shows simple information message
	 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
	 * @param text
	 */
	public static void inform( Window parent, String instruction, String text ) {
		build( parent, instruction, text ).inform();
	}

	/**
	 * Shows simple error message
	 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
	 * @param text
	 */
	public static void error( Window parent, String instruction, String text ) {
		build( parent, instruction, text ).error();
	}

	/**
	 * Shows simple question
	 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
	 * @param text
	 * @return
	 */
	public static boolean ask( Window parent, String instruction, String text ) {
		return build( parent, instruction, text ).ask();
	}

	/**
	 * Shows simple warning message
	 * @deprecated use isConfirmed instead
	 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
	 * @param text
	 * @return
	 */
	@Deprecated()
	public static boolean warn( Window parent, String instruction, String text ) {
		return build( parent, instruction, text ).warn();
	}

	/**
	 * Shows simple warning message
	 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
	 * @param text
	 * @return true if accepted
	 */
	public static boolean isConfirmed( Window parent, String instruction, String text ) {
		return build( parent, instruction, text ).isConfirmed();
	}

	/**
	 * Shows exception with stack trace as expandable component.
	 * @param ex
	 */
	public static void showException( Throwable ex ) {
		build().showException(ex);
	}

	/**
	 * Simplifies the presentation of choice based on radio buttons
	 * @param instruction instruction text. Wait interval can be added at the end. i.e. "@@10" will wait for 10 sec
	 * @param text
	 * @param defaultChoice initial choice selection
	 * @param choices collection of available choices
	 * @return selection index or -1 if nothing is selected
	 */
	public static final int radioChoice( Window parent, String instruction, String text, int defaultChoice, List<String> choices ) {
		return build(parent, instruction, text).radioChoice(defaultChoice, choices);
	}



	/**
	 * Simplifies the presentation of choice based on radio buttons
	 * @param instruction
	 * @param text
	 * @param defaultChoice
	 * @param choices
	 * @return
	 */
	public static final int radioChoice( Window parent, String instruction, String text, int defaultChoice, String... choices ) {
		return build( parent, instruction, text).radioChoice(defaultChoice, choices);
	}

	/**
	 * Produces choice dialog based on command links. Task dialog commands are suppressed.
	 * @param instruction
	 * @param text
	 * @param defaultChoice command link index used as default choice. -1 is none is required
	 * @param choices collection of command links
	 * @return
	 */
	public static final int choice( Window parent, String instruction, String text, int defaultChoice, List<CommandLink> choices ) {
		return build(parent, instruction,text).choice( defaultChoice, choices );
	}

	/**
	 * Produces choice dialog based on command links. Task dialog commands are suppressed.
	 * @param instruction
	 * @param text
	 * @param defaultChoice command link index used as default choice. -1 is none is required
	 * @param choices array of command links
	 * @return
	 */
	public static final int choice( Window parent, String instruction, String text, int defaultChoice, CommandLink... choices ) {
		return build(parent, instruction,text).choice( defaultChoice, choices );
	}

	/**
	 * Shows simple input dialog with one JFormattedTextField.
	 * @param <T>
	 * @param defaultValue
	 * @return edited value or null if dialog was canceled
	 */
	public static final <T> T input( Window parent, String instruction, String text, T defaultValue ) {
		return build(parent, instruction,text).inputColumns(25).input( defaultValue );
	}


 /*----------------------------------------------------------------------------------------------------------*/

	private static class TextWithWaitInterval {

		String text;
		int waitInterval = 0;

		public TextWithWaitInterval( String text ) {

			this.text = text;
			int prefixPos = text.indexOf(TaskDialog.I18N_PREFIX);
			if ( prefixPos >= 0) {
				try {
					waitInterval = Integer.valueOf( text.substring( prefixPos + TaskDialog.I18N_PREFIX.length()));
				} catch( Throwable ex ) {
					waitInterval = 0;
				}
				this.text = text.substring(0, prefixPos);
			}

		}

		public String getText() {
			return text;
		}

		public int getWaitInterval() {
			return waitInterval;
		}
	}


	/**
	 * Creates Task dialog using provided attributes and one "Close" button
	 * @param title
	 * @param icon
	 * @param instruction
	 * @param text
	 * @return
	 */
	private static TaskDialog messageDialog( Window parent, String title, Icon icon, String instruction, String text ) {

		TextWithWaitInterval twi = new TextWithWaitInterval(instruction);

		TaskDialog dlg = new TaskDialog(parent, title);
        dlg.setInstruction( twi.getText() );
        dlg.setText( text );
        dlg.setIcon( icon );
        dlg.setCommands( StandardCommand.CANCEL.derive(TaskDialog.makeKey("Close"), twi.getWaitInterval()));
        return dlg;

	}

	/**
	 * Creates Task dialog using provided attributes and "Yes" and "No" buttons
	 * @param title
	 * @param icon
	 * @param instruction
	 * @param text
	 * @return
	 */
	private static TaskDialog questionDialog( Window parent, String title, Icon icon, String instruction, String text ) {

		TextWithWaitInterval twi = new TextWithWaitInterval(instruction);

		TaskDialog dlg = new TaskDialog(parent, title);
        dlg.setInstruction( twi.getText() );
        dlg.setText( text );
        dlg.setIcon( icon );
        dlg.setCommands(
        	StandardCommand.OK.derive(TaskDialog.makeKey("Yes"), twi.getWaitInterval()),
        	StandardCommand.CANCEL.derive(TaskDialog.makeKey("No")) );
        return dlg;

	}

}
