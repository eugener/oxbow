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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import org.oxbow.swingbits.dialog.task.ICommandLinkPainter;
import org.oxbow.swingbits.dialog.task.IContentDesign;
import org.oxbow.swingbits.dialog.task.TaskDialog;

public class MacOsContentDesign extends DefaultContentDesign {

    private ICommandLinkPainter commandButtonPainter;

    @Override
    public void updateUIDefaults() {

        putUIDefaultIfAbsent( ICON_MORE_DETAILS,  createResourceIcon( "moreDetailsMac.png" ));
        putUIDefaultIfAbsent( ICON_FEWER_DETAILS, createResourceIcon( "fewerDetailsMac.png" ));

        putUIDefaultIfAbsent( COLOR_MESSAGE_BACKGROUND,     SystemColor.control );
        putUIDefaultIfAbsent( COLOR_INSTRUCTION_FOREGROUND, SystemColor.textText );

        putUIDefaultIfAbsent( FONT_INSTRUCTION, deriveFont( "Label.font", Font.BOLD, 1f ) );
        putUIDefaultIfAbsent( FONT_TEXT, deriveFont( "Label.font", Font.PLAIN, .85f ) );

        putUIDefaultIfAbsent( TEXT_MORE_DETAILS,  TaskDialog.makeKey("Details") );
        putUIDefaultIfAbsent( TEXT_FEWER_DETAILS, TaskDialog.makeKey("Details") );

        // now set other UIDefaults
        super.updateUIDefaults();
        
    }

    @Override
    public TaskDialogContent buildContent() {

        TaskDialogContent content = new TaskDialogContent();

        content.setMinimumSize( new Dimension(200, 70));

        content.lbInstruction.setFont( UIManager.getFont(IContentDesign.FONT_INSTRUCTION ));
        content.lbInstruction.setForeground( UIManager.getColor( IContentDesign.COLOR_INSTRUCTION_FOREGROUND ));

        content.lbText.setFont( UIManager.getFont(IContentDesign.FONT_TEXT ));

        content.pComponent.setOpaque(false);

        content.removeAll();
        content.setLayout( createMigLayout("ins dialog, hidemode 3, fill", "[]", "[][][]"));

        // message pane
        JPanel pMessage = new JPanel( createMigLayout( "ins 0, gapx 7, hidemode 3", "[][grow]", "[][][]"));
        pMessage.setBackground( UIManager.getColor( IContentDesign.COLOR_MESSAGE_BACKGROUND ) );
        pMessage.add( content.lbIcon,        "cell 0 0 0 2, aligny top");
        pMessage.add( content.lbInstruction, "cell 1 0, growx, aligny top");
        pMessage.add( content.lbText,        "cell 1 1, growx, aligny top");
        pMessage.add( content.pComponent,    "cell 1 3, grow");

        content.setBackground( pMessage.getBackground());
        content.add( pMessage, "cell 0 0");

        // footer
        content.pFooter.setLayout( new MigLayout("ins 0"));
        content.pFooter.add( content.lbFooter, "dock center" );

        content.add( content.pFooter, "cell 0 2" );

        // command pane
        content.pExpandable.setBorder(
            BorderFactory.createCompoundBorder(
                UIManager.getBorder("InsetBorder.aquaVariant"),
                BorderFactory.createEmptyBorder(7,7,7,7)));
        content.pCommandPane.setLayout(
            createMigLayout("ins 0, hidemode 3", "[pref!][grow]", "[pref!][pref!,grow][pref!][pref!]"));
        content.pCommandPane.add( content.cbDetails,     "cell 0 0");
        content.pCommandPane.add( content.pExpandable,   "cell 0 1 3 1, grow");
        content.pCommandPane.add( content.pCommands,     "cell 2 2, alignx right" );
        content.pCommandPane.add( content.cbFooterCheck, "cell 0 2");

        content.add( content.pCommandPane, "cell 0 1, grow");

        return content;

    }


    @Override
    public ICommandLinkPainter getCommandLinkPainter() {
        if (commandButtonPainter == null) {
            commandButtonPainter = new MacOsCommandLinkPainter();
        }
        return commandButtonPainter;
    }
    
    @Override
    public boolean isCommandButtonSizeLocked() {
        return false;
    }


}
