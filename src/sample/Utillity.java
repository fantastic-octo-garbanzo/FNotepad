package sample;

import javafx.scene.control.*;

public class Utillity {
    public static MenuBar createMenuBar(){
        MenuBar mb;
        mb = new MenuBar();
        MenuItem fileMenuItem1 = new MenuItem("New Window");
        MenuItem fileMenuItem2 = new MenuItem("New File");
        MenuItem fileMenuItem3 = new MenuItem("Open File");
        MenuItem fileMenuItem4 = new MenuItem("Save File");
        MenuItem fileMenuItem5 = new MenuItem("Save File As…");
        SeparatorMenuItem filesp1 = new SeparatorMenuItem();
        MenuItem fileMenuItem6 = new MenuItem("Page Setup");
        SeparatorMenuItem filesp2 = new SeparatorMenuItem();
        MenuItem fileMenuItem7 = new MenuItem("Print");
        SeparatorMenuItem filesp3 = new SeparatorMenuItem();
        MenuItem fileMenuItem8 = new MenuItem("Exit");
        Menu fileMenu = new Menu("File",
                mb, fileMenuItem1, fileMenuItem2, fileMenuItem3, fileMenuItem4, fileMenuItem5, filesp1, fileMenuItem6, filesp2,
                fileMenuItem7, filesp3, fileMenuItem8);

        MenuItem editMenuItem1 = new MenuItem("Undo");
        SeparatorMenuItem editsp1 = new SeparatorMenuItem();
        MenuItem editMenuItem2 = new MenuItem("Cut");
        MenuItem editMenuItem3 = new MenuItem("Copy");
        MenuItem editMenuItem4 = new MenuItem("Paste");
        MenuItem editMenuItem5 = new MenuItem("Delete");
        SeparatorMenuItem editsp2 = new SeparatorMenuItem();
        MenuItem editMenuItem6 = new MenuItem("Find");
        MenuItem editMenuItem7 = new MenuItem("Find Next");
        MenuItem editMenuItem8 = new MenuItem("Replace");
        MenuItem editMenuItem9 = new MenuItem("Go to");
        SeparatorMenuItem editsp3 = new SeparatorMenuItem();
        MenuItem editMenuItem10 = new MenuItem("Select All");
        MenuItem editMenuItem11 = new MenuItem("Time/Date");
        MenuItem editMenuItem12 = new MenuItem("Open Terminal");
        Menu editMenu = new Menu("Edit",
                mb, editMenuItem1, editsp1, editMenuItem2, editMenuItem3, editMenuItem4, editMenuItem5, editsp2, editMenuItem6,
                editMenuItem7, editMenuItem8, editMenuItem9, editsp3, editMenuItem10, editMenuItem11, editMenuItem12);

        CheckMenuItem formatCheckMenuItem1 = new CheckMenuItem("Word Wrap");
        SeparatorMenuItem formatsp1 = new SeparatorMenuItem();
        MenuItem formatMenuItem2 = new MenuItem("Set Text Color");
        MenuItem formatMenuItem3 = new MenuItem("Set Pad Color");
        Menu formatMenu = new Menu("Format",
                mb, formatCheckMenuItem1, formatsp1, formatMenuItem2, formatMenuItem3);

        CheckMenuItem viewCheckMenuItem1 = new CheckMenuItem("Status Bar");
        MenuItem lookandFeelMenuItem1 = new MenuItem("Look 1");
        Menu lookAndFeelMenu = new Menu("Change Look and Feel", mb, lookandFeelMenuItem1);
        Menu viewMenu = new Menu("View", mb, viewCheckMenuItem1, lookAndFeelMenu);

        MenuItem helpMenuItem1 = new MenuItem("View Website");
        MenuItem helpMenuItem2 = new MenuItem("Offline Help");
        MenuItem helpMenuItem3 = new MenuItem("Online Help");
        SeparatorMenuItem helpsp1 = new SeparatorMenuItem();
        MenuItem helpMenuItem4 = new MenuItem("About FNotepad");
        Menu helpMenu = new Menu("Help",
                mb, helpMenuItem1, helpMenuItem2, helpMenuItem3, helpsp1, helpMenuItem4);

        MenuItem languageMenuItem1 = new MenuItem("Deutsch");
        MenuItem languageMenuItem2 = new MenuItem("Italiano");
        MenuItem languageMenuItem3 = new MenuItem("Francais");
        Menu languageMenu = new Menu("Language",
                mb, languageMenuItem1, languageMenuItem2, languageMenuItem3);

        return new MenuBar(fileMenu, editMenu, formatMenu, viewMenu, helpMenu, languageMenu);

    }


    public static TabPane createTabPane(){
        TextArea ta1 = new TextArea();
        ta1.setPrefHeight(910.0);
        ta1.setPrefHeight(1920.0);
        Tab t1 = new Tab("Untitled", ta1);

        TextArea ta2 = new TextArea();
        ta2.setPrefHeight(910.0);
        ta2.setPrefHeight(1920.0);
        Tab t2 = new Tab("Untitled 1", ta2);

        TabPane tp = new TabPane();
        tp.getTabs().addAll(t1, t2);
        tp.setPrefHeight(1020.0);
        tp.setPrefWidth(1920.0);
        tp.setVisible(true);

        return new TabPane();
    }
}
