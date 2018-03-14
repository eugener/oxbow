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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.oxbow.swingbits.list.ActionCheckListModel;
import org.oxbow.swingbits.list.CheckList;
import org.oxbow.swingbits.list.CheckListFilterType;
import org.oxbow.swingbits.list.DefaultCheckListModel;
import org.oxbow.swingbits.list.ICheckListModel;
import org.oxbow.swingbits.list.IListFilter;
import org.oxbow.swingbits.misc.JSearchTextField;
import org.oxbow.swingbits.popup.PopupWindow;
import org.oxbow.swingbits.util.CollectionUtils;
import org.oxbow.swingbits.util.IObjectToStringTranslator;

class TableFilterColumnPopup extends PopupWindow implements MouseListener {

        static class ColumnAttrs {
            public Dimension preferredSize;
        }

        private boolean enabled = false;

        private final CheckList<DistinctColumnItem> filterList = new CheckList.Builder().build();
        private final JSearchTextField searchField = new JSearchTextField();

        private final Map<Integer, ColumnAttrs> colAttrs = new HashMap<Integer, ColumnAttrs>();
        private int mColumnIndex = -1;
        
        private final ITableFilter<?> filter;
        private boolean searchable;
        private IListFilter searchFilter = CheckListFilterType.CONTAINS;
        private IObjectToStringTranslator translator;
        private boolean actionsVisible = true;
        private boolean useTableRenderers = false;
        ResourceBundle bundle = ResourceBundle.getBundle( "task-dialog" ); // NOI18N

