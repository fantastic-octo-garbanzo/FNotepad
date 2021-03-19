package NotePads;
// Imports
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.*;

import FileOperation.FileOperationEN;
import FindDialog.FindDialogEN;
import FontChooser.FontChooserEN;
import LookAndFeelMenu.LookAndFeelMenuEN;
import MenuConstants.MenuConstantsEN;

/************************************/

public class FNotepadEN implements ActionListener, MenuConstantsEN {

    public JFrame f;
    public JTextArea ta;
    public JLabel statusBar;
    int tabSize = 4;

    private String fileName = "Untitled";
    private boolean saved = true;
    String applicationName = "FNotepad";

    FileOperationEN fileHandler;
    FontChooserEN fontDialog = null;
    FindDialogEN findReplaceDialog = null;
    JColorChooser bcolorChooser = null;
    JColorChooser fcolorChooser = null;
    JDialog backgroundDialog = null;
    JDialog foregroundDialog = null;
    JDialog tabulatorSize;
    JMenuItem cutItem, copyItem, deleteItem, findItem, findNextItem, replaceItem, gotoItem, selectAllItem;
    /****************************/
    public FNotepadEN(boolean fullscreen) {
        f = new JFrame(fileName + " - " + applicationName);

        URL iconURL = getClass().getResource("/bin/FNotepad.jpg");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        f.setIconImage(icon.getImage());

        ta = new JTextArea(30, 60);
        statusBar = new JLabel("Tabulatorsize: "+tabSize+"     ||      Letters 0, Words 0       ||       Ln 1, Col 1  ", JLabel.RIGHT);
        f.add(new JScrollPane(ta), BorderLayout.CENTER);
        f.add(statusBar, BorderLayout.SOUTH);
        f.add(new JLabel("  "), BorderLayout.EAST);
        f.add(new JLabel("  "), BorderLayout.WEST);
        createMenuBar(f);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        if(!fullscreen){f.setSize(650, 600);}

        fileHandler = new FileOperationEN(this);
/////////////////////
        ta.addCaretListener(
                new CaretListener() {
                    public void caretUpdate(CaretEvent e) {
                        int lineNumber = 0, column = 0, pos = 0, wordCount = 0, letterCount = 0;

                        try {
                            pos = ta.getCaretPosition();
                            lineNumber = ta.getLineOfOffset(pos);
                            column = pos - ta.getLineStartOffset(lineNumber);
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

                            //letterCount = text.length();
                            wordCount = text.split("\\s").length;
                            if (!FileOperationEN.isSave()){
                                f.setTitle(FileOperationEN.getFileName() + "* - " + applicationName);
                            } else {
                                f.setTitle(FileOperationEN.getFileName() + " - " + applicationName);
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
                        statusBar.setText("Tabulatorsize: "+tabSize+"       ||      Letters " + letterCount + ", Words "+ wordCount + "       ||       Line " + (lineNumber + 1) + ", Column " + (column + 1));
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
//////////////////
    }
    ////////////////////////////////////
    void goTo() {
        int lineNumber = 0;
        try {
            lineNumber = ta.getLineOfOffset(ta.getCaretPosition()) + 1;
            String tempStr = JOptionPane.showInputDialog(f, "Enter Line Number:", "" + lineNumber);
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
                    FNotepadEN.this.f,
                    "Get ur printer repaired first! It seems u dont have one!",
                    "Bad Printer",
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
            if (FNotepadEN.this.ta.getText().length() == 0)
                return;    // text box have no text
            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialogEN(FNotepadEN.this.ta);
            findReplaceDialog.showDialog(FNotepadEN.this.f, true);//find
        }
////////////////////////////////////
        else if (cmdText.equals(editFindNext)) {
            if (FNotepadEN.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                statusBar.setText("Nothing to search for, use Find option of Edit Menu first !!!!");
            else
                findReplaceDialog.findNextWithSelection();
        }
////////////////////////////////////
        else if (cmdText.equals(editReplace)) {
            if (FNotepadEN.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialogEN(FNotepadEN.this.ta);
            findReplaceDialog.showDialog(FNotepadEN.this.f, false);//replace
        }
////////////////////////////////////
        else if (cmdText.equals(editGoTo)) {
            if (FNotepadEN.this.ta.getText().length() == 0)
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
                fontDialog = new FontChooserEN(ta.getFont());

            if (fontDialog.showDialog(FNotepadEN.this.f, "Choose a font"))
                FNotepadEN.this.ta.setFont(fontDialog.createFont());
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
        else if (cmdText.equals(helpHelpTopic))
            try {
                loadHelp();
            } catch (Exception e) {}

        else if (cmdText.equals(helpHelpoffline))
            try {
                loadHelpoffline();
            } catch (Exception e) {
            }
////////////////////////////////////
        else if (cmdText.equals(helpAboutFNotepad)) {
            JOptionPane.showMessageDialog(FNotepadEN.this.f, aboutText, "About FNotepad", JOptionPane.INFORMATION_MESSAGE);
        }
////////////////////////////////////
        else if (cmdText.equals(filePageSetup)) {
            showTabulatorDialog();
        }
////////////////////////////////////
        else if (cmdText.equals(LangDE)) {
            changeLanguageDE();
        }
/////////////////////////////////////
        else if (cmdText.equals(LangIT)) {
            changeLanguageIT();
        }
/////////////////////////////////////
        else if (cmdText.equals(helpHelpTopic)){
            try {
                loadHelp();
            } catch (Exception e) {
            }
        }
        ////////////////////////////////////
        else if (cmdText.equals(helpHelpoffline))
            try {
                loadHelpoffline();
            } catch (Exception e) {
            }
/////////////////////////////////////
        else if (cmdText.equals(commandoopen)) {
            ProcessBuilder pb = new ProcessBuilder( "cmd", "/k", "start" );
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
            statusBar.setText("This command is yet to be implemented");
        }
    }//action Performed
    ////////////////////////////////////
    void showTabulatorDialog(){

        tabulatorSize = new JDialog();
        tabulatorSize.setTitle(filePageSetup);
        tabulatorSize.setBounds(50, 50, 100, 60);
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
        tabulatorSize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    ////////////////////////////////////
    void showBackgroundColorDialog() {
        if (bcolorChooser == null)
            bcolorChooser = new JColorChooser();
        if (backgroundDialog == null)
            backgroundDialog = JColorChooser.createDialog
                    (FNotepadEN.this.f,
                            formatBackground,
                            false,
                            bcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepadEN.this.ta.setBackground(bcolorChooser.getColor());
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
                    (FNotepadEN.this.f,
                            formatForeground,
                            false,
                            fcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepadEN.this.ta.setForeground(fcolorChooser.getColor());
                                }
                            },
                            null);

        foregroundDialog.setVisible(true);
    }
    ////////////////////////////////////
    void loadHelp() throws IOException {

        Runtime rt = Runtime.getRuntime();
        String url = "https://github.com/fantastic-octo-garbanzo/FNotepad/wiki/Help";
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) { // Wenn das Betriebsystem Windows ist
            rt.exec("rundll32 url.dll, FileProtocolHandler " + url);
        } else if (os.indexOf("mac") >= 0) { // Wenn das Betriebssystem MacOS ist
            rt.exec("open " + url);
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
            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
    }
    ////////////////////////////////////
    ////////////////////////////////////
    void loadHelpoffline() throws IOException {

        Runtime rt = Runtime.getRuntime();
        URL url = getClass().getResource("/bin/Help.html");

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) { // Wenn das Betriebsystem Windows ist
            rt.exec("rundll32 url.dll, FileProtocolHandler " + url);
        } else if (os.contains("mac")) { // Wenn das Betriebssystem MacOS ist
            rt.exec("open " + url);
        } else if (os.contains("nix") || os.contains("nux")) { // Wenn das Betriebssystem Linux ist
            String[] browsers = {"firefox", "mozilla", "opera", "konqueror", "links", "lynx"};

            StringBuffer cmd = new StringBuffer();
            for (int i = 0; i < browsers.length; i++) {
                if (i == 0)
                    cmd.append(String.format("%s \"%s\"", browsers[i], url));
                else
                    cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                // Wenn der erste nicht funktioniert, wird der nächste probiert usw.
            }
            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
    }

    void loadHelpoffline() throws IOException {

        Runtime rt = Runtime.getRuntime();
        //URL url = getClass().getResource("/bin/Hilfe.html");
        InputStream is = getClass().getResourceAsStream("/bin/Help.html");
        File temp = File.createTempFile("Help", ".html");
        temp.deleteOnExit();
        assert is != null;
        try {
            Files.copy(is, Paths.get(temp.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
        }
        URL url = Paths.get(temp.getPath()).toUri().toURL();


        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) { // Wenn das Betriebsystem Windows ist
            rt.exec("rundll32 url.dll, FileProtocolHandler " + url);
        } else if (os.contains("mac")) { // Wenn das Betriebssystem MacOS ist
            rt.exec("open " + url);
        } else if (os.contains("nix") || os.contains("nux")) { // Wenn das Betriebssystem Linux ist
            String[] browsers = {"firefox", "mozilla", "opera", "konqueror", "links", "lynx"};

            StringBuffer cmd = new StringBuffer();
            for (int i = 0; i < browsers.length; i++) {
                if (i == 0)
                    cmd.append(String.format("%s \"%s\"", browsers[i], url));
                else
                    cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                // Wenn der erste nicht funktioniert, wird der nächste probiert usw.
            }
            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
    }
    ///////////////////////////////////
    void changeLanguageDE() {
        if (!FileOperationEN.saved) return;
        new FNotepadDE(true);
        f.dispose();
    }
    ///////////////////////////////////
    void changeLanguageIT() {
        if (!FileOperationEN.saved) return;
        new FNotepadIT(true);
        f.dispose();
    }
    ///////////////////////////////////
    void newWindow() {
        new FNotepadEN(true);
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
        JMenuBar mb = new JMenuBar();
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
        temp = createMenuItem(filePrint, KeyEvent.VK_P, fileMenu, KeyEvent.VK_P, this);
        temp.setEnabled(false);
        fileMenu.addSeparator();
        createMenuItem(fileExit, KeyEvent.VK_X, fileMenu, this);

        temp = createMenuItem(editUndo, KeyEvent.VK_U, editMenu, KeyEvent.VK_Z, this);
        temp.setEnabled(false);
        editMenu.addSeparator();
        cutItem = createMenuItem(editCut, KeyEvent.VK_T, editMenu, KeyEvent.VK_X, this);
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
        LookAndFeelMenuEN.createLookAndFeelMenuItem(viewMenu, this.f);


        createMenuItem(helpHelpTopic, KeyEvent.VK_H, helpMenu, this);
        createMenuItem(helpHelpoffline, KeyEvent.VK_H, helpMenu, this);

        createMenuItem(helpHelpOnline, KeyEvent.VK_H, helpMenu, this);

        helpMenu.addSeparator();
        createMenuItem(helpAboutFNotepad, KeyEvent.VK_A, helpMenu, this);

        createMenuItem(LangDE, KeyEvent.VK_G, changeMenu, this);
        createMenuItem(LangIT, KeyEvent.VK_Y, changeMenu, this);

        MenuListener editMenuListener = new MenuListener() {
            public void menuSelected(MenuEvent evvvv) {
                if (FNotepadEN.this.ta.getText().length() == 0) {
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
                if (FNotepadEN.this.ta.getSelectionStart() == ta.getSelectionEnd()) {
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
        new FNotepadEN(true);
    }
}
/**************************************/
// Menu
