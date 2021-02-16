package NotePads;
// Imports

import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
/**********/

// Beginn der Klasse FNotepad
public class FNotepad extends JFrame{

    // Holt die Bildschrimgröße ohne Taskbar
    public static Dimension getScreenDimensionWithoutTaskbar(Frame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        int taskBarSize = screenInsets.bottom;
        return new Dimension(width, height - taskBarSize);
    }

    public FNotepad(){
        setTitle("Language - FNotepad"); // Titel
        JLabel l = new JLabel("Welcome to FNotepad"); // Text über Auswahlmenü
        l.setBounds(225,50, 100,50);
        l.setSize(400,100);
        
		// Button zur Auswahlbestätigung
        JButton b = new JButton("Choose Language");
        b.setBounds(200,300, 200,50);
     
		// Button zum Abbrechen
		JButton a = new JButton("Cancel");
		a.setBounds(200, 375, 200, 50);
		
		// Auswahlmenü
        String languagesList[] = {"English", "Deutsch"};


        JComboBox c = new JComboBox(languagesList);
        c.setBounds(250,150, 100,50);



        Choice ch = new Choice();
        ch.setBounds(250,225, 100,50);
        ch.add("fullscreen");
        ch.add("windowed");

        add(l);
		add(a);
		add(b);
        add(c);
        add(ch);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(650, 600);

        setLayout(null);
        setVisible(true);

        
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Öffnet Fenster im Vollbild

        c.addItemListener(ie -> {
            // Wenn Deutsch ausgewählt ist, wird alles auf Deutsch gesetzt
            if(c.getSelectedItem().equals("Deutsch")) {
                l.setText("Willkommen im FNotepad!");
                setTitle("FNotepad - Sprache");
                b.setText("Sprache ausw\u00E4hlen");
				a.setText("Abbrechen");
				ch.removeAll();
                ch.add("Vollbild");
                ch.add("Fenstermodus");
            }
            // Wenn Englisch ausgewählt ist, wird alles auf Englisch gesetzt
            if(c.getSelectedItem().equals("English")) {
                l.setText("Welcome to FNotepad!");
                setTitle("FNotepad - Language");
                b.setText("Choose Language");
				a.setText("Cancel");
				ch.removeAll();
                ch.add("fullscreen");
                ch.add("windowed");
            }
        });

        // Wenn der Auswahl-Button gedrückt wurde
        b.addActionListener(e -> {

            dispose();
            if(c.getSelectedItem().equals("Deutsch")){
                if ( ch.getSelectedItem().equals("Vollbild")) {new FNotepadDE(true);}
                if ( ch.getSelectedItem().equals("Fenstermodus")) {new FNotepadDE(false);}

            }
            if(c.getSelectedItem().equals("English")){
                if ( ch.getSelectedItem().equals("fullscreen")) {new FNotepadEN(true);}
                if ( ch.getSelectedItem().equals("windowed")) {new FNotepadEN(false);}

            }
        });
		a.addActionListener((ActionEvent actionEvent) ->
		{
			System.exit(0);
			setVisible(false);
		});
		
	}

    public static void main(String[] args) {
        new FNotepad();
    }
}