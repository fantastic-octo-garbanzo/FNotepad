package NotePads;

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

    public class Splash extends JWindow
    {
        //some private variables
        private Container container;
        private JLabel label;
        private JLabel infoLabel ;
        private JPanel panel;

        public Splash()
        {
            infoLabel = new JLabel("Ladebildschirm");
            URL iconurl = getClass().getResource("/bin/FNotepad.jpg");
            ImageIcon logo = new ImageIcon(iconurl);
            container = this.getContentPane();
            container.setLayout(new BorderLayout());


            label = new JLabel(logo); //this label includes the Splash screen picture

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
            setBounds(30, 80, 200, 300);

        }

        public void showSplash()
        {
            setVisible(true);
        }

        public void updateInfoLabel(String info)
        {
            infoLabel.setText(info);
        }


    }//end of class Splash.java

