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

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SimpleTaskDialogDemo {

    public static void main( String[] args ) {

        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    
                    try {
                        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    
                    
                        final String title = "Exit";
                        final String instruction = "Exiting TaskDialog";
                        final String text = "Are you sure you want to exit ?";

                        TaskDialog dlg = new TaskDialog( null, title);
                        dlg.setInstruction(instruction);
                        dlg.setText(text);
                        dlg.setIcon(TaskDialog.StandardIcon.QUESTION);

                        final JLabel commentTitle = new JLabel("Tell us why you are exiting TaskDialog");
                        final JTextArea comment = new JTextArea(5, 100);
                        final JCheckBox disableComments = new JCheckBox("don't ask me to comment again");
                        disableComments.setOpaque(false);

                        final JPanel panel = new JPanel(new BorderLayout());
                        panel.setOpaque(false);
                        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                        panel.add(commentTitle, BorderLayout.NORTH);
                        panel.add(new JScrollPane(comment));
                        panel.add(disableComments, BorderLayout.SOUTH);

                        dlg.getDetails().setExpandableComponent(panel);
//                      dlg.getDetails().setExpanded(true);
                        dlg.getDetails().setAlwaysExpanded(true);

                        dlg.setCommands(
                            TaskDialog.StandardCommand.OK.derive(TaskDialog.makeKey("Yes")),
                            TaskDialog.StandardCommand.CANCEL.derive(TaskDialog.makeKey("No")) );

                        dlg.getFooter().setCheckBoxText( "don't ask me again" );
                        dlg.getFooter().setCheckBoxSelected(false);

                        final TaskDialog.Command result = dlg.show();

                        boolean exit = result.equals(TaskDialog.StandardCommand.OK);
                        final boolean disableConfirm = dlg.getFooter().isCheckBoxSelected();

                        System.out.println("exit = " + exit);
                        System.out.println("disableConfirm = " + disableConfirm);
                        System.out.println("disableComments = " + disableComments.isSelected());
                        System.out.println("comment = " + comment.getText());

                        System.exit(0);
                }
        });

}
    
}
