package org.oxbow.swingbits;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.oxbow.swingbits.action.ActionContainerBuilderFactory;
import org.oxbow.swingbits.action.CheckAction;
import org.oxbow.swingbits.action.RadioAction;

import static org.oxbow.swingbits.action.Actions.actions;
import static org.oxbow.swingbits.action.Actions.collapsedGroup;
import static org.oxbow.swingbits.action.Actions.expandedGroup;
import static org.oxbow.swingbits.action.Actions.separator;

public class MenuBuilderTest implements Runnable {

public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater( new MenuBuilderTest() );

    }

    @Override
    public void run() {

        final JFrame f = new JFrame("Swing Table Filter Test");
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        f.setPreferredSize( new Dimension( 1000, 600 ));

        Icon icon = new ImageIcon( getClass().getResource("pin_blue.png"));

        Collection<Action> actions = actions(

            collapsedGroup("Group 1", icon).actions(
                    new XCheckAction("Action 1.1", icon),
                    collapsedGroup( "Group 1.1", icon).actions(
                        new EmptyAction("Action 1.1.1", icon),
                        new EmptyAction("Action 1.1.2"),
                        new EmptyAction("Action 1.1.3")
                    ),
                    expandedGroup( "Group 1.2", icon).actions(
                        new XRadioAction("Action 1.2.1"),
                        new XRadioAction("Action 1.2.2", icon),
                        new XRadioAction("Action 1.2.3")
                    ),
                    separator(),
                    new XCheckAction("Action 1.2", icon)),
            collapsedGroup("Group 2", icon),
            collapsedGroup("Group 3"),
            new XCheckAction("Action 4", icon)

        );

        final JMenuBar mbar = ActionContainerBuilderFactory.getMenuBarBuilder().build(actions);
        final JPopupMenu menu = ActionContainerBuilderFactory.getPopupMenuBuilder().build(actions);
        final JToolBar toolbar = ActionContainerBuilderFactory.getToolBarBuilder().build(actions);

        f.setJMenuBar(mbar);
        JPanel content = (JPanel) f.getContentPane();
        content.setLayout( new BorderLayout());
        content.add( toolbar, BorderLayout.NORTH);
        content.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menu.show(f, e.getX(), e.getY() );
            }
        });

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }
    
    @SuppressWarnings("serial")
    public static class EmptyAction extends AbstractAction {
        
        public EmptyAction( String name, Icon icon ) { super( name, icon ); }
        public EmptyAction( String name ) { super( name ); }
        
        @Override
        public void actionPerformed(ActionEvent e) {}

    }

    @SuppressWarnings("serial")
    @CheckAction
    public static final class XCheckAction extends EmptyAction {

        public XCheckAction(String name, Icon icon) {
            super(name, icon);
        }
        public XCheckAction( String name ) { super( name ); }
        
    }


    @SuppressWarnings("serial")
    @RadioAction
    public static final class XRadioAction extends EmptyAction {

        public XRadioAction(String name, Icon icon) {
            super(name, icon);
        }
        public XRadioAction( String name ) { super( name ); }
        
    }
    
}
