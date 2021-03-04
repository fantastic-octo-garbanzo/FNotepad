package FileFilter;

import java.io.File;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/***************************************************/
class FileFilterDemoIT extends JFrame {
    JLabel myLabel;
    JButton myButton;

    JFileChooser chooser;

    FileFilterDemoIT() {
        super("File Filter Demo");
        myLabel = new JLabel("Nessun file selezionato");
        myButton = new JButton("Selezionare il file");

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (FileFilterDemoIT.this.chooser == null) chooser = new JFileChooser();
                chooser.addChoosableFileFilter(new FileFilterDE(".java", "Java Source Files(*.java)"));
                chooser.addChoosableFileFilter(new FileFilterDE(".txt", "Text Files(*.txt)"));
                if(chooser.showDialog(FileFilterDemoIT.this, "Seleziona questo file") == JFileChooser.APPROVE_OPTION) FileFilterDemoIT.this.myLabel.setText(chooser.getSelectedFile().getPath());
            }
        };

        myButton.addActionListener(listener);

        add(myLabel, "Medio");
        add(myButton, "Sud");

        setSize(300,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        FileFilterDemoDE ffd = new FileFilterDemoDE();
        ffd.setVisible(true);
    }
}
/***************************************************/
public class FileFilterIT extends FileFilter {
    private String extension;
    private String description;
    ////////////////
    public FileFilterIT() {
        setExtension(null);
        setDescription(null);
    }
    ////////////////
    public FileFilterIT(final String ext, final String desc) {
        setExtension(ext);
        setDescription(desc);
    }
    ////////////////
    public boolean accept(File f) {
        final String filename=f.getName();

        if(	f.isDirectory() || extension == null || filename.toUpperCase().endsWith(extension.toUpperCase())) return true;
        return false;
    }
    ////////////////
    public String getDescription() {
        return description;
    }
    ////////////////
    public void setDescription(String desc) {
        if(desc == null) description = new String("Tutti i file(*.*)");
        else description = new String(desc);
    }
    ////////////////
    public void setExtension(String ext) {
        if(ext == null) {extension = null;  return;}
        extension = new String(ext).toLowerCase();
        if(!ext.startsWith(".")) extension = "." +extension;
    }
    ////////////////
}
/***************************************************/
