package LookAndFeelMenu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/***************************************************/
class LookAndFeelDemoIT extends JFrame
{
    JLabel label;
    JMenuBar jmb;
    JMenu fileMenu;

    LookAndFeelDemoIT() {
        super("Cambiare aspetto");
        add(label = new JLabel("Questo è un testo"));
        add(new JButton("Pulsante"));
        add(new JCheckBox("CheckBox"));
        add(new JRadioButton("RadioButton"));
        setLayout(new FlowLayout());
        setSize(350,350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jmb = new JMenuBar();
        setJMenuBar(jmb);
        fileMenu = new JMenu("Cambiare aspetto");
        jmb.add(fileMenu);
        LookAndFeelMenuIT.createLookAndFeelMenuItem(fileMenu, this);
        setVisible(true);
    }
    ////////////////////////

    ///////////////////////
    public static void main(String[] args) {
        new LookAndFeelDemoIT();
    }
    ////////////////////////
}
/************************/
public class LookAndFeelMenuIT {

    public static void createLookAndFeelMenuItem(JMenu jmenu, Component cmp) {
        final UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();

        JRadioButtonMenuItem rbm[] = new JRadioButtonMenuItem[infos.length];
        ButtonGroup bg = new ButtonGroup();
        JMenu tmp = new JMenu("Cambiare aspetto");
        tmp.setMnemonic('C');
        for(int i = 0; i < infos.length; i++) {
            rbm[i] = new JRadioButtonMenuItem(infos[i].getName());
            rbm[i].setMnemonic(infos[i].getName().charAt(0));
            tmp.add(rbm[i]);
            bg.add(rbm[i]);
            rbm[i].addActionListener(new LookAndFeelMenuListenerIT(infos[i].getClassName(),cmp));
        }

        rbm[0].setSelected(true);
        jmenu.add(tmp);

    }

}
/**************************/
class LookAndFeelMenuListenerIT implements ActionListener {
    String classname;
    Component jf;
    /////////////////////
    LookAndFeelMenuListenerIT(String cln, Component jf) {
        this.jf = jf;
        classname = new String(cln);
    }
    /////////////////////
    public void actionPerformed(ActionEvent ev) {
        try {
            UIManager.setLookAndFeel(classname);
            SwingUtilities.updateComponentTreeUI(jf);
        }
        catch(Exception e){System.out.println(e);}
    }
    ///////////////////////////////
}
/*************************/
