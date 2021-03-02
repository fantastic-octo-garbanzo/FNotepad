package NotePads;
// Imports
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.*;

import FileOperation.FileOperationIT;
import FindDialog.FindDialogIT;
import FontChooser.FontChooserIT;
import LookAndFeelMenu.LookAndFeelMenuIT;

/************************************/

public class FNotepadIT implements ActionListener, MenuConstantsIT {


    public JFrame f;
    public JTextArea ta;
    public JLabel statusBar;
    int tabSize = 4;

    private String fileName = "Unbenannt";
    private boolean saved = true;
    String applicationName = "FNotepad";

    String searchString, replaceString;
    int lastSearchIndex;

    FileOperationIT fileHandler;
    FontChooserIT fontDialog = null;
    FindDialogIT findReplaceDialog = null;
    JColorChooser bcolorChooser = null;
    JColorChooser fcolorChooser = null;
    JDialog backgroundDialog = null;
    JDialog foregroundDialog = null;
    JDialog tabulatorSize;
    JMenuItem cutItem, copyItem, deleteItem, findItem, findNextItem, replaceItem, gotoItem, selectAllItem;

    /****************************/
    public static Dimension getScreenDimensionWithoutTaskbarIT(Frame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        int taskBarSize = screenInsets.bottom;
        return new Dimension(width, height - taskBarSize);
    }
    /****************************/
    public FNotepadIT(boolean fullscreen) {
        f = new JFrame(fileName + " - " + applicationName);

        URL iconURL = getClass().getResource("/bin/FNotepad.jpg");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        f.setIconImage(icon.getImage());

        ta = new JTextArea(30, 60);
        statusBar = new JLabel("Tabulatorbreite: "+tabSize+"     ||      Zeichen: 0, W\u00F6rter: 0       ||       Zeile: 1, Spalte: 1  ", JLabel.RIGHT);
        ta.setTabSize(tabSize);
        f.add(new JScrollPane(ta), BorderLayout.CENTER);
        f.add(statusBar, BorderLayout.SOUTH);

        f.add(new JLabel("  "), BorderLayout.EAST);
        f.add(new JLabel("  "), BorderLayout.WEST);
        createMenuBar(f);


        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        if(!fullscreen){f.setSize(800, 600);}

        f.setVisible(true);

        fileHandler = new FileOperationIT(this);

/////////////////////

        ta.addCaretListener(
                new CaretListener() {
                    public void caretUpdate(CaretEvent e) {
                        int lineNumber = 0, column = 0, pos = 0, wordCount = 0, letterCount = 0;

                        try {
                            String text = ta.getText();
                            String textTabs = ta.getText();
                            for(char c : textTabs.toCharArray()){
                                System.out.println(c);
                                if("\t".equals(""+c)){
                                    letterCount = letterCount + tabSize;
                                }
                                else {
                                    letterCount++;
                                }
                            }
                            pos = ta.getCaretPosition();
                            lineNumber = ta.getLineOfOffset(pos);
                            column = pos - ta.getLineStartOffset(lineNumber);
                            /**
                             if (text.length() == '9')
                             column = column + tabSize;
                             */
                            //letterCount = text.length();
                            wordCount = text.split("\\s").length;
                            if (!FileOperationIT.isSave()){
                                f.setTitle(FileOperationIT.getFileName() + "* - " + applicationName);
                            } else {
                                f.setTitle(FileOperationIT.getFileName() + " - " + applicationName);
                            }
                            //System.out.println(wordCount+ " " +letterCount);

                        } catch (Exception excp) {
                        }
                        if (ta.getText().length() == 0) {
                            lineNumber = 0;
                            column = 0;
                            wordCount = 0;
                            letterCount = 0;
                        }
                        statusBar.setText("Tabulatorbreite: "+tabSize+"     ||      Zeichen "+letterCount+", W\u00F6rter "+wordCount+"       ||       Zeile "+(lineNumber + 1)+", Spalte "+(column + 1));
                    }
                });
//////////////////
        DocumentListener myListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                fileHandler.saved = false;
            }

            public void removeUpdate(DocumentEvent e) {
                fileHandler.saved = false;
            }

