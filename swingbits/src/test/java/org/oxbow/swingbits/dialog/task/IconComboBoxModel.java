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

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;

import org.oxbow.swingbits.util.swing.Icons;

/**
 * Model for a set of task dialog icons to be represented in JComboBox. 
 * The icons can be automatically scaled to specific width and height  
 * 
 * @author Eugene Ryzhikov
 * 
 */

@SuppressWarnings("serial")
public class IconComboBoxModel extends DefaultComboBoxModel {

    public IconComboBoxModel( Icon[] icons, int width, int height  ) {
        super( scaleIcons( icons, width, height ));
    }
    
    public IconComboBoxModel( Icon[] icons ) {
        super( icons );
    }
    
    private static Icon[] scaleIcons( Icon[] icons, int width, int height ) {
        
        Icon[] result = new Icon[ icons.length ];
        for( int i=0; i<icons.length; i++) {
            result[i] = icons[i] == null? null: Icons.scale(icons[i], 16, 16 );
        }
        
        return result;
        
    }
    
    
}
