package FileOperation;
// Imports
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import FileFilter.*;
import NotePads.FNotepad;

class FileOperationExampleLANG extends JFrame {
    JMenuBar mb;
    JMenu file;
    JMenuItem open;
    JTextArea ta;
    FileOperationExampleLANG(){
        open = new JMenuItem("\u00D6ffne Datei");
        file = new JMenu("Datei");
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
        new FileOperationExampleLANG();
    }
}
/*************************************/
// Beginn der Klasse FileOperationDE
public class FileOperationLANG {
    FNotepad npd;

    public static boolean saved;
    boolean newFileFlag;
    static String fileName;
    String applicationTitle = this.npd.bundle.getString("applicationName");

    File fileRef;
    JFileChooser chooser;
    File temp = null;
    FileWriter fout = null;
    FileInputStream fin = null;
    BufferedReader din = null;
    /////////////////////////////
    public static boolean isSave() {
        return saved;
    }
    /////////////////////////////
    void setSave(boolean saved) {
        this.saved = saved;
    }
    /////////////////////////////
    public static String getFileName() {
        return new String(fileName);
    }
    /////////////////////////////
    void setFileName(String fileName) {
        this.fileName = new String(fileName);
    }
    /////////////////////////
    public FileOperationLANG(FNotepad npd) {
        this.npd = npd;

        saved = true;
        newFileFlag = true;
        fileName = this.npd.bundle.getString("fileName");
        fileRef = new File(fileName);
        this.npd.f.setTitle(fileName + " - " + applicationTitle);

        // Verschiedene Dateiendungen
        chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileFilterLANG("*", this.npd.bundle.getString("FileOperation.FileFilter")));
        chooser.addChoosableFileFilter(new FileFilterLANG(".txt", "Text Files(*.txt)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".java", "Java Source Files(*.java)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".py", "Python Files(*.py)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".c", "C Programming Language(*.c)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".cpp", "C++(*.cpp)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".cs", "C#(*.cs)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".d", "D Programming Language(*.d)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".sh", "Shell Script File(*.sh)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".bat", "Batch File(*.bat)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".rtf", "Rich Text Format(*.rtf)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".pdf", "Portable Document Files(*.pdf)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".html", "Hyper Text Markup Language(*.html)"));
        chooser.addChoosableFileFilter(new FileFilterLANG(".asm", "Assembler(*.asm)"));
        chooser.setCurrentDirectory(new File("."));
    }
    //////////////////////////////////////
    boolean saveFile(File temp) {
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
    //////////////////////////////////////
    public boolean saveThisFile() {
        if (!newFileFlag) {
            return saveFile(fileRef);
        }
        return saveAsFile();
    }
    //////////////////////////////////////
    public boolean saveAsFile() {
        chooser.setDialogTitle(this.npd.bundle.getString("FileOperation.save1"));
        chooser.setApproveButtonText(this.npd.bundle.getString("FileOperation.save2"));
        chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
        chooser.setApproveButtonToolTipText(this.npd.bundle.getString("FileOperation.save3"));
        do {
            if (chooser.showSaveDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return false;
            temp = chooser.getSelectedFile();
            if (!temp.exists()) break;
            if (JOptionPane.showConfirmDialog(
                    this.npd.f, "<html>" + temp.getPath() + this.npd.bundle.getString("FileOperation.save4"),
                    this.npd.bundle.getString("FileOperation.save5"), JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION)
                break;
        } while (true);
        return saveFile(temp);
    }
    //////////////////////////////////////
    boolean openFile(File temp) {
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
    //////////////////////////////////////
    public void openFile() {
        if (!confirmSave()) return;
        chooser.setDialogTitle(this.npd.bundle.getString("FileOperation.open1"));
        chooser.setApproveButtonText(this.npd.bundle.getString("FileOperation.open2"));
        chooser.setApproveButtonMnemonic(KeyEvent.VK_O);
        chooser.setApproveButtonToolTipText(this.npd.bundle.getString("FileOperation.open3"));

        do {
            if (chooser.showOpenDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return;
            temp = chooser.getSelectedFile();

            if (temp.exists()) break;

            JOptionPane.showMessageDialog(this.npd.f,
                    "<html>" + temp.getName() + this.npd.bundle.getString("FileOperation.open4"),
                    this.npd.bundle.getString("FileOperation.open5"), JOptionPane.INFORMATION_MESSAGE);

        } while (true);

        this.npd.ta.setText("");

        if (!openFile(temp)) {
            fileName = this.npd.bundle.getString("fileName");
            saved = true;
            this.npd.f.setTitle(fileName + " - " + applicationTitle);
        }
        if (!temp.canWrite())
            newFileFlag = true;

    }
    //////////////////////////////////////
    void updateStatus(File temp, boolean saved) {
        if (saved) {
            this.saved = true;
            fileName = new String(temp.getName());
            if (!temp.canWrite()) {
                fileName += this.npd.bundle.getString("FileOperation.readonly");
                newFileFlag = true;
            }
            fileRef = temp;
            npd.f.setTitle(fileName + " - " + applicationTitle);
            npd.statusBar.setText(this.npd.bundle.getString("FileOperation.readonly1"));
            newFileFlag = false;
        } else {
            npd.statusBar.setText(this.npd.bundle.getString("FileOperation.readonly2") + temp.getPath());
        }
    }
    //////////////////////////////////////
    public boolean confirmSave() {
        String strMsg = this.npd.bundle.getString("FileOperation.confirmSave1") + fileName + this.npd.bundle.getString("FileOperation.confirmSave2");
        if (!saved) {
            int x = JOptionPane.showConfirmDialog(this.npd.f, strMsg, applicationTitle, JOptionPane.YES_NO_CANCEL_OPTION);

            if (x == JOptionPane.CANCEL_OPTION) return false;
            if (x == JOptionPane.YES_OPTION && !saveAsFile()) return false;
        }
        return true;
    }
    //////////////////////////////////////
    public void newFile() {
        if (!confirmSave()) return;
        this.npd.ta.setText("");
        fileName = this.npd.bundle.getString("fileName");
        fileRef = new File(fileName);
        saved = true;
        newFileFlag = true;
        this.npd.f.setTitle(fileName + " - " + applicationTitle);
    }
//////////////////////////////////////
} // Ende der Klasse FileOperationDE