package FindDialog;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/******************************************************/
class FindReplaceDemoDE extends JFrame {
	FindDialogDE dialog = null; 
	JTextArea ta;
	JButton findButton, replaceButton;

	FindReplaceDemoDE() {
		super("Suchen");

		ta = new JTextArea(7, 20);
		findButton = new JButton("Text suchen");

		ActionListener ac1 = new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(dialog == null) dialog = new FindDialogDE(FindReplaceDemoDE.this.ta);
				dialog.showDialog(FindReplaceDemoDE.this, true);// find
			}
		};
		findButton.addActionListener(ac1);

		replaceButton = new JButton("Text ersetzen");

		ActionListener ac2 = new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(dialog == null) dialog = new FindDialogDE(FindReplaceDemoDE.this.ta);
				dialog.showDialog(FindReplaceDemoDE.this,false);// find
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
		new FindReplaceDemoDE();
	}

}
/******************************************************/
public class FindDialogDE extends JPanel implements ActionListener {
	JTextArea jta;
	public int lastIndex;
	JLabel replaceLabel;

	private TextField findWhat;
	private JTextField replaceWith;

	private JCheckBox matchCase;

	JRadioButton up, down;

	JButton findNextButton, replaceButton, replaceAllButton, cancelButton;

	JPanel direction, buttonPanel, findButtonPanel, replaceButtonPanel;
	CardLayout card;

	private boolean ok;
	private JDialog dialog;
	///////////////////////
	public FindDialogDE(JTextArea jta) {

		this.jta = jta;
		findWhat = new TextField(20);
		replaceWith = new JTextField(20);

		matchCase = new JCheckBox("Gro\u00DF/Kleinschreibung");

		up = new JRadioButton("Hoch");
		down = new JRadioButton("Runter");

		down.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(up);
		bg.add(down);

		direction = new JPanel();
		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched,"Richtung");
		direction.setBorder(titled);
		direction.setLayout(new GridLayout(1, 2));
		direction.add(up);
		direction.add(down);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		southPanel.add(matchCase);
		southPanel.add(direction);


		findNextButton = new JButton("N\u00E4chstes suchen");
		replaceButton = new JButton("Ersetzen");
		replaceAllButton = new JButton("Alles ersetzen");
		cancelButton = new JButton("Abbrechen");

		/*
		findButtonPanel = new JPanel();
		findButtonPanel.setLayout(new GridLayout(2, 1));
		findButtonPanel.add(findNextButton);
		findButtonPanel.add(cancelButton);
		*/
		
		replaceButtonPanel = new JPanel();
		replaceButtonPanel.setLayout(new GridLayout(4, 1));
		replaceButtonPanel.add(findNextButton);
		replaceButtonPanel.add(replaceButton);
		replaceButtonPanel.add(replaceAllButton);
		replaceButtonPanel.add(cancelButton);
		
