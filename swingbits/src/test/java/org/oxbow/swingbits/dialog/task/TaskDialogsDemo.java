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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

public class TaskDialogsDemo extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JButton btInform = new JButton("inform");
	private final JButton btWarn = new JButton("warn");
	private final JButton btRadioChoice = new JButton("radioChoice");
	private final JButton btCheckChoice = new JButton("checkChoice");
	private final JButton btChoice = new JButton("choice");
	private final JButton btException = new JButton("showException");
	private final JButton btSimple = new JButton("simple message");
    private final JButton btInput = new JButton("input");
    private final JComboBox cbLocales = new JComboBox();
    private final JLabel lblDefaultLocale = new JLabel("Default Locale");
    
    private static class DisplayableLocale  {
    	
    	private final Locale locale;
    	
    	public DisplayableLocale( String id ) {
    		locale = id == null? getDefaultLocale(): new Locale( id.toLowerCase(), id.toUpperCase());
		}

    	public DisplayableLocale( String language, String country ) {
    		locale = new Locale( language.toLowerCase(), country.toUpperCase());
		}
    	
    	public DisplayableLocale() { this( (Locale)null ); }
    	
    	public DisplayableLocale(Locale locale) {
    		this.locale = locale == null? getDefaultLocale(): locale;
    	}
    	
    	
    	@Override
    	public String toString() {
    		return locale.getDisplayName();
    	}
    	
    	public Locale getLocale() {
			return locale;
		}
    	
    }
    

	public TaskDialogsDemo() {
		createLayout();
		
		DisplayableLocale[] locales = new DisplayableLocale[]{
			new DisplayableLocale(),
			new DisplayableLocale(Locale.GERMANY),// DE
			new DisplayableLocale("ES"),          // Spain
			new DisplayableLocale("PT"),          // Portugal
			new DisplayableLocale("pt", "BR"),    // Brazil
			new DisplayableLocale(Locale.FRANCE), // FR
			new DisplayableLocale(Locale.ITALY),  // IT
			new DisplayableLocale("PL"),          // Poland 
			new DisplayableLocale(Locale.CHINA)   // zh_CN
		};
		
		cbLocales.setModel(	new DefaultComboBoxModel( locales ));	
		cbLocales.setMaximumRowCount(locales.length);
		
		cbLocales.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault( ((DisplayableLocale) cbLocales.getSelectedItem()).getLocale() );
			}
		});

		btSimple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskDialog dlg = new TaskDialog(null, "Task Dialog");
				dlg.setInstruction("This Simple Task Dialog");
				dlg.setText("Here is task dialog content");
				dlg.show();
			}
		});
		
		btInform.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TaskDialogs.inform( null, "You've won!", "The game is over with the 15:3 score");
			}
		});
		
		btInput.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println( "Your input: " + TaskDialogs.input( null, "Enter your name", "or any other text if you prefer", "Steve Jobs" ));
			}
		});

		btWarn.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TaskDialogs.isConfirmed( null, "Are you sure you want to quit?", "Please do not quit yet!");
			}
		});


		btRadioChoice.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = TaskDialogs.radioChoice( null, "You've got selection to make", "Go ahead", 1, "Yes", "No", "May be" );
				System.out.println("Your choice is " + choice );
			}
		});

		btCheckChoice.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Collection<String> result =  TaskDialogs.build( null, "You've got selection to make", "Go ahead" ).
				                                checkChoice( Arrays.asList("Yes", "No", "May be"), Arrays.asList("No") );
				System.out.println("Your choice is " + result );
			}
		});
		
		btChoice.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int choice =  TaskDialogs.build(null, "What do you want to do with your game in\nprogress?", "").
						      //icon( TaskDialog.StandardIcon.ERROR ).
						      choice( 1, 
								new CommandLink("Exit and save my game", "Save your game in progress, then exit. " +
										        "This will\noverwrite any previosely saved games."),
								new CommandLink("Exit and don't save", "Exit without saving your game. " +
										        "This is counted\nas a loss in your statistics." )
						      /*,new CommandLink("Don't exit", "Return to your game progress" )*/						    		  
		         );
				
				
				
				System.out.println( "Your choice is " + choice );
			}
			
		});

		btException.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new BigDecimal("seven");
				} catch( Throwable ex ) {
					TaskDialogs.showException(ex);
				}
			}
		});


	}

	private void createLayout() {
		setLayout(new MigLayout("", "[][135px]15px[135px]15px[135px,grow]", "[pref!][20px][23px][23px][]"));
		
		add(this.lblDefaultLocale, "cell 2 0,alignx trailing,aligny baseline");
		
		add(this.cbLocales, "cell 3 0,growx,aligny baseline");
		
		add(this.btSimple, "cell 1 2,growx,aligny top");
		add(btInform, "cell 1 3,growx,aligny top");
		add(btWarn, "cell 1 4,growx,aligny top");
		add(btRadioChoice, "cell 2 2,growx,aligny top");
		add(btCheckChoice, "cell 2 3,growx,aligny top");
		add(btChoice, "cell 2 4,growx,aligny top");
		add(btException, "cell 3 2,growx,aligny top");
		add(btInput, "cell 3 3,growx,aligny top");
		
//		setPreferredSize( new Dimension(500,300));
		
	}

	public static void main(String[] args) {

//		Locale.setDefault( new Locale("de", "DE" ) );
		
		System.out.println( "Resolution: " + Toolkit.getDefaultToolkit().getScreenResolution());

		SwingUtilities.invokeLater( new Runnable() {

			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
//					UIManager.setLookAndFeel( NimbusLookAndFeel.class.getName());
				} catch (Throwable e) {
					e.printStackTrace();
				}

				JFrame f = new JFrame("Task Dialog Demo");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setContentPane( new TaskDialogsDemo());
				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);

			}

		});


	}
}
