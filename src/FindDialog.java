package src;
// Imports

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

/******************************************************/
class FindReplaceDemo extends JFrame {
    FindDialog dialog = null;
    JTextArea ta;
    JButton findButton, replaceButton;

    FindReplaceDemo() {
        super("Suchen");

        ta = new JTextArea(7, 20);
        findButton = new JButton("Text suchen");

        ActionListener ac1 = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (dialog == null) dialog = new FindDialog(FindReplaceDemo.this.ta);
                dialog.showDialog(FindReplaceDemo.this, true);// find
            }
        };
        findButton.addActionListener(ac1);

        replaceButton = new JButton("Text ersetzen");

        ActionListener ac2 = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (dialog == null) dialog = new FindDialog(FindReplaceDemo.this.ta);
                dialog.showDialog(FindReplaceDemo.this, false);// find
            }
        };
        replaceButton.addActionListener(ac2);

        add(ta, BorderLayout.CENTER);
        add(replaceButton, BorderLayout.NORTH);
        add(findButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 400, 400);
        ta.setCaretPosition(0);
        setVisible(true);
    }

    ////////////////////////////////
    public static void main(String[] args) {
        new FindReplaceDemo();
    }
}

/******************************************************/
public class FindDialog extends JPanel implements ActionListener {
    JTextArea jta;
    public int lastIndex;
    JLabel replaceLabel;

    private TextField findWhat;
    private JTextField replaceWith;

    private JCheckBox matchCase;

    JRadioButton up, down;

    JButton findNextButton, replaceButton, replaceAllButton, cancelButton;

    JPanel direction, replaceButtonPanel;
    CardLayout card;

    private boolean ok;
    private JDialog dialog;

    ///////////////////////
    public FindDialog(JTextArea jta) {

        this.jta = jta;
        findWhat = new TextField(20);
        replaceWith = new JTextField(20);

        matchCase = new JCheckBox(FNotepad.bundle.getString("FindDialog.matchCase"));

        up = new JRadioButton(FNotepad.bundle.getString("FindDialog.Up"));
        down = new JRadioButton(FNotepad.bundle.getString("FindDialog.Down"));

        down.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(up);
        bg.add(down);

        direction = new JPanel();
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, FNotepad.bundle.getString("FindDialog.Direction"));
        direction.setBorder(titled);
        direction.setLayout(new GridLayout(1, 2));
        direction.add(up);
        direction.add(down);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(matchCase);
        southPanel.add(direction);

        findNextButton = new JButton(FNotepad.bundle.getString("FindDialog.Next"));
        replaceButton = new JButton(FNotepad.bundle.getString("FindDialog.Replace"));
        replaceAllButton = new JButton(FNotepad.bundle.getString("FindDialog.ReplaceA"));
        cancelButton = new JButton(FNotepad.bundle.getString("FontChooser.Cancel"));

