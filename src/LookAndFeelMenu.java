package src;
// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***************************************************/
class LookAndFeelDemo extends JFrame {
  JLabel label;
  JMenuBar jmb;
  JMenu fileMenu;

  LookAndFeelDemo() {
    super("Aussehen \u00E4ndern");
    add(label = new JLabel("Das ist ein Text"));
    add(new JButton("Button")); 
    add(new JCheckBox("CheckBox"));
    add(new JRadioButton("RadioButton"));
    setLayout(new FlowLayout());
    setSize(350,350);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jmb = new JMenuBar();
    setJMenuBar(jmb);
    fileMenu = new JMenu("Aussehen \u00E4ndern");
    jmb.add(fileMenu);
    LookAndFeelMenu.createLookAndFeelMenuItem(fileMenu, this);
    setVisible(true);
  }
  ////////////////////////
  public static void main(String[] args) {
    new LookAndFeelDemo();
  }
  ////////////////////////
}

/************************/
public class LookAndFeelMenu {
  public static void createLookAndFeelMenuItem(JMenu jmenu, Component cmp) {
    final UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();

    JRadioButtonMenuItem rbm[] = new JRadioButtonMenuItem[infos.length];
    ButtonGroup bg = new ButtonGroup();
    JMenu tmp = new JMenu(FNotepad.bundle.getString("LookandFeel"));
    tmp.setMnemonic('C');
    for(int i = 0; i < infos.length; i++) {
      rbm[i] = new JRadioButtonMenuItem(infos[i].getName());
      rbm[i].setMnemonic(infos[i].getName().charAt(0));
      tmp.add(rbm[i]);
      bg.add(rbm[i]);
      rbm[i].addActionListener(new LookAndFeelMenuListener(infos[i].getClassName(),cmp));
    }
    rbm[0].setSelected(true);
    jmenu.add(tmp);
  }
}

/**************************/
class LookAndFeelMenuListener implements ActionListener {
  String classname;
  Component jf;
  /////////////////////
  LookAndFeelMenuListener(String cln, Component jf) {
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