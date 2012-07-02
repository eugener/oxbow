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

import java.io.Serializable;
import java.util.Collection;

import javax.swing.DefaultRowSorter;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JTableFilter extends AbstractTableFilter<JTable> {

	private static final long serialVersionUID = 1L;

	private final TableRowFilter filter = new TableRowFilter();

	public JTableFilter( JTable table ) {
		super(table);
	}

	@Override
	protected boolean execute(int col, Collection<DistinctColumnItem> items) {

		RowSorter<?> rs = getTable().getRowSorter();

		if (!( rs instanceof DefaultRowSorter )) return false;

		DefaultRowSorter<?, ?> drs = (DefaultRowSorter<?, ?>) rs;

		@SuppressWarnings("unchecked")
		RowFilter<Object,Object> prevFilter = (RowFilter<Object, Object>) drs.getRowFilter();
		if ( !(prevFilter instanceof TableRowFilter)) {
			filter.setParentFilter(prevFilter);
		}

		drs.setRowFilter(filter);
		return true;


	}

	class TableRowFilter extends RowFilter<Object,Object> implements Serializable {

		private static final long serialVersionUID = 1L;

		private RowFilter<Object, Object> parentFilter;

		public RowFilter<Object, Object> getParentFilter() {
			return parentFilter;
		}

		public void setParentFilter( RowFilter<Object, Object> parentFilter ) {
			this.parentFilter = (parentFilter == null ||  parentFilter == this )? null: parentFilter;
		}

		@Override
		public boolean include( final RowFilter.Entry<? extends Object, ? extends Object> entry) {

			// use parent filter condition
			if ( parentFilter != null && !parentFilter.include(entry)) return false;

			return includeRow( new ITableFilter.Row() {

				@Override
				public Object getValue(int column) { return entry.getValue(column); }

				@Override
				public int getValueCount() { return entry.getValueCount(); }

			});

		}

	}

	public void modelChanged( TableModel model ) {
		getTable().setRowSorter(  new TableRowSorter<TableModel>( model ));
	}


}
