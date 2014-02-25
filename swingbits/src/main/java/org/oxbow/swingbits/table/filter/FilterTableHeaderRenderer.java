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

package org.oxbow.swingbits.table.filter;

import java.awt.Component;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.oxbow.swingbits.table.TableHeaderRenderer;
import org.oxbow.swingbits.util.swing.CompoundIcon;

/**
 * Table header renderer to show the column filter state 
 * 
 * Created on Feb 10, 2011
 * @author Eugene Ryzhikov
 *
 */
class FilterTableHeaderRenderer extends TableHeaderRenderer {

    private static final long serialVersionUID = 1L;

    private ImageIcon icon;
    private final ITableFilter<?> tableFilter;
    private boolean rendererInit = true;
    private int originalHorizontalTextPosition;

    public FilterTableHeaderRenderer( ITableFilter<?> tableFilter ) {
        this.tableFilter = tableFilter;
    }
    
    private Icon getFilterIcon() {

        if (icon == null) {
            icon = new ImageIcon( getClass().getResource("funnel.png"));
            icon = new ImageIcon( icon.getImage().getScaledInstance( 12, 12, Image.SCALE_SMOOTH  ));
        }
        return icon;

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        
        final JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if ( rendererInit ) {
            originalHorizontalTextPosition = label.getHorizontalTextPosition();
            rendererInit = false;
        }
        
        int modelColumn =  table.convertColumnIndexToModel(column);
        if ( tableFilter.isFiltered(modelColumn) ) {
            
            Icon originalIcon = label.getIcon();
            if ( originalIcon == null ) {
              label.setIcon( getFilterIcon() );     
            } else {
              label.setIcon( new CompoundIcon( getFilterIcon(), originalIcon ) );
            }
            label.setHorizontalTextPosition( JLabel.TRAILING );
            
        } else {
            label.setHorizontalTextPosition( originalHorizontalTextPosition );
        }
        
        return label;
    }

}