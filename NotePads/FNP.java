package NotePads;
// Imports
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.*;

import FileOperation.FileOperation;
import FileOperation.FileOperation;
import FindDialog.FindDialog;
import FileFilter.FileFilterFNP;
import FontChooser.FontChooser;

import LookAndFeelMenu.LookAndFeelMenu;

public class FNP implements ActionListener {

    String th_is;
    String command = " command is yet to be implemented";
    String aboutText2 = "About FNotepad!";
    String fontChoose = "Choose a font";
    String searchText = "Nothing to search for, use Find option of Edit Menu first !!!!";
    String searchLine = "Enter Line Number:";
    String letters = "Letters ";
    String words = ", Words ";
    String line = "       ||       Line ";
    String column = ", Column ";
    String countInit;
    String defaultFileName = "Untitled";
    String defaultApplicationName = "FNotepad";
    String claf = "Change Look and Feel";
    String matchCaseVar = "Match case";
    String up = "Up";
    String down = "Down";
    String direction = "Direction";
    String findText = "Find Next";
    String replaceText = "Replace";
    String replaceAllText = "Replace All";
    String cancelText = "Cancel";



    String fileText = "File";
    String editText = "Edit";
    String formatText = "Format";
    String viewText = "View";
    String helpText = "Help";

    String fileNew = "New";
    String fileOpen = "Open...";
    String fileSave = "Save";
    String fileSaveAs = "Save As...";
    String filePageSetup = "Page Setup...";
    String filePrint = "Print";
    String fileExit = "Exit";

    String editUndo = "Undo";
    String editCut = "Cut";
    String editCopy = "Copy";
    String editPaste = "Paste";
    String editDelete = "Delete";
    String editFind = "Find...";
    String editFindNext = "Find Next";
    String editReplace = "Replace";
    String editGoTo = "Go To...";
    String editSelectAll = "Select All";
    String editTimeDate = "Time/Date";

    String formatWordWrap = "Word Wrap";
    String formatFont = "Font...";
    String formatForeground = "Set Text color...";
    String formatBackground = "Set Pad color...";

    String viewStatusBar = "Status Bar";

    String helpHelpTopic = "Help Topic";
    String helpAboutFNotepad = "About FNotepad";

    String aboutText =
            "<html><big>FNotepad</big><hr><hr>"
                    + "<p align=right>From fantastic-octo-garbanzo!"
                    + "<hr><p align=left>Compiled by OpenJDK15.<br><br>"
                    + "<strong>Thank you for using FNotepad!</strong><br>"
                    + "Please make an issue on<p align=center>"
                    + "<hr><em><big>https://github.com/fantastic-octo-garbanzo/FNotepad</big></em><hr><html>";




    public JFrame f;
    public JTextArea ta;
    public JLabel statusBar;

    private String fileName = defaultFileName;
    private boolean saved = true;
    String applicationName = defaultApplicationName;

    String searchString, replaceString;
    int lastSearchIndex;

    FileOperation fileHandler;
    FontChooser fontDialog = null;
    FindDialog findReplaceDialog = null;
    JColorChooser bcolorChooser = null;
    JColorChooser fcolorChooser = null;
    JDialog backgroundDialog = null;
    JDialog foregroundDialog = null;
    JMenuItem cutItem, copyItem, deleteItem, findItem, findNextItem, replaceItem, gotoItem, selectAllItem;




