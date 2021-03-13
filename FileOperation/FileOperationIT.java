package FileOperation;
// Imports
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import NotePads.FNotepadIT;
import FileFilter.FileFilterIT;


class FileOperationExampleIT extends JFrame {
    JMenuBar mb;
    JMenu file;
    JMenuItem open;
    JTextArea ta;
    FileOperationExampleIT(){
        open = new JMenuItem("Aprire il file");
        file = new JMenu("file");
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
        new FileOperationExampleIT();
    }
}
/*************************************/
// start of class FileOperationEN
public class FileOperationIT {
    FNotepadIT npd;

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
    public FileOperationIT(FNotepadIT npd) {
        this.npd = npd;

        saved = true;
        newFileFlag = true;
        fileName = new String("Senza titolo");
        fileRef = new File(fileName);
        this.npd.f.setTitle(fileName + " - " + applicationTitle);

        // Different file extensions
        chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileFilterIT("*", "All Files"));
        chooser.addChoosableFileFilter(new FileFilterIT(".txt", "Text Files(*.txt)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".java", "Java Source Files(*.java)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".py", "Python Files(*.py)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".c", "C Programming Language(.c)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".cpp", "C++(.cpp)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".cs", "C#"));
        chooser.addChoosableFileFilter(new FileFilterIT(".d", "D Programming Language(.d)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".sh", "Shell Script File(*.sh)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".bat", "Batch File(*.bat)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".rtf", "Rich Text Format(*.rtf)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".pdf", "Portable Document Files(*.pdf)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".html", "Hyper Text Markup Language(*.html)"));
        chooser.addChoosableFileFilter(new FileFilterIT(".asm", "Assembler(*.asm)"));
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
        chooser.setDialogTitle("Salva con nome...");
        chooser.setApproveButtonText("Salva ora");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
        chooser.setApproveButtonToolTipText("Clicca su di me per salvare!");

        do {
            if (chooser.showSaveDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return false;
            temp = chooser.getSelectedFile();
            if (!temp.exists()) break;
            if (JOptionPane.showConfirmDialog(
                    this.npd.f, "<html>" + temp.getPath() + " esiste già.<br>Vuoi sostituirlo?<html>",
                    "Salva con nome", JOptionPane.YES_NO_OPTION
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
        chooser.setDialogTitle("Aprire il file...");
        chooser.setApproveButtonText("Aprire questo");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_O);
        chooser.setApproveButtonToolTipText("Clicca su me per aprire il file selezionato.!");

        File temp = null;
        do {
            if (chooser.showOpenDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return;
            temp = chooser.getSelectedFile();

            if (temp.exists()) break;

            JOptionPane.showMessageDialog(this.npd.f,
                    "<html>" + temp.getName() + "<br>file non trovato.<br>" +
                            "Si prega di verificare che il nome del file sia corretto.<html>",
                    "Aprire", JOptionPane.INFORMATION_MESSAGE);

        } while (true);

        this.npd.ta.setText("");

        if (!openFile(temp)) {
            fileName = "Senza titolo";
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
                fileName += "(Leggere solo)";
                newFileFlag = true;
            }
            fileRef = temp;
            npd.f.setTitle(fileName + " - " + applicationTitle);
            npd.statusBar.setText("file : " + temp.getPath() + " salvato/aperto con successo.");
            newFileFlag = false;
        } else {
            npd.statusBar.setText("Impossibile salvare/aprire : " + temp.getPath());
        }
    }

    ///////////////////////
    public boolean confirmSave() {
        String strMsg = "<html>Il testo nel " + fileName + " è stato cambiato.<br>" +
                "Vuoi salvare le modifiche?<html>";
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
        fileName = new String("Senza titolo");
        fileRef = new File(fileName);
        saved = true;
        newFileFlag = true;
        this.npd.f.setTitle(fileName + " - " + applicationTitle);
    }
//////////////////////////////////////
} // end of class FileOperationEN