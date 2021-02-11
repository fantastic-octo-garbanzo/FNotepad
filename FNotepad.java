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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(getScreenDimensionWithoutTaskbar(this));

        setLayout(null);
        setVisible(true);

        l.setText("Welcome to FNotepad!");
        b.setText("Choose Language");
        c.addItemListener(ie -> {
            // Wenn Deutsch ausgewählt ist, wird alles auf Deutsch gesetzt
            if(c.getSelectedItem().equals("Deutsch")) {
                l.setText("Willkommen im FNotepad!");
                setTitle("FNotepad - Sprache");
                b.setText("Sprache ausw\u00E4hlen");
            }
            // Wenn Englisch ausgewählt ist, wird alles auf Englisch gesetzt
            if(c.getSelectedItem().equals("English")) {
                l.setText("Welcome to FNotepad!");
                setTitle("FNotepad - Language");
                b.setText("Choose Language");
            }
        });

        // Wenn der Auswahl-Button gedrückt wurde
        b.addActionListener(e -> {
            dispose(); // Schließt das Sprachauswahlfenster
            if(c.getItem(c.getSelectedIndex()).equals("Deutsch")){ // Wenn die Sprache Deutsch ausgewählt ist ...
                new FNotepadDE(true); // wird das FNotepad auf Deutsch gestartet
            }
            if(c.getItem(c.getSelectedIndex()).equals("English")){ // Wenn die Sprache Englisch ausgewählt ist ...
                new FNotepadEN(true); // wird das FNotepad auf Englisch gestartet
            }
        });
    }

    public static void main(String[] args) {
        new FNotepad();
    }
}
