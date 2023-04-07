package org.oxbow.swingbits.table.filter;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExcelFilterTableHeaderRenderer extends JPanel implements TableCellRenderer {
    private static final long serialVersionUID = 1L;

    private final int filterIconPlacement;
    private final ITableFilter<?> tableFilter;

    private int column = -1;
    private JTable table = null;
    private JButton button;
    private JLabel title;
    private Icon filteringIcon;//icon which is displayed on column before any data filtered
    private Icon filteredIcon;//icon which is displayed on column after any data filtered

    public ExcelFilterTableHeaderRenderer(ITableFilter<?> tableFilter,
                                          int filterIconPlacement,
    									  String columnName,
                                          Icon filteringIcon,
                                          Icon filteredIcon) {
        super(new BorderLayout());
        this.filterIconPlacement = filterIconPlacement;
        if (this.filterIconPlacement != SwingConstants.LEADING &&
                this.filterIconPlacement != SwingConstants.TRAILING) {
            throw new UnsupportedOperationException("The filter icon " +
                    "placement can only take the values of " +
                    "SwingConstants.LEADING or SwingConstants.TRAILING");
        }

        this.tableFilter = tableFilter;
        this.filteringIcon = filteringIcon;
        this.filteredIcon = filteredIcon;
        this.tableFilter.addChangeListener(filter -> button.setIcon(filter.isFiltered(column)? filteredIcon : filteringIcon));

        button = new JButton(this.filteringIcon);
        button.setPreferredSize(new Dimension(25, 15));
        button.setBorder(BorderFactory.createBevelBorder(1,Color.GRAY, Color.GRAY));
        title = new JLabel(columnName);

        switch (this.filterIconPlacement) {
            case SwingConstants.LEADING:
                add(button, BorderLayout.WEST);
                add(title, BorderLayout.CENTER);
                break;
            case SwingConstants.TRAILING:
                add(title, BorderLayout.CENTER);
                add(button, BorderLayout.EAST);
                break;
        }
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (table != null && this.table != table) {
        	//commented to update the column with respective names
            //title.setText(table.getColumnName(column));
            this.table = table;
            final JTableHeader header = table.getTableHeader();
            if (header != null) {
                header.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int col = header.getTable().columnAtPoint(e.getPoint());
                        if (col != column || col == -1) return;

                        int index = header.getColumnModel().getColumnIndexAtX(e.getPoint().x);
                        if (index == -1) return;

                        setBounds(header.getHeaderRect(index));
                        header.add(ExcelFilterTableHeaderRenderer.this);
                        validate();

                        Rectangle buttonBounds = new Rectangle(button.getLocationOnScreen().x, button.getLocationOnScreen().y,
                                button.getBounds().width, button.getBounds().height);
                        if ((buttonBounds.contains(e.getLocationOnScreen()))) {
                            button.doClick();

                            for (MouseListener ml : header.getMouseListeners()) {
                                if (ml instanceof TableFilterColumnPopup) {
                                    ((TableFilterColumnPopup) ml).showPopupWindow(e);
                                }
                            }
                        }
                        header.remove(ExcelFilterTableHeaderRenderer.this);

                        header.repaint();
                    }
                });
            }
        }
        this.column = column;
        return this;
    }

}
