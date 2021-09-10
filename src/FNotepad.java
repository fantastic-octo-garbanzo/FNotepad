package src;
// Imports
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
/************************************/

public class FNotepad implements ActionListener {

    public Locale locale;
    public static ResourceBundle bundle;

    public JFrame f;
    public JTextArea ta;
    public JLabel statusBar;
    public JToolBar tbar;
    public JButton exit, save, terminal, open, save_as, cut, paste, copy, german, english, french, italian;
    int tabSize = 4;

    private String fileName;
    private boolean saved = true;
    String applicationName = "FNotepad 1.4.1";

    FileOperation fileHandler;
    FontChooser fontDialog = null;
    FindDialog findReplaceDialog = null;
    JColorChooser bcolorChooser = null;
    JColorChooser fcolorChooser = null;
    JDialog backgroundDialog = null;
    JDialog foregroundDialog = null;
    JDialog tabulatorSize;
    JMenuItem cutItem, copyItem, deleteItem, findItem, findNextItem, replaceItem, gotoItem, selectAllItem;

    /****************************/
    public FNotepad(boolean fullscreen, Locale startLanguage) {
        this.locale = startLanguage;
        this.bundle = ResourceBundle.getBundle("Bundle_"+locale, locale);
        this.fileName = bundle.getString("fileName");
        f = new JFrame(fileName + " - " + applicationName);

        URL iconURL = getClass().getResource("/bin/FNotepad.jpg");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        f.setIconImage(icon.getImage());

        ta = new JTextArea(30, 60);
        statusBar = new JLabel(bundle.getString("statusbar.init1")+tabSize+bundle.getString("statusbar.init2"), JLabel.RIGHT);
        ta.setTabSize(tabSize);
        f.add(new JScrollPane(ta), BorderLayout.CENTER);
        f.add(statusBar, BorderLayout.SOUTH);
        f.add(new JLabel("  "), BorderLayout.EAST);
        f.add(new JLabel("  "), BorderLayout.WEST);
        createMenuBar(f);
        tbar = new JToolBar();
        tbar.setSize(1080, 1000);
        tbar.addSeparator();
        tbar.setFloatable(false);
        f.add(tbar, BorderLayout.NORTH);
        // JButton for opening
        URL iconURL2 = getClass().getResource("/Icons/Open.png");
        // iconURL is null when not found
        ImageIcon icon2 = new ImageIcon(iconURL2);
        f.setIconImage(icon.getImage());
        open = new JButton(icon2);
        open.setSize(100, 50);
        open.addActionListener(b -> {fileHandler.openFile();});
        tbar.add(open);
        // JButton for saving
        URL iconURL3 = getClass().getResource("/Icons/Save.jpg");
        // iconURL is null when not found
        ImageIcon icon3 = new ImageIcon(iconURL3);
        f.setIconImage(icon.getImage());
        save = new JButton(icon3);
        save.setSize(100, 50);
        save.addActionListener(b -> {fileHandler.saveThisFile();});
        tbar.add(save);
        // JButton for save as
        URL iconURL4 = getClass().getResource("/Icons/Save_as.png");
        // iconURL is null when not found
        ImageIcon icon4 = new ImageIcon(iconURL4);
        f.setIconImage(icon.getImage());
        save_as = new JButton(icon4);
        save_as.setSize(100, 50);
        save_as.addActionListener(b -> {fileHandler.saveAsFile();});
        tbar.add(save_as);// JButton for paste
        URL iconURL5 = getClass().getResource("/Icons/Copy.png");
        // iconURL is null when not found
        ImageIcon icon5 = new ImageIcon(iconURL5);
        f.setIconImage(icon.getImage());
        copy = new JButton(icon5);
        copy.setSize(100, 50);
        copy.addActionListener(b -> {ta.copy();});
        tbar.add(copy);
        // JButton for cut
        URL iconURL6 = getClass().getResource("/Icons/Cut.png");
        // iconURL is null when not found
        ImageIcon icon6 = new ImageIcon(iconURL6);
        f.setIconImage(icon.getImage());
        cut = new JButton(icon6);
        cut.setSize(100, 50);
        cut.addActionListener(a -> {ta.cut();});
        tbar.add(cut);
        // JButton for paste
        URL iconURL7 = getClass().getResource("/Icons/Paste.png");
        // iconURL is null when not found
        ImageIcon icon7 = new ImageIcon(iconURL7);
        f.setIconImage(icon.getImage());
        paste = new JButton(icon7);
        paste.setSize(100, 50);
        paste.addActionListener(b -> {ta.paste();});
        tbar.add(paste);
        // JButton for Terminal
        URL iconURL8 = getClass().getResource("/Icons/Terminal.png");
        // iconURL is null when not found
        ImageIcon icon8 = new ImageIcon(iconURL8);
        f.setIconImage(icon.getImage());
        terminal = new JButton(icon8);
        terminal.setSize(100, 50);
        terminal.addActionListener(c -> {;
            ProcessBuilder pb = new ProcessBuilder( "C:/Windows/system32/cmd.exe", "/c", "start");
            ProcessBuilder lt = new ProcessBuilder("x-terminal-emulator", "/c", "start");
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
        });
        tbar.add(terminal);
        // JButton for german
        URL iconURL9 = getClass().getResource("/Icons/Germany.png");
        // iconURL is null when not found
        ImageIcon icon9 = new ImageIcon(iconURL9);
        f.setIconImage(icon.getImage());
        german = new JButton(icon9);
        german.setSize(100, 50);
        german.addActionListener(b -> {if (fileHandler.confirmSave()) new FNotepad(true, Locale.GERMAN);f.dispose();});
        tbar.add(german);
        // JButton for english
        URL iconURL10 = getClass().getResource("/Icons/English.png");
        // iconURL is null when not found
        ImageIcon icon10 = new ImageIcon(iconURL10);
        f.setIconImage(icon.getImage());
        english = new JButton(icon10);
        english.setSize(100, 50);
        english.addActionListener(b -> {if (fileHandler.confirmSave()) new FNotepad(true, Locale.ENGLISH);f.dispose();});
        tbar.add(english);
         // JButton for french
        URL iconURL11 = getClass().getResource("/Icons/France.png");
        // iconURL is null when not found
        ImageIcon icon11 = new ImageIcon(iconURL11);
        f.setIconImage(icon.getImage());
        french = new JButton(icon11);
        french.setSize(100, 50);
        french.addActionListener(b -> {if (fileHandler.confirmSave()) new FNotepad(true, Locale.FRENCH);f.dispose();});
        tbar.add(french);
        // JButton for italian
        URL iconURL12 = getClass().getResource("/Icons/Italia.png");
        // iconURL is null when not found
        ImageIcon icon12 = new ImageIcon(iconURL12);
        f.setIconImage(icon.getImage());
        italian = new JButton(icon12);
        italian.setSize(100, 50);
        italian.addActionListener(b -> {if (fileHandler.confirmSave()) new FNotepad(true, Locale.ITALIAN);f.dispose();});
        tbar.add(italian);
        // JButton for exit
        URL iconURL13 = getClass().getResource("/Icons/Exit.png");
        // iconURL is null when not found
        ImageIcon icon13 = new ImageIcon(iconURL13);
        f.setIconImage(icon.getImage());
        exit = new JButton(icon13);
        exit.setSize(100, 50);
        exit.addActionListener(a -> {if (fileHandler.confirmSave()) System.exit(0);});
        tbar.add(exit);
        f.pack();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        if(!fullscreen){f.setBounds(200, 80, 700, 600);}
        f.setVisible(true);

        fileHandler = new FileOperation(this);
/////////////////////
        ta.addCaretListener(
                new CaretListener() {
                    public void caretUpdate(CaretEvent e) {
                        int lineNumber = 0, column = 0, pos = 0, wordCount = 0, letterCount = 0;

                        try {
                            String text = ta.getText();
                            String textTabs = ta.getText();
                            for(char c : textTabs.toCharArray()){
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
                            if (!FileOperation.isSave()){
                                f.setTitle(FileOperation.getFileName() + "* - " + applicationName);
                            } else {
                                f.setTitle(FileOperation.getFileName() + " - " + applicationName);
                            }

                        } catch (Exception excp) {
                        }
                        if (ta.getText().length() == 0) {
                            lineNumber = 0;
                            column = 0;
                            wordCount = 0;
                            letterCount = 0;
                        }
                        statusBar.setText(bundle.getString("statusbar.init1")+tabSize+bundle.getString("statusbar.work1")+letterCount+bundle.getString("statusbar.work2")+wordCount+bundle.getString("statusbar.work3")+(lineNumber + 1)+bundle.getString("statusbar.work4")+(column + 1));
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
            String tempStr = JOptionPane.showInputDialog(f, bundle.getString("goto"), "" + lineNumber);
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
        if (cmdText.equals(bundle.getString("windowNew")))
            newWindow();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("fileNew")))
            fileHandler.newFile();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("fileOpen")))
            fileHandler.openFile();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("fileSave")))
            fileHandler.saveThisFile();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("fileSaveAs")))
            fileHandler.saveAsFile();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("fileExit"))) {
            if (fileHandler.confirmSave()) System.exit(0);
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editCut")))
            ta.cut();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editCopy")))
            ta.copy();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editPaste")))
            ta.paste();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editDelete")))
            ta.replaceSelection("");
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editFind"))) {
            if (FNotepad.this.ta.getText().length() == 0)
                return;    // text box have no text
            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialog(FNotepad.this.ta);
            findReplaceDialog.showDialog(FNotepad.this.f, true);//find
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editFindNext"))) {
            if (FNotepad.this.ta.getText().length() == 0)
                return;

            if (findReplaceDialog == null)
                statusBar.setText(bundle.getString("search.text1"));
            else
                findReplaceDialog.findNextWithSelection();
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editReplace"))) {
            if (FNotepad.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialog(FNotepad.this.ta);
            findReplaceDialog.showDialog(FNotepad.this.f, false);//replace
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editGoTo"))) {
            if (FNotepad.this.ta.getText().length() == 0)
                return;
            goTo();
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editSelectAll")))
            ta.selectAll();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("editTimeDate")))
            ta.insert(new Date().toString(), ta.getSelectionStart());
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("formatWordWrap"))) {
            JCheckBoxMenuItem temp = (JCheckBoxMenuItem) ev.getSource();
            ta.setLineWrap(temp.isSelected());
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("formatFont"))) {
            if (fontDialog == null)
                fontDialog = new FontChooser(ta.getFont());

            if (fontDialog.showDialog(FNotepad.this.f, bundle.getString("font.text1")))
                FNotepad.this.ta.setFont(fontDialog.createFont());
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("formatForeground")))
            showForegroundColorDialog();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("formatBackground")))
            showBackgroundColorDialog();
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("viewStatusBar"))) {
            JCheckBoxMenuItem temp = (JCheckBoxMenuItem) ev.getSource();
            statusBar.setVisible(temp.isSelected());
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("helpHelpTopic")))
            try {
                loadHelp();
            } catch (Exception e) {
            }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("website")))
            try {
                loadwebsite();
            } catch (Exception e) {
            }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("helpHelpoffline")))
            try {
                loadHelpoffline();
            } catch (Exception e) {
            }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("helpAboutFNotepad"))) {
            JOptionPane.showMessageDialog(FNotepad.this.f, bundle.getString("aboutText"), bundle.getString("about.text1"), JOptionPane.INFORMATION_MESSAGE);
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("filePageSetup"))) {
            showTabulatorDialog();
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("Lang1"))) {
            changeLanguage1();
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("Lang2"))) {
            changeLanguage2();
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("Lang3"))) {
            changeLanguage3();
        }
