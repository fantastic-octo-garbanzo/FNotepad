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
        infoLabel = new JLabel("FNotepad 1.4.1");
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



        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setBounds(30, 80, 486, 486);
        setLocationRelativeTo(null);
        setEnabled(true);
        setVisible(true);


        JProgressBar progress = new JProgressBar(0, 50);


        progress.setSize(486, 20);


        progress.setValue(0);


        progress.setStringPainted(true);


        panel.add(progress);

        container.add(panel);


        for(int i=0; i<=progress.getMaximum(); i++){
            progress.setValue(i);
            try {
                Thread.sleep(20);
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
