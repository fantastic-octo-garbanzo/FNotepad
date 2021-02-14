package NotePads;// Imports
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import FileOperation.FileOperationDE;
import FindDialog.FindDialogDE;
import FontChooser.FontChooserDE;
import LookAndFeelMenu.LookAndFeelMenuDE;



/************************************/

public class FNotepadDE implements ActionListener, MenuConstantsDE {


    public JFrame f;
    public JTextArea ta;
    public JLabel statusBar;

    private String fileName = "Unbenannt";
    private boolean saved = true;
    String applicationName = "FNotepad";

    String searchString, replaceString;
    int lastSearchIndex;

    FileOperationDE fileHandler;
    FontChooserDE fontDialog = null;
    FindDialogDE findReplaceDialog = null;
    JColorChooser bcolorChooser = null;
    JColorChooser fcolorChooser = null;
    JDialog backgroundDialog = null;
    JDialog foregroundDialog = null;
    JMenuItem cutItem, copyItem, deleteItem, findItem, findNextItem, replaceItem, gotoItem, selectAllItem;

    /****************************/
    public static Dimension getScreenDimensionWithoutTaskbarDE(Frame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        int taskBarSize = screenInsets.bottom;
        return new Dimension(width, height - taskBarSize);
    }

    public FNotepadDE(boolean fullscreen) {
        f = new JFrame(fileName + " - " + applicationName);
        ta = new JTextArea(30, 60);
        statusBar = new JLabel("Zeichen 0, W\u00F6rter 0       ||       Zeile 1, Spalte 1  ", JLabel.RIGHT);
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





        fileHandler = new FileOperationDE(this);

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

                            letterCount = text.length();
                            wordCount = text.split("\\s").length;
                            //System.out.println(wordCount+ " " +letterCount);



                        } catch (Exception excp) {
                        }
                        if (ta.getText().length() == 0) {
                            lineNumber = 0;
                            column = 0;
                            wordCount = 0;
                            letterCount = 0;
                        }
                        statusBar.setText("Zeichen " + letterCount + ", W\u00F6rter "+ wordCount + "       ||       Zeile " + (lineNumber + 1) + ", Spalte  " + (column + 1));
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
        else if (cmdText.equals(filePrint))
            JOptionPane.showMessageDialog(
                    FNotepadDE.this.f,
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
            if (FNotepadDE.this.ta.getText().length() == 0)
                return;    // text box have no text
            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialogDE(FNotepadDE.this.ta);
            findReplaceDialog.showDialog(FNotepadDE.this.f, true);//find
        }
////////////////////////////////////
        else if (cmdText.equals(editFindNext)) {
            if (FNotepadDE.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                statusBar.setText("Nichts zu suchen, bitte Finden im Bearbeiten-Men\u00FC zuerst probieren !!!!");
            else
                findReplaceDialog.findNextWithSelection();
        }
////////////////////////////////////
        else if (cmdText.equals(editReplace)) {
            if (FNotepadDE.this.ta.getText().length() == 0)
                return;    // text box have no text

            if (findReplaceDialog == null)
                findReplaceDialog = new FindDialogDE(FNotepadDE.this.ta);
            findReplaceDialog.showDialog(FNotepadDE.this.f, false);//replace
        }
////////////////////////////////////
        else if (cmdText.equals(editGoTo)) {
            if (FNotepadDE.this.ta.getText().length() == 0)
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
                fontDialog = new FontChooserDE(ta.getFont());

            if (fontDialog.showDialog(FNotepadDE.this.f, "Schrift ausw\u00E4hlen"))
                FNotepadDE.this.ta.setFont(fontDialog.createFont());
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
            JOptionPane.showMessageDialog(FNotepadDE.this.f, aboutText, "\u00FCber FNotepad", JOptionPane.INFORMATION_MESSAGE);
        } else
            statusBar.setText("Dieser " + cmdText + " Befehl wird gerade integriert");
    }//action Performed

    ////////////////////////////////////
    void showBackgroundColorDialog() {
        if (bcolorChooser == null)
            bcolorChooser = new JColorChooser();
        if (backgroundDialog == null)
            backgroundDialog = JColorChooser.createDialog
                    (FNotepadDE.this.f,
                            formatBackground,
                            false,
                            bcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepadDE.this.ta.setBackground(bcolorChooser.getColor());
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
                    (FNotepadDE.this.f,
                            formatForeground,
                            false,
                            fcolorChooser,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evvv) {
                                    FNotepadDE.this.ta.setForeground(fcolorChooser.getColor());
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
    void createMenuBar(JFrame f) {
        JMenuBar mb = new JMenuBar(); // Menü-Leiste
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
        LookAndFeelMenuDE.createLookAndFeelMenuItem(viewMenu, this.f);



        temp = createMenuItem(helpHelpTopic, KeyEvent.VK_H, helpMenu, this);
        temp.setEnabled(false);
        helpMenu.addSeparator();
        createMenuItem(helpAboutFNotepad, KeyEvent.VK_A, helpMenu, this);

        MenuListener editMenuListener = new MenuListener() {
            public void menuSelected(MenuEvent evvvv) {
                if (FNotepadDE.this.ta.getText().length() == 0) {
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
                if (FNotepadDE.this.ta.getSelectionStart() == ta.getSelectionEnd()) {
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
        new FNotepadDE(true);
    }
}

/**************************************/
// Menü-Leiste
interface MenuConstantsDE {
    String fileText = "Datei";
    String editText = "Bearbeiten";
    String formatText = "Format";
    String viewText = "Ansicht";
    String helpText = "Hilfe";

    String fileNew = "Neu";
    String fileOpen = "\u00D6ffnen...";
    String fileSave = "Speichern";
    String fileSaveAs = "Speichern Als...";
    String filePageSetup = "Seiteneinstellungen...";
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

    String formatWordWrap = "Zeilenumbruch";
    String formatFont = "Schrift...";
    String formatForeground = "Textfarbe...";
    String formatBackground = "Hintergrundfarbe...";

    String viewStatusBar = "Statusleiste";

    String helpHelpTopic = "Hilfe";
    String helpAboutFNotepad = "\u00DCber FNotepad";

    String aboutText =

            "<html><big>FNotepad</big><hr><hr>"
                    + "<p align=right>Von fantastic-octo-garbanzo!"
                    + "<hr><p align=left>Mit OpenJDK15 compiliert.<br><br>"
                    + "<strong>Danke f\u00FCr das Benutzen von FNotepad!</strong><br>"
                    + "Bei Bugs und Ideen gerne ein Issue stellen auf<p align=center>"
                    + "<hr><em><big><a href=\\\"https://github.com/fantastic-octo-garbanzo/FNotepad\\\">https://github.com/fantastic-octo-garbanzo/FNotepad</a></big></em><hr><html>";
}
