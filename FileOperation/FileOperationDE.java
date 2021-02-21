package FileOperation;
// Imports
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import FileFilter.*;
import FindDialog.*;
import FontChooser.*;
import LookAndFeelMenu.*;
import NotePads.FNotepadDE;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

class FileOperationExampleDE extends JFrame {
	JMenuBar mb;
	JMenu file;
	JMenuItem open;
	JTextArea ta;
	FileOperationExampleDE(){
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
		new FileOperationExampleDE();
	}
}
/*************************************/
// Beginn der Klasse FileOperationDE
public class FileOperationDE {
    FNotepadDE npd;

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
    public FileOperationDE(FNotepadDE npd) {
        this.npd = npd;

        saved = true;
        newFileFlag = true;
        fileName = new String("Unbenannt");
        fileRef = new File(fileName);
        this.npd.f.setTitle(fileName + " - " + applicationTitle);

		// Verschiedene Dateiendungen
        chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileFilterEN("*", "Alle Dateien"));
        chooser.addChoosableFileFilter(new FileFilterDE(".txt", "Text Files(*.txt)"));
        chooser.addChoosableFileFilter(new FileFilterDE(".java", "Java Source Files(*.java)"));
        chooser.addChoosableFileFilter(new FileFilterDE(".py", "Python Files(*.py)"));
        chooser.addChoosableFileFilter(new FileFilterDE(".c", "C Programming Language(.c)"));
		chooser.addChoosableFileFilter(new FileFilterDE(".cpp", "C++(.cpp)"));
		chooser.addChoosableFileFilter(new FileFilterDE(".cs", "C#"));
		chooser.addChoosableFileFilter(new FileFilterDE(".d", "D Programming Language(.d)"));
		chooser.addChoosableFileFilter(new FileFilterDE(".sh", "Shell Script File(*.sh)"));
		chooser.addChoosableFileFilter(new FileFilterDE(".bat", "Batch File(*.bat)"));
		chooser.addChoosableFileFilter(new FileFilterDE(".rtf", "Rich Text Format(*.rtf)"));
        chooser.addChoosableFileFilter(new FileFilterDE(".pdf", "Portable Document Files(*.pdf)"));
        chooser.addChoosableFileFilter(new FileFilterDE(".html", "Hyper Text Markup Language(*.html)"));
        chooser.addChoosableFileFilter(new FileFilterDE(".asm", "Assembler(*.asm)"));
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
        chooser.setDialogTitle("Speichern als...");
        chooser.setApproveButtonText("Jetzt speichern");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
        chooser.setApproveButtonToolTipText("Hier speichern!");

        do {
            if (chooser.showSaveDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return false;
            temp = chooser.getSelectedFile();
            if (!temp.exists()) break;
            if (JOptionPane.showConfirmDialog(
                    this.npd.f, "<html>" + temp.getPath() + " existiert schon.<br>Ersetzen?<html>",
                    "Speichern", JOptionPane.YES_NO_OPTION
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
    public void exportTxtToPDF() throws IOException {
        chooser.setDialogTitle("Datei als PDF exportieren");
        chooser.setApproveButtonText("Exportieren");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_E);
        chooser.setApproveButtonToolTipText("Ausgew\u00E4hlte Datei exportieren.");

        do {
            if (chooser.showSaveDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return;

            File sourceFile = chooser.getSelectedFile();
            String dest = sourceFile.getPath().replace(".txt", ".pdf");
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest));

            Document document = new Document(pdfDocument);
            document.setTextAlignment(TextAlignment.LEFT);
            document.setFontSize((float) 8.0);
            document.setLeftMargin((float) 40.0);
            document.setRightMargin((float) 40.0);

            FileReader input = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(input);
            String line;
            //Paragraph paragraph = new Paragraph();
            while ((line = br.readLine()) != null) {
                document.add(new Paragraph(line));
            }
            document.close();
            br.close();
        } while (true);
    }
    ///////////////////////
    public void exportHTML() {
        chooser.setDialogTitle("Exportiere HTML ...");
        chooser.setApproveButtonText("Exportieren");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_E);
        chooser.setApproveButtonToolTipText("Ausgew\u00E4hlte Datei exportieren.");

    }
    ///////////////////////
    public void openFile() {
        if (!confirmSave()) return;
        chooser.setDialogTitle("\u00D6ffne Datei...");
        chooser.setApproveButtonText("\u00D6ffnen");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_O);
        chooser.setApproveButtonToolTipText("Ausgew\u00E4hlte Datei \u00F6ffnen.");

        File temp = null;
        do {
            if (chooser.showOpenDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                return;
            temp = chooser.getSelectedFile();

            if (temp.exists()) break;

            JOptionPane.showMessageDialog(this.npd.f,
                    "<html>" + temp.getName() + "<br>Datei nicht gefunden.<br>" +
                            "Bitte \u00FCberpr\u00FCfen Sie den angegebenen Dateinamen.<html>",
                    "Öffnen", JOptionPane.INFORMATION_MESSAGE);

        } while (true);

        this.npd.ta.setText("");

        if (!openFile(temp)) {
            fileName = "Unbenannt";
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
                fileName += "(Schreibgesch\u00FCtzt)";
                newFileFlag = true;
            }
            fileRef = temp;
            npd.f.setTitle(fileName + " - " + applicationTitle);
            npd.statusBar.setText("Datei : " + temp.getPath() + " erfolgreich gespeichert/ge\u00F6ffnet.");
            newFileFlag = false;
        } else {
            npd.statusBar.setText("Fehler beim \u00D6ffnen/Speichern : " + temp.getPath());
        }
    }

    ///////////////////////
    public boolean confirmSave() {
        String strMsg = "<html>Der Inhalt der Datei \"" + fileName + "\" wurde ge\u00E4ndert.<br>" +
                "Wollen Sie die \u00C4nderungen speichern?<html>";
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
        fileName = new String("Unbenannt");
        fileRef = new File(fileName);
        saved = true;
        newFileFlag = true;
        this.npd.f.setTitle(fileName + " - " + applicationTitle);
    }
//////////////////////////////////////
} // Ende der Klasse FileOperationDE