		/*
		card = new CardLayout();

		buttonPanel = new JPanel();
		buttonPanel.setLayout(card);

		buttonPanel.add(replaceButtonPanel, "replace");
		buttonPanel.add(findButtonPanel, "find");
		card.first(buttonPanel);

		*/

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(3, 2));
		textPanel.add(new JLabel("Was suchen "));
		textPanel.add(findWhat);
		textPanel.add(replaceLabel = new JLabel("Ersetzen mit "));
		textPanel.add(replaceWith);
		textPanel.add(new JLabel(" ")); //dummy Lable
		textPanel.add(new JLabel(" ")); //dummy Lable

		setLayout(new BorderLayout());

		add(new JLabel("       "), BorderLayout.NORTH); //dummy label
		add(textPanel, BorderLayout.CENTER);
		add(replaceButtonPanel, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);

		setSize(200, 200);

		findNextButton.addActionListener(this);
		replaceButton.addActionListener(this);
		replaceAllButton.addActionListener(this);

		cancelButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){dialog.setVisible(false);}});

		findWhat.addFocusListener(new FocusAdapter(){public void focusLost(FocusEvent te){enableDisableButtons();}});
		findWhat.addTextListener(new TextListener(){public void textValueChanged(TextEvent te){enableDisableButtons();}});

	}
	//////////////////////////
	void enableDisableButtons() {
		if(findWhat.getText().length() == 0) {
			findNextButton.setEnabled(false);
			replaceButton.setEnabled(false);
			replaceAllButton.setEnabled(false);
		}
		else {
			findNextButton.setEnabled(true);
			replaceButton.setEnabled(true);
			replaceAllButton.setEnabled(true);
		}
	}
	///////////////////////////////////
	public void actionPerformed(ActionEvent ev) {

		if(ev.getSource() == findNextButton) findNextWithSelection();
		else if(ev.getSource() == replaceButton) replaceNext();
		else if(ev.getSource() == replaceAllButton) JOptionPane.showMessageDialog(null, "Vollst\u00E4ndig ersetzt = "+replaceAllNext());

	}
	/////////////////////////
	int findNext() {

		String s1 = jta.getText();
		String s2 = findWhat.getText();

		lastIndex = jta.getCaretPosition();

		int selStart = jta.getSelectionStart();
		int selEnd = jta.getSelectionEnd();

		if(up.isSelected()) {
			if(selStart != selEnd) lastIndex = selEnd - s2.length() - 1;
			
			/***** FNotepad doesn't use the else part, but it should be, instead of using caretPosition.***
			else
				lastIndex = lastIndex-s2.length();
			******/

			if(!matchCase.isSelected()) lastIndex = s1.toUpperCase().lastIndexOf(s2.toUpperCase(), lastIndex);
			else lastIndex = s1.lastIndexOf(s2, lastIndex);	
		}
		else {
		if(selStart != selEnd)
			lastIndex = selStart + 1;
		if(!matchCase.isSelected())
			lastIndex = s1.toUpperCase().indexOf(s2.toUpperCase(), lastIndex);
		else
			lastIndex = s1.indexOf(s2, lastIndex);	
		}

		return lastIndex;
	}
	///////////////////////////////////////////////
	public void findNextWithSelection() {
		int idx = findNext();
		if(idx != -1) {
			jta.setSelectionStart(idx);
			jta.setSelectionEnd(idx + findWhat.getText().length());
		}
		else JOptionPane.showMessageDialog(this, "Konnte nicht gefunden werden" + " \"" +findWhat.getText()+ "\"", "Suchen", JOptionPane.INFORMATION_MESSAGE);
	}
	//////////////////////////////////////////////
	void replaceNext() {
		// if nothing is selectd
		if(jta.getSelectionStart() == jta.getSelectionEnd()){findNextWithSelection(); return;}

		String searchText = findWhat.getText();
		String temp = jta.getSelectedText();	// get selected text

		// check if the selected text matches the search text then do replacement

		if((matchCase.isSelected() && temp.equals(searchText)) || (!matchCase.isSelected() && temp.equalsIgnoreCase(searchText)))jta.replaceSelection(replaceWith.getText());
		findNextWithSelection();
	}
	//////////////////////////////////////////////
	int replaceAllNext() {
		if(up.isSelected()) jta.setCaretPosition(jta.getText().length() - 1);
		else jta.setCaretPosition(0);
		int idx = 0;
		int counter = 0;
		do {
			idx = findNext();
			if(idx == -1) break;
			counter++;
			jta.replaceRange(replaceWith.getText(), idx, idx + findWhat.getText().length());
		} while(idx != -1);

		return counter;
	}
	//////////////////////////////////////////////
	public boolean showDialog(Component parent, boolean isFind ) {

		Frame owner = null;
		
		if(parent instanceof Frame) owner = (Frame)parent;
		else owner = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, parent);
		
		if(dialog == null || dialog.getOwner() != owner) {
			dialog = new JDialog(owner, false);
			dialog.add(this);
			dialog.getRootPane().setDefaultButton(findNextButton);
		}

		if(findWhat.getText().length() == 0) findNextButton.setEnabled(false);
		else findNextButton.setEnabled(true);

		replaceButton.setVisible(false);
		replaceAllButton.setVisible(false);
		replaceWith.setVisible(false);
		replaceLabel.setVisible(false);

		if(isFind) {
			// card.show(buttonPanel, "find");
			dialog.setSize(460, 180);
			dialog.setTitle("Suchen");
		}
		else {
			replaceButton.setVisible(true);
			replaceAllButton.setVisible(true);
			replaceWith.setVisible(true);
			replaceLabel.setVisible(true);

			// card.show(buttonPanel, "replace");
			dialog.setSize(450, 200);
			dialog.setTitle("Ersetzen");
		}

		dialog.setVisible(true);

		// System.out.println(dialog.getWidth() + " " + dialog.getHeight());
		return ok;
	}
	//////////////////////////////
}
