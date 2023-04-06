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
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.oxbow.swingbits.list.CheckListFilterType;
import org.oxbow.swingbits.list.IListFilter;
import org.oxbow.swingbits.util.IObjectToStringTranslator;

public final class TableRowFilterSupport {

    private boolean searchable = false;
    private IListFilter searchFilter = CheckListFilterType.CONTAINS;
    private IObjectToStringTranslator translator;
    private final ITableFilter<?> filter;
    private boolean actionsVisible = true;
    private int filterIconPlacement = SwingConstants.LEADING;
    private boolean useTableRenderers = false;
    private boolean autoclean = false;

    public enum FilterType {
        DEFAULT,
        EXCEL
    }
    private Set<?> searchableColumns;
    private boolean enableRightClick;

    private Icon filteringIcon;//icon which is displayed on column before any data filtered
    private Icon filteredIcon;//icon which is displayed on column after any data filtered
    private FilterType filterType = FilterType.DEFAULT;
    private boolean clearTableFilter = false;

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
     * Set the placement of the filter icon with respect to the existing icon
     * in the column label.
     *
     * @param filterIconPlacement either SwingConstants.LEADING or
     *         SwingConstants.TRAILING.
     * @return this
     */
    public TableRowFilterSupport filterIconPlacement(int filterIconPlacement) {
        if (filterIconPlacement != SwingConstants.LEADING &&
                filterIconPlacement != SwingConstants.TRAILING) {
            throw new UnsupportedOperationException("The filter icon " +         
                    "placement can only take the values of " +                   
                    "SwingConstants.LEADING or SwingConstants.TRAILING");
        }
        this.filterIconPlacement = filterIconPlacement;
        return this;
    }

    /**
     * Column filter list is searchable
     * @param searchable
     * @return
     */
    public TableRowFilterSupport searchable( boolean searchable ) {
        this.searchable = searchable;
        return this;
    }
    
    /**
     * Set flag to clear all filters automatically if model row count is 0
     * @param autoclean
     * @return
     */
    public TableRowFilterSupport autoclean( boolean autoclean ) {
        this.autoclean = autoclean;
        return this;
    }

    public TableRowFilterSupport searchFilter(IListFilter searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }

    public TableRowFilterSupport searchTransalator( IObjectToStringTranslator translator ) {
        this.translator = translator;
        return this;
    }

    public TableRowFilterSupport useTableRenderers( boolean value ) {
        this.useTableRenderers = value;
        return this;
    }

    public TableRowFilterSupport onFilterChange(IFilterChangeListener listener) {
        this.filter.addChangeListener( listener );
        return this;
    }

    public JTable apply() {

        final TableFilterColumnPopup filterPopup = new TableFilterColumnPopup(filter);
        filterPopup.setEnabled(true);
        filterPopup.setActionsVisible(actionsVisible);
        filterPopup.setSearchable(searchable);
        filterPopup.setSearchFilter(searchFilter);
        filterPopup.setSearchTranslator(translator);
        filterPopup.setUseTableRenderers( useTableRenderers );
        filterPopup.setSearchableColumns(searchableColumns);
        filterPopup.setEnableRightClick(enableRightClick);
        filterPopup.setFilteringIcon(filteringIcon);
        filterPopup.setFilteredIcon(filteredIcon);
        filterPopup.setClearFilterIcon(clearTableFilter);

        setupTableHeader();
        
        filter.setAutoClean(autoclean);
        
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
    }

    public void applyColumnFilters(Map<Integer, Set<DistinctColumnItem>> columnFilters) {

        if ( columnFilters == null ) return;

        for ( Integer key : columnFilters.keySet()) {
            Set<DistinctColumnItem> checked = columnFilters.get(key);
            this.filter.apply(key, checked);
        }
    }


    private void setupHeaderRenderers( TableModel newModel, boolean fullSetup ) {

        JTable table =  filter.getTable();

        filter.modelChanged( newModel );

        //default icons
        if (filteringIcon == null) {
            filteringIcon = new ImageIcon(getClass().getResource("filtering.png"));
        }
        if (filteredIcon == null) {
            filteredIcon = new ImageIcon(getClass().getResource("filtered.png"));
        }

        TableCellRenderer headerRenderer = null;
        switch (filterType) {
            case DEFAULT:
            	for( TableColumn c:  Collections.list( table.getColumnModel().getColumns()) ) {
                    if (searchable && ((searchableColumns == null || searchableColumns.isEmpty())
                            || searchableColumns.contains(c.getHeaderValue()))) {
                    	headerRenderer = new FilterTableHeaderRenderer(filter, filterIconPlacement);
                        c.setHeaderRenderer( headerRenderer );
                    }
                }
                break;
            case EXCEL:
            	for( TableColumn c:  Collections.list( table.getColumnModel().getColumns()) ) {
                    if (searchable && ((searchableColumns == null || searchableColumns.isEmpty())
                            || searchableColumns.contains(c.getHeaderValue()))) {
                    	headerRenderer =
                    			new ExcelFilterTableHeaderRenderer(filter, filterIconPlacement, (String) c.getHeaderValue(), filteringIcon, filteredIcon);
                        c.setHeaderRenderer( headerRenderer );
                    }
                }
                break;
        }
        

        if ( !fullSetup ) return;

        table.addPropertyChangeListener("model", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent e) {
                setupHeaderRenderers( (TableModel) e.getNewValue(), false );
            }

        });

    }

    /**
     * Column filter list is searchable & supported columns for searching
     * Any column is not listed in the table model will be ignored
     *
     * @param searchableColumns
     * @return
     */
    public TableRowFilterSupport searchableColumns(Object... searchableColumns) {
        return this.searchableColumns(Arrays.stream(searchableColumns).collect(Collectors.toSet()));
    }

    /**
     * Column filter list is searchable & supported columns for searching
     * Any column is not listed in the table model will be ignored
     *
     * @param searchableColumns
     * @return
     */
    public TableRowFilterSupport searchableColumns(Set<?> searchableColumns) {
        this.searchable = true;
        this.searchableColumns = searchableColumns;
        return this;
    }

    /**
     * Column filter list is searchable & supported columns for searching
     * Any column is not listed in the table model will be ignored
     *
     * @param searchableColumns
     * @return
     */
    public TableRowFilterSupport searchableColumns(List<?> searchableColumns) {
        return this.searchableColumns(new HashSet<>(searchableColumns));
    }

    public TableRowFilterSupport sortable(boolean sortable) {
        this.filter.setSortable(sortable);
        return this;
    }

    public TableRowFilterSupport enableRightClick(boolean enableRightClick) {
        this.enableRightClick = enableRightClick;
        return this;
    }

    public TableRowFilterSupport filteringIcon(Icon filteringIcon) {
        this.filteringIcon = filteringIcon;
        return this;
    }

    public TableRowFilterSupport filteredIcon(Icon filteredIcon) {
        this.filteredIcon = filteredIcon;
        return this;
    }

    public TableRowFilterSupport filterType(FilterType filterType) {
        this.filterType = filterType;
        return this;
    }
    
    public TableRowFilterSupport enableClearTableFilter(boolean clearTableFilter) {
    	this.clearTableFilter = clearTableFilter;
    	return this;
    }
}