            public void insertUpdate(DocumentEvent e) {
                fileHandler.saved = false;
            }
        };
        ta.getDocument().addDocumentListener(myListener);
/////////
        WindowListener frameClose = new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                if (fileHandler.confirmSave()) f.dispose();
            }
        };
        f.addWindowListener(frameClose);
////////////////////////////////////

    }
    ////////////////////////////////////
    void goTo() {
        int lineNumber = 0;
        try {
            lineNumber = ta.getLineOfOffset(ta.getCaretPosition()) + 1;
            String tempStr = JOptionPane.showInputDialog(f, "Zeile eingeben:", "" + lineNumber);
            if (tempStr == null) {
                return;
            }
            lineNumber = Integer.parseInt(tempStr);
            ta.setCaretPosition(ta.getLineStartOffset(lineNumber - 1));
        } catch (Exception e) {
        }
    }

    ///////////////////////////////////
    public void actionPerformed(ActionEvent ev) {
        String cmdText = ev.getActionCommand();
////////////////////////////////////
        if (cmdText.equals(windowNew))
            newWindow();
////////////////////////////////////
        else if (cmdText.equals(fileNew))
            fileHandler.newFile();
////////////////////////////////////
        else if (cmdText.equals(fileOpen))
            fileHandler.openFile();
////////////////////////////////////
        else if (cmdText.equals(fileSave))
            fileHandler.saveThisFile();
////////////////////////////////////
        else if (cmdText.equals(fileSaveAs))
            fileHandler.saveAsFile();
////////////////////////////////////
        else if (cmdText.equals(fileExit)) {
            if (fileHandler.confirmSave()) System.exit(0);
        }
////////////////////////////////////
        else if (cmdText.equals(filePrint))
            JOptionPane.showMessageDialog(
                    FNotepadIT.this.f,
                    "Es wurde kein Drucker gefunden, bitte schlie\u00DF einen Drucker an!!",
                    "Falscher Drucker!!",
                    JOptionPane.INFORMATION_MESSAGE
            );
////////////////////////////////////
        else if (cmdText.equals(editCut))
            ta.cut();
////////////////////////////////////
        else if (cmdText.equals(editCopy))
            ta.copy();
////////////////////////////////////
        else if (cmdText.equals(editPaste))
            ta.paste();
////////////////////////////////////
        else if (cmdText.equals(editDelete))
            ta.replaceSelection("");
////////////////////////////////////
        else if (cmdText.equals(editFind)) {
            if (FNotepadIT.this.ta.getText().length() == 0)
                return;    // text box have no text
            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialogIT(FNotepadIT.this.ta);
            findReplaceDialog.showDialog(FNotepadIT.this.f, true);//find
        }
////////////////////////////////////
        else if (cmdText.equals(editFindNext)) {
            if (FNotepadIT.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                statusBar.setText("Nichts zu suchen, bitte Finden im Bearbeiten-Men\u00FC zuerst probieren !!!!");
            else
                findReplaceDialog.findNextWithSelection();
        }
////////////////////////////////////
        else if (cmdText.equals(editReplace)) {
            if (FNotepadIT.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialogIT(FNotepadIT.this.ta);
            findReplaceDialog.showDialog(FNotepadIT.this.f, false);//replace
        }
////////////////////////////////////
        else if (cmdText.equals(editGoTo)) {
            if (FNotepadIT.this.ta.getText().length() == 0)
                return;    // text box have no text
            goTo();
        }
////////////////////////////////////
        else if (cmdText.equals(editSelectAll))
            ta.selectAll();
////////////////////////////////////
        else if (cmdText.equals(editTimeDate))
            ta.insert(new Date().toString(), ta.getSelectionStart());
////////////////////////////////////
        else if (cmdText.equals(formatWordWrap)) {
            JCheckBoxMenuItem temp = (JCheckBoxMenuItem) ev.getSource();
            ta.setLineWrap(temp.isSelected());
        }
////////////////////////////////////
        else if (cmdText.equals(formatFont)) {
            if (fontDialog == null)
                fontDialog = new FontChooserIT(ta.getFont());

            if (fontDialog.showDialog(FNotepadIT.this.f, "Schrift ausw\u00E4hlen"))
                FNotepadIT.this.ta.setFont(fontDialog.createFont());
        }
////////////////////////////////////
        else if (cmdText.equals(formatForeground))
            showForegroundColorDialog();
////////////////////////////////////
        else if (cmdText.equals(formatBackground))
            showBackgroundColorDialog();
////////////////////////////////////
        else if (cmdText.equals(viewStatusBar)) {
            JCheckBoxMenuItem temp = (JCheckBoxMenuItem) ev.getSource();
            statusBar.setVisible(temp.isSelected());
        }
////////////////////////////////////
        else if (cmdText.equals(helpHelpOnline))
            try {
                openGithub();
            } catch (Exception e) {
            }
////////////////////////////////////
        else if (cmdText.equals(helpAboutFNotepad)) {
            JOptionPane.showMessageDialog(FNotepadIT.this.f, aboutText, "\u00DCber FNotepad", JOptionPane.INFORMATION_MESSAGE);
        }
////////////////////////////////////
        else if (cmdText.equals(filePageSetup)) {
            showTabulatorDialog();
        }
////////////////////////////////////
        else if (cmdText.equals(changeLang)) {
            changeLanguage();
        }
////////////////////////////////////
        else if (cmdText.equals(helpHelpTopic)){
            loadHelp();
        }
/////////////////////////////////////
        else if (cmdText.equals(commandoopen)) {
            ProcessBuilder pb = new ProcessBuilder( "cmd", "/k", "start");
            ProcessBuilder lt = new ProcessBuilder("konsole", "/k", "start");
            String os = System.getProperty("os.name").toLowerCase();
            try {
                if (os.indexOf("win") >= 0) {
                    pb.start();
                } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                    lt.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
////////////////////////////////////
        else {
            statusBar.setText("Dieser Befehl wird gerade integriert");
        }
    }
    ////////////////////////////////////
    void showTabulatorDialog(){

        tabulatorSize = new JDialog();
        tabulatorSize.setTitle(filePageSetup);
        tabulatorSize.setBounds(50, 50, 400, 100);
        tabulatorSize.setVisible(true);
        tabulatorSize.setAlwaysOnTop(true);

        Choice c = new Choice();
        c.add("2");
        c.add("4");
        c.add("8");

        tabulatorSize.add(c);
        c.select(String.valueOf(tabSize));
        ta.setTabSize(tabSize);
        c.addItemListener(ie -> {
            if(c.getSelectedItem().equals("2")) {
                tabSize = 2;
                ta.setTabSize(tabSize);
            }
            if(c.getSelectedItem().equals("4")) {
                tabSize = 4;
                ta.setTabSize(tabSize);
            }
            if(c.getSelectedItem().equals("8")) {
                tabSize = 8;
                ta.setTabSize(tabSize);
            }
        });
        tabulatorSize.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    ////////////////////////////////////
    void showBackgroundColorDialog() {
        if (bcolorChooser == null)
            bcolorChooser = new JColorChooser();
        if (backgroundDialog == null)
            backgroundDialog = JColorChooser.createDialog
                    (FNotepadIT.this.f,
                            formatBackground,
                            false,
                            bcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepadIT.this.ta.setBackground(bcolorChooser.getColor());
                                }
                            },
                            null);

        backgroundDialog.setVisible(true);
    }

    ////////////////////////////////////
    void showForegroundColorDialog() {
        if (fcolorChooser == null)
            fcolorChooser = new JColorChooser();
        if (foregroundDialog == null)
            foregroundDialog = JColorChooser.createDialog
                    (FNotepadIT.this.f,
                            formatForeground,
                            false,
                            fcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepadIT.this.ta.setForeground(fcolorChooser.getColor());
                                }
                            },
                            null);

        foregroundDialog.setVisible(true);
    }

    ///////////////////////////////////
    void openGithub() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String url = "https://github.com/fantastic-octo-garbanzo/FNotepad";
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) { // Wenn das Betriebsystem Windows ist
            rt.exec("rundll32 url.dll, FileProtocolHandler "+url);
        } else if (os.indexOf("mac") >= 0) { // Wenn das Betriebssystem MacOS ist
            rt.exec("open "+url);
        } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) { // Wenn das Betriebssystem Linux ist
            String[] browsers = {"firefox", "mozilla", "opera", "konqueror", "links", "lynx"};

            StringBuffer cmd = new StringBuffer();
            for (int i = 0; i < browsers.length; i++) {
                if (i == 0)
                    cmd.append(String.format("%s \"%s\"", browsers[i], url));
                else
                    cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                // Wenn der erste nicht funktioniert, wird der nächste probiert usw.
            }
            rt.exec(new String[] {"sh", "-c", cmd.toString() });
        }
    }

    ///////////////////////////////////
    void changeLanguage() {
        if (!FileOperationIT.saved) return;
        new FNotepadEN(true);
        f.dispose();
    }
    ///////////////////////////////////
    void newWindow() {
        new FNotepadDE(true);
    }

    void commandoopen() {
        JFrame commando = new JFrame();
        commando.setTitle(commandoopen);
        commando.setBounds(50, 50, 700, 300);
        commando.setVisible(true);
        commando.setResizable(true);
        commando.setAlwaysOnTop(true);
        commando.setSize(500, 500);
        commando.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        URL iconURL = getClass().getResource("/bin/FNotepad.jpg");
        ImageIcon helpicon = new ImageIcon(iconURL);
        commando.setIconImage(helpicon.getImage());
        commando.setVisible(true);
    }

    void loadHelp(){
        FileReader fr;
        fr = null;
        JFrame helpPage = new JFrame();

        helpPage.setTitle(helpText);
        helpPage.setBounds(50, 50, 700, 300);
        helpPage.setVisible(true);
        helpPage.setAlwaysOnTop(true);
        helpPage.setResizable(true);
        JTextArea helptxtArea = new JTextArea();
        helptxtArea.setEditable(false);
        URL fileURL = getClass().getResource("/bin/Hilfe.txt");
        try {
            File file = new File(fileURL.toURI());
            fr = new FileReader(file);
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            ArrayList<String> listOfStrings = new ArrayList<>();
            listOfStrings.add(line);
            while(line != null) {
                line = br.readLine();
                listOfStrings.add(line);
            }
            for(int i = 0; i < listOfStrings.size(); i++) {
                sb.append(listOfStrings.get(i)+"\n");
            }
            sb.delete(sb.length()-5, sb.length());
            helptxtArea.setText(sb.toString());
        }
        catch (IOException | URISyntaxException ex) { System.out.println(ex);
        }
        finally {

            try {
                if (fr != null) fr.close();
            }
            catch (Exception ex) {

            }
        }
        helpPage.add(helptxtArea);
        helpPage.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-1200), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-120));
        helpPage.setVisible(true);
        helpPage.setResizable(true);
        helpPage.toFront();
        helpPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        helptxtArea.setVisible(true);
        URL iconURL = getClass().getResource("/bin/FNotepad.jpg");
        ImageIcon helpicon = new ImageIcon(iconURL);
        helpPage.setIconImage(helpicon.getImage());
        helpPage.setVisible(true);
    }



    ///////////////////////////////////
    JMenuItem createMenuItem(String s, int key, JMenu toMenu, ActionListener al) {
        JMenuItem temp = new JMenuItem(s, key);
        temp.addActionListener(al);
        toMenu.add(temp);

        return temp;
    }

    ////////////////////////////////////
    JMenuItem createMenuItem(String s, int key, JMenu toMenu, int aclKey, ActionListener al) {
        JMenuItem temp = new JMenuItem(s, key);
        temp.addActionListener(al);
        temp.setAccelerator(KeyStroke.getKeyStroke(aclKey, ActionEvent.CTRL_MASK));
        toMenu.add(temp);

        return temp;
    }

    ////////////////////////////////////
    JCheckBoxMenuItem createCheckBoxMenuItem(String s, int key, JMenu toMenu, ActionListener al) {
        JCheckBoxMenuItem temp = new JCheckBoxMenuItem(s);
        temp.setMnemonic(key);
        temp.addActionListener(al);
        temp.setSelected(false);
        toMenu.add(temp);

        return temp;
    }

    ////////////////////////////////////
    JMenu createMenu(String s, int key, JMenuBar toMenuBar) {
        JMenu temp = new JMenu(s);
        temp.setMnemonic(key);
        toMenuBar.add(temp);
        return temp;
    }

    /*********************************/
    void createMenuBar(JFrame f) {
        JMenuBar mb = new JMenuBar(); // Menü-Leiste
        JMenuItem temp;

        JMenu fileMenu = createMenu(fileText, KeyEvent.VK_F, mb);
        JMenu editMenu = createMenu(editText, KeyEvent.VK_E, mb);
        JMenu formatMenu = createMenu(formatText, KeyEvent.VK_O, mb);
        JMenu viewMenu = createMenu(viewText, KeyEvent.VK_V, mb);
        JMenu helpMenu = createMenu(helpText, KeyEvent.VK_H, mb);
        JMenu changeMenu = createMenu(changeText, KeyEvent.VK_G, mb);

        createMenuItem(windowNew, KeyEvent.VK_G, fileMenu, KeyEvent.VK_G, this);
        createMenuItem(fileNew, KeyEvent.VK_N, fileMenu, KeyEvent.VK_N, this);
        createMenuItem(fileOpen, KeyEvent.VK_O, fileMenu, KeyEvent.VK_O, this);
        createMenuItem(fileSave, KeyEvent.VK_S, fileMenu, KeyEvent.VK_S, this);
        createMenuItem(fileSaveAs, KeyEvent.VK_A, fileMenu, this);
        fileMenu.addSeparator();
        createMenuItem(filePageSetup, KeyEvent.VK_U, fileMenu, this);
        fileMenu.addSeparator();
        temp = createMenuItem(fileExportasPDF, KeyEvent.VK_Y, fileMenu, KeyEvent.VK_Y, this);
        temp.setEnabled(false);
        temp = createMenuItem(fileExportasHTML, KeyEvent.VK_Y, fileMenu, KeyEvent.VK_Y, this);
        temp.setEnabled(false);
        fileMenu.addSeparator();
        temp = createMenuItem(filePrint, KeyEvent.VK_P, fileMenu, KeyEvent.VK_P, this);
        temp.setEnabled(false);
        fileMenu.addSeparator();
        createMenuItem(fileExit, KeyEvent.VK_X, fileMenu, this);

        temp = createMenuItem(editUndo, KeyEvent.VK_U, editMenu, KeyEvent.VK_Z, this);
        temp.setEnabled(false);
        editMenu.addSeparator();
        cutItem = createMenuItem(editCut, KeyEvent 	.VK_T, editMenu, KeyEvent.VK_X, this);
        copyItem = createMenuItem(editCopy, KeyEvent.VK_C, editMenu, KeyEvent.VK_C, this);
        createMenuItem(editPaste, KeyEvent.VK_P, editMenu, KeyEvent.VK_V, this);
        deleteItem = createMenuItem(editDelete, KeyEvent.VK_L, editMenu, this);
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        editMenu.addSeparator();
        findItem = createMenuItem(editFind, KeyEvent.VK_F, editMenu, KeyEvent.VK_F, this);
        findNextItem = createMenuItem(editFindNext, KeyEvent.VK_N, editMenu, this);
        findNextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        replaceItem = createMenuItem(editReplace, KeyEvent.VK_R, editMenu, KeyEvent.VK_H, this);
        gotoItem = createMenuItem(editGoTo, KeyEvent.VK_G, editMenu, KeyEvent.VK_G, this);
        editMenu.addSeparator();
        selectAllItem = createMenuItem(editSelectAll, KeyEvent.VK_A, editMenu, KeyEvent.VK_A, this);
        createMenuItem(editTimeDate, KeyEvent.VK_D, editMenu, this).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        createMenuItem(commandoopen, KeyEvent.VK_E, editMenu, KeyEvent.VK_T, this);

        createCheckBoxMenuItem(formatWordWrap, KeyEvent.VK_W, formatMenu, this);

        createMenuItem(formatFont, KeyEvent.VK_F, formatMenu, this);
        formatMenu.addSeparator();
        createMenuItem(formatForeground, KeyEvent.VK_T, formatMenu, this);
        createMenuItem(formatBackground, KeyEvent.VK_P, formatMenu, this);

        createCheckBoxMenuItem(viewStatusBar, KeyEvent.VK_S, viewMenu, this).setSelected(true);
/************For Look and Feel, May not work properly on different operating environment***/
        LookAndFeelMenuIT.createLookAndFeelMenuItem(viewMenu, this.f);

        createMenuItem(helpHelpTopic, KeyEvent.VK_H, helpMenu, this);
        createMenuItem(helpHelpOnline, KeyEvent.VK_H, helpMenu, this);
        helpMenu.addSeparator();
        createMenuItem(helpAboutFNotepad, KeyEvent.VK_A, helpMenu, this);

        createMenuItem(changeLang, KeyEvent.VK_G, changeMenu, this);

        MenuListener editMenuListener = new MenuListener() {
            public void menuSelected(MenuEvent evvvv) {
                if (FNotepadIT.this.ta.getText().length() == 0) {
                    findItem.setEnabled(false);
                    findNextItem.setEnabled(false);
                    replaceItem.setEnabled(false);
                    selectAllItem.setEnabled(false);
                    gotoItem.setEnabled(false);
                } else {
                    findItem.setEnabled(true);
                    findNextItem.setEnabled(true);
                    replaceItem.setEnabled(true);
                    selectAllItem.setEnabled(true);
                    gotoItem.setEnabled(true);
                }
                if (FNotepadIT.this.ta.getSelectionStart() == ta.getSelectionEnd()) {
                    cutItem.setEnabled(false);
                    copyItem.setEnabled(false);
                    deleteItem.setEnabled(false);
                } else {
                    cutItem.setEnabled(true);
                    copyItem.setEnabled(true);
                    deleteItem.setEnabled(true);
                }
            }

            public void menuDeselected(MenuEvent evvvv) {
            }

            public void menuCanceled(MenuEvent evvvv) {
            }
        };
        editMenu.addMenuListener(editMenuListener);
        f.setJMenuBar(mb);
    }

    /*************Constructor**************/
