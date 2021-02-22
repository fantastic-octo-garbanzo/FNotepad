package FileOperation;
// Imports
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import NotePads.FNP;
import FileFilter.FileFilterFNP;


class FileOperationExample extends JFrame {
	JMenuBar mb;
	JMenu file;
	JMenuItem open;
	JTextArea ta;
	FileOperationExample(){
		open = new JMenuItem("Open File");
		file = new JMenu("File");
		file.add(open);
		mb = new JMenuBar();
		mb.setBounds(0,0,800,20);
		mb.add(file);
		ta = new JTextArea(800,800);
		ta.setBounds(0,20,800,800);
		add(mb);
		add(ta);
	}      
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == open) {
			JFileChooser fc = new JFileChooser();
			int i = fc.showOpenDialog(this);
			if(i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				String filepath = f.getPath();
				try {
					BufferedReader br = new BufferedReader(new FileReader(filepath));
					String s1 = "",s2 = "";
					while((s1 = br.readLine()) != null){
						s2+=s1+"\n";
					}
					ta.setText(s2);
					br.close();
				}catch (Exception ex) {ex.printStackTrace();}
			}
		}
    }
///////////////////////////////////////
    public static void main(String[] args) {
		new FileOperationExample();
	}
}
/*************************************/
// start of class FileOperationEN
public class FileOperation {
    FNP npd;

    public static boolean saved;
    boolean newFileFlag;
    static String fileName;
    String applicationTitle = "FNotepad";

    File fileRef;
    JFileChooser chooser;

    /////////////////////////////
    public static boolean isSave() {
        return saved;
    }

    void setSave(boolean saved) {
        this.saved = saved;
    }

    public static String getFileName() {
        return new String(fileName);
    }

    void setFileName(String fileName) {
        this.fileName = new String(fileName);
    }

    /////////////////////////
    public FileOperation(FNP npd) {
        this.npd = npd;

        saved = true;
        newFileFlag = true;
        fileName = new String("Untitled");
        fileRef = new File(fileName);
        this.npd.f.setTitle(fileName + " - " + applicationTitle);

        // Different file extensions 
        chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileFilterFNP(".java", "Java Source Files(*.java)"));
        chooser.addChoosableFileFilter(new FileFilterFNP(".py", "Python Files(*.py)"));
        chooser.addChoosableFileFilter(new FileFilterFNP(".c", "C Programming Language(.c)"));
		chooser.addChoosableFileFilter(new FileFilterFNP(".cpp", "C++(.cpp)"));
		chooser.addChoosableFileFilter(new FileFilterFNP(".cs", "C#"));
		chooser.addChoosableFileFilter(new FileFilterFNP(".d", "D Programming Language(.d)"));
		chooser.addChoosableFileFilter(new FileFilterFNP(".sh", "Shell Script File(*.sh)"));
		chooser.addChoosableFileFilter(new FileFilterFNP(".bat", "Batch File(*.bat)"));
        chooser.addChoosableFileFilter(new FileFilterFNP(".txt", "Text Files(*.txt)"));
		chooser.addChoosableFileFilter(new FileFilterFNP(".rtf", "Rich Text Format(*.rtf)"));
        chooser.addChoosableFileFilter(new FileFilterFNP(".pdf", "Portable Document Files(*.pdf)"));
        chooser.addChoosableFileFilter(new FileFilterFNP(".html", "Hyper Text Markup Language(*.html)"));
        chooser.addChoosableFileFilter(new FileFilterFNP(".asm", "Assembler(*.asm)"));
		chooser.addChoosableFileFilter(new FileFilterFNP("*", "All Files"));
        chooser.setCurrentDirectory(new File("."));
    }
