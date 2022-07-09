package src;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class Splash extends JWindow {

    public Container container;
    public JLabel label;
    public JLabel infoLabel;
    public JPanel panel;

    public Splash() {
        infoLabel = new JLabel("FNotepad 1.4.2");
        URL iconurl = getClass().getResource("/bin/FNotepad.jpg");
        ImageIcon logo = new ImageIcon(iconurl);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());

        label = new JLabel(logo);

        panel = new JPanel(new BorderLayout());

        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        panel.add(label, BorderLayout.CENTER);
        panel.add(infoLabel, BorderLayout.SOUTH);

        container.add(panel);


        setBounds(30, 80, 486, 486);
        setLocationRelativeTo(null);
        setVisible(true);


        JProgressBar progress = new JProgressBar(0, 100);


        progress.setSize(486, 20);


        progress.setValue(0);


        progress.setStringPainted(true);


        panel.add(progress);

        container.add(panel);


        for (int i = 0; i <= progress.getMaximum(); i++) {
            progress.setValue(i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void showSplash() {
        setVisible(true);
        dispose();
    }

    public void updateInfoLabel(String info) {
        infoLabel.setText(info);
    }

}
