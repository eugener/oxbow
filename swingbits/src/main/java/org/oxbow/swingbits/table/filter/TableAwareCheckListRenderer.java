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
import java.lang.reflect.Method;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.oxbow.swingbits.list.CheckListRenderer;

@SuppressWarnings("serial")
public class TableAwareCheckListRenderer extends CheckListRenderer {
	
	private final JTable table;
	private final int column;

	public TableAwareCheckListRenderer( JTable table, int column ) {
		this.table = table;
		this.column = column;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		// try to retrieve the text from table's cell renderer
		if (value instanceof DistinctColumnItem) {

			DistinctColumnItem item = (DistinctColumnItem) value;
			TableCellRenderer renderer = table.getCellRenderer( item.getRow(), column);
			
			try {
				
				Component cmpt = renderer.getTableCellRendererComponent(
						table, item.getValue(), isSelected, hasFocus(), item.getRow(), column );

				Method method = cmpt.getClass().getMethod("getText");
				Object s = method.invoke(cmpt);
				
				if ( s instanceof String ) {
					setText( (String)s );
				}
				
			} catch (Throwable e) {
//				e.printStackTrace();
			}
			
		}
		return this;
		
	}
	
}
