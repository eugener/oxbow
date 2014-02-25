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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import org.oxbow.swingbits.dialog.task.IContentDesign;
import org.oxbow.swingbits.dialog.task.TaskDialog;
import org.oxbow.swingbits.util.Markup;
import org.oxbow.swingbits.util.Strings;
import org.oxbow.swingbits.util.swing.Icons;

public class TaskDialogContent extends JPanel implements TaskDialog.Details, TaskDialog.Footer {

    private static final long serialVersionUID = 1L;

    final JLabel lbIcon        = hidden( new JLabel());
    final JLabel lbInstruction = hidden( new JLabel());
    final JLabel lbText        = hidden( new JLabel());
    final JPanel pExpandable   = hidden( new JPanel( new BorderLayout()));
    final JPanel pComponent    = hidden( new JPanel( new BorderLayout()));

    final DetailsToggleButton cbDetails = hidden( new DetailsToggleButton());
    final JCheckBox cbFooterCheck = hidden( new JCheckBox());
    final JLabel lbFooter = hidden( new JLabel());
    final JPanel pCommands = new JPanel( new MigLayout( "ins 0, nogrid, fillx, aligny 100%, gapy unrel" ));
    final JPanel pFooter = hidden( new JPanel( new MigLayout()));
    final JPanel pCommandPane = new JPanel( new MigLayout());

    private final String[] detailsText = new String[2];
    private String instruction = null;
    private String text;
    private boolean alwaysExpanded;

    private static <T extends JComponent> T hidden( T c ) {
        c.setVisible(false);
        return c;
    }

