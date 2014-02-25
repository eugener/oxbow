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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.oxbow.swingbits.list.CheckListFilterType;
import org.oxbow.swingbits.util.IObjectToStringTranslator;

public final class TableRowFilterSupport {

    private boolean searchable = false;
    private CheckListFilterType searchType = CheckListFilterType.CONTAINS;
    private IObjectToStringTranslator translator;
    private final ITableFilter<?> filter;
    private boolean actionsVisible = true;
    private boolean useTableRenderers = false;

    private TableRowFilterSupport( ITableFilter<?> filter ) {
        if ( filter == null ) throw new NullPointerException();
        //this.table = table;
        this.filter = filter;
    }

    public static TableRowFilterSupport forTable( JTable table ) {
        return new TableRowFilterSupport(new JTableFilter(table));
    }

    public static TableRowFilterSupport forFilter( ITableFilter<?> filter ) {
        return new TableRowFilterSupport(filter);
    }

    /**
     * Additional actions visible in column filter list
     * @param visible
     * @return
     */
    public TableRowFilterSupport actions( boolean visible ) {
        this.actionsVisible = visible;
        return this;
    }

    /**
     * Comlumn filter list is searchable
     * @param searchable
     * @return
     */
    public TableRowFilterSupport searchable( boolean searchable ) {
        this.searchable = searchable;
        return this;
    }

    public TableRowFilterSupport searchTransalator( IObjectToStringTranslator translator ) {
        this.translator = translator;
        return this;
    }

    public TableRowFilterSupport searchType( CheckListFilterType searchType ) {
        this.searchType = searchType;
        return this;
    }

    public TableRowFilterSupport useTableRenderers( boolean value ) {
        this.useTableRenderers = value;
        return this;
    }

    public JTable apply() {

        final TableFilterColumnPopup filterPopup = new TableFilterColumnPopup(filter);
        filterPopup.setEnabled(true);
        filterPopup.setActionsVisible(actionsVisible);
        filterPopup.setSearchable(searchable);
        filterPopup.setSearchTranslator(translator);
        filterPopup.setSearchType(searchType);
        filterPopup.setUseTableRenderers( useTableRenderers );

        setupTableHeader();

        return filter.getTable();
    }

    private void setupTableHeader() {

        final JTable table = filter.getTable();

        filter.addChangeListener(new IFilterChangeListener() {

            @Override
            public void filterChanged(ITableFilter<?> filter) {
                table.getTableHeader().repaint();

            }
        });

        // make sure that search component is reset after table model changes
        setupHeaderRenderers(table.getModel(), true );
//        table.addPropertyChangeListener("model", new PropertyChangeListener() {
//
//            public void propertyChange(PropertyChangeEvent evt) {
//
//                FilterTableHeaderRenderer headerRenderer = new FilterTableHeaderRenderer(filter);
//                filter.modelChanged((TableModel) evt.getNewValue());
//
//                for( TableColumn c:  Collections.list( filter.getTable().getColumnModel().getColumns()) ) {
//                    c.setHeaderRenderer( headerRenderer );
//                }
//            }}
//
//        );
    }

    private void setupHeaderRenderers( TableModel newModel, boolean fullSetup ) {

        JTable table =  filter.getTable();

        FilterTableHeaderRenderer headerRenderer = new FilterTableHeaderRenderer(filter);
        filter.modelChanged( newModel );

        for( TableColumn c:  Collections.list( table.getColumnModel().getColumns()) ) {
            c.setHeaderRenderer( headerRenderer );
        }

        if ( !fullSetup ) return;

        table.addPropertyChangeListener("model", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent e) {
                setupHeaderRenderers( (TableModel) e.getNewValue(), false );
            }

        });


    }


}