    public FNP(String th_is, String command, String aboutText2, String fontChoose, String searchText, String searchLine,
               String letters, String words, String line, String column, String countInit, String defaultFileName,
               String defaultApplicationName, String claf, String matchCaseVar, String up, String down, String direction,
               String findText, String replaceText, String replaceAllText, String cancelText, String fileText, String editText,
               String formatText, String viewText, String helpText, String fileNew, String fileOpen, String fileSave,
               String fileSaveAs, String filePageSetup, String filePrint, String fileExit, String editUndo, String editCut,
               String editCopy, String editPaste, String editDelete, String editFind, String editFindNext, String editReplace,
               String editGoTo, String editSelectAll, String editTimeDate, String formatWordWrap, String formatFont,
               String formatForeground, String formatBackground, String viewStatusBar, String helpHelpTopic, String helpAboutFNotepad, String aboutText) {
        System.out.println("Here");
        this.th_is = th_is;
        this.command = command;
        this.aboutText2 = aboutText2;
        this.fontChoose = fontChoose;
        this.searchText = searchText;
        this.searchLine = searchLine;
        this.letters = letters;
        this.words = words;
        this.line = line;
        this.column = column;
        this.countInit = countInit;
        this.defaultFileName = defaultFileName;
        this.defaultApplicationName = defaultApplicationName;
        this.claf = claf;
        this.matchCaseVar = matchCaseVar;
        this.up = up;
        this.down = down;
        this.direction = direction;
        this.findText = findText;
        this.replaceText = replaceText;
        this.replaceAllText = replaceAllText;
        this.cancelText = cancelText;
        this.fileText = fileText;
        this.editText = editText;
        this.formatText = formatText;
        this.viewText = viewText;
        this.helpText = helpText;
        this.fileNew = fileNew;
        this.fileOpen = fileOpen;
        this.fileSave = fileSave;
        this.fileSaveAs = fileSaveAs;
        this.filePageSetup = filePageSetup;
        this.filePrint = filePrint;
        this.fileExit = fileExit;
        this.editUndo = editUndo;
        this.editCut = editCut;
        this.editCopy = editCopy;
        this.editPaste = editPaste;
        this.editDelete = editDelete;
        this.editFind = editFind;
        this.editFindNext = editFindNext;
        this.editReplace = editReplace;
        this.editGoTo = editGoTo;
        this.editSelectAll = editSelectAll;
        this.editTimeDate = editTimeDate;
        this.formatWordWrap = formatWordWrap;
        this.formatFont = formatFont;
        this.formatForeground = formatForeground;
        this.formatBackground = formatBackground;
        this.viewStatusBar = viewStatusBar;
        this.helpHelpTopic = helpHelpTopic;
        this.helpAboutFNotepad = helpAboutFNotepad;
        this.aboutText = aboutText;

        System.out.println("Here2");
        f = new JFrame(fileName + " - " + applicationName);
        ta = new JTextArea(30, 60);
        statusBar = new JLabel(countInit, JLabel.RIGHT);
        f.add(new JScrollPane(ta), BorderLayout.CENTER);
        f.add(statusBar, BorderLayout.SOUTH);
        f.add(new JLabel("  "), BorderLayout.EAST);
        f.add(new JLabel("  "), BorderLayout.WEST);
        createMenuBar(f);



        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        if(!true){f.setSize(650, 600);}

        fileHandler = new FileOperation(this);

/////////////////////

        ta.addCaretListener(
                new CaretListener() {
                    public void caretUpdate(CaretEvent e) {
                        int lineNumber = 0, columnNumber = 0, pos = 0, wordCount = 0, letterCount = 0;

                        try {
                            pos = ta.getCaretPosition();
                            lineNumber = ta.getLineOfOffset(pos);
                            columnNumber = pos - ta.getLineStartOffset(lineNumber);
                            String text = ta.getText();

                            letterCount = text.length();
                            wordCount = text.split("\\s").length;
                            if (!FileOperation.isSave()){
                                f.setTitle(FileOperation.getFileName() + "* - " + applicationName);
                            } else {
                                f.setTitle(FileOperation.getFileName() + " - " + applicationName);
                            }
                            //System.out.println(wordCount+ " " +letterCount);



                        } catch (Exception excp) {
                        }
                        if (ta.getText().length() == 0) {
                            lineNumber = 0;
                            columnNumber = 0;
                            wordCount = 0;
                            letterCount = 0;
                        }
                        statusBar.setText(letters + letterCount + words + wordCount + line + (lineNumber + 1) + column + (columnNumber + 1));
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

    }

    /**
    public FNP(boolean fullscreen) {
        f = new JFrame(fileName + " - " + applicationName);
        ta = new JTextArea(30, 60);
        statusBar = new JLabel(countInit, JLabel.RIGHT);
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

        fileHandler = new FileOperation(this);

/////////////////////

        ta.addCaretListener(
                new CaretListener() {
                    public void caretUpdate(CaretEvent e) {
                        int lineNumber = 0, columnNumber = 0, pos = 0, wordCount = 0, letterCount = 0;

                        try {
                            pos = ta.getCaretPosition();
                            lineNumber = ta.getLineOfOffset(pos);
                            columnNumber = pos - ta.getLineStartOffset(lineNumber);
                            String text = ta.getText();

                            letterCount = text.length();
                            wordCount = text.split("\\s").length;
                            if (!FileOperation.isSave()){
                                f.setTitle(FileOperation.getFileName() + "* - " + applicationName);
                            } else {
                                f.setTitle(FileOperation.getFileName() + " - " + applicationName);
                            }
                            //System.out.println(wordCount+ " " +letterCount);



                        } catch (Exception excp) {
                        }
                        if (ta.getText().length() == 0) {
                            lineNumber = 0;
                            columnNumber = 0;
                            wordCount = 0;
                            letterCount = 0;
                        }
                        statusBar.setText(letters + letterCount + words + wordCount + line + (lineNumber + 1) + column + (columnNumber + 1));
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
*/
    ////////////////////////////////////
    public void goTo() {
        int lineNumber = 0;
        try {
            lineNumber = ta.getLineOfOffset(ta.getCaretPosition()) + 1;
            String tempStr = JOptionPane.showInputDialog(f, searchLine, "" + lineNumber);
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
        if (cmdText.equals(fileNew))
            fileHandler.newFile();
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
            if (FNP.this.ta.getText().length() == 0)
                return;    // text box have no text
            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialog(FNP.this.ta);
            findReplaceDialog.showDialog(FNP.this.f, true);//find
        }
////////////////////////////////////
        else if (cmdText.equals(editFindNext)) {
            if (FNP.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                statusBar.setText(searchText);
            else
                findReplaceDialog.findNextWithSelection();
        }
////////////////////////////////////
        else if (cmdText.equals(editReplace)) {
            if (FNP.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialog(FNP.this.ta);
            findReplaceDialog.showDialog(FNP.this.f, false);//replace
        }
////////////////////////////////////
        else if (cmdText.equals(editGoTo)) {
            if (FNP.this.ta.getText().length() == 0)
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
                fontDialog = new FontChooser(ta.getFont());

            if (fontDialog.showDialog(FNP.this.f, fontChoose))
                FNP.this.ta.setFont(fontDialog.createFont());
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
        else if (cmdText.equals(helpAboutFNotepad)) {
            JOptionPane.showMessageDialog(FNP.this.f, aboutText, aboutText2, JOptionPane.INFORMATION_MESSAGE);
        } else
            statusBar.setText(th_is + cmdText + command);
    }//action Performed

    ////////////////////////////////////
    public void showBackgroundColorDialog() {
        if (bcolorChooser == null)
            bcolorChooser = new JColorChooser();
        if (backgroundDialog == null)
            backgroundDialog = JColorChooser.createDialog
                    (FNP.this.f,
                            formatBackground,
                            false,
                            bcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNP.this.ta.setBackground(bcolorChooser.getColor());
                                }
                            },
                            null);

        backgroundDialog.setVisible(true);
    }

    ////////////////////////////////////
    public void showForegroundColorDialog() {
        if (fcolorChooser == null)
            fcolorChooser = new JColorChooser();
        if (foregroundDialog == null)
            foregroundDialog = JColorChooser.createDialog
                    (FNP.this.f,
                            formatForeground,
                            false,
                            fcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNP.this.ta.setForeground(fcolorChooser.getColor());
                                }
                            },
                            null);

        foregroundDialog.setVisible(true);
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
    public void createMenuBar(JFrame f) {
        JMenuBar mb = new JMenuBar();
        JMenuItem temp;

        JMenu fileMenu = createMenu(fileText, KeyEvent.VK_F, mb);
        JMenu editMenu = createMenu(editText, KeyEvent.VK_E, mb);
        JMenu formatMenu = createMenu(formatText, KeyEvent.VK_O, mb);
        JMenu viewMenu = createMenu(viewText, KeyEvent.VK_V, mb);
        JMenu helpMenu = createMenu(helpText, KeyEvent.VK_H, mb);

        createMenuItem(fileNew, KeyEvent.VK_N, fileMenu, KeyEvent.VK_N, this);
        createMenuItem(fileOpen, KeyEvent.VK_O, fileMenu, KeyEvent.VK_O, this);
        createMenuItem(fileSave, KeyEvent.VK_S, fileMenu, KeyEvent.VK_S, this);
        createMenuItem(fileSaveAs, KeyEvent.VK_A, fileMenu, this);
        fileMenu.addSeparator();
        temp = createMenuItem(filePageSetup, KeyEvent.VK_U, fileMenu, this);
        temp.setEnabled(false);
        createMenuItem(filePrint, KeyEvent.VK_P, fileMenu, KeyEvent.VK_P, this);
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

        createCheckBoxMenuItem(formatWordWrap, KeyEvent.VK_W, formatMenu, this);

        createMenuItem(formatFont, KeyEvent.VK_F, formatMenu, this);
        formatMenu.addSeparator();
        createMenuItem(formatForeground, KeyEvent.VK_T, formatMenu, this);
        createMenuItem(formatBackground, KeyEvent.VK_P, formatMenu, this);

        createCheckBoxMenuItem(viewStatusBar, KeyEvent.VK_S, viewMenu, this).setSelected(true);
/************For Look and Feel, May not work properly on different operating environment***/
        LookAndFeelMenu.createLookAndFeelMenuItem(viewMenu, this.f);


        temp = createMenuItem(helpHelpTopic, KeyEvent.VK_H, helpMenu, this);
        temp.setEnabled(false);
        helpMenu.addSeparator();
        createMenuItem(helpAboutFNotepad, KeyEvent.VK_A, helpMenu, this);

        MenuListener editMenuListener = new MenuListener() {
            public void menuSelected(MenuEvent evvvv) {
                if (FNP.this.ta.getText().length() == 0) {
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
                if (FNP.this.ta.getSelectionStart() == ta.getSelectionEnd()) {
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

    }
}

/**************************************/
// Menu
