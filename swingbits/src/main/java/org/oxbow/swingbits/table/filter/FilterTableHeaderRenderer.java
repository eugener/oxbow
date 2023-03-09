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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.oxbow.swingbits.table.TableHeaderRenderer;
import org.oxbow.swingbits.util.swing.CompoundIcon;

/**
 * Table header renderer to show the column filter state
 *
 * Created on Feb 10, 2011
 * @author Eugene Ryzhikov
 *
 */
class FilterTableHeaderRenderer extends JPanel implements TableCellRenderer {

    private static final long serialVersionUID = 1L;

    private ImageIcon icon;
    private final int filterIconPlacement;
    private final ITableFilter<?> tableFilter;

    private int     column  = -1;
    private JTable  table   = null;
    private MenuButton b;
    private String filterIcon;

    public FilterTableHeaderRenderer(ITableFilter<?> tableFilter,
                                     int filterIconPlacement,
                                     String columnName,
                                     String filterIcon) {
        super(new BorderLayout());
        this.tableFilter = tableFilter;
        this.filterIconPlacement = filterIconPlacement;
        this.filterIcon = filterIcon;

        if (this.filterIconPlacement != SwingConstants.LEADING &&
                this.filterIconPlacement != SwingConstants.TRAILING) {
            throw new UnsupportedOperationException("The filter icon " +
                    "placement can only take the values of " +
                    "SwingConstants.LEADING or SwingConstants.TRAILING");
        }
        b = new MenuButton(getFilterIcon(filterIcon));
        b.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        JLabel l = new JLabel(columnName);
        l.setFont(l.getFont().deriveFont(Font.PLAIN));
        l.setBorder(BorderFactory.createEmptyBorder(1,5,1,1));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        add(l, BorderLayout.CENTER);
        add(b, BorderLayout.EAST);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }

    private Icon getFilterIcon(String filterIcon) {
        if (icon == null) {
            icon = new ImageIcon( getClass().getResource(filterIcon));
            icon = new ImageIcon( icon.getImage().getScaledInstance( 12, 12, Image.SCALE_SMOOTH  ));
        }
        return icon;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

//        final JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//        int modelColumn =  table.convertColumnIndexToModel(column);
//        if (tableFilter.isFiltered(modelColumn)) {
//            Icon oldIcon = label.getIcon();
//            Icon newIcon = null;
//            if (oldIcon == null) {
//                newIcon = getFilterIcon();
//            } else {
//                ComponentOrientation orientation =
//                        label.getComponentOrientation();
//                if (ComponentOrientation.RIGHT_TO_LEFT.equals(orientation)) {
//                    if (filterIconPlacement == SwingConstants.LEADING) {
//                        newIcon = new CompoundIcon(oldIcon, getFilterIcon());
//                    } else {
//                        newIcon = new CompoundIcon(getFilterIcon(), oldIcon);
//                    }
//                } else {
//                    if (filterIconPlacement == SwingConstants.LEADING) {
//                        newIcon = new CompoundIcon(getFilterIcon(), oldIcon);
//                    } else {
//                        newIcon = new CompoundIcon(oldIcon, getFilterIcon());
//                    }
//                }
//            }
//            label.setIcon(newIcon);
//        }
//
//        return label;
        if (table != null && this.table != table) {
            this.table = table;
            final JTableHeader header = table.getTableHeader();
            if (header != null) {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());

                header.addMouseListener(new MouseAdapter() {

                    @Override
                    public void  mouseClicked(MouseEvent e) {
                        int col = header.getTable().columnAtPoint(e.getPoint());
                        if (col != column || col == -1) return;

                        int index = header.getColumnModel().getColumnIndexAtX(e.getPoint().x);
                        if (index == -1) return;

                        setBounds(header.getHeaderRect(index));
                        header.add(FilterTableHeaderRenderer.this);
                        validate();

                        b.doClick();

                        header.remove(FilterTableHeaderRenderer.this);

                        header.repaint();

                        for (MouseListener ml : header.getMouseListeners()) {
                            e.setSource(b);
                            ml.mousePressed(e);
                        }
                    }
                });
            }
        }
        this.column = column;
        return this;
    }

    class MenuButton extends JToggleButton {
        public MenuButton(Icon imageIcon) {
            super(imageIcon);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    JToggleButton b = MenuButton.this;
                    //now trigger th action back to header action
                }
            });
        }
    }

}
