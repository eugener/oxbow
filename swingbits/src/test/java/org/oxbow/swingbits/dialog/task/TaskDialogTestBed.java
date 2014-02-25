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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.oxbow.swingbits.util.Markup;
import org.oxbow.swingbits.util.OperatingSystem;


@SuppressWarnings("serial")
public class TaskDialogTestBed extends JFrame {
    private final JCheckBox cbDebug = new JCheckBox("Debug");
    private final JPanel pDetails = new JPanel();
    private final JPanel pFooter = new JPanel();
    private final JPanel pCommons = new JPanel();
    private final JTextField txCheckBoxText;
    private final JTextField txFooterText;
    private final JTextField txInstruction = new JTextField();
    private final JTextField txTitle = new JTextField();
    private final JComboBox cbIcon = new JComboBox();
    private final JTextArea txText = new JTextArea();
    private final JCheckBox cbDetailsExpanded = new JCheckBox("Expanded");
    private final JTextField txCollapsedLabel = new JTextField("More Details");
    private final JTextField txExpandedLabel  = new JTextField("Fewer Details");
    private final JComboBox cbFooterIcon = new JComboBox();
    private final JCheckBox cbFooterCheckBoxSelected = new JCheckBox("Check Box Selected");


    private final JComboBox cbLaf = new JComboBox( LookAndFeel.getComboModel(lafs));
    private final JLabel lblLookAndFeel = new JLabel("Look And Feel");


    public TaskDialogTestBed( int lafIndex ) {
        setResizable(false);

        setTitle("Task  Dialog Test Bed");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new MigLayout("", "[pref!][238.00,grow][][-91.00][pref!,grow]", "[pref!,grow][pref!][][]"));
        pCommons.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        getContentPane().add(pCommons, "cell 0 0 5 1,grow");
        pCommons.setLayout(new MigLayout("", "[46px][352px,grow]", "[][][][48px,grow]"));

        JLabel lblTitle = new JLabel("Title");
        lblTitle.setHorizontalAlignment(SwingConstants.TRAILING);
        pCommons.add(lblTitle, "cell 0 0,alignx trailing");
        txTitle.setText("Application Error");
        pCommons.add(txTitle, "cell 1 0,growx");

        JLabel lbInstruction = new JLabel("Instruction");
        lbInstruction.setHorizontalAlignment(SwingConstants.TRAILING);
        pCommons.add(lbInstruction, "cell 0 1,alignx trailing");

        txInstruction.setText("CRASH AND BURN!");
        pCommons.add(txInstruction, "cell 1 1,growx");

        JLabel lbIcon = new JLabel("Icon");
        lbIcon.setHorizontalAlignment(SwingConstants.TRAILING);
        pCommons.add(lbIcon, "cell 0 2,growx,aligny center");

        pCommons.add(cbIcon, "cell 1 2,alignx left,aligny top");

        JLabel lbText = new JLabel("Text");
        lbText.setHorizontalAlignment(SwingConstants.TRAILING);
        pCommons.add(lbText, "cell 0 3,growx,aligny top");

        JScrollPane scrollPane = new JScrollPane();
        pCommons.add(scrollPane, "cell 1 3,grow");

        txText.setText("The applicaiton has performed an illegal action.\n This action has been logged and reported.");
        txText.setFont( txInstruction.getFont());
        scrollPane.setViewportView(txText);

