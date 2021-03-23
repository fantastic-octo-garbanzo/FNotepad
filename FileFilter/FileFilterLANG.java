package FileFilter;
// Imports

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import NotePads.FNotepad;

/***************************************************/
class FileFilterDemoLANG extends JFrame {
	JLabel myLabel;
	JButton myButton;

	JFileChooser chooser;

	FileFilterDemoLANG() {
		super("File Filter Demo");
		myLabel = new JLabel("Keine Datei ausgew\u00E4hlt");
		myButton = new JButton("Datei w\u00E4hlen");

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (FileFilterDemoLANG.this.chooser == null) chooser = new JFileChooser();
				chooser.addChoosableFileFilter(new FileFilterLANG(".java", "Java Source Files(*.java)"));
				chooser.addChoosableFileFilter(new FileFilterLANG(".txt", "Text Files(*.txt)"));
				if(chooser.showDialog(FileFilterDemoLANG.this, "Diese Datei ausw\u00E4hlen") == JFileChooser.APPROVE_OPTION) FileFilterDemoLANG.this.myLabel.setText(chooser.getSelectedFile().getPath());
			}
		};

		myButton.addActionListener(listener);

		add(myLabel, "Mitte");
		add(myButton, "Süden");

		setSize(300,300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		FileFilterDemoLANG ffd = new FileFilterDemoLANG();
		ffd.setVisible(true);
	}
}

/***************************************************/
public class FileFilterLANG extends FileFilter {
	private String extension;
	private String description;
	////////////////
	public FileFilterLANG(final String ext, final String desc) {
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
		if(desc == null) description = FNotepad.bundle.getString("FileFilter.AllFiles");
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