//////////////////////////////////////

    boolean saveFile(File temp) {
        FileWriter fout = null;
        try {
            fout = new FileWriter(temp);
            fout.write(npd.ta.getText());
        } catch (IOException ioe) {
            updateStatus(temp, false);
            return false;
        } finally {
            try {
                fout.close();
            } catch (IOException excp) {
            }
        }
        updateStatus(temp, true);
        return true;
    }

    ////////////////////////
    public boolean saveThisFile() {

        if (!newFileFlag) {
            return saveFile(fileRef);
        }

        return saveAsFile();
    }

    ////////////////////////////////////
    public boolean saveAsFile() {
        File temp = null;
        chooser.setDialogTitle("Save As...");
        chooser.setApproveButtonText("Save Now");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
        chooser.setApproveButtonToolTipText("Click me to save!");

        do {
            if (chooser.showSaveDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return false;
            temp = chooser.getSelectedFile();
            if (!temp.exists()) break;
            if (JOptionPane.showConfirmDialog(
                    this.npd.f, "<html>" + temp.getPath() + " already exists.<br>Do you want to replace it?<html>",
                    "Save As", JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION)
                break;
        } while (true);


        return saveFile(temp);
    }

    ////////////////////////
    boolean openFile(File temp) {
        FileInputStream fin = null;
        BufferedReader din = null;

        try {
            fin = new FileInputStream(temp);
            din = new BufferedReader(new InputStreamReader(fin));
            String str = " ";
            while (str != null) {
                str = din.readLine();
                if (str == null)
                    break;
                this.npd.ta.append(str + "\n");
            }

        } catch (IOException ioe) {
            updateStatus(temp, false);
            return false;
        } finally {
            try {
                din.close();
                fin.close();
            } catch (IOException excp) {
            }
        }
        updateStatus(temp, true);
        this.npd.ta.setCaretPosition(0);
        return true;
    }

    ///////////////////////
    public void openFile() {
        if (!confirmSave()) return;
        chooser.setDialogTitle("Open File...");
        chooser.setApproveButtonText("Open this");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_O);
        chooser.setApproveButtonToolTipText("Click me to open the selected file.!");

        File temp = null;
        do {
            if (chooser.showOpenDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return;
            temp = chooser.getSelectedFile();

            if (temp.exists()) break;

            JOptionPane.showMessageDialog(this.npd.f,
                    "<html>" + temp.getName() + "<br>file not found.<br>" +
                            "Please verify the correct file name was given.<html>",
                    "Open", JOptionPane.INFORMATION_MESSAGE);

        } while (true);

        this.npd.ta.setText("");

        if (!openFile(temp)) {
            fileName = "Untitled";
            saved = true;
            this.npd.f.setTitle(fileName + " - " + applicationTitle);
        }
        if (!temp.canWrite())
            newFileFlag = true;

    }

    ////////////////////////
    void updateStatus(File temp, boolean saved) {
        if (saved) {
            this.saved = true;
            fileName = new String(temp.getName());
            if (!temp.canWrite()) {
                fileName += "(Read only)";
                newFileFlag = true;
            }
            fileRef = temp;
            npd.f.setTitle(fileName + " - " + applicationTitle);
            npd.statusBar.setText("File : " + temp.getPath() + " saved/opened successfully.");
            newFileFlag = false;
        } else {
            npd.statusBar.setText("Failed to save/open : " + temp.getPath());
        }
    }

    ///////////////////////
    public boolean confirmSave() {
        String strMsg = "<html>The text in the " + fileName + " file has been changed.<br>" +
                "Do you want to save the changes?<html>";
        if (!saved) {
            int x = JOptionPane.showConfirmDialog(this.npd.f, strMsg, applicationTitle, JOptionPane.YES_NO_CANCEL_OPTION);

            if (x == JOptionPane.CANCEL_OPTION) return false;
            if (x == JOptionPane.YES_OPTION && !saveAsFile()) return false;
        }
        return true;
    }

    ///////////////////////////////////////
    public void newFile() {
        if (!confirmSave()) return;
        


        this.npd.ta.setText("");
        fileName = new String("Untitled");
        fileRef = new File(fileName);
        saved = true;
        newFileFlag = true;
        this.npd.f.setTitle(fileName + " - " + applicationTitle);
    }
//////////////////////////////////////
} // end of class FileOperationEN
