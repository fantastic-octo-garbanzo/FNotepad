package src;
// Imports
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.util.*;

/**********/

// Begin of class LangSelect
public class LangSelect extends JFrame {

    public LangSelect(){

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch(Exception e) {
                System.out.println("Setting Look and Feel Failed");  
            }
            
        setTitle("Language - FNotepad 1.4.2");
        JLabel l = new JLabel("Welcome to FNotepad");
        l.setFont(new Font("Serif", Font.ITALIC, 35));
        l.setBounds(500,50, 500,100);
        l.setSize(500,100);

        URL iconURL = getClass().getResource("/bin/FNotepad.jpg");
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());

        // choose language button
        JButton b = new JButton("Choose Language");
        b.setBounds(550,300, 200,50);

        // cancel button
        JButton a = new JButton("Cancel");
        a.setBounds(550, 375, 200, 50);

        // Menu
        String[] languagesList = {"English", "Deutsch", "Italiano", "Fran\u00E7ais"};
        String[] windowList = {"fullscreen", "windowed"};


        JComboBox c = new JComboBox(languagesList);
        c.setBounds(550,150, 200,50);

        JComboBox ch = new JComboBox(windowList);
        ch.setBounds(550,225, 200,50);

        add(l);
        add(a);
        add(b);
        add(c);
        add(ch);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setSize(650, 600);

        setLayout(null);
        setVisible(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        c.addItemListener(ie -> {
            if(c.getSelectedItem().equals("Deutsch")) {
                l.setText("Willkommen im FNotepad");
                setTitle("Sprache - FNotepad 1.4.2");
                b.setText("Sprache ausw\u00E4hlen");
                a.setText("Abbrechen");
                ch.removeAllItems();
                ch.addItem("Vollbild");
                ch.addItem("Fenstermodus");
            }
            if(c.getSelectedItem().equals("Italiano")) {
                l.setText("Benvenuto in FNotepad");
                setTitle("Lingua - FNotepad 1.4.2");
                b.setText("seleziona la tua lingua");
                a.setText("Interrompi");
                ch.removeAllItems();
                ch.addItem("a schermo intero");
                ch.addItem("finestrato");
            }
            if(c.getSelectedItem().equals("Fran\u00E7ais")) {
                l.setText("Benvenuto in FNotepad");
                setTitle("Langue - FNotepad 1.4.2");
                b.setText("sélectionnez votre langue");
                a.setText("Stop");
                ch.removeAllItems();
                ch.addItem("Plein écran");
                ch.addItem("Mode fenêtre");
            }
            if(c.getSelectedItem().equals("English")) {
                l.setText("Welcome to FNotepad");
                setTitle("Language - FNotepad 1.4.2");
                b.setText("Choose Language");
                a.setText("Cancel");
                ch.removeAllItems();
                ch.addItem("fullscreen");
                ch.addItem("windowed");
            }
        });

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

        a.addActionListener((ActionEvent actionEvent) -> {
            System.exit(0);
        });
    }
    public static void main(String[] args) {
        new Splash().showSplash();
        new LangSelect();
    }
}