////////////////////////////////////
        else if (cmdText.equals(bundle.getString("helpHelpTopic"))){
            try {
                loadHelp();
            } catch (Exception e) {
            }
        }
//////////////////////////////////////

/////////////////////////////////////
        else if (cmdText.equals(bundle.getString("commandoopen"))) {
            ProcessBuilder pb = new ProcessBuilder( "C:/Windows/system32/cmd.exe", "/c", "start");
            ProcessBuilder lt = new ProcessBuilder("x-terminal-emulator", "/c", "start");
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
            statusBar.setText(bundle.getString("implement.text1"));
        }
    }
    ////////////////////////////////////
    void showTabulatorDialog(){
        tabulatorSize = new JDialog();
        tabulatorSize.setTitle(bundle.getString("filePageSetup"));
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
                    (FNotepad.this.f,
                            bundle.getString("formatBackground"),
                            false,
                            bcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepad.this.ta.setBackground(bcolorChooser.getColor());
                                }
                            }, null);
        backgroundDialog.setVisible(true);
    }
    ////////////////////////////////////
    void showForegroundColorDialog() {
        if (fcolorChooser == null)
            fcolorChooser = new JColorChooser();
        if (foregroundDialog == null)
            foregroundDialog = JColorChooser.createDialog
                    (FNotepad.this.f,
                            bundle.getString("formatForeground"),
                            false,
                            fcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepad.this.ta.setForeground(fcolorChooser.getColor());
                                }
                            },
                            null);

        foregroundDialog.setVisible(true);
    }
    ///////////////////////////////////
    void changeLanguage1() {
        if (!FileOperation.saved) {
            fileHandler.saveAsFile();
        }
        if (locale == Locale.ENGLISH) {
            new FNotepad(true, Locale.GERMAN);
        }
        if (locale == Locale.GERMAN) {
            new FNotepad(true, Locale.ENGLISH);
        }
        if (locale == Locale.ITALIAN) {
            new FNotepad(true, Locale.ENGLISH);
        }
        if (locale == Locale.FRENCH) {
            new FNotepad(true, Locale.ENGLISH);
        }
        f.dispose();
    }
    ///////////////////////////////////
    void changeLanguage2() {
        if (!FileOperation.saved) fileHandler.saveAsFile();
        if (locale == Locale.ENGLISH) {
            new FNotepad(true, Locale.ITALIAN);
        }
        if (locale == Locale.GERMAN) {
            new FNotepad(true, Locale.ITALIAN);
        }
        if (locale == Locale.ITALIAN) {
            new FNotepad(true, Locale.GERMAN);
        }
        if (locale == Locale.FRENCH) {
            new FNotepad(true, Locale.GERMAN);
        }
        f.dispose();
    }
    ///////////////////////////////////
    void changeLanguage3() {
        if (!FileOperation.saved) fileHandler.saveAsFile();
        if (locale == locale.ENGLISH) {
            new FNotepad(true, Locale.FRENCH);
        }
        if (locale == Locale.GERMAN) {
            new FNotepad(true, Locale.FRENCH);
        }
        if (locale == Locale.ITALIAN) {
            new FNotepad(true, Locale.FRENCH);
        }
        if (locale == Locale.FRENCH) {
            new FNotepad(true, Locale.ITALIAN);
        }
        f.dispose();
    }
    ///////////////////////////////////
    void newWindow() {
        new FNotepad(true, locale);
    }
    ////////////////////////////////////
    void loadHelp() throws IOException {

        Runtime rt = Runtime.getRuntime();
        String url = bundle.getString("help.URL");
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
                // Wenn der erste nicht funktioniert, wird der n�chste probiert usw.
            }
            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
    }
    ////////////////////////////////////
    void loadwebsite() throws IOException {

        Runtime rt = Runtime.getRuntime();
        String url = bundle.getString("website.URL");
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
                // Wenn der erste nicht funktioniert, wird der n�chste probiert usw.
            }
            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
    }
    ////////////////////////////////////
    void loadHelpoffline() throws IOException {

        Runtime rt = Runtime.getRuntime();
        //URL url = getClass().getResource("/bin/Hilfe.html");
        InputStream is = getClass().getResourceAsStream(bundle.getString("help.File"));
        File temp = File.createTempFile(bundle.getString("help.Temp"), ".html");
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
                // Wenn der erste nicht funktioniert, wird der n�chste probiert usw.
            }
            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
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

        JMenuBar mb = new JMenuBar(); // Men�-Leiste
        JMenuItem temp;

        JMenu fileMenu = createMenu(bundle.getString("fileText"), KeyEvent.VK_F, mb);
        JMenu editMenu = createMenu(bundle.getString("editText"), KeyEvent.VK_E, mb);
        JMenu formatMenu = createMenu(bundle.getString("formatText"), KeyEvent.VK_O, mb);
        JMenu viewMenu = createMenu(bundle.getString("viewText"), KeyEvent.VK_V, mb);
        JMenu helpMenu = createMenu(bundle.getString("helpText"), KeyEvent.VK_H, mb);
        JMenu changeMenu = createMenu(bundle.getString("changeText"), KeyEvent.VK_G, mb);

        createMenuItem(bundle.getString("windowNew"), KeyEvent.VK_G, fileMenu, KeyEvent.VK_G, this);
        createMenuItem(bundle.getString("fileNew"), KeyEvent.VK_N, fileMenu, KeyEvent.VK_N, this);
        createMenuItem(bundle.getString("fileOpen"), KeyEvent.VK_O, fileMenu, KeyEvent.VK_O, this);
        createMenuItem(bundle.getString("fileSave"), KeyEvent.VK_S, fileMenu, KeyEvent.VK_S, this);
        createMenuItem(bundle.getString("fileSaveAs"), KeyEvent.VK_A, fileMenu, this);
        fileMenu.addSeparator();
        createMenuItem(bundle.getString("filePageSetup"), KeyEvent.VK_U, fileMenu, this);
        fileMenu.addSeparator();
        temp = createMenuItem(bundle.getString("filePrint"), KeyEvent.VK_P, fileMenu, KeyEvent.VK_P, this);
        temp.setEnabled(false);
        fileMenu.addSeparator();
        createMenuItem(bundle.getString("fileExit"), KeyEvent.VK_X, fileMenu, this);

        temp = createMenuItem(bundle.getString("editUndo"), KeyEvent.VK_U, editMenu, KeyEvent.VK_Z, this);
        temp.setEnabled(false);
        editMenu.addSeparator();
        cutItem = createMenuItem(bundle.getString("editCut"), KeyEvent  .VK_T, editMenu, KeyEvent.VK_X, this);
        copyItem = createMenuItem(bundle.getString("editCopy"), KeyEvent.VK_C, editMenu, KeyEvent.VK_C, this);
        createMenuItem(bundle.getString("editPaste"), KeyEvent.VK_P, editMenu, KeyEvent.VK_V, this);
        deleteItem = createMenuItem(bundle.getString("editDelete"), KeyEvent.VK_L, editMenu, this);
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        editMenu.addSeparator();
        findItem = createMenuItem(bundle.getString("editFind"), KeyEvent.VK_F, editMenu, KeyEvent.VK_F, this);
        findNextItem = createMenuItem(bundle.getString("editFindNext"), KeyEvent.VK_N, editMenu, this);
        findNextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        replaceItem = createMenuItem(bundle.getString("editReplace"), KeyEvent.VK_R, editMenu, KeyEvent.VK_H, this);
        gotoItem = createMenuItem(bundle.getString("editGoTo"), KeyEvent.VK_G, editMenu, KeyEvent.VK_G, this);
        editMenu.addSeparator();
        selectAllItem = createMenuItem(bundle.getString("editSelectAll"), KeyEvent.VK_A, editMenu, KeyEvent.VK_A, this);
        createMenuItem(bundle.getString("editTimeDate"), KeyEvent.VK_D, editMenu, this).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        createMenuItem(bundle.getString("commandoopen"), KeyEvent.VK_E, editMenu, KeyEvent.VK_T, this);

        createCheckBoxMenuItem(bundle.getString("formatWordWrap"), KeyEvent.VK_W, formatMenu, this);

        createMenuItem(bundle.getString("formatFont"), KeyEvent.VK_F, formatMenu, this);
        formatMenu.addSeparator();
        createMenuItem(bundle.getString("formatForeground"), KeyEvent.VK_T, formatMenu, this);
        createMenuItem(bundle.getString("formatBackground"), KeyEvent.VK_P, formatMenu, this);

        createCheckBoxMenuItem(bundle.getString("viewStatusBar"), KeyEvent.VK_S, viewMenu, this).setSelected(true);
