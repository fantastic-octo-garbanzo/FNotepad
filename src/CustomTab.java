package src;
// Imports
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
/************************************/
class Tabs extends JFrame {
    JTabbedPane tabbedPane;
    int tabCount;

    public Tabs() {
        createGUI();
        setDisplay();
    }

    private void setDisplay() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGUI() {
        createJTabbedPane();
        add(tabbedPane);
    }

    private void createJTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.add(createJPanel(), "Tab " + String.valueOf(tabCount), tabCount++);
        tabbedPane.setTabComponentAt(0, new CustomTab(this));
        tabbedPane.add(new JPanel(), "+", tabCount++);
        tabbedPane.addChangeListener(changeListener);
    }

    private JPanel createJPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.add(new JScrollPane(createTextArea(10, 40)));
        return panel;
    }

    private JTextArea createTextArea(int row, int col) {
        JTextArea ta = new JTextArea(row, col);
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        return ta;
    }

    ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            addNewTab();
        }
    };

    private void addNewTab() {
        int index = tabCount - 1;
        if (tabbedPane.getSelectedIndex() == index) {
            tabbedPane.add(createJPanel(), "Tab " + String.valueOf(index), index);
            tabbedPane.setTabComponentAt(index, new CustomTab(this));
            tabbedPane.removeChangeListener(changeListener);
            tabbedPane.setSelectedIndex(index);
            tabbedPane.addChangeListener(changeListener);
            tabCount++;
        }
    }

    public void removeTab(int index) {
        tabbedPane.remove(index);
        tabCount--;

        if (index == tabCount - 1 && index > 0) {
            tabbedPane.setSelectedIndex(tabCount - 2);
        } else {
            tabbedPane.setSelectedIndex(index);
        }

        if (tabCount == 1) {
            addNewTab();
        }
    }
}
/************************************/
public class CustomTab extends JPanel {

    JTabbedPane tabbedPane = new JTabbedPane();
    Tabs customTabbedPane;

    public CustomTab(Tabs customTabbedPane) {
        this.customTabbedPane = customTabbedPane;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(new EmptyBorder(5, 2, 2, 2));
        setOpaque(false);
        addLabel();
        add(new CustomButton("x"));
    }

    private void addLabel() {
        JLabel label = new JLabel() {
            public String getText() {
                int index = customTabbedPane.tabbedPane.indexOfTabComponent(CustomTab.this);
                if (index != -1)
                    return customTabbedPane.tabbedPane.getTitleAt(index);
                return null;
            }
        };
        label.setBorder(new EmptyBorder(0, 0, 0, 10));
        add(label);
    }

    class CustomButton extends JButton implements MouseListener {
        public CustomButton(String text) {
            int size = 15;
            setText(text);
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Tab schließen");
            setContentAreaFilled(false);
            setBorder(new EtchedBorder());
            setBorderPainted(false);
            setFocusable(false);
            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int index = customTabbedPane.tabbedPane.indexOfTabComponent(CustomTab.this);
            if (index != -1)
                customTabbedPane.removeTab(index);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setBorderPainted(true);
            setForeground(Color.RED);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setBorderPainted(false);
            setForeground(Color.BLACK);
        }
    }
    public static void main(String[] args) {
        new Tabs();
    }
}