////////////////////////////////////
    public static void main(String[] s) {

        new FNotepadIT(true);

    }
}

/**************************************/
// Menü-Leiste
interface MenuConstantsIT {
    String fileText = "Datei";
    String editText = "Bearbeiten";
    String formatText = "Format";
    String viewText = "Ansicht";
    String helpText = "Hilfe";
    String changeText = "Sprache";

    String windowNew = "Neues Fenster";
    String fileNew = "Neue Datei";
    String fileOpen = "Datei \u00D6ffnen...";
    String fileSave = "Datei speichern";
    String fileSaveAs = "Datei speichern als...";
    String filePageSetup = "Seiteneinstellungen...";
    String fileExportasPDF = "Datei als PDF exportieren";
    String fileExportasHTML = "Datei als HTML exportieren";
    String filePrint = "Drucken";
    String fileExit = "Beenden";

    String editUndo = "R\u00FCckg\u00E4ngig";
    String editCut = "Ausschneiden";
    String editCopy = "Kopieren";
    String editPaste = "Einf\u00FCgen";
    String editDelete = "L\u00F6schen";
    String editFind = "Suchen...";
    String editFindNext = "N\u00E4chstes finden";
    String editReplace = "Ersetzen";
    String editGoTo = "Gehe zu...";
    String editSelectAll = "Alles ausw\u00E4hlen";
    String editTimeDate = "Zeit/Datum";
    String commandoopen = "Terminal \u00F6ffnen";

    String formatWordWrap = "Zeilenumbruch";
    String formatFont = "Schrift...";
    String formatForeground = "Textfarbe...";
    String formatBackground = "Hintergrundfarbe...";

    String viewStatusBar = "Statusleiste";

    String helpHelpTopic = "Hilfe";
    String helpHelpOnline = "Github-Hilfe";
    String helpAboutFNotepad = "\u00DCber FNotepad";

    String aboutText =

            "<html><big>FNotepad</big><hr><hr>"
                    + "<p align=center>Von fantastic-octo-garbanzo!"
                    + "<hr><p align=center>Mit OpenJDK15 compiliert.<br><br>"
                    + "<strong>Danke f\u00FCr das Benutzen von FNotepad!</strong><br>"
                    + "Bei Bugs und Ideen gerne ein Issue auf Github stellen<p align=center>";

    String changeLang = "English";


}