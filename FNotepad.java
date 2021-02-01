//Imports
import java.awt.*;
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
    //Constructor
    public FNotepad(){
        setTitle("Language - FNotepad");
        //JLabel l wird erstellt
        JLabel l = new JLabel();
        l.setBounds(225,50, 100,50);
        l.setSize(400,100);

        //JButton b wird erstellt
        JButton b = new JButton("Choose Language");
        b.setBounds(200,250, 200,50);

        //Choice c wird erstellt
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

        //l und b werden beschrieben
        l.setText("Welcome to FNotepad!");
        b.setText("Choose Language");
        //wenn sich der Status von c ändert wird der text geändert
        c.addItemListener(ie -> {
            if(c.getSelectedItem().equals("Deutsch")) {
                l.setText("Willkommen im FNotepad!");
                setTitle("FNotepad - Sprache");
                b.setText("Sprache auswählen");
            }
            if(c.getSelectedItem().equals("English")) {
                l.setText("Welcome to FNotepad!");
                setTitle("FNotepad - Language");
                b.setText("Choose Language");
            }
        });

        //jenachdem was ausgewählt ist, wird FNotepadDE oder FNotepadEN gestartet.
        b.addActionListener(e -> {
            dispose();
            if(c.getItem(c.getSelectedIndex()).equals("Deutsch")){
                new FNotepadDE(true);
            }
            if(c.getItem(c.getSelectedIndex()).equals("English")){
                new FNotepadEN(true);
            }
        });
    }

    //Main methode
    public static void main(String[] args) {
        new FNotepad();
    }
}
