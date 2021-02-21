package Exports;
import FileFilter.FileFilterDE;
import NotePads.FNotepadEN;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class SaveToPdf {
    FNotepadEN npd;
    public void SaveToPdf() throws IOException {
        JFileChooser chooser = new JFileChooser();
        File temp = null;
        chooser.setDialogTitle("Exportiere PDF ...");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setApproveButtonText("Exportieren");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_E);
        chooser.setApproveButtonToolTipText("Ausgew\u00E4hlte Datei exportieren.");
        chooser.resetChoosableFileFilters();
        chooser.addChoosableFileFilter(new FileFilterDE(".pdf", "Portable Document Files(*.pdf)"));

        do {
            if (chooser.showSaveDialog(this.npd.f) != JFileChooser.APPROVE_OPTION)
                chooser.cancelSelection();

            temp = chooser.getSelectedFile();
            String newName = temp.getName().replace(".txt", ".pdf");

            File dest = new File(newName);
            dest.getParentFile().mkdirs();

            PdfWriter pdfWriter = new PdfWriter(newName);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);
            document.close();
        } while (true);
    }
    public static void main(String args[]) throws Exception{
        new SaveToPdf();
    }
}
