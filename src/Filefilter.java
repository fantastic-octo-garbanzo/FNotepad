package src;
// Imports

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;


/***************************************************/
class FileFilterDemo extends JFrame {
    JLabel myLabel;
    JButton myButton;

    JFileChooser chooser;

    FileFilterDemo() {
        super("File Filter Demo");
        myLabel = new JLabel("Keine Datei ausgew\u00E4hlt");
        myButton = new JButton("Datei w\u00E4hlen");

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (FileFilterDemo.this.chooser == null) chooser = new JFileChooser();
                chooser.addChoosableFileFilter(new Filefilter(".java", "Java Source Files(*.java)"));
                chooser.addChoosableFileFilter(new Filefilter(".txt", "Text Files(*.txt)"));
                if (chooser.showDialog(FileFilterDemo.this, "Diese Datei ausw\u00E4hlen") == JFileChooser.APPROVE_OPTION)
                    FileFilterDemo.this.myLabel.setText(chooser.getSelectedFile().getPath());
            }
        };

        myButton.addActionListener(listener);

        add(myLabel, "Mitte");
        add(myButton, "Süden");

        setSize(300, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        FileFilterDemo ffd = new FileFilterDemo();
        ffd.setVisible(true);
    }
}

/***************************************************/
public class Filefilter extends FileFilter {
    private String extension;
    private String description;

    ////////////////
    public Filefilter(final String ext, final String desc) {
        setExtension(ext);
        setDescription(desc);
    }

    ////////////////
    public boolean accept(File f) {
        final String filename = f.getName();

        if (f.isDirectory() || extension == null || filename.toUpperCase().endsWith(extension.toUpperCase()))
            return true;
        return false;
    }

    ////////////////
    public String getDescription() {
        return description;
    }

    ////////////////
    public void setDescription(String desc) {
        if (desc == null) description = FNotepad.bundle.getString("FileFilter.AllFiles");
        else description = new String(desc);
    }

    ////////////////
    public void setExtension(String ext) {
        if (ext == null) {
            extension = null;
            return;
        }
        extension = new String(ext).toLowerCase();
        if (!ext.startsWith(".")) extension = "." + extension;
    }
    ////////////////
}