/************For Look and Feel, May not work properly on different operating environment***/
        LookAndFeelMenu.createLookAndFeelMenuItem(viewMenu, this.f);

        createMenuItem(bundle.getString("website"), KeyEvent.VK_H, helpMenu, this);
        createMenuItem(bundle.getString("helpHelpTopic"), KeyEvent.VK_H, helpMenu, this).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        createMenuItem(bundle.getString("helpHelpoffline"), KeyEvent.VK_H, helpMenu, this);
        helpMenu.addSeparator();
        createMenuItem(bundle.getString("helpAboutFNotepad"), KeyEvent.VK_A, helpMenu, this);

        createMenuItem(bundle.getString("Lang1"), KeyEvent.VK_G, changeMenu, this);
        createMenuItem(bundle.getString("Lang2"), KeyEvent.VK_Y, changeMenu, this);
        createMenuItem(bundle.getString("Lang3"), KeyEvent.VK_F, changeMenu, this);

        MenuListener editMenuListener = new MenuListener() {
            public void menuSelected(MenuEvent evvvv) {
                if (FNotepad.this.ta.getText().length() == 0) {
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
                if (FNotepad.this.ta.getSelectionStart() == ta.getSelectionEnd()) {
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
        new FNotepad(true, Locale.GERMAN);
    }
}