        replaceButtonPanel = new JPanel();
        replaceButtonPanel.setLayout(new GridLayout(4, 1));
        replaceButtonPanel.add(findNextButton);
        replaceButtonPanel.add(replaceButton);
        replaceButtonPanel.add(replaceAllButton);
        replaceButtonPanel.add(cancelButton);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(3, 2));
        textPanel.add(new JLabel(FNotepad.bundle.getString("FindDialog.What")));
        textPanel.add(findWhat);
        textPanel.add(replaceLabel = new JLabel(FNotepad.bundle.getString("FindDialog.ReplaceW")));
        textPanel.add(replaceWith);
        textPanel.add(new JLabel(" "));
        textPanel.add(new JLabel(" "));

        setLayout(new BorderLayout());

        add(new JLabel("       "), BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(replaceButtonPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);

        setSize(200, 200);

        findNextButton.addActionListener(this);
        replaceButton.addActionListener(this);
        replaceAllButton.addActionListener(this);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                dialog.setVisible(false);
            }
        });

        findWhat.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent te) {
                enableDisableButtons();
            }
        });
        findWhat.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent te) {
                enableDisableButtons();
            }
        });
    }

    //////////////////////////
    void enableDisableButtons() {
        if (findWhat.getText().length() == 0) {
            findNextButton.setEnabled(false);
            replaceButton.setEnabled(false);
            replaceAllButton.setEnabled(false);
        } else {
            findNextButton.setEnabled(true);
            replaceButton.setEnabled(true);
            replaceAllButton.setEnabled(true);
        }
    }

    ///////////////////////////////////
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == findNextButton) findNextWithSelection();
        else if (ev.getSource() == replaceButton) replaceNext();
        else if (ev.getSource() == replaceAllButton)
            JOptionPane.showMessageDialog(null, FNotepad.bundle.getString("FindDialog.Total") + replaceAllNext());
    }

    /////////////////////////
    int findNext() {

        String s1 = jta.getText();
        String s2 = findWhat.getText();

        lastIndex = jta.getCaretPosition();

        int selStart = jta.getSelectionStart();
        int selEnd = jta.getSelectionEnd();

        if (up.isSelected()) {
            if (selStart != selEnd) lastIndex = selEnd - s2.length() - 1;

            if (!matchCase.isSelected()) lastIndex = s1.toUpperCase().lastIndexOf(s2.toUpperCase(), lastIndex);
            else lastIndex = s1.lastIndexOf(s2, lastIndex);
        } else {
            if (selStart != selEnd)
                lastIndex = selStart + 1;
            if (!matchCase.isSelected())
                lastIndex = s1.toUpperCase().indexOf(s2.toUpperCase(), lastIndex);
            else
                lastIndex = s1.indexOf(s2, lastIndex);
        }

        return lastIndex;
    }

    ///////////////////////////////////////////////
    public void findNextWithSelection() {
        int idx = findNext();
        if (idx != -1) {
            jta.setSelectionStart(idx);
            jta.setSelectionEnd(idx + findWhat.getText().length());
        } else
            JOptionPane.showMessageDialog(this, FNotepad.bundle.getString("FindDialog.Error1") + findWhat.getText() + "\"", FNotepad.bundle.getString("FindDialog.ErrorT"), JOptionPane.INFORMATION_MESSAGE);
    }

    //////////////////////////////////////////////
    void replaceNext() {
        // if nothing is selectd
        if (jta.getSelectionStart() == jta.getSelectionEnd()) {
            findNextWithSelection();
            return;
        }

        String searchText = findWhat.getText();
        String temp = jta.getSelectedText(); // get selected text

        // check if the selected text matches the search text then do replacement
        if ((matchCase.isSelected() && temp.equals(searchText)) || (!matchCase.isSelected() && temp.equalsIgnoreCase(searchText)))
            jta.replaceSelection(replaceWith.getText());
        findNextWithSelection();
    }

    //////////////////////////////////////////////
    int replaceAllNext() {
        if (up.isSelected()) jta.setCaretPosition(jta.getText().length() - 1);
        else jta.setCaretPosition(0);
        int idx = 0;
        int counter = 0;
        do {
            idx = findNext();
            if (idx == -1) break;
            counter++;
            jta.replaceRange(replaceWith.getText(), idx, idx + findWhat.getText().length());
        } while (idx != -1);

        return counter;
    }

    //////////////////////////////////////////////
    public boolean showDialog(Component parent, boolean isFind) {

        Frame owner = null;

        if (parent instanceof Frame) owner = (Frame) parent;
        else owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);

        if (dialog == null || dialog.getOwner() != owner) {
            dialog = new JDialog(owner, false);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(findNextButton);
        }

        if (findWhat.getText().length() == 0) findNextButton.setEnabled(false);
        else findNextButton.setEnabled(true);

        replaceButton.setVisible(false);
        replaceAllButton.setVisible(false);
        replaceWith.setVisible(false);
        replaceLabel.setVisible(false);

        if (isFind) {
            dialog.setSize(460, 180);
            dialog.setTitle(FNotepad.bundle.getString("FindDialog.Search"));
        } else {
            replaceButton.setVisible(true);
            replaceAllButton.setVisible(true);
            replaceWith.setVisible(true);
            replaceLabel.setVisible(true);

            dialog.setSize(450, 200);
            dialog.setTitle(FNotepad.bundle.getString("FindDialog.Replace"));
        }
        dialog.setVisible(true);
        return ok;
    }
    //////////////////////////////
}