        pDetails.setBorder(new TitledBorder(null, " Details ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(pDetails, "cell 0 1 5 1,grow");
        pDetails.setLayout(new MigLayout("", "[80px][318px,grow]", "[20px][20px][23px]"));

        cbDetailsExpanded.setHorizontalAlignment(SwingConstants.TRAILING);
        pDetails.add(cbDetailsExpanded, "cell 1 2,alignx right,aligny top");

        pDetails.add(txCollapsedLabel, "cell 1 0,growx,aligny top");

        JLabel lbCollapsedLabel = new JLabel("Collapsed Label");
        lbCollapsedLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        pDetails.add(lbCollapsedLabel, "cell 0 0,growx,aligny center");

        JLabel lbExpandedLabel = new JLabel("Expanded Label");
        lbExpandedLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        pDetails.add(lbExpandedLabel, "cell 0 1,growx,aligny center");
        pDetails.add(txExpandedLabel, "cell 1 1,growx,aligny top");

        JButton button = new JButton("Show Task Dialog");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTaskDialog();
            }

        });

        pFooter.setBorder(new TitledBorder(null, "Footer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(pFooter, "cell 0 2 5 1,grow");
        pFooter.setLayout(new MigLayout("", "[78px][231px,grow]", "[23px][20px][20px][20px]"));

        JLabel lbFooterText = new JLabel("Text");
        lbFooterText.setHorizontalAlignment(SwingConstants.TRAILING);
        pFooter.add(lbFooterText, "cell 0 0,growx,aligny center");

        txFooterText = new JTextField();
        txFooterText.setText("Your application chrashed because a developer forgot to write a unit test");
        txFooterText.setColumns(10);
        pFooter.add(txFooterText, "cell 1 0,growx,aligny top");

        JLabel lbFooterIcon = new JLabel("Icon");
        lbFooterIcon.setHorizontalAlignment(SwingConstants.TRAILING);
        pFooter.add(lbFooterIcon, "cell 0 1,alignx right,aligny center");

        pFooter.add(cbFooterIcon, "cell 1 1,alignx left,aligny top");

        JLabel lbCheckBoxText = new JLabel("Check Box Text");
        lbCheckBoxText.setHorizontalAlignment(SwingConstants.TRAILING);
        pFooter.add(lbCheckBoxText, "cell 0 2,alignx left,aligny center");

        txCheckBoxText = new JTextField();
        txCheckBoxText.setText("Perform this action every time");
        txCheckBoxText.setColumns(10);
        pFooter.add(txCheckBoxText, "cell 1 2,growx,aligny top");

        cbFooterCheckBoxSelected.setHorizontalAlignment(SwingConstants.TRAILING);
        pFooter.add(cbFooterCheckBoxSelected, "cell 1 3,alignx right");

        getContentPane().add(lblLookAndFeel, "cell 0 3,alignx trailing");

        getContentPane().add(cbLaf, "cell 1 3,alignx left");

                cbDebug.addActionListener( new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TaskDialog.setDebugMode(cbDebug.isSelected());
                    }
                });
                getContentPane().add(cbDebug, "cell 2 3,grow");
        getContentPane().add(button, "cell 4 3,alignx trailing");

        cbLaf.setSelectedIndex(lafIndex);
        cbLaf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {

                        LookAndFeel laf = (LookAndFeel) cbLaf.getSelectedItem();

                        try {
                            UIManager.setLookAndFeel(laf.getName());
                            SwingUtilities.updateComponentTreeUI(TaskDialogTestBed.this);
                            TaskDialogTestBed.this.getContentPane().validate();
//                            updateIconModels();
                            TaskDialogTestBed.this.pack();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

        });

        updateIconModels();
        cbIcon.setSelectedIndex(3);


    }

    private void updateIconModels() {
        cbIcon.setModel(new IconComboBoxModel( TaskDialog.StandardIcon.values() ));
        cbFooterIcon.setModel(new IconComboBoxModel( TaskDialog.StandardIcon.values(), 16, 16 ));
    }

    private void showTaskDialog() {

        TaskDialog dlg = new TaskDialog( null, txTitle.getText());
        dlg.setInstruction( txInstruction.getText() );
        dlg.setText(  txText.getText() );
        dlg.setIcon( (Icon)cbIcon.getSelectedItem() );

        dlg.getDetails().setExpandableComponent(
            new JLabel( Markup.toHTML("Here are some details.\n 123456789")));

        dlg.getDetails().setCollapsedLabel( txCollapsedLabel.getText());
        dlg.getDetails().setExpandedLabel( txExpandedLabel.getText());
        dlg.getDetails().setExpanded(cbDetailsExpanded.isSelected());

        dlg.getFooter().setText( txFooterText.getText());
        dlg.getFooter().setIcon( (Icon) cbFooterIcon.getSelectedItem());

        dlg.getFooter().setCheckBoxText( txCheckBoxText.getText() );
        dlg.getFooter().setCheckBoxSelected( cbFooterCheckBoxSelected.isSelected());

        dlg.addPropertyListener("result", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println( "Result is set to " +  evt.getNewValue());
            }
        });

        dlg.show();

    }


    static abstract class TaskDialogExample implements Runnable {

        protected String title;

        protected abstract TaskDialog getDialog();

        @Override
        public String toString() {
            if (title == null) {
                title = getDialog().getTitle();
            }
            return title;
        }

        @Override
        public void run() {
            getDialog().show();
        }

    }

    private static LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

    public static void main(String[] args) {

        System.out.println( "Running on " + OperatingSystem.getCurrent());

        int index = 0;
        String sysLaf = UIManager.getSystemLookAndFeelClassName();
        for( int i=0; i<lafs.length; i++) {
            if ( lafs[i].getClassName().equals( sysLaf )) {
                index = i;
                break;
            }
        }

        try {
            UIManager.setLookAndFeel( lafs[index].getClassName());
        } catch (Throwable e) {
            e.printStackTrace();
        }


        final int lafIndex = index;

        Locale.setDefault( new Locale( "es", "ES"));

        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {

                TaskDialogTestBed testBed = new TaskDialogTestBed(lafIndex);
                testBed.pack();
                testBed.setLocationRelativeTo(null);
                testBed.setVisible(true);

            }
        });

    }
}