        public TableFilterColumnPopup( ITableFilter<?> filter ) {

            super( true );

            this.filter = filter;
            filterList.getList().setVisibleRowCount(8);

            setupTableHeader();
            filter.getTable().addPropertyChangeListener( "tableHeader", new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    setupTableHeader();
                }}
            );
            filter.getTable().addPropertyChangeListener( "model", new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    colAttrs.clear();
                }}
            );
            
            searchField.getDocument().addDocumentListener( new DocumentListener() {
                
                @Override
                public void removeUpdate(DocumentEvent e) { perform(e); }
                
                @Override
                public void insertUpdate(DocumentEvent e) { perform(e); }
                
                @Override
                public void changedUpdate(DocumentEvent e) { perform(e);}
                
                private void perform(DocumentEvent e) {
                    filterList.filter(searchField.getText(), translator, searchFilter);
                }
                
            });

        }
        
        public void setSearchable( boolean searchable ) {
            this.searchable = searchable;
        }
        
        public void setSearchTranslator( IObjectToStringTranslator tranlsator ) {
            this.translator = tranlsator;
        }

        public void setSearchFilter(IListFilter searchFilter) {
            this.searchFilter = searchFilter;
        }
        
        public void setActionsVisible(boolean actionsVisible) {
            this.actionsVisible = actionsVisible;
        }
        
        public void setUseTableRenderers(boolean reuseRenderers) {
            this.useTableRenderers = reuseRenderers;
        }

        private void setupTableHeader() {
            JTableHeader header = filter.getTable().getTableHeader();
            if ( header != null ) header.addMouseListener( this );
        }


        @SuppressWarnings("serial")
        @Override
        protected JComponent buildContent() {

            JPanel owner = new JPanel( new BorderLayout(3,3) );
            owner.setBorder( BorderFactory.createEmptyBorder(1, 1, 1, 1));
            owner.setPreferredSize(new Dimension(250, 150)); // default popup size

            Box commands = new Box(BoxLayout.LINE_AXIS);
            
            JToolBar toolbar = new JToolBar();
            toolbar.setFloatable(false);
            toolbar.setOpaque(false);
            toolbar.add( new PopupWindow.CommandAction(
                    bundle.getString( "Clear_ALL_COLUMN_FILTERS" ),
                    new ImageIcon(getClass().getResource("funnel_delete.png"))) {
                @Override
                protected boolean perform() {
                    return clearAllFilters();
                }
            });
            commands.add( toolbar );
            
            commands.add(Box.createHorizontalGlue());
            
            commands.add( new JButton( new PopupWindow.CommandAction(bundle.getString("Apply")){
                @Override
                protected boolean perform() {
                    return applyColumnFilter();
                }})
             );
            commands.add( Box.createHorizontalStrut(5) );
            commands.add( new JButton( new PopupWindow.CommandAction(bundle.getString("Cancel"))));
            commands.setBorder( BorderFactory.createEmptyBorder(3, 0, 3, 0));
            commands.setBackground(UIManager.getColor("Panel.background"));
            commands.setOpaque(true);

            if ( searchable) {
                owner.add( searchField, BorderLayout.NORTH );
            }
            owner.add( new JScrollPane( filterList.getList() ), BorderLayout.CENTER );
            owner.add( commands, BorderLayout.SOUTH );

            return owner;

        }
        
        private boolean applyColumnFilter() {
            Collection<DistinctColumnItem> checked = filterList.getCheckedItems();
            ICheckListModel<DistinctColumnItem> model = filterList.getModel(); 
            model.filter("", translator, CheckListFilterType.CONTAINS); // clear filter to get true results
            filter.apply(mColumnIndex, checked);
            return true;
        }
        
        private boolean clearAllFilters() {
            filter.clear();
            return true;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        // Popup menus are triggered differently on different platforms
        // Therefore, isPopupTrigger should be checked in both mousePressed and mouseReleased
        // events for for proper cross-platform functionality
        
        @Override
        public void mousePressed(MouseEvent e) {
            if ( enabled && e.isPopupTrigger() ) showFilterPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if ( enabled && e.isPopupTrigger() ) showFilterPopup(e);
        }

        private void showFilterPopup(MouseEvent e) {
            JTableHeader header = (JTableHeader)(e.getSource());
            TableColumnModel colModel = filter.getTable().getColumnModel();

            // The index of the column whose header was clicked
            int vColumnIndex = colModel.getColumnIndexAtX(e.getX());
            if ( vColumnIndex < 0 ) return;


            // Determine if mouse was clicked between column heads
            Rectangle headerRect = filter.getTable().getTableHeader().getHeaderRect(vColumnIndex);
            if (vColumnIndex == 0) {
                headerRect.width -= 2;
            } else {
                headerRect.grow(-2, 0);
            }

            // Mouse was clicked between column heads
            if (!headerRect.contains(e.getX(), e.getY())) return;

            // restore popup's size for the column
            mColumnIndex = filter.getTable().convertColumnIndexToModel(vColumnIndex);
            setPreferredSize( getColumnAttrs(vColumnIndex).preferredSize );

            Collection<DistinctColumnItem> distinctItems = filter.getDistinctColumnItems(mColumnIndex);

            DefaultCheckListModel<DistinctColumnItem> model = new DefaultCheckListModel<DistinctColumnItem>(distinctItems);
            filterList.setModel( actionsVisible? new ActionCheckListModel<DistinctColumnItem>( model): model);
            Collection<DistinctColumnItem> checked = filter.getFilterState(mColumnIndex);
            
            // replace empty checked items with full selection
            filterList.setCheckedItems( CollectionUtils.isEmpty(checked)? distinctItems: checked);

            if ( useTableRenderers ) {
                filterList.getList().setCellRenderer( new TableAwareCheckListRenderer( filter.getTable(), vColumnIndex) );
            }
            
            // show pop-up
            show( header, headerRect.x, header.getHeight() );
        }

        private ColumnAttrs getColumnAttrs( int column ) {
            ColumnAttrs attrs = colAttrs.get(column);
            if ( attrs == null ) {
                attrs = new ColumnAttrs();
                colAttrs.put( column, attrs);
            }

            return attrs;
        }

        
        @Override
        protected void beforeShow() {
            if ( searchable ) {
                SwingUtilities.invokeLater( new Runnable() {
                    @Override
                    public void run() {
                        searchField.setText("");
                        searchField.requestFocusInWindow();
                    }
                });
            }
        }
        
        @Override
        public void beforeHide() {
            // save pop-up's dimensions before pop-up becomes hidden
            getColumnAttrs(mColumnIndex).preferredSize = getPreferredSize();
        }


        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}


    }

