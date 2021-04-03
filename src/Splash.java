package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.*;

public class Splash extends JWindow {

    public Container container;
    public JLabel label;
    public JLabel infoLabel ;
    public JPanel panel;

    public Splash()
    {
        infoLabel = new JLabel("FNotepad");
        URL iconurl = getClass().getResource("/bin/FNotepad.jpg");
        ImageIcon logo = new ImageIcon(iconurl);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());


        label = new JLabel(logo);

        panel = new JPanel(new BorderLayout());

        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        panel.add(label, BorderLayout.CENTER);
        panel.add(infoLabel, BorderLayout.SOUTH) ;

        container.add(panel);

        pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setBounds(30, 80, 800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        // JProgressBar-Objekt wird erzeugt
        JProgressBar progress = new JProgressBar(0, 100);

        //Größe für JProgressBar wird festgelegt
        progress.setSize(800, 20);

        // Wert für den Ladebalken wird gesetzt
        progress.setValue(0);

        // Der aktuelle Wert wird als
        // Text in Prozent angezeigt
        progress.setStringPainted(true);

        // JProgressBar wird Panel hinzugefügt
        panel.add(progress);

        container.add(panel);

        // Wert des Ladebalkens wird in der Schleife
        // bei jedem Durchgang um 1 erhöht bis der
        // maximale Wert erreicht ist
        for(int i=0; i<=progress.getMaximum(); i++){
            progress.setValue(i);
            try {
                // aktueller Thread pausiert
                // für 50 Millisekunden
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void showSplash()
    {
        setVisible(true);
        dispose();
    }

    public void updateInfoLabel(String info)
    {
        infoLabel.setText(info);
    }

}
