package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Splash extends JWindow {

    public Container container;
    public JLabel label;
    public JLabel infoLabel ;
    public JPanel panel;

    public Splash()
    {
        infoLabel = new JLabel("Ladebildschirm");
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
        setEnabled(true);
        setVisible(true);
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
