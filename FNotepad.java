// Imports
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;

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
        b.setBounds(200,250, 200,50);
		
		// Auswahlmenü
        Choice c = new Choice();
        c.setBounds(250,150, 100,50);
        c.add("English");
        c.add("Deutsch");



        Choice ch = new Choice();
        ch.setBounds(250,200, 100,50);
        ch.add("fullscreen");
        ch.add("windowed");



        add(l);
        add(b);
        add(c);
        add(ch);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(650, 600);

        setLayout(null);
        setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Öffnet Fenster im Vollbild

        c.addItemListener(ie -> {
            // Wenn Deutsch ausgewählt ist, wird alles auf Deutsch gesetzt
            if(c.getSelectedItem().equals("Deutsch")) {
                l.setText("Willkommen im FNotepad!");
                setTitle("FNotepad - Sprache");
                b.setText("Sprache ausw\u00E4hlen");
                ch.removeAll();
                ch.add("Vollbild");
                ch.add("Fenstermodus");
            }
            // Wenn Englisch ausgewählt ist, wird alles auf Englisch gesetzt
            if(c.getSelectedItem().equals("English")) {
                l.setText("Welcome to FNotepad!");
                setTitle("FNotepad - Language");
                b.setText("Choose Language");
                ch.removeAll();
                ch.add("fullscreen");
                ch.add("windowed");
            }
        });

        // Wenn der Auswahl-Button gedrückt wurde
        b.addActionListener(e -> {

            dispose();
            if(c.getItem(c.getSelectedIndex()).equals("Deutsch")){
                if ( ch.getSelectedItem().equals("Vollbild")) {new FNotepadDE(true);}
                if ( ch.getSelectedItem().equals("Fenstermodus")) {new FNotepadDE(false);}

            }
            if(c.getItem(c.getSelectedIndex()).equals("English")){
                if ( ch.getSelectedItem().equals("fullscreen")) {new FNotepadEN(true);}
                if ( ch.getSelectedItem().equals("windowed")) {new FNotepadEN(false);}

            }
        });
    }

    public static void main(String[] args) {
        new FNotepad();
    }
}
