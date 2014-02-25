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

import org.oxbow.swingbits.dialog.task.design.TaskDialogContent;

public interface IContentDesign {

    
    static final String ICON_INFO     = "OptionPane.informationIcon";
    static final String ICON_QUESTION = "OptionPane.questionIcon";
    static final String ICON_WARNING  = "OptionPane.warningIcon";
    static final String ICON_ERROR    = "OptionPane.errorIcon";
    

    /**
     * UIDefaults key for task dialog "fewer details" icon
     */
    static final String ICON_FEWER_DETAILS = "TaskDialog.fewerDetailsIcon";

    
    /**
     * UIDefaults key for task dialog "more details" icon
     */
    static final String ICON_MORE_DETAILS  = "TaskDialog.moreDetailsIcon";

    
    /**
     * UIDefaults key for task dialog command link icon
     */
    static final String ICON_COMMAND_LINK  = "TaskDialog.commandLinkIcon";

    
    /**
     * UIDefaults key for task dialog message background color
     */
    static final String COLOR_MESSAGE_BACKGROUND = "TaskDialog.messageBackground";

    
    /**
     * UIDefaults key for task dialog instruction foreground color
     */
    static final String COLOR_INSTRUCTION_FOREGROUND = "TaskDialog.instructionForeground";

    
    /**
     * UIDefaults key for task dialog instruction font
     */
    static final String FONT_INSTRUCTION = "TaskDialog.instructionFont";
    
    
    /**
     * UIDefaults key for task dialog text font
     */
    static final String FONT_TEXT = "TaskDialog.textFont";

    
    /**
     * UIDefaults key for "More Details" label 
     */
    static final String TEXT_MORE_DETAILS = "TaskDialog.moreDetailsText";

    
    /**
     * UIDefaults key for "Fewer Details" label
     */
    static final String TEXT_FEWER_DETAILS = "TaskDialog.fewerDetailsText";

    
    
    
    /**
     *  Updates UIDefaults with appropriate design settings 
     */
    void updateUIDefaults();
    
    
    /**
     * Builds and returns contents of task dialog  
     * @return
     */
    TaskDialogContent buildContent();
    
    
    /**
     * Returns true if command buttons are locked ( should have the same size )
     * @return
     */
    boolean isCommandButtonSizeLocked();
    
    
    /**
     * Returns command link painter
     * @return
     */
    ICommandLinkPainter getCommandLinkPainter();

}
