import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;


public class FNotepad extends JFrame{
	public FNotepad(){
		setTitle("Language - FNotepad");
		addWindowListener(new WindowListener());
		JLabel l = new JLabel();
		l.setBounds(225,50, 100,50);
		l.setSize(400,100);
		
		JButton b = new JButton("Choose Language");
		b.setBounds(200,250, 200,50);
		
		Choice c = new Choice();
		c.setBounds(250,150, 100,50);
		c.add("English");
		c.add("Deutsch");
		
		add(l);
		add(b);
		add(c);
		
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		setLayout(null);
		setVisible(true);
		
		c.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
				if(c.getSelectedItem() == "Deutsch") {
					l.setText("Willkommen im FNotepad!");
					setTitle("FNotepad - Sprache");
					b.setText("Sprache auswählen");
				}
				if(c.getSelectedItem() == "English"){
					l.setText("Welcome to FNotepad!");
					setTitle("FNotepad - Language");
					b.setText("Choose Language");
				}
			}
		});
		
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(c.getItem(c.getSelectedIndex()) == "Deutsch"){
					new FNotepadDE();
				}
				if(c.getItem(c.getSelectedIndex()) == "English"){
					new FNotepadEN();
				}
			}
		});
	}
    
	class WindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e){
			e.getWindow().dispose();
			System.exit(0);
		}
	}
     
	public static void main(String args[]) {    
		new FNotepad();    
	}    
}
