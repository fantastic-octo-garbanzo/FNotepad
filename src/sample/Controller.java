package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.ResourceBundle;

import static sample.Main.loader;
import static sample.Main.window;


public class Controller {
    public static Locale currentLocale;

    public MenuItem lang1, lang2, lang3;

    public MenuItem helpWebsite, helpHelpOffline, helpHelpTopic, helpAboutFNotepad;

    public MenuItem fileNew, fileOpen, fileSave, fileSaveAs, filepageSetup, filePrint, fileExit;


    public TabPane tabPane;

    public MenuItem getLang1() {
        return lang1;
    }
    public MenuItem getLang2() {
        return lang2;
    }
    public MenuItem getLang3() {
        return lang3;
    }


    public void changeLang1() throws IOException {

        if(getLang1().getText().equals("Deutsch")){
            currentLocale = new Locale("de");
        }
        if(getLang1().getText().equals("English")){
            currentLocale = new Locale("en");
        }
        if(getLang1().getText().equals("Italiano")){
            currentLocale = new Locale("it");
        }
        if(getLang1().getText().equals("Fran\u00E7ais")){
            currentLocale = new Locale("fr");
        }
        loader = new FXMLLoader(getClass().getResource("FNotepad.fxml"), ResourceBundle.getBundle("sample.Main", currentLocale));
        window.setScene(new Scene(loader.load()));
    }
    public void changeLang2() throws IOException {
        if(getLang2().getText().equals("Deutsch")){
            currentLocale = new Locale("de");
        }
        if(getLang2().getText().equals("English")){
            currentLocale = new Locale("en");
        }
        if(getLang2().getText().equals("Italiano")){
            currentLocale = new Locale("it");
        }
        if(getLang2().getText().equals("Fran\u00E7ais")){
            currentLocale = new Locale("fr");
        }
        loader = new FXMLLoader(getClass().getResource("FNotepad.fxml"), ResourceBundle.getBundle("sample.Main", currentLocale));
        window.setScene(new Scene(loader.load()));
    }
    public void changeLang3() throws IOException {
        if(getLang3().getText().equals("Deutsch")){
            currentLocale = new Locale("de");
        }
        if(getLang3().getText().equals("English")){
            currentLocale = new Locale("en");
        }
        if(getLang3().getText().equals("Italiano")){
            currentLocale = new Locale("it");
        }
        if(getLang3().getText().equals("Fran\u00E7ais")){
            currentLocale = new Locale("fr");
        }
        loader = new FXMLLoader(getClass().getResource("FNotepad.fxml"), ResourceBundle.getBundle("sample.Main", currentLocale));
        window.setScene(new Scene(loader.load()));
    }

    public void loadHelp() throws IOException {

        Runtime rt = Runtime.getRuntime();
        String url = ResourceBundle.getBundle("sample.Main", currentLocale).getString("help.URL");
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
    public void loadwebsite() throws IOException {

        Runtime rt = Runtime.getRuntime();
        String url = ResourceBundle.getBundle("sample.Main", currentLocale).getString("website.URL");
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
    public void loadHelpoffline() throws IOException {

        Runtime rt = Runtime.getRuntime();
        //URL url = getClass().getResource("/bin/Hilfe.html");
        InputStream is = getClass().getResourceAsStream(ResourceBundle.getBundle("sample.Main", currentLocale).getString("help.File"));
        File temp = File.createTempFile(ResourceBundle.getBundle("sample.Main", currentLocale).getString("help.Temp"), ".html");
        temp.deleteOnExit();
        assert is != null;
        try {
            Files.copy(is, Paths.get(temp.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {
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
    public void loadHelpAboutFNotepad(){
        AboutBox.display(ResourceBundle.getBundle("sample.Main", currentLocale).getString("helpAboutFNotepad"),
                ResourceBundle.getBundle("sample.Main", currentLocale).getString("aboutText1"),
                ResourceBundle.getBundle("sample.Main", currentLocale).getString("aboutText2"),
                ResourceBundle.getBundle("sample.Main", currentLocale).getString("aboutText3"),
                ResourceBundle.getBundle("sample.Main", currentLocale).getString("aboutText4"),
                ResourceBundle.getBundle("sample.Main", currentLocale).getString("aboutText5"));
    }

    public void newTab(){
        TextArea ta = new TextArea();
        Tab tab = new Tab(ResourceBundle.getBundle("sample.Main", currentLocale).getString("fileName")+" "+tabPane.getTabs().size(), ta);
        tabPane.getTabs().addAll(tab);
    }

    public void exit(){
        Main.close();
    }


    public void updateTitle(Event event) {
        event.consume();
        window.setTitle(tabPane.getSelectionModel().getSelectedItem().getText() + " - Fnotepad");
    }
}