    public TaskDialogContent() {

        pExpandable.setOpaque(false);
        pComponent.setOpaque(false);

        cbDetails.addItemListener( new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                final boolean selected = e.getStateChange() == ItemEvent.SELECTED;

                cbDetails.setText( selected? getExpandedLabel():getCollapsedLabel() );
                SwingUtilities.invokeLater( new Runnable(){

                    @Override
                    public void run() {
                        pExpandable.setVisible(selected);
                        SwingUtilities.getWindowAncestor(TaskDialogContent.this).pack();
                    }}
                );

            }
        });


    }


    public void setInstruction( String instruction ) {
           this.instruction = instruction;

           boolean visible = instruction != null && instruction.trim().length() > 0;
           lbInstruction.setVisible(visible);
           if (visible) lbInstruction.setText( Markup.toHTML(instruction) );
    }

    public String getInstruction() {
        return instruction;
    }
    
    public void setCommands( Set<? extends TaskDialog.Command> commands, boolean lockButtonSize ) {

        pCommands.removeAll();

        String group = lockButtonSize? "sgx commands, ": "";
        TaskDialog owner = getOwner();
        for( final TaskDialog.Command c: commands) {
            String tag = c.getTag() == null? "": c.getTag().toString();
            JButton button = new JButton( new CommandAction(c, owner) );
            pCommands.add( button, group + "aligny top, " + tag  );
        }

    }
    
    public boolean isCommandsVisible() {
        return pCommandPane.isVisible();
    }

    public void setCommandsVisible( boolean visible ) {
        pCommandPane.setVisible(visible);
    }

    public void setMainText( String text ) {
           this.text = text;
        boolean isEmtpy = Strings.isEmpty(text);
           lbText.setText( Markup.toHTML(text) );
           lbText.setVisible( !isEmtpy );
    }

    public String getMainText() {
        return text;
    }

    private TaskDialog getOwner() {
        return TaskDialog.getInstance(this);
    }

    @Override
    public String getCollapsedLabel() {
        return Strings.isEmpty( detailsText[0])?
                getOwner().getString( UIManager.getString(IContentDesign.TEXT_MORE_DETAILS)): detailsText[0];
    }

    @Override
    public void setCollapsedLabel(String collapsedLabel) {
        detailsText[0] = collapsedLabel;
    }

    @Override
    public String getExpandedLabel() {
        return Strings.isEmpty(detailsText[1])?
                getOwner().getString(UIManager.getString(IContentDesign.TEXT_FEWER_DETAILS)): detailsText[1];
    }

    @Override
    public void setExpandedLabel(String expandedLabel) {
        detailsText[1] = expandedLabel;
    }

    @Override
    public JComponent getExpandableComponent() {
        return pExpandable.getComponentCount() == 0? null: (JComponent)pExpandable.getComponent(0);
    }

    @Override
    public void setExpandableComponent(JComponent c) {
         pExpandable.removeAll();
         if ( c != null ) pExpandable.add( c );
         cbDetails.setVisible(c != null && !alwaysExpanded);
    }

    @Override
    public boolean isExpanded() {
        return cbDetails.isSelected();
    }

    @Override
    public void setExpanded(boolean expanded) {
        cbDetails.setSelected( !expanded );
        cbDetails.setSelected( expanded );
        pExpandable.setVisible(expanded);
    }
    
    @Override
    public void setAlwaysExpanded( boolean alwaysExpanded ) {
        if (alwaysExpanded) {
            setExpanded(true);
        }
        cbDetails.setVisible(getExpandableComponent() != null && !alwaysExpanded);
        this.alwaysExpanded = alwaysExpanded;
    }

    @Override
    public boolean isAlwaysExpanded() {
        return alwaysExpanded;
    }
    
    @Override
    public String getCheckBoxText() {
        return cbFooterCheck.getText();
    }

    private Icon icon;


    public void setMainIcon( Icon icon ) {
        lbIcon.setVisible( icon != null );
        lbIcon.setIcon(icon);
    }

    public Icon getMainIcon() {
        return lbIcon.getIcon();
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public void setIcon(Icon icon) {
        this.icon = icon; // stored to preserve actual size
        lbFooter.setIcon( Icons.scale(icon, 16, 16));
    }

    @Override
    public String getText() {
        return lbFooter.getText();
    }

    @Override
    public void setText(String text) {
        boolean footerLabelVisible = !Strings.isEmpty(text);
        pFooter.setVisible( footerLabelVisible );
        lbFooter.setVisible(footerLabelVisible);
        lbFooter.setText(Markup.toHTML(text));
    }

    public void setComponent( JComponent c ) {
        pComponent.removeAll();
        if ( c != null ) pComponent.add( c );
        pComponent.setVisible(c != null);
    }

    public JComponent getComponent() {
        return pComponent.getComponentCount() == 0? null: (JComponent)pComponent.getComponent(0);
    }

    @Override
    public boolean isCheckBoxSelected() {
        return cbFooterCheck.isVisible() && cbFooterCheck.isSelected();
    }

    @Override
    public void setCheckBoxSelected(boolean selected) {
        cbFooterCheck.setSelected(selected);
    }

    @Override
    public void setCheckBoxText(String text) {
        cbFooterCheck.setVisible( !Strings.isEmpty( text ) );
        cbFooterCheck.setText( text == null? "": text );
    }

    class CommandAction extends AbstractAction implements TaskDialog.ValidationListener {

        private static final long serialVersionUID = 1L;

        private final TaskDialog.Command command;
        private final TaskDialog dlg;
        private Timer timer;
        private int counter;

        public CommandAction( TaskDialog.Command command, TaskDialog dlg ) {
            super( dlg.getString( command.getTitle()) );

            this.command = command;
            this.dlg = dlg;
            this.counter = command.getWaitInterval();
            
            // setup default keystrokes
            KeyStroke keyStroke = command.getKeyStroke();
            if ( keyStroke != null ) {
                String actionID = "TaskDialog.Command." + UUID.randomUUID().toString();
                TaskDialogContent.this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke,actionID);
                TaskDialogContent.this.getActionMap().put(actionID,this);
            }
            
            
            dlg.addValidationListener(this);

            putValue( Action.NAME, getTitle() );

            if ( counter > 0 ) {

                setEnabled( false );

                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tick();
                    }

                });

                dlg.addPropertyListener("visible", new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if ( Boolean.TRUE.equals(e.getNewValue())) {
                            timer.start();
                        }
                    }
                });
            }

        }
        
        @Override
        public void validationFinished(boolean validationResult) {
            
            setEnabled( command.isEnabled(validationResult) );
            
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            dlg.setResult( command );
            if ( command.isClosing() ) dlg.setVisible(false);

        }

        private String getTitle() {
            String title = dlg.getString( command.getTitle());
            return  counter > 0? String.format( "%s (%d)", title, counter): title;
        }

        private void tick() {
            if ( --counter <= 0 ) {
                timer.stop();
            }
            putValue( Action.NAME, getTitle() );
            setEnabled(counter <= 0);
        }

    }
    
    
}



