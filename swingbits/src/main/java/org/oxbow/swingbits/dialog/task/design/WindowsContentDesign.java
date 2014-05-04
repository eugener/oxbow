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

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

import org.oxbow.swingbits.dialog.task.ICommandLinkPainter;

public class WindowsContentDesign extends DefaultContentDesign {

    @Override
    public void updateUIDefaults() {

        putUIDefaultIfAbsent( COLOR_INSTRUCTION_FOREGROUND, new Color(0x0033A0)); 

        Font fontBase =  new Font("Segoe UI", 0, 11);
        putUIDefaultIfAbsent( FONT_INSTRUCTION, 
            fontBase.deriveFont( fontBase.getStyle(), fontBase.getSize2D() * 1.4f ) );
        
        // now set other UIDefaults
        super.updateUIDefaults();
    }
    
    
    @Override
    public ICommandLinkPainter getCommandLinkPainter() {
        if (commandButtonPainter == null) {
            commandButtonPainter = new WindowsCommandLinkPainter();
        }
        return commandButtonPainter;
    }    
    
    
}
