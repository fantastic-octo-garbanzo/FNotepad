import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;


public class FNotepad extends JFrame{

	public static Dimension getScreenDimensionWithoutTaskbar(Frame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		int taskBarSize = screenInsets.bottom;
		return new Dimension(width, height - taskBarSize);
	}

	public FNotepad(){
		setTitle("Language - FNotepad");
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


		setSize(getScreenDimensionWithoutTaskbar(this));

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
                dispose();
				if(c.getItem(c.getSelectedIndex()) == "Deutsch"){
					new FNotepadDE(true);
				}
				if(c.getItem(c.getSelectedIndex()) == "English"){
					new FNotepadEN(true);
				}
			}
		});
	}
         
	public static void main(String args[]) {    
		new FNotepad();
	}    
}
