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

package org.oxbow.swingbits.util.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;

public class EmptyIcon implements Icon {
    
    private final int size;
    private boolean paintImage;
    
    public static final EmptyIcon visible( int size ) {
        return new EmptyIcon(size, true);
    }

    public static final EmptyIcon hidden() {
        return new EmptyIcon(0, false); 
    }
    
    private EmptyIcon( int size, boolean paintImage ) {
        this.size = Math.abs(size == 0? 1: size); // needs at least 1px to copy images (see Icons class)
        this.paintImage = paintImage;
    }
    
    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        if ( paintImage && size > 2 ) {
        
             Graphics2D g2 = (Graphics2D)g;
             g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

             int radius = size / 3;
             RoundRectangle2D r = new RoundRectangle2D.Float(x, y, size-1, size-1, radius, radius );
             
             g2.setColor( new Color( 255, 255, 0, 127) );
             g2.fill(r);
             
             g2.setStroke( new BasicStroke(3));
             g2.setColor(Color.RED);
             g2.draw(r);
             
             Point center = new Point( x+size/2, y+size/2 );
             
             int d = size / 4;
             int xad = center.x-d;
             int xsd = center.x+d;
             int yad = center.y+d;
             int ysd = center.y-d;
             g2.drawLine( xad, ysd, xsd, yad );
             g2.drawLine( xad, yad, xsd, ysd );
        
        }

    }
}