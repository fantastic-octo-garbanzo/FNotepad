package src;
// Imports
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Locale;

/**********/

// Beginn der Klasse FNotepad
public class LangSelect extends JFrame{

    // Holt die Bildschrimgröße ohne Taskbar
    public static Dimension getScreenDimensionWithoutTaskbar(Frame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        int taskBarSize = screenInsets.bottom;
        return new Dimension(width, height - taskBarSize);
    }

    public LangSelect(){
        setTitle("Language - FNotepad"); // Titel
        JLabel l = new JLabel("Welcome to FNotepad"); // Text über Auswahlmenü
        l.setBounds(225,50, 100,50);
        l.setSize(400,100);


        URL iconURL = getClass().getResource("/bin/FNotepad.jpg");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());

        // Button zur Auswahlbestätigung
        JButton b = new JButton("Choose Language");
        b.setBounds(200,300, 200,50);

        // Button zum Abbrechen
        JButton a = new JButton("Cancel");
        a.setBounds(200, 375, 200, 50);

        // Auswahlmenü
        String[] languagesList = {"English", "Deutsch", "Italiano", "Fran\u00E7ais"};
        String[] windowList = {"fullscreen", "windowed"};


        JComboBox c = new JComboBox(languagesList);
        c.setBounds(250,150, 100,50);

        JComboBox ch = new JComboBox(windowList);
        ch.setBounds(250,225, 100,50);

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
                ch.removeAllItems();
                ch.addItem("Vollbild");
                ch.addItem("Fenstermodus");
            }
            //Wenn Italienisch ausgewählt ist, wird alles auf Italienisch gesetzt
            if(c.getSelectedItem().equals("Italiano")) {
                l.setText("Benvenuto in FNotepad!");
                setTitle("FNotepad - Lingua");
                b.setText("seleziona la tua lingua");
                a.setText("Interrompi");
                ch.removeAllItems();
                ch.addItem("a schermo intero");
                ch.addItem("finestrato");
            }
            //Wenn Französisch ausgewählt ist, wird alles auf Französisch gesetzt
            if(c.getSelectedItem().equals("Fran\u00E7ais")) {
                l.setText("Benvenuto in FNotepad!");
                setTitle("FNotepad - Langue");
                b.setText("sélectionnez votre langue");
                a.setText("Stop");
                ch.removeAllItems();
                ch.addItem("Plein écran");
                ch.addItem("Mode fenêtre");
            }
            // Wenn Englisch ausgewählt ist, wird alles auf Englisch gesetzt
            if(c.getSelectedItem().equals("English")) {
                l.setText("Welcome to FNotepad!");
                setTitle("FNotepad - Language");
                b.setText("Choose Language");
                a.setText("Cancel");
                ch.removeAllItems();
                ch.addItem("fullscreen");
                ch.addItem("windowed");
            }
            });


        // Wenn der Auswahl-Button gedrückt wurde
        b.addActionListener(e -> {
            if(c.getSelectedItem().equals("Deutsch")){
                if (ch.getSelectedItem().equals("Vollbild")) {new FNotepad(true, Locale.GERMAN);}
                if (ch.getSelectedItem().equals("Fenstermodus")) {new FNotepad(false, Locale.GERMAN);}
            }
            if(c.getSelectedItem().equals("Italiano")){
                if (ch.getSelectedItem().equals("a schermo intero")) {new FNotepad(true, Locale.ITALIAN);}
                if (ch.getSelectedItem().equals("finestrato")) {new FNotepad(false, Locale.ITALIAN);}
            }
            if(c.getSelectedItem().equals("Fran\u00E7ais")){
                if (ch.getSelectedItem().equals("Plein écran")) {new FNotepad(true, Locale.FRENCH);}
                if (ch.getSelectedItem().equals("Mode fenêtre")) {new FNotepad(false, Locale.FRENCH);}
            }
            if(c.getSelectedItem().equals("English")){
                if (ch.getSelectedItem().equals("fullscreen")) {new FNotepad(true, Locale.ENGLISH);}
                if (ch.getSelectedItem().equals("windowed")) {new FNotepad(false, Locale.ENGLISH);}
            }

            dispose();
        });
        a.addActionListener((ActionEvent actionEvent) ->
        {
            System.exit(0);
            setVisible(false);
        });

    }

    public static void main(String[] args) {
        new LangSelect();
    }
}